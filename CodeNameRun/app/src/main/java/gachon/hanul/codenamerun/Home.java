package gachon.hanul.codenamerun;

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

        /* new fragment */
        selectStage = new SelectStage();
        agentInformation = new AgentInformation();

        /* if 번들이 extra 를 가지고 있으면,
            agent information fragment 를 띄운다
            (Ranking activity 에서 home, select stage 를 선택하기 위함)
            없으면 select stage 를 띄운다
         */
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.menu_fragment_container,agentInformation).commit();
        }
        getSupportFragmentManager().beginTransaction().replace(R.id.menu_fragment_container,selectStage).commit();

        /* menu buttons onclick*/
        AgentInfo.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                getSupportFragmentManager().beginTransaction().replace(R.id.menu_fragment_container,agentInformation).commit();
            }
        });
        Home.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                getSupportFragmentManager().beginTransaction().replace(R.id.menu_fragment_container,selectStage).commit();
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