package gachon.hanul.codename;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

public class ScoreBoard extends AppCompatActivity {
    Button backButton;
    TextView scoreboard;
    TextView distanceboard;
    TextView calorieboard;
    int score;
    int cal;
    int dis;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score_board);
        score = getIntent().getIntExtra("score",0);
        cal = getIntent().getIntExtra("calorie",0);
        dis = getIntent().getIntExtra("distance",0);

        backButton = findViewById(R.id.backButton);
        scoreboard = findViewById(R.id.score);
        distanceboard = findViewById(R.id.distance);
        calorieboard = findViewById(R.id.calorie);

        scoreboard.setText(Integer.toString(score));
        distanceboard.setText(Integer.toString(dis));
        calorieboard.setText(Integer.toString(cal));

        /*back home*/

        backButton.setOnClickListener(v -> {
            Intent HomeIntent= new Intent(getApplicationContext(), Home.class);
            startActivity(HomeIntent);
        });
    }
}