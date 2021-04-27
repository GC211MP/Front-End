package gachon.hanul.codenamerun;

import android.app.Activity;
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

    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater=context.getLayoutInflater();
        View rowView=inflater.inflate(R.layout.listview_ranking, null,true);

        //this code gets references to objects in the listview_row.xml file
        TextView nameTextField = (TextView) rowView.findViewById(R.id.name);
        TextView recordTextField = (TextView) rowView.findViewById(R.id.record);


        //this code sets the values of the objects to values from the arrays
        nameTextField.setText(nameArray[position]);
        recordTextField.setText(recordArray[position]);

        return rowView;

    };


}
