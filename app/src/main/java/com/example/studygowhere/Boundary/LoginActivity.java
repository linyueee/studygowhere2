package com.example.studygowhere.Boundary;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.example.studygowhere.Control.AccountWorker;
import com.example.studygowhere.R;


public class LoginActivity extends AppCompatActivity {
    EditText etUsername, etPassword;

    static private String Un = null;
    static private String currentUserName;
    Button btnLogin, btnRegister, btnToMap;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        etUsername = (EditText) findViewById(R.id.username);
        etPassword = (EditText) findViewById(R.id.password);
        btnLogin = (Button) findViewById(R.id.login);
        btnRegister = (Button) findViewById(R.id.register);
        btnToMap = (Button) findViewById(R.id.btnToMap);

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(i);
            }
        });

        btnToMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(LoginActivity.this, MapsActivity.class);
                startActivity(i);
            }
        });
    }

    public void OnLogin(View view) throws InterruptedException {
        String username = etUsername.getText().toString();
        String password = etPassword.getText().toString();
        if(username.equals("") || password.equals(""))
            setUn(null);
        else
             setUn(username);
        String type = "login";
        AccountWorker bgw = new AccountWorker(this);
        bgw.execute(type, username, password);
    }


    static public String getUn()
    {
        return Un;
    }

    static public void setUn(String username)
    {
        Un = username;
    }

    public static String getCurrentUserName() {
        return currentUserName;
    }

    public static void setCurrentUserName(String cun) {
        currentUserName = cun;
    }
}