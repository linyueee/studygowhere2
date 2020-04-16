package com.example.studygowhere.Boundary;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import static com.example.studygowhere.Boundary.LoginActivity.getCurrentUsername;
import static com.example.studygowhere.Boundary.LoginActivity.getUn;
import static com.example.studygowhere.Boundary.LoginActivity.setCurrentUsername;
import static com.example.studygowhere.Boundary.LoginActivity.setUn;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.studygowhere.Control.DataHandler;
import com.example.studygowhere.Control.StudyAreaRecyclerAdapter;
import com.example.studygowhere.Control.Worker;
import com.example.studygowhere.Entity.StudyArea;
import com.example.studygowhere.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.List;

/**
 * <h1>My Profile UI</h1>
 * This is an user interface that displays the user's name and profile picture as well as the bookmarks that the user added.
 *
 * @author ILOVESSADMORE
 * @version 1.0
 */
public class ProfileActivity extends AppCompatActivity {

    /**
     * Static variable that is set to false when there is an update in user's bookmark.
     */
    public static boolean bookmarkFlag = false;

    /**
     * Instance variable where TextView tvName in the XML file will be assigned to.
     */
    TextView tvName;

    /**
     * Instance variable where Button btnBack in the XML file will be assigned to.
     */
    Button btnBack;

    /**
     * Instance variable where Button btnLogout in the XML file will be assigned to.
     */
    Button btnLogout;

    /**
     * Instance variable where Button btnMyReviews in the XML file will be assigned to.
     */
    Button btnMyReview;

    /**
     * Static variable where RecyclerView recycler_view_profile in the XML file will be assigned to.
     */
    static RecyclerView rvBookmark;

    /**
     * Static variable that store the context of ProfileActivity.
     */
    static Context context;

    /**
     * Static variable that store the list of bookmark added.
     */
    static List<StudyArea> BookmarkList;

    /**
     * Override method to assign value to instance variables.
     * It also calls the ReadBookmark method by passing in Un as parameter.
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        btnBack = (Button) findViewById(R.id.btnBack);
        btnLogout = (Button) findViewById(R.id.btnLogout);
        tvName = (TextView) findViewById(R.id.tvName);
        rvBookmark = (RecyclerView) findViewById(R.id.recycler_view_profile);
        btnMyReview = (Button) findViewById(R.id.btnMyReviews);
        context = getApplicationContext();
        tvName.setText(getUn());

        btnBack.setOnClickListener(new View.OnClickListener() {
            /**
             * Upon clicking btnBack, MapsActivity class will be started.
             * @param v
             */
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ProfileActivity.this, MapsActivity.class);
                startActivity(i);
            }
        });


        btnLogout.setOnClickListener(new View.OnClickListener() {
            /**
             * Upon clicking btnLogout, LoginActivity class will be started.
             * @param v
             */
            @Override
            public void onClick(View v) {
                setUn(null);
                Intent i = new Intent(ProfileActivity.this, LoginActivity.class);
                startActivity(i);
            }
        });

        btnMyReview.setOnClickListener(new View.OnClickListener() {
            /**
             * Upon clicking btnMyReview, MyReviewActivity class will be started.
             * @param v
             */
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ProfileActivity.this, MyReviewActivity.class);
                startActivity(i);
            }
        });

        Worker readBm = new Worker(this);
        readBm.ReadBookmark(getUn());
    }


    /**
     * This method is to parse the JSon string returned from the Database and add the Study Area names that the user
     * has added into a bookmark arraylist. This arraylist of Study Area name is later compared with the arrarlist containing
     * all Study Areas to find out other attributes of the corresponding Study Area.
     * The Study Areas with name matches the name in bookmark list is put in another arraylist.
     * This arraylist is then passed into an adapter to be displayed in recycler view.
     * @param result JSon string returned from the Database containing the name of Study Area.
     */
    static public void DisplayBookmark(String result) {
        List<String> myBookmarkString;
        RecyclerView.Adapter mAdapter;
        RecyclerView.LayoutManager layoutManager;
        if (!bookmarkFlag || !getCurrentUsername().equals(getUn())) {
            BookmarkList = new ArrayList<>();
            myBookmarkString = new ArrayList<>();
            try {
                JSONObject jsonObject = new JSONObject(result);
                int success = jsonObject.getInt("success");
                if (success == 1) {
                    JSONArray bookmarks = jsonObject.getJSONArray("bookmark");
                    for (int i = 0; i < bookmarks.length(); i++) {
                        JSONObject bookmark = bookmarks.getJSONObject(i);
                        String studyAreaName = bookmark.getString("studyareaname");
                        myBookmarkString.add(studyAreaName);
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            for (int i = 0; i < DataHandler.studyAreaList.size(); i++) {
                StudyArea temp = (StudyArea) DataHandler.studyAreaList.get(i);
                String tempName = temp.getName();
                for (int j = 0; j < myBookmarkString.size(); j++) {
                    if ((tempName.equals(myBookmarkString.get(j)))) {
                        BookmarkList.add(temp);
                    }
                }
            }
            ProfileActivity.bookmarkFlag = true;
            setCurrentUsername(getUn());
        }
        rvBookmark.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(ProfileActivity.getContext());
        rvBookmark.setLayoutManager(layoutManager);
        mAdapter = new StudyAreaRecyclerAdapter(ProfileActivity.getContext(), BookmarkList);
        rvBookmark.setAdapter(mAdapter);
    }


    /**
     * Getter method of context
     * @return context
     */
    static public Context getContext()
    {
        return context;
    }
}
