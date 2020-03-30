package com.example.studygowhere.Boundary;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.studygowhere.Control.CustomiseBookmarkWorker;
import com.example.studygowhere.Control.DataHandler;
import com.example.studygowhere.Control.ReadReviewWorker;
import com.example.studygowhere.Control.ReviewRecyclerAdapter;
import com.example.studygowhere.Control.Worker;
import com.example.studygowhere.Entity.Review;
import com.example.studygowhere.R;
import com.google.android.gms.maps.model.LatLng;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static com.example.studygowhere.Boundary.LoginActivity.getUn;

public class DetailActivity extends AppCompatActivity {

    static RecyclerView reviewLV;
    TextView detailName;
    static TextView avgRating;
    ImageView image;
    Button btnAddBookmark, btnViewOnMap, btnDelete, btnWriteReview, btnSGW, btnAcc;
    ImageButton btnWalk, btnDrive, btnPT;
    static String saName;
    String imageURL = null;
    static Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        final Intent intent = getIntent();

        detailName = (TextView) findViewById(R.id.tvDetailName);
        image = (ImageView) findViewById(R.id.imageView);
        btnAddBookmark = (Button) findViewById(R.id.addBookmark);
        btnViewOnMap = (Button) findViewById(R.id.viewOnMap);
        btnDelete = (Button) findViewById(R.id.btnDelete);
        btnWriteReview = (Button) findViewById(R.id.btnAddReview);
        reviewLV = (RecyclerView) findViewById(R.id.rvReviewList);
        avgRating = (TextView) findViewById(R.id.avgRate);
        btnSGW = (Button) findViewById(R.id.toSGW);
        btnAcc = (Button) findViewById(R.id.toAcc);
        btnPT = (ImageButton) findViewById(R.id.btnPT);
        btnWalk = (ImageButton) findViewById(R.id.btnWalk);
        btnDrive = (ImageButton) findViewById(R.id.btnDrive);
        saName = intent.getStringExtra("Name");
        detailName.setText(saName);
        imageURL = intent.getStringExtra("Image");

        if(imageURL == null)
        {
            for(int i = 0; i < DataHandler.studyAreaList.size(); i++)
            {
                if(saName.equals(DataHandler.studyAreaList.get(i).getName()))
                {
                    imageURL = DataHandler.studyAreaList.get(i).getImageURL();
                }
            }
        }

        Picasso.get().load(imageURL).placeholder(R.drawable.ic_launcher_background).into(image);
        final LatLng latlng= intent.getParcelableExtra("LatLng");

        btnViewOnMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(DetailActivity.this, MapsActivity.class);
                try {
                    MapsActivity.viewOnMap(intent);
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                startActivity(i);
            }
        });

        btnWriteReview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(DetailActivity.this, WriteReviewActivity.class);
                i.putExtra("StudyName", intent.getStringExtra("Name"));
                i.putExtra("Picture", intent.getStringExtra("Image"));
                startActivity(i);
            }
        });

        btnSGW.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(DetailActivity.this, SGWActivity.class);
                startActivity(i);
            }
        });

        btnAcc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(getUn() != null) {
                    Intent i = new Intent(DetailActivity.this, ProfileActivity.class);
                    startActivity(i);
                }
                else
                {
                        Intent i = new Intent(DetailActivity.this, LoginActivity.class);
                        startActivity(i);
                }
            }
        });

        btnWalk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(DetailActivity.this, DirectionsActivity.class);
                i.putExtra("Name", getSaName());
                i.putExtra("LatLng",latlng);
                i.putExtra("Mode","mode=walk");
                startActivity(i);
            }
        });

        btnDrive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(DetailActivity.this, DirectionsActivity.class);
                i.putExtra("Name", getSaName());
                i.putExtra("Name", saName);
                i.putExtra("LatLng",latlng);
                i.putExtra("Mode","mode=drive");
                startActivity(i);
            }
        });

        btnPT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(DetailActivity.this, DirectionsActivity.class);
                i.putExtra("Name", getSaName());
                i.putExtra("Name", saName);
                i.putExtra("LatLng",latlng);
                i.putExtra("Mode","mode=transit");
                startActivity(i);
            }
        });

        Worker readReview = new Worker(this);
        readReview.ReadReview(saName);
    }

    public void AddBM(View view) {
        Intent intent = getIntent();
        if (getUn() == null) {
            Toast toast = Toast.makeText(getApplicationContext(), "Please log in first", Toast.LENGTH_LONG);
            toast.show();
        } else {
            String type = "Add";
            Worker addbm = new Worker(this);
            ProfileActivity.bookmarkFlag = false;
            addbm.AddBookmark(getUn(), intent.getStringExtra("Name"), type);
/*            CustomiseBookmarkWorker bmw = new CustomiseBookmarkWorker(this);
            bmw.execute(getUn(), intent.getStringExtra("Name"), type);*/
        }
    }

    public void DeleteBM(View view) {
        Intent intent = getIntent();
        if (getUn() == null) {
            Toast toast = Toast.makeText(getApplicationContext(), "Please log in first", Toast.LENGTH_LONG);
            toast.show();
        } else {
            String type = "Delete";
            Worker delbm = new Worker(this);
            ProfileActivity.bookmarkFlag = false;
            delbm.DeleteBookmark(getUn(), intent.getStringExtra("Name"), type);
/*            CustomiseBookmarkWorker dbm = new CustomiseBookmarkWorker(this);
            dbm.execute(getUn(), intent.getStringExtra("Name"), type);*/
        }
    }

    static public void DisplayReview(String result)
    {
        RecyclerView.Adapter mAdapter;
        RecyclerView.LayoutManager layoutManager;
        List<String> reviewList = new ArrayList<>();
        List<String> reviewerList = new ArrayList<>();
        List<String> ratingList = new ArrayList<>();
        ArrayList<Review> reviewListForDisplay = new ArrayList<>();
            try {
                JSONObject jsonObject = new JSONObject(result);
                String avg = jsonObject.getString("avg");
                avgRating.setText(avg);
                int success = jsonObject.getInt("success");
                if (success == 1) {
                    JSONArray reviews = jsonObject.getJSONArray("review");
                    for (int i = 0; i < reviews.length(); i++) {
                        JSONObject review = reviews.getJSONObject(i);
                        String reviewer = review.getString("Username");
                        reviewerList.add(reviewer);
                        String reviewContent = review.getString("Reviews");
                        reviewList.add(reviewContent);
                        String rating = review.getString("Rating");
                        ratingList.add(rating);
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

            for(int i = 0;i < reviewerList.size(); i++)
            {
                String reviewer = reviewerList.get(i);
                String content = reviewList.get(i);
                String rating = ratingList.get(i);
                reviewListForDisplay.add(new Review(reviewer, content,rating, getSaName()));
            }

        reviewLV.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(DetailActivity.getContext());
        reviewLV.setLayoutManager(layoutManager);
        mAdapter = new ReviewRecyclerAdapter(DetailActivity.getContext(), reviewListForDisplay);
        reviewLV.setAdapter(mAdapter);
    }

    public static Context getContext() {
        return context;
    }

    public static void setContext(Context context) {
        DetailActivity.context = context;
    }

    public static String getSaName() {
        return saName;
    }

    public static void setSaName(String saName) {
        DetailActivity.saName = saName;
    }
}