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

/**
 * <h1>Detail Information On Selected Study Area UI</h1>
 * This is a user interface that displays the detail information of a selected Study Area and the
 * operations that can be performed on the Study Area.
 *
 * @author ILOVESSADMORE
 * @version 1.0
 */
public class DetailActivity extends AppCompatActivity {
    /**
     * This is a static RecyclerView variable to hold the reviews in recycler view.
     */
    static RecyclerView reviewLV;

    /**
     * Instance variable where TextView tvDetailName in the XML file will be assigned to.
     */
    TextView detailName;

    /**
     * Static variable where TextView avgRate in the XML file will be assigned to.
     */
    static TextView avgRating;

    /**
     * Instance variable where ImageView imageView in the XML file will be assigned to.
     */
    ImageView image;

    /**
     * Instance variable where Button btnAddBookmark in the XML file will be assigned to.
     */
    Button btnAddBookmark;

    /**
     * Instance variable where Button viewOnMap in the XML file will be assigned to.
     */
    Button btnViewOnMap;

    /**
     * Instance variable where Button btnDelete in the XML file will be assigned to.
     */
    Button btnDelete;

    /**
     * Instance variable where Button btnAddReview in the XML file will be assigned to.
     */
    Button btnWriteReview;

    /**
     * Instance variable where Button toSGW in the XML file will be assigned to.
     */
    Button btnSGW;

    /**
     * Instance variable where Button toAcc in the XML file will be assigned to.
     */
    Button btnAcc;

    /**
     * Instance variable where ImageButton btnWalk in the XML file will be assigned to.
     */
    ImageButton btnWalk;

    /**
     * Instance variable where ImageButton btnDrive in the XML file will be assigned to.
     */
    ImageButton btnDrive;

    /**
     * Instance variable where ImageButton btnPT in the XML file will be assigned to.
     */
    ImageButton btnPT;

    /**
     * Static variable that stores the Study Area name of the selected Study Area by using getIntentExtra
     */
    static String saName;

    /**
     * Instance variable that store the URL of the image of the selected Study Area by using getIntentExtra.
     * If there is no image URL passed from the previous activity, the list of Study Area will be checked through to find the image URL.
     */
    String imageURL = null;

    /**
     * Context of the activity.
     */
    static Context context;


    /**
     * Override method to assign value to instance variables and call method ReadReview with parameter
     * saName to retrieve reviews on saName from the Database.
     *
     * @param savedInstanceState
     */
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

        Worker readReview = new Worker(this);
        readReview.ReadReview(saName);

        btnViewOnMap.setOnClickListener(new View.OnClickListener() {
            /**
             * Upon clicking btnViewOnMap, MapsActivity class will be started and its viewOnMap method will be
             * call with intent of the DetailActivity class as parameter.
             * @param v
             */
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
            /**
             * Upon clicking btnWriteReview, WriteReviewActivity class will be started and the instance variables
             * saName and imageURL will be stored as "StudyName" and "Picture" respectively in the intent.
             * @param v
             */
            @Override
            public void onClick(View v) {
                Intent i = new Intent(DetailActivity.this, WriteReviewActivity.class);
                i.putExtra("StudyName", saName);
                i.putExtra("Picture", imageURL);
                startActivity(i);
            }
        });



        btnSGW.setOnClickListener(new View.OnClickListener() {
            /**
             * Upon clicking btnSGW, SGWActivity class will be started.
             * @param v
             */
            @Override
            public void onClick(View v) {
                Intent i = new Intent(DetailActivity.this, SGWActivity.class);
                startActivity(i);
            }
        });



        btnAcc.setOnClickListener(new View.OnClickListener() {
            /**
             * Upon clicking btnAcc, ProfileActivity class will be started if the static variable Un is not null,
             * which indicates the user has logged in with an account.
             * If the static variable Un is null, LoginActivity class will be started instead.
             * @param v
             */
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
            /**
             * Upon clicking btnWalk, DirectionsActivity class will be started and the instance variables
             * saName and latlng will be stored as "StudyName" and "LatLng" respectively in the intent.
             * String "mode=walk" is also put under intent name "Mode".
             * @param v
             */
            @Override
            public void onClick(View v) {
                Intent i = new Intent(DetailActivity.this, DirectionsActivity.class);
                i.putExtra("Name", saName);
                i.putExtra("LatLng",latlng);
                i.putExtra("Mode","mode=walk");
                startActivity(i);
            }
        });

        btnDrive.setOnClickListener(new View.OnClickListener() {
            /**
             * Upon clicking btnDrive, DirectionsActivity class will be started and the instance variables
             * saName and latlng will be stored as "StudyName" and "LatLng" respectively in the intent.
             * String "mode=drive" is also put under intent name "Mode".
             * @param v
             */
            @Override
            public void onClick(View v) {
                Intent i = new Intent(DetailActivity.this, DirectionsActivity.class);
                i.putExtra("Name", saName);
                i.putExtra("LatLng",latlng);
                i.putExtra("Mode","mode=drive");
                startActivity(i);
            }
        });

        btnPT.setOnClickListener(new View.OnClickListener() {
            /**
             * Upon clicking btnPT, DirectionsActivity class will be started and the instance variables
             * saName and latlng will be stored as "StudyName" and "LatLng" respectively in the intent.
             * String "mode=transit" is also put under intent name "Mode".
             * @param v
             */
            @Override
            public void onClick(View v) {
                Intent i = new Intent(DetailActivity.this, DirectionsActivity.class);
                i.putExtra("Name", saName);
                i.putExtra("LatLng",latlng);
                i.putExtra("Mode","mode=transit");
                startActivity(i);
            }
        });


    }


    /**
     * This is a listener method of btnAddBookmark.
     * A toast message will be shown if the static variable Un is null, which indicates that the user has not login with an account.
     * If the static variable Un is not null, the type of operation will be set to "Add" and AddBookmark method in Worker class will be called.
     * The static boolean variable bookmarkFlag in ProfileActivity class is also set to false, which signals updates to
     * the list of bookmark. This is done so that ProfileActivity does not need to repeatedly read bookmarks from the Database and
     * only performs the operation when there is an update.
     * @param view
     */
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
        }
    }


    /**
     * This is a listener method of btnDeleteBookmark.
     * A toast message will be shown if the static variable Un is null, which indicates that the user has not login with an account.
     * If the static variable Un is not null, the type of operation will be set to "Delete" and DeleteBookmark method in Worker class will be called.
     * The static boolean variable bookmarkFlag in ProfileActivity class is also set to false, which signals updates to
     * the list of bookmark. This is done so that ProfileActivity does not need to repeatedly read bookmarks from the Database and
     * only performs the operation when there is an update.
     * @param view
     */
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


    /**
     * This method is to convert the JSon string returned by the Database to recycler view
     * avgRating is set with the value in field "avg", which is the average rating on the selected Study Area.
     * The calculation is done in the php file.
     * Fields in the JSon string is read and stored in local variables, which are added to local arraylists.
     * Review Objects are instantiated with each item in the arraylists.
     * The Objects are added to reviewListForDisplay so that they can be passed to the adapter and displayed in recycler view
     * @param result JSon string that is returned by the Database
     */
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



    /**
     * Getter method of static variable context
     * @return context of DetailActivity
     */
    public static Context getContext() {
        return context;
    }

    /**
     * Setter method of static variable context
     * @param context context of DetailActivity
     */
    public static void setContext(Context context) {
        DetailActivity.context = context;
    }


    /**
     *
     * @return Study Area name
     */
    public static String getSaName() {
        return saName;
    }

}