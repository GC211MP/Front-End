package gachon.hanul.codenamerun;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import gachon.hanul.codenamerun.api.DataDTO;

public class CustomListAdapter extends ArrayAdapter{


    private final Activity context;

    private ArrayList<DataDTO> userArrayParam;

    public CustomListAdapter (Activity context, ArrayList<DataDTO>  userArrayParam) {
        super (context, R.layout. listview_ranking , userArrayParam);

        this.context=context;
        this.userArrayParam = userArrayParam;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {

        int colorvariable;

        if (position > 21){
            colorvariable = 21;
        }
        else {
            colorvariable = position;
        }
        int r, g, b;

        r = 102 + colorvariable * 7;
        g = 54 + colorvariable * 8;
        b = 53 + colorvariable * 8;


        int color = Color.rgb(r, g, b);

        Drawable sampleDrawable = context.getResources().getDrawable(R.drawable.round, null);
        sampleDrawable.setTint(color);

        LayoutInflater inflater=context.getLayoutInflater();
        View rowView=inflater.inflate(R.layout.listview_ranking, null,true);



        //this code gets references to objects in the listview_row.xml file
        TextView nameTextField = (TextView) rowView.findViewById(R.id.name);
        TextView recordTextField = (TextView) rowView.findViewById(R.id.record);
        TextView numberTextField =  (TextView) rowView.findViewById(R.id.number);

        //this code sets the values of the objects to values from the arrays
        nameTextField.setText(userArrayParam.get(position).getUser_name());
        recordTextField.setText("Score: " + userArrayParam.get(position).getScore() + "\n" +
                "Distance: " + userArrayParam.get(position).getDistance() +
                "       Calorie: " + userArrayParam.get(position).getCalorie());
        numberTextField.setText(String.valueOf(position + 1));





        return rowView;

    };



}
