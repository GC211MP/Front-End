package gachon.hanul.codenamerun;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

public class ScoreBoard extends AppCompatActivity {
    Button backButton;
    TextView scoreboard;
    int score;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score_board);
        score = getIntent().getIntExtra("score",0);

        backButton = findViewById(R.id.backButton);
        scoreboard = findViewById(R.id.score);

        scoreboard.setText(Integer.toString(score));

        /*back home*/

        backButton.setOnClickListener(v -> {
            Intent HomeIntent= new Intent(getApplicationContext(), Home.class);
            startActivity(HomeIntent);
        });
    }
}