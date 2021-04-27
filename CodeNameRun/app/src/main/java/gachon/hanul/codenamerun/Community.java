package gachon.hanul.codenamerun;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;

public class Community extends AppCompatActivity {

    ImageButton AgentInfo;
    ImageButton Home;
    ImageButton Community;

    String[] nameArray = {"이름 1","이름 2","이름 3","이름 4","이름 5","이름 6" };

    String[] recordArray = {
            "100",
            "80",
            "70",
            "60",
            "50",
            "40"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_community);

        CustomListAdapter whatever = new CustomListAdapter(this, nameArray, recordArray);
        ListView listView = (ListView) findViewById(R.id.listView);
        listView.setAdapter(whatever);

        /* find view by id */
        AgentInfo =  findViewById(R.id.AgentInfoButton);
        Home =  findViewById(R.id.HomeButton);
        Community =  findViewById(R.id.CommunityButton);

        /* menu buttons */
        AgentInfo.setOnClickListener(v -> {
            Intent AgentInfoIntent= new Intent(getApplicationContext(), AgentInfo.class);
            startActivity(AgentInfoIntent);
        });

        Home.setOnClickListener(v -> {
            Intent HomeIntent= new Intent(getApplicationContext(), Home.class);
            startActivity(HomeIntent);
        });

        Community.setOnClickListener(v -> {
            Intent CommunityIntent= new Intent(getApplicationContext(), Community.class);
            startActivity(CommunityIntent);
        });
    }
}