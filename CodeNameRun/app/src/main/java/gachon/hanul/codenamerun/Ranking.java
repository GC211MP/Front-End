package gachon.hanul.codenamerun;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

public class Ranking extends AppCompatActivity {


    /* Ranking fragments */
    Ranking_stage1 ranking_stage1;
    Ranking_stage2 ranking_stage2;
    Ranking_stage3 ranking_stage3;
    Ranking_stage4 ranking_stage4;


    /* Ranking buttons */
    Button ranking_stage1_btn;
    Button ranking_stage2_btn;
    Button ranking_stage3_btn;
    Button ranking_stage4_btn;

    /* menu button */
    ImageButton AgentInfo;
    ImageButton Home;
    ImageButton Ranking;

    SelectStage selectStage;
    AgentInformation agentInformation;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ranking);

        ranking_stage1= new Ranking_stage1();
        ranking_stage2= new Ranking_stage2();
        ranking_stage3= new Ranking_stage3();
        ranking_stage4= new Ranking_stage4();

        ranking_stage1_btn =findViewById(R.id.ranking_stage1_btn);
        ranking_stage2_btn =findViewById(R.id.ranking_stage2_btn);
        ranking_stage3_btn =findViewById(R.id.ranking_stage3_btn);
        ranking_stage4_btn =findViewById(R.id.ranking_stage4_btn);



       getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,ranking_stage1).commit();

        /* Ranking_stage1_btn listener */
        ranking_stage1_btn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
               getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,ranking_stage1).commit();
            }
        });

        /*Ranking_stage2_btn listener*/
        ranking_stage2_btn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,ranking_stage2).commit();
            }
        });
        /*Ranking_stage3_btn listener*/
        ranking_stage3_btn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,ranking_stage3).commit();
            }
        });
        /*Ranking_stage4_btn listener*/
        ranking_stage4_btn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,ranking_stage4).commit();
            }
        });

        /* find view by id */
        AgentInfo =  findViewById(R.id.AgentInfoButton);
        Home =  findViewById(R.id.HomeButton);
        Ranking =  findViewById(R.id.CommunityButton);

        AgentInfo.setImageResource(R.drawable.agent_info_grey);
        Home.setImageResource(R.drawable.home_grey);
        Ranking.setImageResource(R.drawable.community);

        /* menu buttons */
        AgentInfo.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent intent = new Intent(getApplicationContext(),Home.class);
                startActivity(intent);
                finish();
            }
        });
        Home.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent intent = new Intent(getApplicationContext(),Home.class);
                intent.putExtra("fragment","AgentInfo");
                startActivity(intent);
                finish();
            }
        });
        Ranking.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent intent = new Intent(getApplicationContext(),Ranking.class);
                startActivity(intent);
                finish();
            }
        });
    }
}