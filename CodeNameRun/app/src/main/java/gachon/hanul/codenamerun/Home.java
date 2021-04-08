package gachon.hanul.codenamerun;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Home extends AppCompatActivity {

    Button TutorialStageButton;
    Button Stage1Button;
    Button Stage2Button;
    Button Stage3Button;
    Button Stage4Button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        /* find view by id */
        TutorialStageButton = findViewById(R.id.TutorialStageButton);
        Stage1Button = findViewById(R.id.Stage1Button);
        Stage2Button = findViewById(R.id.Stage2Button);
        Stage3Button = findViewById(R.id.Stage3Button);
        Stage4Button = findViewById(R.id.Stage4Button);

        /* buttons */
        TutorialStageButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent TutorialIntent = new Intent(getApplicationContext(), Running.class);
                TutorialIntent.putExtra("stageName", "Tutorial");
                startActivity(TutorialIntent);
            }
        });

        Stage1Button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent Stage1Intent = new Intent(getApplicationContext(), Running.class);
                Stage1Intent.putExtra("stageName", "Stage1");
                startActivity(Stage1Intent);
            }
        });

        Stage2Button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent Stage2Intent = new Intent(getApplicationContext(), Running.class);
                Stage2Intent.putExtra("stageName", "Stage2");
                startActivity(Stage2Intent);
            }
        });

        Stage3Button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent Stage3Intent= new Intent(getApplicationContext(), Running.class);
                Stage3Intent.putExtra("stageName", "Stage3");
                startActivity(Stage3Intent);
            }
        });

        Stage4Button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent Stage4Intent= new Intent(getApplicationContext(), Running.class);
                Stage4Intent.putExtra("stageName", "Stage4");
                startActivity(Stage4Intent);
            }
        });

    }
}