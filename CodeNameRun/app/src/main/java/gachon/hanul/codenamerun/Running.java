package gachon.hanul.codenamerun;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import android.Manifest;
import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.location.LocationManager;
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


import java.util.LinkedList;
import java.util.List;
import java.util.Locale;

import static android.speech.tts.TextToSpeech.ERROR;

public class Running extends AppCompatActivity {

    String[] REQUIRED_PERMISSIONS = {Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION};

    public static final String LOG_IN_RUNNING = "running";
    private static final String NEXT_IS_RUNNING = "next_running";
    private static final String NEXT_IS_TTS = "next_tts";
    private static final int GPS_ENABLE_REQUEST_CODE = 2001;
    private static final int PERMISSIONS_REQUEST_CODE = 100;

    private final int DISTANCE_MULTIPLE = 100;
    private final int SECRET_MULTIPLE = 100;
    private final int[] stage_iter = new int[] {1, 5, 10 , 10}; // 각각 stage에서 멘트 혹은 인터벌의 총 합

    private boolean isSpeedOK = true;
    private boolean isTTSDone;
    private boolean isRunDone;
    private TextToSpeech tts;

    HelpGPS helpGPS;
    HelpMap helpMap;
    Handler handler;

    ImageView TopSecret1;
    ImageView TopSecret2;
    ImageView TopSecret3;
    ImageView TopSecret4;
    ImageView TopSecret5;
    TextView stageNameText;
    String stageName;

    int totalSecret;
    int now_stage;
    int now_step = 0;
    String[] test_msg;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_running);

        /* get stage information */
        stageName = getIntent().getStringExtra("stageName");
        now_stage = getStageNumber(stageName);
        if(now_stage < 0){
            Log.d(LOG_IN_RUNNING,"stage is wrong");
        }

        /* script */
        test_msg = getResources().getStringArray(R.array.test);


        // 브로드캐스터 리시버, 속도랑 시간을 받아 올거야
        LocalReceiver localReceiver = new LocalReceiver();
        IntentFilter filter = new IntentFilter();
        filter.addAction("gachon.hanul.codenamerun.local");
        LocalBroadcastManager.getInstance(this).registerReceiver(localReceiver, filter);

        // gps permission part
        if (checkLocationServicesStatus()) {
            checkRunTimePermission();
        } else showDialogForLocationServiceSetting();

        // helpGPS 랑 helpMap 객체 생성
        helpGPS = new HelpGPS(this);
        helpMap = new HelpMap(this, helpGPS.getLocation());
        helpGPS.setHelpMap(helpMap);

        // 화면에 구글맵 가져오기
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(helpMap); // 지도가 준비되면 콜되는 함수

        handler = new Handler();

        /* set TTS */
        tts = new TextToSpeech(this, status -> {
            if (status != ERROR) {
                tts.setLanguage(Locale.KOREAN); // 언어
                tts.setPitch(1.0f); // 톤 (높낮이, default= 1.0f)
                tts.setSpeechRate(1.5f); // 속도 (default= 1.0f)
            }
        });
        /* set TTS utterance listener */
        tts.setOnUtteranceProgressListener(new UtteranceProgressListener() {
            @Override
            public void onStart(final String utteranceId) {
                Running.this.runOnUiThread(new Runnable() {
                    public void run() {
                    }
                });
            }

            @Override
            public void onError(String utteranceId) {
            }

            @Override
            public void onDone(String utteranceId) {
                Log.d(LOG_IN_RUNNING,"tts done");
                isTTSDone = true;
                if(isTTSDone && isRunDone){
                    playNextStep();
                }
            }
        });

        // setting game values --------------------------------------------------------------------------------
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
        totalSecret = 0;
        isSpeedOK = true;
        isRunDone = true; // 나중에 false로 바꿔라
        isTTSDone = false;


        playNextStep();


// TODO: MediaPlayer 적용
        
//            new Handler().postDelayed(() -> {
//                MainActivity.mediaPlayer = MediaPlayer.create(Running.this, R.raw.footstep);
//                MainActivity.mediaPlayer.start();
//                MainActivity.mediaPlayer.setOnCompletionListener(mp -> {
//                    MainActivity.mediaPlayer.release();
//                    MainActivity.mediaPlayer = null;
//                });
//            }, 4000);

        Log.d(LOG_IN_RUNNING,"onCreate in end");
    }


    public void playNextStep(){

        // 다음이 있는지 확인
        if (now_step < test_msg.length) { // 여기는 다음에 나올 멘트나 뛰는 것을 진행해야해

            // 멘트를 큐에 넣어주고
            isTTSDone = false;
            handler.postDelayed(() -> tts.speak(test_msg[now_step-1], TextToSpeech.QUEUE_ADD, null, "prologue_1"), 1000);
            // 시간 걸어주고
//            helpGPS.setRemainTime(0);
//            helpGPS.setMinSpeed(-1);

            now_step += 1;
            Log.d(LOG_IN_RUNNING,now_step+ "/" + test_msg.length);
        } else { // 여기 들어오면 스테이지가 끝나거야
            Log.d(LOG_IN_RUNNING,"enter ending");
            endStage();
        }
        //

    }

    /*
     * helpGPS에서 제한 속도(speed limit) 보다 느려지면 보내는 브로드캐스트를 받는 클래스
     * boolean 값을 받아서 isSpeedOK 에서 넣어줌
     */
    public class LocalReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            isSpeedOK = intent.getBooleanExtra(HelpGPS.MSG_SLOW, true);
            isRunDone = intent.getBooleanExtra(HelpGPS.MSG_COMPLETE, false);
            if(isRunDone && isTTSDone) {
                playNextStep();
            }

            if ((isRunDone)){
                Log.d(LOG_IN_RUNNING,"run done");
            }
        }
    }

    //--------------------------------------------------------------------------------------------------------------------------------------
    /***** add tts speak
     * List<String> messages = Arrays.asList(getResources().getStringArray(R.array.Lines));
     * if there is nothing speaking -> call this function
     *      get list of the string list
     *      add new line to tts queue
     * if string list is empty
     *      end function
     *****/
    private int endStage(){
        int score;
        double distance;

        // 1. 뛴 거리랑 편지지 갯수 알아오기
        //distance = helpGPS.getTotalDistance();
        // 2. 점수 계산하기
        //score = calculateScore(distance,totalSecret);
        score = calculateScore(100,4);
        // 3. 객체 종료하기 -> gps랑 map
        //helpMap.clearMap();
        helpGPS.onDestroy();
        // TODO: 4. 점수창 띄워주기 -> 궁금한데 인텐트로 점수창 띄워주면 안될거 같은데 fragment나 해야할 것 같은데.. 모르겠다

        Log.d(LOG_IN_RUNNING,"score: " + score);
        return  score;

    }

    private int calculateScore(double distance, int numSecret){
        int score;
        score = (int)(distance*DISTANCE_MULTIPLE) + numSecret*SECRET_MULTIPLE;

        return score;
    }

    private void startInterval(float speedLimit, HelpGPS helpGPS){
        helpGPS.setMinSpeed(speedLimit);
        isSpeedOK = true;
    }

    private void endInterval(HelpGPS helpGPS){
        helpGPS.setMinSpeed(0);
        if(isSpeedOK) totalSecret += 1;
    }

    private int getStageNumber(String str) {
        if(str.equals("Prologue")) return 0;
        if(str.equals("Stage1")) return 1;
        if(str.equals("Stage2")) return 2;
        if(str.equals("Stage3")) return 3;
        if(str.equals("Stage4")) return 4;
        return -1;
    }




    //--------------------------------------------------------------Start <gps permission>----------------------------------------------------------------------
    /*
     * TODO: 다혜가 퍼미션 부분에서 뻑난다고 말했음 -> 고쳐야한다
     * 내코드 아니어서 고치기 힘든데 ㅠㅠ
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