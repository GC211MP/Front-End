package gachon.hanul.codenamerun;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.ShapeDrawable;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

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

        int colorvariable = position;
        int r, g, b;

        r = 102 + colorvariable * 20;
        g = 54 + colorvariable * 20;
        b = 53 + colorvariable * 20;

        int color = Color.rgb(r, g, b);


        LayoutInflater inflater=context.getLayoutInflater();
        View rowView=inflater.inflate(R.layout.listview_ranking, null,true);

        Drawable sampleDrawable = context.getResources().getDrawable(R.drawable.round, null);
        sampleDrawable.setTint(color);

        //this code gets references to objects in the listview_row.xml file
        TextView nameTextField = (TextView) rowView.findViewById(R.id.name);
        TextView recordTextField = (TextView) rowView.findViewById(R.id.record);
        TextView numberTextField =  (TextView) rowView.findViewById(R.id.number);

        //this code sets the values of the objects to values from the arrays
        nameTextField.setText(nameArray[position]);
        recordTextField.setText(recordArray[position]);
        numberTextField.setText(String.valueOf(position + 1));





        return rowView;

    };



}
