package com.example.studygowhere;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;
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

public class SGWActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private RecyclerView mRecyclerView;
//    private List<Object> studyAreaList = new ArrayList<>();
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;
    Spinner dropdownspinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sgw);

        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        dropdownspinner = (Spinner) findViewById(R.id.aSpinner);

        dropdownspinner.setOnItemSelectedListener(this);


        mRecyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(layoutManager);



    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        if(parent.getSelectedItem().toString().equals("ALL"))
        {
            mAdapter = new RecyclerAdapter(getApplicationContext(), Datahandler.studyAreaList);
            mRecyclerView.setAdapter(mAdapter);
        }

        else if(parent.getSelectedItem().toString().equals("SCHOOLS"))
        {
            mAdapter = new RecyclerAdapter(getApplicationContext(), Datahandler.schoolList);
            mRecyclerView.setAdapter(mAdapter);
        }

        else if(parent.getSelectedItem().toString().equals("LIBRARIES"))
        {
            mAdapter = new RecyclerAdapter(getApplicationContext(), Datahandler.libList);
            mRecyclerView.setAdapter(mAdapter);
        }

        else if(parent.getSelectedItem().toString().equals("COMMUNITY CENTERS"))
        {
            mAdapter = new RecyclerAdapter(getApplicationContext(), Datahandler.ccList);
            mRecyclerView.setAdapter(mAdapter);
        }

        else if(parent.getSelectedItem().toString().equals("CAFES/RESTAURANTS"))
        {
            mAdapter = new RecyclerAdapter(getApplicationContext(), Datahandler.cafeList);
            mRecyclerView.setAdapter(mAdapter);
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }



}
