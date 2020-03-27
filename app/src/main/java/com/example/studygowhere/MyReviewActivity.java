package com.example.studygowhere;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static com.example.studygowhere.LoginActivity.getUn;

public class MyReviewActivity extends AppCompatActivity {
    static RecyclerView rvmyreview;
    static Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_review);

        rvmyreview = (RecyclerView) findViewById(R.id.my_recycler_view);
        ReadMyReviewWorker rmrw = new ReadMyReviewWorker(this);
        rmrw.execute(getUn());

    }

    static public void DisplayReview(String result)
    {
        RecyclerView.Adapter mAdapter;
        RecyclerView.LayoutManager layoutManager;
        List<String> reviewlist = new ArrayList<>();
        List<String> salist = new ArrayList<>();
        List<String> ratinglist = new ArrayList<>();
        ArrayList<Review> reviewListfordisplay = new ArrayList<>();
        try {
            JSONObject jsonObject = new JSONObject(result);
            int success = jsonObject.getInt("success");
            if (success == 1) {
                JSONArray reviews = jsonObject.getJSONArray("review");
                for (int i = 0; i < reviews.length(); i++) {
                    JSONObject review = reviews.getJSONObject(i);
                    String sa = review.getString("StudyArea");
                    salist.add(sa);
                    String reviewcontent = review.getString("Reviews");
                    reviewlist.add(reviewcontent);
                    String rating = review.getString("Rating");
                    ratinglist.add(rating);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }


        for(int i = 0;i < salist.size(); i++)
        {
            String sa = salist.get(i);
            String content = reviewlist.get(i);
            String rating = ratinglist.get(i);
            reviewListfordisplay.add(new Review(getUn(), content,rating, sa));
        }
        rvmyreview.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(MyReviewActivity.getContext());
        rvmyreview.setLayoutManager(layoutManager);
        mAdapter = new MyReviewRecyclerAdapter(MyReviewActivity.getContext(), reviewListfordisplay);
        rvmyreview.setAdapter(mAdapter);

    }

    public static Context getContext() {
        return context;
    }

    public static void setContext(Context context) {
        DetailActivity.context = context;
    }
}
