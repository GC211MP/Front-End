package gachon.hanul.codename;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import gachon.hanul.codename.api.PersonalData;
import gachon.hanul.codename.api.StoreManager;


public class MainActivity extends AppCompatActivity {

    public static MediaPlayer mediaPlayer;
    Button startButton;
    StoreManager manager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        manager = StoreManager.getInstance(getApplicationContext()); //서버 불러오기

        startButton = findViewById(R.id.startButton);
        startButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                new checkUserValid().execute();
            }
        });
    }

    private class checkUserValid extends AsyncTask<Void, Integer, Void>{

        @Override
        protected Void doInBackground(Void... voids) {
            try{
                // 1.유저가 등록되어 있다면 Home 이동
                Integer[] stages = {};
                PersonalData user = manager.readUserData(stages);
                publishProgress(0);
            } catch(android.database.CursorIndexOutOfBoundsException error){
                // 2.유저가 등록이 안되어 있다면, WriteAgentInfo로 이동
                publishProgress(-1);
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            if(values[0] == 0){
                // 1.유저가 등록되어 있다면 Home 이동
                Intent intent = new Intent(getApplicationContext(), Home.class);
                startActivity(intent);
            } else {
                // 2.유저가 등록이 안되어 있다면, WriteAgentInfo로 이동
                Intent intent = new Intent(getApplicationContext(), WriteAgentInfo.class);
                startActivity(intent);
            }
        }
    }
}