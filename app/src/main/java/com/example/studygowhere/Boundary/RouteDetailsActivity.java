package com.example.studygowhere.Boundary;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.studygowhere.R;

import java.util.ArrayList;

/**
 * <h1>Route Details UI</h1>
 * This is an user interface that allows user to view details of the public transport route selected.
 *
 * @author ILOVESSADMORE
 * @version 1.0
 */

public class RouteDetailsActivity extends AppCompatActivity {
    /**
     * Instance variable InstList.
     * Refers to the ListView InstList in the XML Layout
     */
    private ListView instList;
    /**
     * Instance variable instAdapter
     * ArrayAdapter containing strings from route details to put in ListView
     */
    private ArrayAdapter<String> instAdapter;
    /**
     * Override method to initialize RouteDetailsActivity
     *
     * @param savedInstanceState The last saved instance state
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_route_details);
        instList = (ListView) findViewById( R.id.InstList );
        ArrayList<String> instructions= getIntent().getStringArrayListExtra("details");
        // Create ArrayAdapter using the planet list.
        instAdapter = new ArrayAdapter<String>(this, R.layout.routedetailsrow, instructions);

        // Set the ArrayAdapter as the ListView's adapter.
        instList.setAdapter( instAdapter );
    }
}
