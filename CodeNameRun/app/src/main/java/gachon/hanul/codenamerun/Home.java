package gachon.hanul.codenamerun;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

public class Home extends AppCompatActivity {

    Button PrologueStageButton;
    Button Stage1Button;
    Button Stage2Button;
    Button Stage3Button;
    Button Stage4Button;

    Button AgentInfo;
    Button Home;
    Button Community;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        /* find view by id */
        PrologueStageButton = findViewById(R.id.PrologueStageButton);
        Stage1Button = findViewById(R.id.Stage1Button);
        Stage2Button = findViewById(R.id.Stage2Button);
        Stage3Button = findViewById(R.id.Stage3Button);
        Stage4Button = findViewById(R.id.Stage4Button);

        /* find view by id */
        AgentInfo =  findViewById(R.id.AgentInfoButton);
        Home =  findViewById(R.id.HomeButton);
        Community =  findViewById(R.id.CommunityButton);

        /* stage buttons */
        PrologueStageButton.setOnClickListener(v -> {
            Intent PrologueIntent = new Intent(getApplicationContext(), Running.class);
            PrologueIntent.putExtra("stageName", "Prologue");
            startActivity(PrologueIntent);
        });

        Stage1Button.setOnClickListener(v -> {
            Intent Stage1Intent = new Intent(getApplicationContext(), Running.class);
            Stage1Intent.putExtra("stageName", "Stage1");
            startActivity(Stage1Intent);
        });

        Stage2Button.setOnClickListener(v -> {
            Intent Stage2Intent = new Intent(getApplicationContext(), Running.class);
            Stage2Intent.putExtra("stageName", "Stage2");
            startActivity(Stage2Intent);
        });

        Stage3Button.setOnClickListener(v -> {
            Intent Stage3Intent= new Intent(getApplicationContext(), Running.class);
            Stage3Intent.putExtra("stageName", "Stage3");
            startActivity(Stage3Intent);
        });

        Stage4Button.setOnClickListener(v -> {
            Intent Stage4Intent= new Intent(getApplicationContext(), Running.class);
            Stage4Intent.putExtra("stageName", "Stage4");
            startActivity(Stage4Intent);
        });

        /* menu buttons */
        AgentInfo.setOnClickListener(v -> {
            Intent AgentInfoIntent= new Intent(getApplicationContext(), AgentInfo.class);
            startActivity(AgentInfoIntent);
        });

        Home.setOnClickListener(v -> {
            Intent HomeIntent= new Intent(getApplicationContext(), Home.class);
            startActivity(HomeIntent);
        });

        Community.setOnClickListener(v -> {
            Intent CommunityIntent= new Intent(getApplicationContext(), Community.class);
            startActivity(CommunityIntent);
        });

    }
}