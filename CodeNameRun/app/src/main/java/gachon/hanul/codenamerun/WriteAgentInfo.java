package gachon.hanul.codenamerun;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.AlertDialog;
import android.app.AsyncNotedAppOp;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import gachon.hanul.codenamerun.api.SqliteDto;
import gachon.hanul.codenamerun.api.StoreManager;

public class WriteAgentInfo extends AppCompatActivity {

    //TODO name height weight 를 입력하고 확인 버튼을 누르면, 유저 정보가 update 된다.
    Button okButton;

    StoreManager store;

    String[] REQUIRED_PERMISSIONS = {Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION};
    private static final int GPS_ENABLE_REQUEST_CODE = 2001;
    private static final int PERMISSIONS_REQUEST_CODE = 100;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write_agent_info);

        store = StoreManager.getInstance(getApplicationContext());

        TextView lblUserId = findViewById(R.id.lbl_user_id);
        EditText agentPassword = findViewById(R.id.write_password);
        EditText agentHeight = findViewById(R.id.write_height);
        EditText agentName = findViewById(R.id.write_name);
        EditText agentWeight = findViewById(R.id.write_weight);


        /* tts(korean) download */
        showDialogForTTSDownLoad();

        // gps permission part
        if (checkLocationServicesStatus()) {
            Log.d("permission", "point gps permission");
            checkRunTimePermission();
        } else {
            showDialogForLocationServiceSetting();
            Log.d("permission", "point gps permission2");
        }

        String userId = generateRandomString(6);

        lblUserId.setText("Your ID: " + userId);


        okButton = findViewById(R.id.okButton);
        okButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){

                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        boolean result = false;
                        try{

                            String userpw = agentPassword.getText().toString();
                            String username = agentName.getText().toString();
                            int userht = Integer.parseInt(agentHeight.getText().toString());
                            int userwt = Integer.parseInt(agentWeight.getText().toString());

                            result = store.enrollUser(new SqliteDto(userId, userpw, username, userht, userwt, "sex"));

                            System.out.println(userId);
                            System.out.println(userpw);
                            System.out.println(username);
                            System.out.println(userht);
                            System.out.println(userwt);
                            System.out.println("result" + result);

                        } catch (Exception e){
                            Toast.makeText(getApplicationContext(), "계정을 생성할 수 없습니다!", Toast.LENGTH_SHORT).show();
                            return;
                        }

                        if(result == false)
                            Toast.makeText(getApplicationContext(), "계정을 생성할 수 없습니다!", Toast.LENGTH_SHORT).show();
                        else {
                            Intent intent = new Intent(getApplicationContext(), Home.class);
                            startActivity(intent);
                        }
                    }
                }).start();
                finish();
            }
        });
    }


    private String generateRandomString(int length){

        String result = "";

        for(int i = 0; i < length; i++){
            double dValue = Math.random();
            result += (char)(dValue * 26 + 65) + "";
        }

        return result;
    }


    /* TTS 한국어 다운로드 안내 팝업
    * 한번만 실행하면 되기 때문에 회원가입 창에 넣었습니다 */
    private void showDialogForTTSDownLoad() {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("음성 서비스 사용을 위한 다운로드");
        builder.setMessage("앱을 사용하기 위해서는 한국어 TTS 다운로드가 필요합니다.\n"
                + "TTS 음성 데이터를 다운로드 하시겠습니까?");
        builder.setCancelable(true);
        builder.setPositiveButton("다운로드", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                Intent callTTSDownloadIntent = new Intent();
                callTTSDownloadIntent.setPackage("com.google.android.tts");
                callTTSDownloadIntent.setAction(TextToSpeech.Engine.ACTION_INSTALL_TTS_DATA);
                callTTSDownloadIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(callTTSDownloadIntent);
                // startActivityForResult(callGPSSettingIntent, GPS_ENABLE_REQUEST_CODE);
            }
        });
        builder.setNegativeButton("취소", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();
            }
        });
        builder.create().show();
    }

    //--------------------------------------------------------------Start <gps permission>----------------------------------------------------------------------
    /*
     * TODO: 다혜가 퍼미션 부분에서 뻑난다고 말했음 -> 고쳐야한다
     * 뻑날때가 있고 뻑 안날때가 있고
     *
     * GPS 퍼미션 부분은 아래의 출처에서 가져왔습니다.
     * Cdde from https://webnautes.tistory.com/1315
     */


    /* if gps service is not available user have to allow to use gps
     * this function show permission pop up
     */
    private void showDialogForLocationServiceSetting() {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("위치 서비스 비활성화");
        builder.setMessage("앱을 사용하기 위해서는 위치 서비스가 필요합니다.\n"
                + "위치 설정을 수정하실래요?");
        builder.setCancelable(true);
        builder.setPositiveButton("설정", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                Intent callGPSSettingIntent
                        = new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivityForResult(callGPSSettingIntent, GPS_ENABLE_REQUEST_CODE);
            }
        });
        builder.setNegativeButton("취소", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();
            }
        });
        builder.create().show();
    }

    public boolean checkLocationServicesStatus() {
        LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
                || locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
    }

    void checkRunTimePermission() {

        //런타임 퍼미션 처리
        // 1. 위치 퍼미션을 가지고 있는지 체크합니다.
        int hasFineLocationPermission = ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION);
        int hasCoarseLocationPermission = ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_COARSE_LOCATION);


        if (hasFineLocationPermission == PackageManager.PERMISSION_GRANTED &&
                hasCoarseLocationPermission == PackageManager.PERMISSION_GRANTED) {

            // 2. 이미 퍼미션을 가지고 있다면
            // ( 안드로이드 6.0 이하 버전은 런타임 퍼미션이 필요없기 때문에 이미 허용된 걸로 인식합니다.)


            // 3.  위치 값을 가져올 수 있음


        } else {  //2. 퍼미션 요청을 허용한 적이 없다면 퍼미션 요청이 필요합니다. 2가지 경우(3-1, 4-1)가 있습니다.

            // 3-1. 사용자가 퍼미션 거부를 한 적이 있는 경우에는
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, REQUIRED_PERMISSIONS[0])) {

                // 3-2. 요청을 진행하기 전에 사용자가에게 퍼미션이 필요한 이유를 설명해줄 필요가 있습니다.
                Toast.makeText(this, "이 앱을 실행하려면 위치 접근 권한이 필요합니다.", Toast.LENGTH_LONG).show();
                // 3-3. 사용자게에 퍼미션 요청을 합니다. 요청 결과는 onRequestPermissionResult에서 수신됩니다.
                ActivityCompat.requestPermissions(this, REQUIRED_PERMISSIONS,
                        PERMISSIONS_REQUEST_CODE);


            } else {
                // 4-1. 사용자가 퍼미션 거부를 한 적이 없는 경우에는 퍼미션 요청을 바로 합니다.
                // 요청 결과는 onRequestPermissionResult에서 수신됩니다.
                ActivityCompat.requestPermissions(this, REQUIRED_PERMISSIONS,
                        PERMISSIONS_REQUEST_CODE);
            }
        }
    }


    //--------------------------------------------------------------end:<gps permission>---------------------------------------------------------------------
}