package gachon.hanul.codename;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

public class Home extends AppCompatActivity {

    /* menu button declaration*/
    ImageButton AgentInfo;
    ImageButton Home;
    ImageButton Ranking;

    /* fragment declaration */
    SelectStage selectStage;
    AgentInformation agentInformation;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        /* find view by id */
        AgentInfo =  findViewById(R.id.AgentInfoButton);
        Home =  findViewById(R.id.HomeButton);
        Ranking =  findViewById(R.id.CommunityButton);

        AgentInfo.setImageResource(R.drawable.agent_info_grey);
        Home.setImageResource(R.drawable.home);
        Ranking.setImageResource(R.drawable.community_grey);


        /* new fragment */
        selectStage = new SelectStage();
        agentInformation = new AgentInformation();


        getSupportFragmentManager().beginTransaction().replace(R.id.menu_fragment_container,selectStage).commit();

        /* menu buttons onclick*/
        AgentInfo.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent intent = new Intent(getApplicationContext(),AgentInformation.class);
                startActivity(intent);
                // 중첩을 피하기 위해서 다른 activity 로 갈때 quit home activity
                finish();
            }
        });
        Home.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                getSupportFragmentManager().beginTransaction().replace(R.id.menu_fragment_container,selectStage).commit();

                AgentInfo.setImageResource(R.drawable.agent_info_grey);
                Home.setImageResource(R.drawable.home);
                Ranking.setImageResource(R.drawable.community_grey);
            }
        });
        Ranking.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent intent = new Intent(getApplicationContext(),Ranking.class);
                startActivity(intent);
                // 중첩을 피하기 위해서 다른 activity 로 갈때 quit home activity
                finish();
            }
        });
    }
}