package com.example.studygowhere;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.IOException;
import java.io.InputStream;

import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import android.os.Bundle;
import android.widget.EditText;

public class Register extends AppCompatActivity {
    EditText name, password, email, mobile;
    String str_name, str_password, str_email, str_mobile;
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
        BackgroundWorker bgw = new BackgroundWorker(this);
        bgw.execute(type, str_name, str_password, str_email, str_mobile);
    }
}
