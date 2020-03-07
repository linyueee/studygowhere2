package com.example.studygowhere;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import static com.example.studygowhere.LoginActivity.getUn;

public class DetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        Intent intent = getIntent();
        TextView detailName;
        ImageView image;
        Button btnaddBookmark;
        detailName = (TextView) findViewById(R.id.tvDetailName);
        image = (ImageView) findViewById(R.id.imageView2);
        btnaddBookmark = (Button) findViewById(R.id.addBookmark);
        detailName.setText(intent.getStringExtra("Name"));
        Picasso.get().load(intent.getStringExtra("Image")).placeholder(R.drawable.ic_launcher_background).into(image);

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
                AddbookmarkWorker bmw = new AddbookmarkWorker(this);
                bmw.execute(getUn(),intent.getStringExtra("Name"));
            }
        }
}
