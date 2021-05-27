package gachon.hanul.codename;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;

import gachon.hanul.codename.api.DataDTO;
import gachon.hanul.codename.api.StoreManager;

public class Ranking_stage3 extends Fragment {

    ArrayList<DataDTO> userArray;
    Ranking ranking;
    ListView listView;

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
        userArray = new ArrayList<DataDTO>();
        new Ranking_stage3.RankingInstance().execute();
        super.onCreate(savedInstanceState);

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        ViewGroup view=(ViewGroup)inflater.inflate(R.layout.fragment_ranking_stage3, container, false);
        //ListView생성
        listView = (ListView) view.findViewById(R.id.listView);


        /*여기 이제 랭킹 스트링으로 받아오고 리스트뷰에 보이게 채워야함*/

        CustomListAdapter whatever = new CustomListAdapter(getActivity(), userArray);
        listView.setAdapter(whatever);

        return view;
    }
    private class RankingInstance extends AsyncTask<Void, ArrayList<DataDTO>, Void> {

        @Override
        protected Void doInBackground(Void... params) {
            StoreManager manager = StoreManager.getInstance(ranking.getApplicationContext());
            ArrayList<DataDTO> result = manager.getRankTable("score", false, 3);
            publishProgress(result);
            return null;
        }

        @Override
        protected void onProgressUpdate(ArrayList<DataDTO>... values) {
            super.onProgressUpdate(values);
            userArray = values[0];
            CustomListAdapter whatever = new CustomListAdapter(getActivity(), userArray);
            listView.setAdapter(whatever);
        }
    }
}