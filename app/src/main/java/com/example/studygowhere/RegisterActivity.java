package com.example.studygowhere;

import androidx.appcompat.app.AppCompatActivity;

import static com.example.studygowhere.LoginActivity.setUn;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;


public class RegisterActivity extends AppCompatActivity {
    EditText name, password, email, mobile;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        name = (EditText)findViewById(R.id.username);
        password = (EditText)findViewById(R.id.password);
        email = (EditText)findViewById(R.id.email);
        mobile = (EditText)findViewById(R.id.hp);
    }
    public void OnReg(View view)
    {
        String str_name = name.getText().toString();
        String str_password = password.getText().toString();
        String str_email = email.getText().toString();
        String str_mobile = mobile.getText().toString();

        String type = "register";
        setUn(str_name);
        BackgroundWorker bgw = new BackgroundWorker(this);
        bgw.execute(type, str_name, str_password, str_email, str_mobile);
    }
}
