package com.example.studygowhere.Boundary;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.studygowhere.Control.AddReviewWorker;
import com.example.studygowhere.Control.Worker;
import com.example.studygowhere.R;
import com.squareup.picasso.Picasso;

import static com.example.studygowhere.Boundary.LoginActivity.getUn;

public class WriteReviewActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener{
    ImageView imReviewImage;
    TextView tvReviewName;
    EditText edReview, edRate;
    Button btnAddReview;
    String StudyAreaName;
    Spinner ratingSpinner;
    String rating;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write_review);

        final Intent intent = getIntent();

        imReviewImage = (ImageView) findViewById(R.id.reviewImV);
        tvReviewName = (TextView) findViewById(R.id.tvReviewLocationName);
        edReview = (EditText) findViewById(R.id.edReview);
        btnAddReview = (Button) findViewById(R.id.btnAddReview);
        ratingSpinner = (Spinner) findViewById(R.id.spRate);
        ratingSpinner.setOnItemSelectedListener(this);
        StudyAreaName = intent.getStringExtra("StudyName");
        tvReviewName.setText(intent.getStringExtra("StudyName"));
        Picasso.get().load(intent.getStringExtra("Picture")).placeholder(R.drawable.ic_launcher_background).into(imReviewImage);

    }

    public void AddReview(View view)
    {
        if(getUn() == null)
        {
            Toast toast=Toast. makeText(getApplicationContext(),"Please log in first", Toast.LENGTH_LONG);
            toast.show();
        }
        else {
            String review = edReview.getText().toString();
            if(review == null)
                review = "No comment";
            Worker addreview = new Worker(this);
            addreview.AddReview(getUn(), review, StudyAreaName, getRating());
            Intent intent = new Intent(this, DetailActivity.class);
            intent.putExtra("Name", StudyAreaName);
            startActivity(intent);
/*            AddReviewWorker arw = new AddReviewWorker(this);
            arw.execute(getUn(), review, StudyAreaName, getRating());*/
        }
    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        if(parent.getSelectedItem().toString().equals("1"))
        {
            setRating("1");
        }
        else if(parent.getSelectedItem().toString().equals("2"))
        {
            setRating("2");
        }
        else if(parent.getSelectedItem().toString().equals("3"))
        {
            setRating("3");
        }
        else if(parent.getSelectedItem().toString().equals("4"))
        {
            setRating("4");
        }
        else if(parent.getSelectedItem().toString().equals("5"))
        {
            setRating("5");
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        setRating("0");

    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }
}
