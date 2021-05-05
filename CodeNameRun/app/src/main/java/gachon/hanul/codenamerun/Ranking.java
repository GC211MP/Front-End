package gachon.hanul.codenamerun;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

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

    String[] nameArray = {"이름 1","이름 2","이름 3","이름 4","이름 5","이름 6" };

    String[] recordArray = {
            "100",
            "80",
            "70",
            "60",
            "50",
            "40"};

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
    }
}