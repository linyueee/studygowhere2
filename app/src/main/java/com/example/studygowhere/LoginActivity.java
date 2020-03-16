package com.example.studygowhere;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


public class LoginActivity extends AppCompatActivity {
    EditText etUsername, etPassword;

    static private String Un;
    static private String currentusername;
    Button btnLogin, btnRegister, btnToMap;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login2);
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
        String type = "login";
        setUn(username);
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

    public static String getCurrentusername() {
        return currentusername;
    }

    public static void setCurrentusername(String cun) {
        currentusername = cun;
    }
}