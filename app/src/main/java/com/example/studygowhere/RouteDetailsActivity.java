package com.example.studygowhere;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class RouteDetailsActivity extends AppCompatActivity {

    private ListView Instlist;
    private ArrayAdapter<String> instAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_route_details2);
        Instlist = (ListView) findViewById( R.id.InstList );
        ArrayList<String> instructions= getIntent().getStringArrayListExtra("details");
        // Create ArrayAdapter using the planet list.
        instAdapter = new ArrayAdapter<String>(this, R.layout.routedetailsrow, instructions);

        // Set the ArrayAdapter as the ListView's adapter.
        Instlist.setAdapter( instAdapter );
    }
}
