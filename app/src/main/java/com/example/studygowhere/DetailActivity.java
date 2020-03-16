package com.example.studygowhere;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.GoogleMap;
import com.squareup.picasso.Picasso;

import org.json.JSONException;

import java.io.IOException;

import static com.example.studygowhere.LoginActivity.getUn;

public class DetailActivity extends AppCompatActivity {

    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        final Intent intent = getIntent();
        TextView detailName;
        ImageView image;
        Button btnaddBookmark, btnviewonmap, btndelete;
        detailName = (TextView) findViewById(R.id.tvDetailName);
        image = (ImageView) findViewById(R.id.imageView2);
        btnaddBookmark = (Button) findViewById(R.id.addBookmark);
        btnviewonmap = (Button) findViewById(R.id.viewOnMap);
        btndelete = (Button) findViewById(R.id.btndelete);
        detailName.setText(intent.getStringExtra("Name"));
        Picasso.get().load(intent.getStringExtra("Image")).placeholder(R.drawable.ic_launcher_background).into(image);

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
    }

        public void AddBM(View view)
        {
            Intent intent = getIntent();
            if(getUn() == null)
            {
                Toast toast=Toast. makeText(getApplicationContext(),"Please log in first", Toast.LENGTH_LONG);
                toast.show();
            }
            else
            {
                String type = "Add";
                CustomisebookmarkWorker bmw = new CustomisebookmarkWorker(this);
                bmw.execute(getUn(),intent.getStringExtra("Name"), type);
            }
        }

        public void DeleteBM(View view)
        {
            Intent intent = getIntent();
            if(getUn() == null)
            {
                Toast toast=Toast. makeText(getApplicationContext(),"Please log in first", Toast.LENGTH_LONG);
                toast.show();
            }
            else
            {
                String type = "Delete";
                CustomisebookmarkWorker dbm = new CustomisebookmarkWorker(this);
                dbm.execute(getUn(),intent.getStringExtra("Name"), type);
            }
        }

/*    public void viewOnMap(View view) throws IOException, JSONException {
        Intent intent = getIntent();
        Log.i("GeoJsonClick", "Feature clicked: " + intent.getStringExtra("Name"));
        //Log.i("GeoJsonClick", "Feature clicked: " + intent.getStringExtra("type"));
        //StudyArea studyArea = new StudyArea();
        //studyArea.setName(intent.getStringExtra("Name"));

        String name = intent.getStringExtra("Name");
        // for the list of study areas, get the place
        // get the coordinates of the place
        // place the marker on map.
    }*/
}
