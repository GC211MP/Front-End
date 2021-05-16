package gachon.hanul.codenamerun;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;

import gachon.hanul.codenamerun.api.DataDTO;
import gachon.hanul.codenamerun.api.PersonalData;
import gachon.hanul.codenamerun.api.StoreManager;


public class AgentInformation extends Fragment {


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


    //OnAttach는 fragment를 붙일 때 호출, getActivity로  액티비티를 찾아준다.
    @Override
    public void onAttach(Context context){
        super.onAttach(context);
        home = (Home)getActivity();
    }
    @Override
    public void onDetach(){
        super.onDetach();
       home = null;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.e("dahye", "여기까지 왓니111111111111?");


    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        ViewGroup view=(ViewGroup)inflater.inflate(R.layout.fragment_agent_information, container, false);

        name= view.findViewById(R.id.agent_name);
        height= view.findViewById(R.id.agent_height);
        weight=view.findViewById(R.id.agent_weight);
        stage1= view.findViewById(R.id.score_1stage);
        stage2= view.findViewById(R.id.score_2stage);
        stage3= view.findViewById(R.id.score_3stage);
        stage4= view.findViewById(R.id.score_4stage);
        total= view.findViewById(R.id.agent_distance_colories);

        Log.e("dahye", "여기까지 왓니222222222?");
        information = new ArrayList<String>();
        information=null;
        new InformationInstance().execute();
        Log.e("dahye", "여기까지 왓333333?");

/*        name.setText(" Name : "+ information.get(0));
        height.setText(" Height : "+information.get(1));
        weight.setText(" Weight : "+information.get(2));
        stage1.setText(" Stage1\\n\\n   "+information.get(3));
        stage2.setText(" Stage2\\n\\n   "+information.get(4));
        stage3.setText(" Stage3\\n\\n   "+information.get(5));
        stage4.setText(" Stage4\\n\\n   "+information.get(6));
        total.setText(information.get(7)+"/"+information.get(8));*/

        return view;
    }


    private class InformationInstance extends AsyncTask<Void, ArrayList<String>, Void> {


        @Override
        protected Void doInBackground(Void... params) {
            manager = StoreManager.getInstance(home.getApplicationContext()); //서버 불러오기
            Integer[] stages = {};
            PersonalData user = manager.readUserData(stages);
   /*         ArrayList<DataDTO> stage1 = manager.getRankTable("score", false, 1);
            ArrayList<DataDTO> stage2 = manager.getRankTable("score", false, 2);
            ArrayList<DataDTO> stage3 = manager.getRankTable("score", false, 3);
            ArrayList<DataDTO> stage4 = manager.getRankTable("score", false, 4);*/

         /*   ArrayList information; //agent Info를 순서대로 담고 있다.
            information = new ArrayList<String>();*/
            ArrayList<String> temp = new ArrayList<String>();
            Log.e("dahye", "여기까지 왓니?");
            temp.add(user.userName);
            temp.add(String.valueOf(user.userHeight));
            temp.add(String.valueOf(user.userWeight));
            temp.add("임시점수1");
            temp.add("임시점수2");
            temp.add("임시점수3");
            temp.add("임시점수4");

/*          temp.add(String.valueOf(stage1.get(4)));
            temp.add(String.valueOf(stage2.get(0)));
            temp.add(String.valueOf(stage3.get(0)));
            temp.add(String.valueOf(stage4.get(0)));*/
            temp.add(String.valueOf(manager.getTotalDistance(-1)));
            temp.add(String.valueOf(manager.getTotalCalorie(-1)));


            Log.e("dahye", "name까지 왓니?");

            System.out.println("내이름/"+temp.get(0));
            System.out.println("내키/"+temp.get(1));
            System.out.println("내몸무게/"+temp.get(2));

/*            System.out.println("내stage1/"+temp.get(3));
            System.out.println("내stage2/"+temp.get(4));
            System.out.println("내stage3/"+temp.get(5));
            System.out.println("내stage4/"+temp.get(6));*/
            System.out.println("내거리/"+temp.get(7));
            System.out.println("내칼로리/"+temp.get(8));



/*          information.add(user.userWeight);
            information.add(stage1.get(0));
            information.add(stage2.get(0));
            information.add(stage3.get(0));
            information.add(stage4.get(0));
            information.add(manager.getTotalDistance(-1));
            information.add(manager.getTotalCalorie(-1));*/

            publishProgress(temp);
            return null;
        }

        @Override
        protected void onProgressUpdate( ArrayList<String>... values) {
            super.onProgressUpdate(values);
            Log.e("dahye", "onprogressupdate까지 왓니?");
            information = values[0];
            name.setText(" Name : "+ information.get(0));
            height.setText(" Height : "+information.get(1));
            weight.setText(" Weight : "+information.get(2));
            stage1.setText(" Stage1\n\n   "+information.get(3));
            stage2.setText(" Stage2\n\n   "+information.get(4));
            stage3.setText(" Stage3\n\n   "+information.get(5));
            stage4.setText(" Stage4\n\n   "+information.get(6));
            total.setText(information.get(7)+"m/"+information.get(8)+"kcal");



      /*     name.setText(" Name : "+ values[0]);
           height.setText(" Height : "+values[1]);
            weight.setText(" Weight : "+values[2]);
            stage1.setText(" Stage1\\n\\n   "+values[3]);
            stage2.setText(" Stage2\\n\\n   "+values[4]);
            stage3.setText(" Stage3\\n\\n   "+values[4]);
            stage4.setText(" Stage4\\n\\n   "+values[6]);
            total.setText(values[7]+"/"+values[8]);*/

        }
    }


}