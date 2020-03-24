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
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.GoogleMap;
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

    private GoogleMap mMap;
    static RecyclerView reviewlv;
    TextView detailName;
    static TextView avgrating;
    ImageView image;
    Button btnaddBookmark, btnviewonmap, btndelete, btnwritereview, btnsgw, btnacc;
    static String saname;
    String imageurl;
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
        saname = intent.getStringExtra("Name");
        detailName.setText(saname);
        imageurl = intent.getStringExtra("Image");
        Picasso.get().load(imageurl).placeholder(R.drawable.ic_launcher_background).into(image);

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
                Double douavg = Double.parseDouble(avg);
                String rounded = String.format("%.0f", douavg);
                //Log.i("check", "content"+ douavg);
                avgrating.setText(rounded);
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










/*     class DisplayReviewMgr
    {
        String result;

        public DisplayReviewMgr(String result) {
            this.result = result;
        }
        public void DisplayReview() {
            ReviewAdapter adapter;
            List<String> reviewlist = new ArrayList<>();
            RecyclerView.Adapter mAdapter;
            RecyclerView.LayoutManager layoutManager;
            List<String> reviewerlist = new ArrayList<>();
            ArrayList<Review> reviewListfordisplay = new ArrayList<>();
            try {
                JSONObject jsonObject = new JSONObject(result);
                int success = jsonObject.getInt("success");
                if (success == 1) {
                    JSONArray reviews = jsonObject.getJSONArray("review");
                    for (int i = 0; i < reviews.length(); i++) {
                        JSONObject review = reviews.getJSONObject(i);
                        String reviewer = review.getString("Username");
                        reviewerlist.add(reviewer);
                        String reviewcontent = review.getString("Reviews");
                        reviewlist.add(reviewcontent);

                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }


            for (int i = 0; i < reviewerlist.size(); i++) {
                String reviewer = reviewerlist.get(i);
                String content = reviewlist.get(i);
                reviewListfordisplay.add(new Review(reviewer, content, getSaname()));
            }
            Log.i("check", "content" + reviewListfordisplay.get(1).getAuthor());
            Log.i("check", "content" + reviewListfordisplay.size());
            ReviewAdapter reviewAdapter = new ReviewAdapter(reviewListfordisplay, getApplicationContext());
            //adapter = new ReviewAdapter(DetailActivity.getContext(), R.id.review_list, reviewListfordisplay);
            reviewlv.setAdapter(rdapter);

        }

    }
    */




/*    class ReviewAdapter extends BaseAdapter {
        private ArrayList<Review> reviewlist;
        private Context context;

        public ReviewAdapter(ArrayList<Review> reviewlist, Context context) {
            this.reviewlist = reviewlist;
            this.context = context;
        }

        public ReviewAdapter(ArrayList<Review> reviewlist) {
            this.reviewlist = reviewlist;
        }

        @Override
        public int getCount() {
            return reviewlist.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            convertView = getLayoutInflater().inflate(R.layout.review_list,null);

            TextView tvauthor = (TextView) convertView.findViewById(R.id.tvAuthor);
            TextView tvContent = (TextView) convertView.findViewById(R.id.tvcontent);
            TextView tvRating = (TextView) convertView.findViewById(R.id.tvrating);
            tvauthor.setText(reviewlist.get(position).getAuthor());
            tvContent.setText(reviewlist.get(position).getContent());
            tvRating.setText("lalal");
            return convertView;
        }
    }*/
}