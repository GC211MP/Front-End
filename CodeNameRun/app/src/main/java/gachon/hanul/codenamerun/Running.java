package gachon.hanul.codenamerun;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.os.Bundle;

import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.speech.tts.TextToSpeech;

import java.util.Locale;

import static android.speech.tts.TextToSpeech.ERROR;

public class Running extends AppCompatActivity {

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

        /* find view by id */
        TopSecret1 = findViewById(R.id.TopSecret1);
        TopSecret2 = findViewById(R.id.TopSecret2);
        TopSecret3 = findViewById(R.id.TopSecret3);
        TopSecret4 = findViewById(R.id.TopSecret4);
        TopSecret5 = findViewById(R.id.TopSecret5);

        stageNameText = findViewById(R.id.alertStage);

        /* change values */
        TopSecret1.setVisibility(View.VISIBLE);
        TopSecret2.setVisibility(View.VISIBLE);
        TopSecret3.setVisibility(View.INVISIBLE);
        TopSecret4.setVisibility(View.INVISIBLE);
        TopSecret5.setVisibility(View.INVISIBLE);

        stageNameText.setText(stageName);

        /* set TTS */
        tts = new TextToSpeech(this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if(status != ERROR) {
                    tts.setLanguage(Locale.KOREAN); // 언어
                    tts.setPitch(2.0f); // 톤 (높낮이, default= 1.0f)
                    tts.setSpeechRate(1.5f); // 속도 (default= 1.0f)
                }
            }
        });

        /* if 시간이 1000 미만일때는 사운드가 나오지 않는 버그(?)가 있는 것 같습니다*/
        if (stageName.equals("Tutorial")) {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    tts.speak("Tutorial", TextToSpeech.QUEUE_FLUSH, null, "Tutorial");
                }
            }, 1000);
        }
        if (stageName.equals("Stage1")) {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    tts.speak("Stage1", TextToSpeech.QUEUE_FLUSH, null, "Stage1");
                }
            }, 1000);
        }
        if (stageName.equals("Stage2")) {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    tts.speak("Stage2", TextToSpeech.QUEUE_FLUSH, null, "Stage2");
                }
            }, 1000);
        }
        if (stageName.equals("Stage3")) {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    tts.speak("Stage3", TextToSpeech.QUEUE_FLUSH, null, "Stage3");
                }
            }, 1000);
        }
        if (stageName.equals("Stage4")) {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    tts.speak("Stage4", TextToSpeech.QUEUE_FLUSH, null, "Stage3");
                }
            }, 1000);
        }

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                tts.speak("테스트 대사 입니다", TextToSpeech.QUEUE_FLUSH, null, "2");
                /* a bgm cycle */
                MainActivity.mediaPlayer = MediaPlayer.create(Running.this, R.raw.footstep);
                MainActivity.mediaPlayer.start();
                MainActivity.mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mp) {
                        MainActivity.mediaPlayer.release();
                        MainActivity.mediaPlayer = null;
                    }
                });
            }
        }, 2000);
        
    }
}