package gachon.hanul.codenamerun;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;

import gachon.hanul.codenamerun.api.DataDTO;
import gachon.hanul.codenamerun.api.StoreManager;

public class Ranking_stage1 extends Fragment {

    ArrayList<DataDTO> userArray;
    Ranking ranking;

    //OnAttach는 fragment를 붙일 때 호출, getActivity로  액티비티를 찾아준다.
    @Override
    public void onAttach(Context context){
        super.onAttach(context);
        ranking =(Ranking) getActivity();
    }
    @Override
    public void onDetach(){
        super.onDetach();
        ranking = null;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        new RankingInstance().execute();
        super.onCreate(savedInstanceState);

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        ViewGroup view=(ViewGroup)inflater.inflate(R.layout.fragment_ranking_stage1, container, false);
        //ListView생성
        ListView listView=(ListView) view.findViewById(R.id.listView);


        /*여기 이제 랭킹 스트링으로 받아오고 리스트뷰에 보이게 채워야함*/
        CustomListAdapter whatever = new CustomListAdapter(getActivity(), userArray);
        listView.setAdapter(whatever);


        return view;
    }

    private class RankingInstance extends AsyncTask<Void, Integer, Void> {
        @Override
        protected void onPreExecute() {
            StoreManager manager = StoreManager.getInstance(ranking.getApplicationContext());
            userArray = manager.getRankTable("score", true, 1);
        }
        @Override
        protected Void doInBackground(Void... params) {
            return null;
        }
    }
}