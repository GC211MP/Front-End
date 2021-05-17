package gachon.hanul.codenamerun;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import gachon.hanul.codenamerun.api.PersonalData;
import gachon.hanul.codenamerun.api.StoreManager;


public class AgentInformation extends AppCompatActivity {

    ArrayList<String> information;
    Home home;
    StoreManager manager;
    EditText name;
    EditText height;
    EditText weight;
    TextView stage1;
    TextView stage2;
    TextView stage3;
    TextView stage4;
    TextView total;
    AgentInformation agentInformation;

    int[]ImageId={R.drawable.woman_portrait,R.drawable.man_portrait};
    ImageView portrait;

    /* menu button */
    ImageButton AgentInfo;
    ImageButton Home;
    ImageButton Ranking;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agent_information);

        name= findViewById(R.id.agent_name);
        height= findViewById(R.id.agent_height);
        weight= findViewById(R.id.agent_weight);
        stage1= findViewById(R.id.score_1stage);
        stage2= findViewById(R.id.score_2stage);
        stage3= findViewById(R.id.score_3stage);
        stage4= findViewById(R.id.score_4stage);
        total= findViewById(R.id.agent_distance_colories);

        new InformationInstance().execute();

        portrait = (ImageView)findViewById(R.id.portrait);
        portrait.setOnClickListener(new MyListener() //초상화 클릭시 초상화변경
        );


        AgentInfo =  findViewById(R.id.AgentInfoButton);
        Home =  findViewById(R.id.HomeButton);
        Ranking =  findViewById(R.id.CommunityButton);

        AgentInfo.setImageResource(R.drawable.agent_info);
        Home.setImageResource(R.drawable.home_grey);
        Ranking.setImageResource(R.drawable.community_grey);

        /* menu buttons */
        AgentInfo.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent intent = new Intent(getApplicationContext(),AgentInformation.class);
                startActivity(intent);
                finish();
            }
        });
        Home.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent intent = new Intent(getApplicationContext(),Home.class);
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



    private class InformationInstance extends AsyncTask<Void, ArrayList<String>, Void> {


        @Override
        protected Void doInBackground(Void... params) {
            manager = StoreManager.getInstance(getApplicationContext()); //서버 불러오기
            Integer[] stages = {};
            PersonalData user = manager.readUserData(stages);
            ArrayList<String> temp = new ArrayList<String>();
            temp.add(user.userName);
            temp.add(String.valueOf(user.userHeight));
            temp.add(String.valueOf(user.userWeight));

            //점수가 없으면 -1이 나와서 수정
            if(manager.getTopScore(1)==-1){temp.add(String.valueOf(0));}
            else {temp.add(String.valueOf(manager.getTopScore(1)));}

            if(manager.getTopScore(2)==-1){temp.add(String.valueOf(0));}
            else {temp.add(String.valueOf(manager.getTopScore(2)));}

            if(manager.getTopScore(3)==-1){temp.add(String.valueOf(0));}
            else {temp.add(String.valueOf(manager.getTopScore(3)));}

            if(manager.getTopScore(4)==-1){temp.add(String.valueOf(0));}
            else {temp.add(String.valueOf(manager.getTopScore(4)));}


            temp.add(String.valueOf(manager.getTotalDistance(-1)));
            temp.add(String.valueOf(manager.getTotalCalorie(-1)));

            publishProgress(temp);
            return null;
        }

        @Override
        protected void onProgressUpdate( ArrayList<String>... values) {
            super.onProgressUpdate(values);
            information = values[0];
            name.setText(" Name : "+ information.get(0));
            height.setText(" Height : "+information.get(1) +"cm");
            weight.setText(" Weight : "+information.get(2)+"kg");
            stage1.setText(" Stage1\n\n  "+information.get(3));
            stage2.setText(" Stage2\n\n  "+information.get(4));
            stage3.setText(" Stage3\n\n  "+information.get(5));
            stage4.setText(" Stage4\n\n  "+information.get(6));
            total.setText(information.get(7)+"m/"+information.get(8)+"kcal");
        }
    }

    class MyListener implements View.OnClickListener{

        int i=0;
        int length=ImageId.length;

        @Override
        public  void onClick(View v){
            portrait.setImageResource(ImageId[i]);
            i++;
            if(i==ImageId.length) i=0;
        }
    }

}