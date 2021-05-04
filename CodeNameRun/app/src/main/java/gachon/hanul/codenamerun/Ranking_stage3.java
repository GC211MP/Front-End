package gachon.hanul.codenamerun;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

public class Ranking_stage3 extends Fragment {

    Community community;

    //OnAttach는 fragment를 붙일 때 호출, getActivity로  액티비티를 찾아준다.
    @Override
    public void onAttach(Context context){
        super.onAttach(context);
        community = (Community)getActivity();
    }
    @Override
    public void onDetach(){
        super.onDetach();
        community = null;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        ViewGroup view=(ViewGroup)inflater.inflate(R.layout.fragment_ranking_stage3, container, false);
        //ListView생성
        ListView listView=(ListView) view.findViewById(R.id.listView);


        /*여기 이제 랭킹 스트링으로 받아오고 리스트뷰에 보이게 채워야함*/





        return view;
    }
}