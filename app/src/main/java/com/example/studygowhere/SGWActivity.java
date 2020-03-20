package com.example.studygowhere;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import static com.example.studygowhere.Datahandler.cafeList;
import static com.example.studygowhere.Datahandler.ccList;
import static com.example.studygowhere.Datahandler.libList;
import static com.example.studygowhere.Datahandler.schoolList;
import static com.example.studygowhere.StudyArea.sortByDistance;

import static com.example.studygowhere.Ccdatahandler.addccObjectFlag;
import static com.example.studygowhere.Datahandler.studyAreaList;
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

            insertionSort(studyAreaList);
            mAdapter = new RecyclerAdapter(getApplicationContext(), studyAreaList);
            mRecyclerView.setAdapter(mAdapter);
        }

        else if(parent.getSelectedItem().toString().equals("SCHOOLS"))
        {
            insertionSort(schoolList);
            mAdapter = new RecyclerAdapter(getApplicationContext(), Datahandler.schoolList);
            mRecyclerView.setAdapter(mAdapter);
        }

        else if(parent.getSelectedItem().toString().equals("LIBRARIES"))
        {
            insertionSort(libList);
            mAdapter = new RecyclerAdapter(getApplicationContext(), Datahandler.libList);
            mRecyclerView.setAdapter(mAdapter);
        }

        else if(parent.getSelectedItem().toString().equals("COMMUNITY CENTERS"))
        {
            insertionSort(ccList);
            mAdapter = new RecyclerAdapter(getApplicationContext(), Datahandler.ccList);
            mRecyclerView.setAdapter(mAdapter);
        }

        else if(parent.getSelectedItem().toString().equals("CAFES/RESTAURANTS"))
        {
            insertionSort(cafeList);
            mAdapter = new RecyclerAdapter(getApplicationContext(), Datahandler.cafeList);
            mRecyclerView.setAdapter(mAdapter);
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    public static void insertionSort(List<Object> listsa) {
        StudyArea temp = null;
        for (int i = 1; i < listsa.size(); i++) {
            for(int j = i; j > 0; j--)
            {
                if(((StudyArea)listsa.get(j)).getDistancedouble() < ((StudyArea)listsa.get(j-1)).getDistancedouble())
                {
                    temp = (StudyArea)listsa.get(j);
                    listsa.set(j, listsa.get(j-1));
                    listsa.set(j-1, temp);
                }
                else
                    break;
            }
/*            double current = ((StudyArea)listsa.get(i)).getDistancedouble();
            int j = i - 1;
            while(j >= 0) {
                if(current<((StudyArea)listsa.get(j)).getDistancedouble() ) {
                    listsa.set(j + 1, listsa.get(j));
                }
                j--;
            }
            // at this point we've exited, so j is either -1
            // or it's at the first element where current >= a[j]
            listsa.set(j+1, listsa.get(i));*/
        }
    }

}
