package gachon.hanul.codenamerun;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;

public class AgentRanking extends Fragment {

    Home home;

    /* Ranking fragments */
    Ranking_stage1 ranking_stage1;
    Ranking_stage2 ranking_stage2;
    Ranking_stage3 ranking_stage3;
    Ranking_stage4 ranking_stage4;



    String[] nameArray = {"이름 1","이름 2","이름 3","이름 4","이름 5","이름 6" };

    String[] recordArray = {
            "100",
            "80",
            "70",
            "60",
            "50",
            "40"};


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
    public void onCreate(Bundle savedInstanceState) { super.onCreate(savedInstanceState); }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        ViewGroup view=(ViewGroup)inflater.inflate(R.layout.fragment_agent_ranking, container, false);
        getChildFragmentManager().beginTransaction().replace(R.id.fragment_container,ranking_stage1).commit();

        /*getView()는 fragment root의 view를 가지고 온다*/
        Button ranking_stage1_btn =getView().findViewById(R.id.ranking_stage1_btn);
        Button ranking_stage2_btn =getView().findViewById(R.id.ranking_stage2_btn);
        Button ranking_stage3_btn =getView().findViewById(R.id.ranking_stage3_btn);
        Button ranking_stage4_btn =getView().findViewById(R.id.ranking_stage4_btn);


        /*Ranking_stage1_btn listener*/
        ranking_stage1_btn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                getChildFragmentManager().beginTransaction().replace(R.id.fragment_container,ranking_stage1).commit();
            }
        });

        /*Ranking_stage2_btn listener*/
        ranking_stage2_btn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                getChildFragmentManager().beginTransaction().replace(R.id.fragment_container,ranking_stage2).commit();
            }
        });
        /*Ranking_stage3_btn listener*/
        ranking_stage3_btn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                getChildFragmentManager().beginTransaction().replace(R.id.fragment_container,ranking_stage3).commit();
            }
        });

        /*Ranking_stage4_btn listener*/
        ranking_stage4_btn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                getChildFragmentManager().beginTransaction().replace(R.id.fragment_container,ranking_stage4).commit();
            }
        });

/*

        CustomListAdapter whatever = new CustomListAdapter(getActivity(), nameArray, recordArray);
        ListView listView = (ListView) getView().findViewById(R.id.listView);
        listView.setAdapter(whatever);
*/



        return view;
    }
}