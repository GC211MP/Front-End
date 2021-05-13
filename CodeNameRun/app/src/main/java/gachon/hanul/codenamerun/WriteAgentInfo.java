package gachon.hanul.codenamerun;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.AsyncNotedAppOp;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
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
}