package com.example.studygowhere;

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

import com.squareup.picasso.Picasso;

import static com.example.studygowhere.LoginActivity.getUn;

public class WriteReviewActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener{
    ImageView imreviewimage;
    TextView tvreviewname;
    EditText edreview, edrate;
    Button btnaddreview;
    String StudyAreaName;
    Spinner ratingspinner;
    String rating;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write_review);

        final Intent intent = getIntent();

        imreviewimage = (ImageView) findViewById(R.id.reviewimv);
        tvreviewname = (TextView) findViewById(R.id.tvreviewlocationname);
        edreview = (EditText) findViewById(R.id.edreview);
        btnaddreview = (Button) findViewById(R.id.btnaddreview);
        ratingspinner = (Spinner) findViewById(R.id.sprate);
        ratingspinner.setOnItemSelectedListener(this);
        StudyAreaName = intent.getStringExtra("StudyName");
        tvreviewname.setText(intent.getStringExtra("StudyName"));
        Picasso.get().load(intent.getStringExtra("Picture")).placeholder(R.drawable.ic_launcher_background).into(imreviewimage);

    }


    public void AddReview(View view)
    {
        if(getUn() == null)
        {
            Toast toast=Toast. makeText(getApplicationContext(),"Please log in first", Toast.LENGTH_LONG);
            toast.show();
        }
        else {
            String review = edreview.getText().toString();
            if(review == null)
                review = "No comment";
            AddReviewWorker arw = new AddReviewWorker(this);
            arw.execute(getUn(), review, StudyAreaName, getRating());
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
