package com.example.studygowhere.Boundary;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;

import com.example.studygowhere.Control.MyReviewRecyclerAdapter;
import com.example.studygowhere.Control.ReadMyReviewWorker;
import com.example.studygowhere.Control.Worker;
import com.example.studygowhere.Entity.Review;
import com.example.studygowhere.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static com.example.studygowhere.Boundary.LoginActivity.getUn;


/**
 * <h1>My Review UI</h1>
 * This is an user interface that displays the reviews made by the current user in revyvler view.
 *
 * @author ILOVESSADMORE
 * @version 1.0
 */
public class MyReviewActivity extends AppCompatActivity {

    /**
     * Static variable where RecyclerView my_recycler_view in the XML file will be assigned to.
     */
    static RecyclerView rvMyReview;

    /**
     * Static variable that store the context of MyReviewActivity.
     */
    static Context context;

    /**
     * Override method to assign value to instance variables.
     * It also calls the ReadMyReview method by passing in Un as parameter.
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_review);

        rvMyReview = (RecyclerView) findViewById(R.id.my_recycler_view);
        Worker readMyReview = new Worker(this);
        readMyReview.ReadMyReview(getUn());
    }


    /**
     * This method is to display all reviews made by the current user and put them into recycler view.
     * This JSon string result is first parsed and stored in local variables.
     * The variables are used to instantiate Review Objects and the objects are added to a list.
     * The list is then pass into adapter to be displayed in recycler view.
     * @param result JSon string returned from the Database containing the name of Study Area that the user has
     *               reviewed on, the content of the review and the rating that the user gave.
     */
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


    /**
     * Getter method of context
     * @return context
     */
    public static Context getContext() {
        return context;
    }


    /**
     * Setter method of context
     * @param context
     */
    public static void setContext(Context context) {
        DetailActivity.context = context;
    }
}
