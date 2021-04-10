package gachon.hanul.codenamerun;

import androidx.appcompat.app.AppCompatActivity;

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
                    // 언어를 선택한다.
                    tts.setLanguage(Locale.KOREAN);
                }
            }
        });

        /* if 시간이 1000 미만일때는 사운드가 나오지 않는 버그가 있는 것 같습니다*/
        if (stageName.equals("Tutorial")) {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    tts.speak("Tutorial", TextToSpeech.QUEUE_FLUSH, null, "1");
                }
            }, 1000);
        }

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                tts.speak("2", TextToSpeech.QUEUE_FLUSH, null, "2");
            }
        }, 2000);


    }

}