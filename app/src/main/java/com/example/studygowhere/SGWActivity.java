package com.example.studygowhere;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.SearchView;
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
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import static com.example.studygowhere.Ccdatahandler.addccObjectFlag;
import static com.example.studygowhere.Datahandler.studyAreaList;
import static com.example.studygowhere.Librarydatahandler.addLibObjectFlag;

public class SGWActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private RecyclerView mRecyclerView;
//    private List<Object> studyAreaList = new ArrayList<>();
    private RecyclerAdapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;
    Spinner dropdownspinner;

    //steffi add
    private RecyclerAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sgw);

        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        dropdownspinner = (Spinner) findViewById(R.id.aSpinner);
        //searchView = (SearchView) findViewById(R.id.searchView);

        dropdownspinner.setOnItemSelectedListener(this);

        mRecyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(layoutManager);

        //Steffi added
/*        List<StudyArea> tempList = new ArrayList<>();
        for(int i=0; i< studyAreaList.size();i++) {
            StudyArea temp = (StudyArea) studyAreaList.get(i);
            if(temp!=null) {
                tempList.add(temp);
            }
        }*/
        //adapter = new RecyclerAdapter(getApplicationContext(), studyAreaList);
        //mRecyclerView.setAdapter(adapter);

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        if(parent.getSelectedItem().toString().equals("ALL"))
        {
            //studyAreaList.sort(new DistanceSorter());
            mAdapter = new RecyclerAdapter(getApplicationContext(), studyAreaList);
            //mRecyclerView.setAdapter(mAdapter);
        }

        else if(parent.getSelectedItem().toString().equals("SCHOOLS"))
        {
            mAdapter = new RecyclerAdapter(getApplicationContext(), Datahandler.schoolList);
            //mRecyclerView.setAdapter(mAdapter);
        }

        else if(parent.getSelectedItem().toString().equals("LIBRARIES"))
        {
            mAdapter = new RecyclerAdapter(getApplicationContext(), Datahandler.libList);
            //mRecyclerView.setAdapter(mAdapter);
        }

        else if(parent.getSelectedItem().toString().equals("COMMUNITY CENTERS"))
        {
            mAdapter = new RecyclerAdapter(getApplicationContext(), Datahandler.ccList);
            //mRecyclerView.setAdapter(mAdapter);
        }

        else if(parent.getSelectedItem().toString().equals("CAFES/RESTAURANTS"))
        {
            mAdapter = new RecyclerAdapter(getApplicationContext(), Datahandler.cafeList);
            //mRecyclerView.setAdapter(mAdapter);
        }
        adapter = mAdapter;
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    // Steffi added
    @SuppressLint("ResourceType")
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) searchItem.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.getFilter().filter(newText);
                return false;
            }
        });
        return true;
    }
}
