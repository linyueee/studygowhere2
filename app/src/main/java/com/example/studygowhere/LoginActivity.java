package com.example.studygowhere;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import static android.os.AsyncTask.Status.FINISHED;


public class LoginActivity extends AppCompatActivity {
    EditText etUsername, etPassword;


    Button btnLogin, btnRegister;
    static boolean LoggedIn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login2);
        etUsername = (EditText) findViewById(R.id.username);
        etPassword = (EditText) findViewById(R.id.password);
        btnLogin = (Button) findViewById(R.id.login);
        btnRegister = (Button) findViewById(R.id.register);

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(i);
            }
        });


    }


    public void OnLogin(View view) throws InterruptedException {
        String username = etUsername.getText().toString();
        String password = etPassword.getText().toString();
        String type = "login";
        BackgroundWorker bgw = new BackgroundWorker(this);
        bgw.execute(type, username, password);


    }

    public static void IsLoggedIn(String v)
    {
        if(v.equals("Welcome"))
            LoggedIn = true;
    }



}