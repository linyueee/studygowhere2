package com.example.studygowhere.Boundary;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.SearchView;
import android.widget.Spinner;

import com.example.studygowhere.Control.RecyclerAdapter;
import com.example.studygowhere.Entity.StudyArea;
import com.example.studygowhere.R;

import java.util.List;

import static com.example.studygowhere.Control.DataHandler.cafeList;
import static com.example.studygowhere.Control.DataHandler.ccList;
import static com.example.studygowhere.Control.DataHandler.libList;
import static com.example.studygowhere.Control.DataHandler.schoolList;
import static com.example.studygowhere.Control.DataHandler.studyAreaList;

public class SGWActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private RecyclerView mRecyclerView;
    //private List<Object> studyAreaList = new ArrayList<>();
    private RecyclerAdapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;
    Spinner dropDownSpinner;
    private RecyclerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sgw);

        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        dropDownSpinner = (Spinner) findViewById(R.id.aSpinner);
        //searchView = (SearchView) findViewById(R.id.searchView);

        dropDownSpinner.setOnItemSelectedListener(this);

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
        }
        else if(parent.getSelectedItem().toString().equals("SCHOOLS"))
        {
            insertionSort(schoolList);
            mAdapter = new RecyclerAdapter(getApplicationContext(), schoolList);
        }

        else if(parent.getSelectedItem().toString().equals("LIBRARIES"))
        {
            insertionSort(libList);
            mAdapter = new RecyclerAdapter(getApplicationContext(), libList);
        }

        else if(parent.getSelectedItem().toString().equals("COMMUNITY CENTERS"))
        {
            insertionSort(ccList);
            mAdapter = new RecyclerAdapter(getApplicationContext(), ccList);
        }

        else if(parent.getSelectedItem().toString().equals("CAFES/RESTAURANTS"))
        {
            insertionSort(cafeList);
            mAdapter = new RecyclerAdapter(getApplicationContext(), cafeList);
        }
        adapter = mAdapter;
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    public static void insertionSort(List<StudyArea> listSA) {
        StudyArea temp = null;
        for (int i = 1; i < listSA.size(); i++) {
            for(int j = i; j > 0; j--)
            {
                if(((StudyArea)listSA.get(j)).getDistanceDouble() < ((StudyArea)listSA.get(j-1)).getDistanceDouble())
                {
                    temp = (StudyArea)listSA.get(j);
                    listSA.set(j, listSA.get(j-1));
                    listSA.set(j-1, temp);
                }
                else
                    break;
            }
        }
    }

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