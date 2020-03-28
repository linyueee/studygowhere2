package com.example.studygowhere.Boundary;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.studygowhere.R;

import java.util.ArrayList;

public class RouteDetailsActivity extends AppCompatActivity {

    private ListView InstList;
    private ArrayAdapter<String> instAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_route_details);
        InstList = (ListView) findViewById( R.id.InstList );
        ArrayList<String> instructions= getIntent().getStringArrayListExtra("details");
        // Create ArrayAdapter using the planet list.
        instAdapter = new ArrayAdapter<String>(this, R.layout.routedetailsrow, instructions);

        // Set the ArrayAdapter as the ListView's adapter.
        InstList.setAdapter( instAdapter );
    }
}
