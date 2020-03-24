package com.example.studygowhere;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import static com.example.studygowhere.LoginActivity.getCurrentusername;
import static com.example.studygowhere.LoginActivity.getUn;
import static com.example.studygowhere.LoginActivity.setCurrentusername;
import static com.example.studygowhere.LoginActivity.setUn;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.List;

public class ProfileActivity extends AppCompatActivity {

    TextView tvName;
    Button btnBack, btnLogout, btnmyreview;
    static RecyclerView rvbookmark;
    static Context context;
    static boolean bookmarkflag = false;
    static List<StudyArea> BookmarkList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        btnBack = (Button) findViewById(R.id.btnBack);
        btnLogout = (Button) findViewById(R.id.btnLogout);
        tvName = (TextView) findViewById(R.id.tvname);
        rvbookmark = (RecyclerView) findViewById(R.id.recycler_view_profile);
        btnmyreview = (Button) findViewById(R.id.btnmyreviews);
        context = getApplicationContext();


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

        btnmyreview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ProfileActivity.this, MyReviewActivity.class);
                startActivity(i);
            }
        });

        tvName.setText(getUn());


        Readbookmarkworker rbw = new Readbookmarkworker(this);
        rbw.execute(getUn());

    }

    static public void Displaybookmark(String result) {
        List<String> mybookmarkstring;
        RecyclerView.Adapter mAdapter;
        RecyclerView.LayoutManager layoutManager;
        if (!bookmarkflag || getCurrentusername().equals(getUn()) == false) {
            BookmarkList = new ArrayList<>();
            mybookmarkstring = new ArrayList<>();
            try {
                JSONObject jsonObject = new JSONObject(result);
                int success = jsonObject.getInt("success");
                if (success == 1) {
                    JSONArray bookmarks = jsonObject.getJSONArray("bookmark");
                    for (int i = 0; i < bookmarks.length(); i++) {
                        JSONObject bookmark = bookmarks.getJSONObject(i);
                        String studyareaname = bookmark.getString("studyareaname");
                        mybookmarkstring.add(studyareaname);

                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            for (int i = 0; i < Datahandler.studyAreaList.size(); i++) {
                StudyArea temp = (StudyArea) Datahandler.studyAreaList.get(i);
                String tempname = temp.getName();
                for (int j = 0; j < mybookmarkstring.size(); j++) {
                    if ((tempname.equals(mybookmarkstring.get(j)))) {
                        BookmarkList.add(temp);
                    }
                }

            }
            ProfileActivity.bookmarkflag = true;
            setCurrentusername(getUn());
        }
        rvbookmark.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(ProfileActivity.getContext());
        rvbookmark.setLayoutManager(layoutManager);
        mAdapter = new RecyclerAdapter(ProfileActivity.getContext(), BookmarkList);
        rvbookmark.setAdapter(mAdapter);

    }




    static public Context getContext()
    {
        return context;
    }
}
