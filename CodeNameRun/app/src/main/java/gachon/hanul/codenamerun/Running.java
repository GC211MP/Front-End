package gachon.hanul.codenamerun;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.media.MediaPlayer;
import android.os.Bundle;

import android.os.Handler;
import android.speech.tts.UtteranceProgressListener;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.speech.tts.TextToSpeech;
import android.widget.Toast;


import com.google.android.gms.maps.SupportMapFragment;



import java.util.Locale;

import static android.speech.tts.TextToSpeech.ERROR;

public class Running extends AppCompatActivity implements LocationListener {

    // for gps service
    private static final int GPS_ENABLE_REQUEST_CODE = 2001;
    private static final int PERMISSIONS_REQUEST_CODE = 100;
    String[] REQUIRED_PERMISSIONS = {Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION};
    HelpGPS helpGPS;
    HelpMap helpMap;
    private TextToSpeech tts;

    /* variables */
    ImageView TopSecret1;
    ImageView TopSecret2;
    ImageView TopSecret3;
    ImageView TopSecret4;
    ImageView TopSecret5;
    TextView stageNameText;
    String stageName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_running);

        /* data from intent */
        stageName = getIntent().getStringExtra("stageName");

        // gps permission part
        if (checkLocationServicesStatus()) {
            checkRunTimePermission();
        } else showDialogForLocationServiceSetting();

        // help
        helpGPS = new HelpGPS(this);
        helpMap = new HelpMap(this,helpGPS.getLocation());
        helpGPS.setHelpMap(helpMap);

        // 지도에 경로를 표시하기 위해서 지도를 다룰 함수를 콜백하는 겁니다
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(helpMap);


        /* find view by id */
        TopSecret1 = findViewById(R.id.TopSecret1);
        TopSecret2 = findViewById(R.id.TopSecret2);
        TopSecret3 = findViewById(R.id.TopSecret3);
        TopSecret4 = findViewById(R.id.TopSecret4);
        TopSecret5 = findViewById(R.id.TopSecret5);

        stageNameText = findViewById(R.id.alertStage);

        /* change values */
        TopSecret1.setVisibility(View.INVISIBLE);
        TopSecret2.setVisibility(View.INVISIBLE);
        TopSecret3.setVisibility(View.INVISIBLE);
        TopSecret4.setVisibility(View.INVISIBLE);
        TopSecret5.setVisibility(View.INVISIBLE);

        stageNameText.setText(stageName);

        /* set TTS */
        tts = new TextToSpeech(this, status -> {
            if(status != ERROR) {
                tts.setLanguage(Locale.KOREAN); // 언어
                tts.setPitch(2.0f); // 톤 (높낮이, default= 1.0f)
                tts.setSpeechRate(1.5f); // 속도 (default= 1.0f)
            }
        });
        tts.setOnUtteranceProgressListener(new UtteranceProgressListener() {
            @Override
            public void onStart(final String utteranceId) {
                Running.this.runOnUiThread(new Runnable() {
                    public void run() {
                    }
                });
            }

            @Override

            public void onError(String utteranceId) { }

            @Override

            public void onDone(String utteranceId) {
                Running.this.runOnUiThread(new Runnable() {
                    public void run() {
                        Toast.makeText(Running.this, utteranceId, Toast.LENGTH_SHORT).show();
                    }
                });
                Log.d("listener", "start: " + utteranceId);
            }
        });

        /* if 시간이 1000(?) 미만일때는 사운드가 나오지 않음*/
        if (stageName.equals("Prologue")) {
            new Handler().postDelayed(() -> tts.speak(getResources().getString(R.string.prologue_1), TextToSpeech.QUEUE_FLUSH, null, "prologue_1"), 2000);
        }

        if (stageName.equals("Stage1")) {
            /* tts */
            new Handler().postDelayed(() -> {
                tts.speak(getResources().getString(R.string.Stage1_walk1_1), TextToSpeech.QUEUE_ADD, null, "Stage1_walk1_1");
                MainActivity.mediaPlayer = MediaPlayer.create(Running.this, R.raw.footstep);
                MainActivity.mediaPlayer.start();
                MainActivity.mediaPlayer.setOnCompletionListener(mp -> {
                    MainActivity.mediaPlayer.release();
                    MainActivity.mediaPlayer = null;
                });
            }, 2000);
            new Handler().postDelayed(() -> {
                tts.speak(getResources().getString(R.string.Stage1_walk1_2), TextToSpeech.QUEUE_ADD, null, "Stage1_walk1_2");
            }, 4000);
            new Handler().postDelayed(() -> {
                tts.speak(getResources().getString(R.string.Stage1_walk1_3), TextToSpeech.QUEUE_ADD, null, "Stage1_walk1_3");
            }, 6000);
            new Handler().postDelayed(() -> {
                tts.speak(getResources().getString(R.string.Stage1_walk1_4), TextToSpeech.QUEUE_ADD, null, "Stage1_walk1_4");
            }, 8000);
            new Handler().postDelayed(() -> {
                tts.speak(getResources().getString(R.string.Stage1_walk1_5), TextToSpeech.QUEUE_ADD, null, "Stage1_walk1_5");
            }, 10000);
            new Handler().postDelayed(() -> {
                tts.speak(getResources().getString(R.string.Stage1_walk1_6), TextToSpeech.QUEUE_ADD, null, "Stage1_walk1_6");
            }, 12000);

            /* 5번의(4분 ->test로 4초 간격 해둠) 인터벌 표시 마크 보이기 */
            new Handler().postDelayed(() -> TopSecret1.setVisibility(View.VISIBLE), 4000);
            new Handler().postDelayed(() -> TopSecret2.setVisibility(View.VISIBLE), 8000);
            new Handler().postDelayed(() -> TopSecret3.setVisibility(View.VISIBLE), 12000);
            new Handler().postDelayed(() -> TopSecret4.setVisibility(View.VISIBLE), 16000);
            new Handler().postDelayed(() -> TopSecret5.setVisibility(View.VISIBLE), 20000);
        }

        if (stageName.equals("Stage2")) {
            new Handler().postDelayed(() -> tts.speak("Stage2", TextToSpeech.QUEUE_ADD, null, "Stage2"), 1000);
        }

        if (stageName.equals("Stage3")) {
            new Handler().postDelayed(() -> tts.speak("Stage3", TextToSpeech.QUEUE_ADD, null, "Stage3"), 1000);
        }

        if (stageName.equals("Stage4")) {
            new Handler().postDelayed(() -> tts.speak("Stage4", TextToSpeech.QUEUE_ADD, null, "Stage3"), 1000);
        }


    }

    //--------------------------------------------------------------Start <gps permission>----------------------------------------------------------------------
    /*
     * 저의 코딩 실력이 미천하여 클래스화를 하지 못하고 하드 코딩해버렸습니다.
     * 코드가 더럽더라고 이해해주시면 감사하겠습니다.
     * Return true if gps service is available
     * else return false
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

    @Override
    public void onLocationChanged(@NonNull Location location) {

    }
    //--------------------------------------------------------------end:<gps permission>---------------------------------------------------------------------

    }