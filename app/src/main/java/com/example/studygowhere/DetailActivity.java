package com.example.studygowhere;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static com.example.studygowhere.LoginActivity.getCurrentusername;
import static com.example.studygowhere.LoginActivity.getUn;

public class DetailActivity extends AppCompatActivity {

    static RecyclerView reviewlv;
    TextView detailName;
    static TextView avgrating;
    ImageView image;
    Button btnaddBookmark, btnviewonmap, btndelete, btnwritereview, btnsgw, btnacc;
    ImageButton btnWalk, btnDrive, btnPT;
    static String saname;
    String imageurl = null;
    static Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        final Intent intent = getIntent();

        detailName = (TextView) findViewById(R.id.tvDetailName);
        image = (ImageView) findViewById(R.id.imageView2);
        btnaddBookmark = (Button) findViewById(R.id.addBookmark);
        btnviewonmap = (Button) findViewById(R.id.viewOnMap);
        btndelete = (Button) findViewById(R.id.btndelete);
        btnwritereview = (Button) findViewById(R.id.btnaddreview);
        reviewlv = (RecyclerView) findViewById(R.id.rvreviewList);
        avgrating = (TextView) findViewById(R.id.avgrate);
        btnsgw = (Button) findViewById(R.id.toSGW);
        btnacc = (Button) findViewById(R.id.toAcc);
        btnPT = (ImageButton) findViewById(R.id.btnPT);
        btnWalk = (ImageButton) findViewById(R.id.btnWalk);
        btnDrive = (ImageButton) findViewById(R.id.btnDrive);
        saname = intent.getStringExtra("Name");
        detailName.setText(saname);
        imageurl = intent.getStringExtra("Image");
        if(imageurl == null)
        {
            for(int i = 0; i < Datahandler.studyAreaList.size(); i++)
            {
                if(saname.equals(Datahandler.studyAreaList.get(i).getName()))
                {
                    imageurl = Datahandler.studyAreaList.get(i).getImageurl();
                }
            }
        }

        Picasso.get().load(imageurl).placeholder(R.drawable.ic_launcher_background).into(image);
        final LatLng latlng= intent.getParcelableExtra("LatLng");


        btnviewonmap.setOnClickListener(new View.OnClickListener() {
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

        btnwritereview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(DetailActivity.this, WriteReviewActivity.class);
                i.putExtra("StudyName", intent.getStringExtra("Name"));
                i.putExtra("Picture", intent.getStringExtra("Image"));
                startActivity(i);
            }
        });


        btnsgw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(DetailActivity.this, SGWActivity.class);
                startActivity(i);
            }
        });

        btnacc.setOnClickListener(new View.OnClickListener() {
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


        ReadReviewWorker rrw = new ReadReviewWorker(this);
        rrw.execute(saname);


        btnWalk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(DetailActivity.this, DirectionsActivity.class);
                i.putExtra("LatLng",latlng);
                i.putExtra("Mode","mode=walk");
                startActivity(i);
            }
        });
        btnDrive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(DetailActivity.this, DirectionsActivity.class);
                i.putExtra("LatLng",latlng);
                i.putExtra("Mode","mode=drive");
                startActivity(i);
            }
        });
        btnPT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(DetailActivity.this, DirectionsActivity.class);
                i.putExtra("LatLng",latlng);
                i.putExtra("Mode","mode=transit");
                startActivity(i);
            }
        });
    }

    public void AddBM(View view) {
        Intent intent = getIntent();
        if (getUn() == null) {
            Toast toast = Toast.makeText(getApplicationContext(), "Please log in first", Toast.LENGTH_LONG);
            toast.show();
        } else {
            String type = "Add";
            CustomisebookmarkWorker bmw = new CustomisebookmarkWorker(this);
            bmw.execute(getUn(), intent.getStringExtra("Name"), type);
        }
    }

    public void DeleteBM(View view) {
        Intent intent = getIntent();
        if (getUn() == null) {
            Toast toast = Toast.makeText(getApplicationContext(), "Please log in first", Toast.LENGTH_LONG);
            toast.show();
        } else {
            String type = "Delete";
            CustomisebookmarkWorker dbm = new CustomisebookmarkWorker(this);
            dbm.execute(getUn(), intent.getStringExtra("Name"), type);
        }
    }




    static public void DisplayReview(String result)
    {
        RecyclerView.Adapter mAdapter;
        RecyclerView.LayoutManager layoutManager;
        List<String> reviewlist = new ArrayList<>();
        List<String> reviewerlist = new ArrayList<>();
        List<String> ratinglist = new ArrayList<>();
        ArrayList<Review> reviewListfordisplay = new ArrayList<>();
            try {
                JSONObject jsonObject = new JSONObject(result);
                String avg = jsonObject.getString("avg");
                avgrating.setText(avg);
                int success = jsonObject.getInt("success");
                if (success == 1) {
                    JSONArray reviews = jsonObject.getJSONArray("review");
                    for (int i = 0; i < reviews.length(); i++) {
                        JSONObject review = reviews.getJSONObject(i);
                        String reviewer = review.getString("Username");
                        reviewerlist.add(reviewer);
                        String reviewcontent = review.getString("Reviews");
                        reviewlist.add(reviewcontent);
                        String rating = review.getString("Rating");
                        ratinglist.add(rating);
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }


            for(int i = 0;i < reviewerlist.size(); i++)
            {
                String reviewer = reviewerlist.get(i);
                String content = reviewlist.get(i);
                String rating = ratinglist.get(i);
                reviewListfordisplay.add(new Review(reviewer, content,rating, getSaname()));
            }
        reviewlv.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(DetailActivity.getContext());
        reviewlv.setLayoutManager(layoutManager);
        mAdapter = new ReviewRecyclerAdapter(DetailActivity.getContext(), reviewListfordisplay);
        reviewlv.setAdapter(mAdapter);

    }

    public static Context getContext() {
        return context;
    }

    public static void setContext(Context context) {
        DetailActivity.context = context;
    }

    public static String getSaname() {
        return saname;
    }

    public static void setSaname(String saname) {
        DetailActivity.saname = saname;
    }

}