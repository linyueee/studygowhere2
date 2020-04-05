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


/**
 * <h1>Write Review UI</h1>
 * This is an user interface that allows user to write review and give rating on the selected Study Area.
 *
 * @author ILOVESSADMORE
 * @version 1.0
 */
public class WriteReviewActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener{

    /**
     * Instance variable where ImageView reviewImV in the XML file will be assigned to.
     */
    ImageView imReviewImage;

    /**
     * Instance variable where TextView tvReviewLocationName in the XML file will be assigned to.
     */
    TextView tvReviewName;

    /**
     * Instance variable where EditText edReview in the XML file will be assigned to.
     */
    EditText edReview;

    /**
     * Instance variable where Button btnAddReview in the XML file will be assigned to.
     */
    Button btnAddReview;

    /**
     * Instance variable that stores the Study Area name of the selected Study Area by using getIntentExtra
     */
    String StudyAreaName;

    /**
     * Instance variable that allows that user to selected a rating from 1 to 5
     */
    Spinner ratingSpinner;

    /**
     * Instance variable that store the selected rating.
     */
    String rating;

    /**
     * Override method to assign value to instance variables.
     * @param savedInstanceState
     */
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


    /**
     * This method is a listener method of button Add review
     * A toast message will be shown if static variable Un is null, which indicates the user has yet to login.
     * If Un is not null, the input review will be stored to local String review and passed to method AddReview
     * together with Un, the name of the Study Area and the rating.
     * It will then start DetailActivity while AddReview is running in the thread.
     * @param view
     */
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
        }
    }



    /**
     * This is an override method of the spinner.
     * It checks the String value of the selected position and sets rating to the selected value.
     * @param parent
     * @param view
     * @param position
     * @param id
     */
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


    /**
     * This is an override method of the spinner
     * @param parent
     */
    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }


    /**
     * This is a Getter method of instance variable rating
     * @return string rating
     */
    public String getRating() {
        return rating;
    }


    /**
     * This is a Setter method of instance variable rating
     * @param rating the rating that the user gives to the selected Study Area.
     */
    public void setRating(String rating) {
        this.rating = rating;
    }
}
