package com.example.studygowhere.Boundary;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import static com.example.studygowhere.Boundary.LoginActivity.getCurrentUserName;
import static com.example.studygowhere.Boundary.LoginActivity.getUn;
import static com.example.studygowhere.Boundary.LoginActivity.setCurrentUserName;
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

public class ProfileActivity extends AppCompatActivity {

    public static boolean bookMarkFlag;
    TextView tvName;
    Button btnBack, btnLogout, btnMyReview;
    static RecyclerView rvBookmark;
    static Context context;
    static boolean bookmarkFlag = false;
    static List<StudyArea> BookmarkList;


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
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ProfileActivity.this, MapsActivity.class);
                startActivity(i);
            }
        });
        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setUn(null);
                Intent i = new Intent(ProfileActivity.this, LoginActivity.class);
                startActivity(i);
            }
        });

        btnMyReview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ProfileActivity.this, MyReviewActivity.class);
                startActivity(i);
            }
        });

        Worker readBm = new Worker(this);
        readBm.ReadBookmark(getUn());
/*        ReadBookmarkWorker rbw = new ReadBookmarkWorker(this);
        rbw.execute(getUn());*/
    }

    static public void DisplayBookmark(String result) {
        List<String> myBookmarkString;
        RecyclerView.Adapter mAdapter;
        RecyclerView.LayoutManager layoutManager;
        if (!bookmarkFlag || !getCurrentUserName().equals(getUn())) {
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
            setCurrentUserName(getUn());
        }
        rvBookmark.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(ProfileActivity.getContext());
        rvBookmark.setLayoutManager(layoutManager);
        mAdapter = new StudyAreaRecyclerAdapter(ProfileActivity.getContext(), BookmarkList);
        rvBookmark.setAdapter(mAdapter);
    }

    static public Context getContext()
    {
        return context;
    }
}
