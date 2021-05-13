package gachon.hanul.codenamerun;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import gachon.hanul.codenamerun.api.StoreManager;


public class MainActivity extends AppCompatActivity {

    public static MediaPlayer mediaPlayer;
    Button startButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        StoreManager manager = StoreManager.getInstance(getApplicationContext()); //서버 불러오기

        startButton = findViewById(R.id.startButton);
        startButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){

                //TODO 유저 등록 유무를 확인해서
                // 1.유저가 등록되어 있다면 Home 이동
                Intent intent = new Intent(getApplicationContext(), Home.class);
                startActivity(intent);
                //TODO 2.유저가 등록이 안되어 있다면, WriteAgentInfo로 이동
                //Intent intent = new Intent(getApplicationContext(), WriteAgetnInfo.class);
                //startActivity(intent);
            }
        });
    }
}