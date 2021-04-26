package gachon.hanul.codenamerun;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

public class ScoreBoard extends AppCompatActivity {
    Button backButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score_board);

        backButton = findViewById(R.id.backButton);

        /*back home*/

        backButton.setOnClickListener(v -> {
            Intent HomeIntent= new Intent(getApplicationContext(), Home.class);
            startActivity(HomeIntent);
        });
    }
}