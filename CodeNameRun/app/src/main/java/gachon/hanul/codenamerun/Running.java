package gachon.hanul.codenamerun;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

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

    }

    @Override
    protected void onStart() {
        super.onStart();
        tts.speak("나는 어렸을 때부터 정의로운 비밀요원 같은 영화에나 나올 법한 영웅에 관심이 많았다...\n" +
                "하지만 현실은 그저 평범한 일반인에 지나지 않았다!\n" +
                "그러던 도중 발견한 구인광고!\n" +
                "비밀요원을 구한다고?\n" +
                "누가 비밀요원을 이렇게 전단지로 구하냐 싶냐 만은,\n" +
                "나의 오랜 꿈을 위해 용기 있게 지원한다! \n" +
                "어서 오세요! 오늘부터 미션을 수행하시면 됩니다!! 요원 님!!\n" +
                "네? 저 오늘 취직했는데...\n" +
                "일단 달려요!!\n" +
                "으아아!\n",TextToSpeech.QUEUE_FLUSH, null, "1");
    }




}