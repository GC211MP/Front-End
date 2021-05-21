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
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.os.Bundle;

import android.os.Handler;
import android.os.storage.StorageManager;
import android.speech.tts.UtteranceProgressListener;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.speech.tts.TextToSpeech;
import android.widget.Toast;


import com.google.android.gms.maps.SupportMapFragment;


import java.util.ArrayList;
import java.util.Locale;

import gachon.hanul.codenamerun.api.DataDTO;
import gachon.hanul.codenamerun.api.PersonalData;
import gachon.hanul.codenamerun.api.SqliteDto;
import gachon.hanul.codenamerun.api.StoreManager;

import static android.speech.tts.TextToSpeech.ERROR;

public class Running extends AppCompatActivity {


    // for score
    private final int DISTANCE_MULTIPLE = 1;
    private final int SECRET_MULTIPLE = 200;
    public static final String LOG_IN_RUNNING = "running";
    private final String NEXT_TTS = "멘트";
    private final String BGM_START = "배경음 시작";
    private final String EFFECT = "효과음";

    /* 우리의 브금술사 */
    private MediaPlayer bgmPlayer;
    private SoundPool effectPlayer;
    private ArrayList<String> musicList;
    private ArrayList<Integer> musicResource;
    private TextToSpeech tts;
    // for gps
    HelpGPS helpGPS;
    HelpMap helpMap;
    Handler handler;

    // ui object
    ImageView TopSecret1;
    ImageView TopSecret2;
    ImageView TopSecret3;
    ImageView TopSecret4;
    ImageView TopSecret5;
    TextView stageNameText;
    TextView timeText;

    String stageName;
    private boolean isSpeedOK = true;
    private boolean isTTSDone;
    private boolean isRunDone;
    private boolean isLost;
    private boolean isMapReady;
    private boolean backKey = false;

    int nowStage;
    int totalSecret;
    int now_step = 0;
    int time;
    int targetTime;
    String[] storyLists;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_running);

        /* get stage information */
        stageName = getIntent().getStringExtra("stageName");


        // set broadcaster receiver
        LocalReceiver localReceiver = new LocalReceiver();
        IntentFilter filter = new IntentFilter();
        filter.addAction("gachon.hanul.codenamerun.local");
        LocalBroadcastManager.getInstance(this).registerReceiver(localReceiver, filter);

        // set helpGPS and helpMap
        helpGPS = new HelpGPS(this);
        helpMap = new HelpMap(this, helpGPS.getLastLocation());
        helpGPS.setHelpMap(helpMap);

        // set google map
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
                Log.d(LOG_IN_RUNNING, "tts done");
                isTTSDone = true;
                if (isTTSDone && isRunDone) {
                    playNextStep();
                }
            }
        });

        // TODO: MediaPlayer 적용
        /***** 브금 선언 *****/

        effectPlayer = new SoundPool(8, AudioManager.STREAM_MUSIC, 0);
        musicList = new ArrayList<String>();
        musicResource = new ArrayList<Integer>();

        putMusic("구두소리", R.raw.footstep);
        putMusic("on_a_mission", R.raw.on_a_mission);
        putMusic("phone_ring", R.raw.phone_ring);
        putMusic("walking_spy", R.raw.walking_spy);
        putMusic("전화벨소리", R.raw.phone_ring);
        putMusic("위험한알람", R.raw.warning);
        putMusic("평화로운배경음", R.raw.default_bgm);
        putMusic("평화로운 bgm", R.raw.default_bgm);
        putMusic("긴박한배경음", R.raw.on_a_mission);
        putMusic("전투소리", R.raw.gun_shot);
        putMusic("신나는배경음", R.raw.walking_spy);
        putMusic("불길한배경음", R.raw.ominous_bgm);
        putMusic("불길한 bgm", R.raw.ominous_bgm);
        putMusic("쿠구궁", R.raw.shocking);
        putMusic("신나는 bgm", R.raw.walking_spy);

        // setting game values --------------------------------------------------------------------------------
        /* find view by id */
        TopSecret1 = findViewById(R.id.TopSecret1);
        TopSecret2 = findViewById(R.id.TopSecret2);
        TopSecret3 = findViewById(R.id.TopSecret3);
        TopSecret4 = findViewById(R.id.TopSecret4);
        TopSecret5 = findViewById(R.id.TopSecret5);

        stageNameText = findViewById(R.id.alertStage);
        timeText = findViewById(R.id.timer);

        /* change values */
        TopSecret1.setVisibility(View.VISIBLE);
        TopSecret2.setVisibility(View.VISIBLE);
        TopSecret3.setVisibility(View.VISIBLE);
        TopSecret4.setVisibility(View.VISIBLE);
        TopSecret5.setVisibility(View.VISIBLE);

        stageNameText.setText(stageName);
        timeText.setText("00:00");
        totalSecret = 5;
        isSpeedOK = true;
        isRunDone = false; // 나중에 false로 바꿔라
        isTTSDone = false;
        isLost = false;
        targetTime = 0;

        /* script */
//        storyLists = getResources().getStringArray(R.array.test); // test 용
        storyLists = getStageStringArray(stageName); // 찐

        Log.d(LOG_IN_RUNNING, "onCreate in end");
    }
    /* onCreate end -----------------------------------------------------------------------------------------*/


    public void ByeBye() {
        bgmPlayer.release();
        helpGPS.stopUsingGPS();
        helpGPS.onDestroy();
        helpMap.finish();
        finish();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        switch (keyCode) {
            /**
             * if user press back key once than toast a message
             * if user press back key one more than finish stage
             */
            case KeyEvent.KEYCODE_BACK:
                if (backKey == false) {
                    backKey = true;
                    Toast.makeText(getApplicationContext(), "뒤로키를 한번더 누르시면 종료됩니다", Toast.LENGTH_SHORT).show();
                    handler.postDelayed(() -> backKey = false, 1500);
                    return false;
                } else {
                    // 중첩을 피하기 위해서 다른 activity 로 갈때 quit home activity
                    ByeBye();
                    super.onKeyDown(keyCode, event);
                }
        }
        return super.onKeyDown(keyCode, event);
    }


    /**
     * playNextStop
     * 다음 스토리를 진행하는 함수입니다.
     * 멘트(TTS), 배경음, 효과음 크게 3가지로 나뉘어서 진행
     * 더 이상 진행할 스토리가 없으면 endStage
     */
    public void playNextStep() {
        isTTSDone = false;
        isRunDone = false;


        // 다음이 있는지 확인
        if (now_step < storyLists.length) { // 스토지를 진행하자

            if (storyLists[now_step].equals(NEXT_TTS)) { // TTS 실행
                isLost = false;
                // 속도랑 시간을 걸어주자
                now_step++;
                float speed = Float.parseFloat(storyLists[now_step]);
                if (speed > 1) {
                    handler.postDelayed(() -> helpGPS.setMinSpeed(speed), 20000);  // 속도
                } else {
                    helpGPS.setMinSpeed(speed);
                }
                now_step++;
                targetTime = time + Integer.parseInt(storyLists[now_step]); // 시간

                // 멘트를 큐에 넣어주고
                now_step++;
                String msg = storyLists[now_step];
                handler.postDelayed(() -> tts.speak(msg, TextToSpeech.QUEUE_ADD, null, "prologue_1"), 1000);

                Log.d(LOG_IN_RUNNING, storyLists[now_step] + now_step + "/" + storyLists.length);
                now_step++;

            } else if (storyLists[now_step].equals(BGM_START)) { // 배경음 틀어주기
                now_step++;

                if (bgmPlayer != null && bgmPlayer.isPlaying()) {
                    bgmPlayer.release();

                }

                bgmPlayer = MediaPlayer.create(Running.this, getMusicResource(storyLists[now_step]));
                bgmPlayer.setLooping(true);
                bgmPlayer.start();

                now_step++;
                playNextStep();

            } else if (storyLists[now_step].equals(EFFECT)) { // 효과음 틀어주기
                now_step++;

                int sound = effectPlayer.load(Running.this, getMusicResource(storyLists[now_step]), 1);
                effectPlayer.setOnLoadCompleteListener(new SoundPool.OnLoadCompleteListener() {
                    @Override
                    public void onLoadComplete(SoundPool soundPool, int sampleId, int status) {
                        effectPlayer.play(sound, 1, 1, 0, 0, 1);

                        now_step++;

                        handler.postDelayed(() -> playNextStep(), 2000);
                    }
                });

            }

        } else { // 여기 들어오면 스테이지가 끝나거야
            Log.d(LOG_IN_RUNNING, "enter ending");
            endStage();
        }
    }

    /*
     * helpGPS에서 제한 속도(speed limit) 보다 느려지면 보내는 브로드캐스트를 받는 클래스
     * boolean 값을 받아서 isSpeedOK 에서 넣어줌
     */
    public class LocalReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            isSpeedOK = intent.getBooleanExtra(HelpGPS.MSG_SLOW, true);
            isMapReady = intent.getBooleanExtra("onMapReady", false);

            // 속도가 느려졋을때
            if (!isSpeedOK && !isLost) {
                if (totalSecret == 5) {
                    TopSecret5.setVisibility(View.INVISIBLE);
                } else if (totalSecret == 4) {
                    TopSecret4.setVisibility(View.INVISIBLE);
                } else if (totalSecret == 3) {
                    TopSecret3.setVisibility(View.INVISIBLE);
                } else if (totalSecret == 2) {
                    TopSecret2.setVisibility(View.INVISIBLE);
                } else { // 1
                    TopSecret1.setVisibility(View.INVISIBLE);
                }
                totalSecret--;
                isLost = true;

                tts.speak("속도가 느려졌습니다. 조금 더 빨리 뛰시길 바랍니다", TextToSpeech.QUEUE_ADD, null, "prologue_1");
            }

            if (isMapReady) {
                isMapReady = false;
                new Thread(new TimeHandler()).start();
                playNextStep(); // 얘의 위치를 좀 바꾸자자
            }
        }
    }

    private void endStage() {
        int score;
        int distance;
        int calorie;


        // 1. 뛴 거리랑 편지지 갯수 알아오기
        distance = (int) helpGPS.getDistance();
        // 2. 점수 계산하기
        //score = calculateScore(distance,totalSecret);
        score = calculateScore(distance, totalSecret);
        calorie = helpGPS.getCalories();
        // 4. 점수창 띄워주기
        Intent intent = new Intent(getApplicationContext(), ScoreBoard.class);
        intent.putExtra("score", score);
        intent.putExtra("calorie", calorie);
        intent.putExtra("distance", distance);
        startActivity(intent);

        // 서버에 점수 보내기
        new Thread(new Runnable() {
            @Override
            public void run() {
                StoreManager manager = StoreManager.getInstance(getApplicationContext());
                //manager.readUserData TODO: 이거봐꿔야행
                PersonalData pd = manager.readUserData(new Integer[]{1,2,3,4});
                DataDTO dataDTO = new DataDTO(pd.userName, nowStage, (int) distance, calorie, score);
                manager.setRank(dataDTO);
            }
        }).start();

        // 중첩을 피하기 위해서 다른 activity 로 갈때 quit home activity
        ByeBye();
    }

    /**
     * calculateScore
     * calculate score by distance and number of success interval running
     * @param distance: distance that user walk and run
     * @param numSecret: number of sucess interval running
     * @return
     */
    private int calculateScore(double distance, int numSecret) {
        int score;
        score = (int) (distance * DISTANCE_MULTIPLE) + numSecret * SECRET_MULTIPLE;
        return score;
    }

    /**
     * getStageStringArray
     * get story array and stage information
     * @param stage: String that contains stage
     * @return: String array that story lines
     */
    private String[] getStageStringArray(String stage) {
        getResources().getStringArray(R.array.test);
        if (stage.equals("Prologue")) {
            nowStage = 0;
            return getResources().getStringArray(R.array.Prologue);
        }
        if (stage.equals("Stage1")) {
            nowStage = 1;
            return getResources().getStringArray(R.array.Stage1);
        }
        if (stage.equals("Stage2")) {
            nowStage = 2;
            return getResources().getStringArray(R.array.Stage2);
        }
        if (stage.equals("Stage3")) {
            nowStage = 3;
            return getResources().getStringArray(R.array.Stage3);
        }
        if (stage.equals("Stage4")) {
            nowStage = 4;
            return getResources().getStringArray(R.array.Stage4);
        }
        return null;
    }

    private boolean putMusic(String name, int rsrc) {
        musicList.add(name);
        musicResource.add(rsrc);

        if (musicList.indexOf(name) == musicResource.indexOf((rsrc)))
            return true;
        return false;
    }

    private int getMusicResource(String name) {
        return musicResource.get(musicList.indexOf(name));
    }

    private class TimeHandler implements Runnable {

        String timeSec;
        String timeMin;

        @Override
        public void run() {
            try {
                time = 0;
                while (true) {
                    Thread.sleep(1000);
                    time++;
                    // 시간 보여주기
                    if (time / 60 < 10) {
                        timeMin = "0" + time / 60;
                    } else {
                        timeMin = "" + time / 60;
                    }
                    if (time % 60 < 10) {
                        timeSec = "0" + time % 60;
                    } else {
                        timeSec = "" + time % 60;
                    }
                    timeText.setText(timeMin + ":" + timeSec);

                    // 시간 지났는지 확인하기
                    if (targetTime != 0 && time > targetTime) {
                        isRunDone = true;

                        if (isRunDone && isTTSDone) {
                            playNextStep();
                        }
                    }

                }

            } catch (Exception e) {

            }
        }
    }

}