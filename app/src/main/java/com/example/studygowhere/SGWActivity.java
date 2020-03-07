package com.example.studygowhere;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.gms.maps.GoogleMap;
import com.google.maps.android.geojson.GeoJsonFeature;
import com.google.maps.android.geojson.GeoJsonLayer;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import static com.example.studygowhere.Ccdatahandler.addccObjectFlag;
import static com.example.studygowhere.Librarydatahandler.addLibObjectFlag;

public class SGWActivity extends AppCompatActivity  {

    private RecyclerView mRecyclerView;
//    private List<Object> studyAreaList = new ArrayList<>();
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sgw);

        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);



        mRecyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(layoutManager);

        mAdapter = new RecyclerAdapter(getApplicationContext(), Datahandler.studyAreaList);
        mRecyclerView.setAdapter(mAdapter);


    }

/*    public void onItemClick(int position)
    {
        Intent selected = new Intent(this, DetailActivity.class);
        StudyArea sa = (StudyArea) Datahandler.studyAreaList.get(position);
        selected.putExtra("Name", sa.getName());
        this.startActivity(selected);
    }*/


/*    private void addobject(GeoJsonLayer layer)
    {
        for(GeoJsonFeature feature:layer.getFeatures())
        {
            if(feature.getProperty("Name") != null)
            {
                Toast toast=Toast. makeText(getApplicationContext(),feature.getProperty("Name"), Toast.LENGTH_SHORT);
                toast.show();
                String name = feature.getProperty("Name");
                StudyArea sa = new StudyArea();
                sa.setName(name);
                studyAreaList.add(sa);
            }


        }
    }*/

}
