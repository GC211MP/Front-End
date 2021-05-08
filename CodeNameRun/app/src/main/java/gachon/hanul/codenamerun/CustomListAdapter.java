package gachon.hanul.codenamerun;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class CustomListAdapter extends ArrayAdapter{


    private final Activity context;

    private final String[] nameArray ;

    private final String[] recordArray ;

    public CustomListAdapter (Activity context, String [] nameArrayParam, String [] recordArrayParam) {
        super (context, R.layout. listview_ranking , nameArrayParam);

        this.context=context;
        this.nameArray = nameArrayParam;
        this.recordArray = recordArrayParam;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater=context.getLayoutInflater();
        View rowView=inflater.inflate(R.layout.listview_ranking, null,true);

        //this code gets references to objects in the listview_row.xml file
        TextView nameTextField = (TextView) rowView.findViewById(R.id.name);
        TextView recordTextField = (TextView) rowView.findViewById(R.id.record);
        TextView numberTextField =  (TextView) rowView.findViewById(R.id.number);

        //this code sets the values of the objects to values from the arrays
        nameTextField.setText(nameArray[position]);
        recordTextField.setText(recordArray[position]);
        numberTextField.setText(String.valueOf(position + 1));

        int colorvariable;
        int r, g, b;
        if (position < 10){
            colorvariable = position;
        }
        else {
            colorvariable = 10;
        }
        r = 218 + colorvariable;
        g = 116 + colorvariable * 3;
        b = 111 + colorvariable * 3;

        int color = Color.argb(255, r, g, b);
        // numberTextField.setBackgroundColor(color);

        return rowView;

    };



}
