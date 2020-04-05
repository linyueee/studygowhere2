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

import com.example.studygowhere.Control.StudyAreaRecyclerAdapter;
import com.example.studygowhere.Entity.StudyArea;
import com.example.studygowhere.R;

import java.util.List;

import static com.example.studygowhere.Control.DataHandler.cafeList;
import static com.example.studygowhere.Control.DataHandler.ccList;
import static com.example.studygowhere.Control.DataHandler.libList;
import static com.example.studygowhere.Control.DataHandler.schoolList;
import static com.example.studygowhere.Control.DataHandler.studyAreaList;


/**
 * <h1>StudyGoWhere UI</h1>
 * This is an user interface that allows user to view all Study Areas in list-view.
 * All Study Area will be sorted by distance away from user current location by default.
 * Upon selecting different categories in the dropdown list, the Study Areas will be sorted according to category.
 * By clicking the search icon on the toolbar, the user can input the name of the desired Study Area.
 * Upon clicking a Study Area, DetailActivity of the selected Study Area will be started.
 *
 * @author ILOVESSADMORE
 * @version 1.0
 */
public class SGWActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    /**
     * Instance variable where RecyclerView recycler_view in the XML file will be assigned to.
     */
    private RecyclerView mRecyclerView;

    /**
     * Instance variable to store the StudyAreaRecyclerAdapter Object
     */
    private StudyAreaRecyclerAdapter mAdapter;

    /**
     * Instance variable to store the LinearLayoutManager Object
     */
    private RecyclerView.LayoutManager layoutManager;

    /**
     * Instance variable where Spinner aSpinner in the XML file will be assigned to.
     */
    Spinner dropDownSpinner;

    /**
     * Instance variable to filter the recycler view when searching.
     */
    private StudyAreaRecyclerAdapter adapter;


    /**
     * Override method to assign value to instance variables.
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sgw);

        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        dropDownSpinner = (Spinner) findViewById(R.id.aSpinner);

        dropDownSpinner.setOnItemSelectedListener(this);

        mRecyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(layoutManager);
    }


    /**
     * This is an override method of the spinner.
     * It checks the String value of the selected position and passes the corresponding list into the adapter.
     * All lists will be sorted by distance away from user current location using insertion sort before be passed to the adapter.
     * @param parent
     * @param view
     * @param position
     * @param id
     */
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        if(parent.getSelectedItem().toString().equals("ALL"))
        {
            insertionSort(studyAreaList);
            mAdapter = new StudyAreaRecyclerAdapter(getApplicationContext(), studyAreaList);
        }
        else if(parent.getSelectedItem().toString().equals("SCHOOLS"))
        {
            insertionSort(schoolList);
            mAdapter = new StudyAreaRecyclerAdapter(getApplicationContext(), schoolList);
        }

        else if(parent.getSelectedItem().toString().equals("LIBRARIES"))
        {
            insertionSort(libList);
            mAdapter = new StudyAreaRecyclerAdapter(getApplicationContext(), libList);
        }

        else if(parent.getSelectedItem().toString().equals("COMMUNITY CENTERS"))
        {
            insertionSort(ccList);
            mAdapter = new StudyAreaRecyclerAdapter(getApplicationContext(), ccList);
        }

        else if(parent.getSelectedItem().toString().equals("CAFES/RESTAURANTS"))
        {
            insertionSort(cafeList);
            mAdapter = new StudyAreaRecyclerAdapter(getApplicationContext(), cafeList);
        }
        adapter = mAdapter;
        mRecyclerView.setAdapter(mAdapter);
    }


    /**
     * This is an override method of the spinner
     * @param parent
     */
    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }


    /**
     * This is a method to sort the list of Study Area passed in by DistanceDouble attribute which is the distance away from user current location.
     * @param listSA list the Study Area
     */
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


    /**
     * This is an override method to create the search icon and create search listener
     * @param menu
     * @return
     */
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