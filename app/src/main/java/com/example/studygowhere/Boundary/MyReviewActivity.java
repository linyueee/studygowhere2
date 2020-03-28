package com.example.studygowhere.Boundary;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;

import com.example.studygowhere.Control.MyReviewRecyclerAdapter;
import com.example.studygowhere.Control.ReadMyReviewWorker;
import com.example.studygowhere.Entity.Review;
import com.example.studygowhere.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static com.example.studygowhere.Boundary.LoginActivity.getUn;

public class MyReviewActivity extends AppCompatActivity {
    static RecyclerView rvMyReview;
    static Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_review);

        rvMyReview = (RecyclerView) findViewById(R.id.my_recycler_view);
        ReadMyReviewWorker rmRW = new ReadMyReviewWorker(this);
        rmRW.execute(getUn());
    }

    static public void DisplayReview(String result)
    {
        RecyclerView.Adapter mAdapter;
        RecyclerView.LayoutManager layoutManager;
        List<String> reviewList = new ArrayList<>();
        List<String> saList = new ArrayList<>();
        List<String> ratingList = new ArrayList<>();
        ArrayList<Review> reviewListForDisplay = new ArrayList<>();
        try {
            JSONObject jsonObject = new JSONObject(result);
            int success = jsonObject.getInt("success");
            if (success == 1) {
                JSONArray reviews = jsonObject.getJSONArray("review");
                for (int i = 0; i < reviews.length(); i++) {
                    JSONObject review = reviews.getJSONObject(i);
                    String sa = review.getString("StudyArea");
                    saList.add(sa);
                    String reviewContent = review.getString("Reviews");
                    reviewList.add(reviewContent);
                    String rating = review.getString("Rating");
                    ratingList.add(rating);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        for(int i = 0; i < saList.size(); i++)
        {
            String sa = saList.get(i);
            String content = reviewList.get(i);
            String rating = ratingList.get(i);
            reviewListForDisplay.add(new Review(getUn(), content,rating, sa));
        }
        rvMyReview.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(MyReviewActivity.getContext());
        rvMyReview.setLayoutManager(layoutManager);
        mAdapter = new MyReviewRecyclerAdapter(MyReviewActivity.getContext(), reviewListForDisplay);
        rvMyReview.setAdapter(mAdapter);
    }

    public static Context getContext() {
        return context;
    }

    public static void setContext(Context context) {
        DetailActivity.context = context;
    }
}
