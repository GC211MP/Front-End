package gachon.hanul.codenamerun;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class SelectStage extends Fragment {

    Home home;

    Button PrologueStageButton;
    Button Stage1Button;
    Button Stage2Button;
    Button Stage3Button;
    Button Stage4Button;


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
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        ViewGroup view=(ViewGroup)inflater.inflate(R.layout.fragment_select_stage, container, false);


        PrologueStageButton = view.findViewById(R.id.PrologueStageButton);
        Stage1Button = view.findViewById(R.id.Stage1Button);
        Stage2Button = view.findViewById(R.id.Stage2Button);
        Stage3Button = view.findViewById(R.id.Stage3Button);
        Stage4Button = view.findViewById(R.id.Stage4Button);
       /* stage buttons */
        PrologueStageButton.setOnClickListener(v -> {
            Intent PrologueIntent = new Intent(getActivity(), Running.class);
            PrologueIntent.putExtra("stageName", "Prologue");
            startActivity(PrologueIntent);
        });

        Stage1Button.setOnClickListener(v -> {
            Intent Stage1Intent = new Intent(getActivity(), Running.class);
            Stage1Intent.putExtra("stageName", "Stage1");
            startActivity(Stage1Intent);
        });

        Stage2Button.setOnClickListener(v -> {
            Intent Stage2Intent = new Intent(getActivity(), Running.class);
            Stage2Intent.putExtra("stageName", "Stage2");
            startActivity(Stage2Intent);
        });

        Stage3Button.setOnClickListener(v -> {
            Intent Stage3Intent= new Intent(getActivity(), Running.class);
            Stage3Intent.putExtra("stageName", "Stage3");
            startActivity(Stage3Intent);
        });

        Stage4Button.setOnClickListener(v -> {
            Intent Stage4Intent= new Intent(getActivity(), Running.class);
            Stage4Intent.putExtra("stageName", "Stage4");
            startActivity(Stage4Intent);
        });
        return view;
    }
}