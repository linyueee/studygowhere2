package com.example.studygowhere.Boundary;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.studygowhere.Control.Worker;
import com.example.studygowhere.R;

public class ResetPasswordActivity extends AppCompatActivity {

    EditText ednewpass, edconpass;
    Button btnreset;
    static String username;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);

        final Intent intent = getIntent();
        ednewpass = (EditText) findViewById(R.id.newpass);
        edconpass = (EditText) findViewById(R.id.confirmpass);
        btnreset = (Button) findViewById(R.id.btnresetdone);
        username = intent.getStringExtra("username");
    }

    public void Reset(View view)
    {
        String newpass = ednewpass.getText().toString();
        String confirmpass = edconpass.getText().toString();

        if(!newpass.equals(confirmpass))
        {
            Toast toast=Toast. makeText(this,"The 2 entries must be the same", Toast.LENGTH_LONG);
            toast.show();
        }
        else
        {
            Worker reset = new Worker(this);
            reset.ResetPassword(username, newpass);
        }

    }
}
