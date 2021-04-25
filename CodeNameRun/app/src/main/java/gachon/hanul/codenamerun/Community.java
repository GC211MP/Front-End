package gachon.hanul.codenamerun;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;

public class Community extends AppCompatActivity {

    ImageButton AgentInfo;
    ImageButton Home;
    ImageButton Community;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_community);

        /* find view by id */
        AgentInfo =  findViewById(R.id.AgentInfoButton);
        Home =  findViewById(R.id.HomeButton);
        Community =  findViewById(R.id.CommunityButton);

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