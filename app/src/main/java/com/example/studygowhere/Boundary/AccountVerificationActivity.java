package com.example.studygowhere.Boundary;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.studygowhere.Control.Worker;
import com.example.studygowhere.R;

public class AccountVerificationActivity extends AppCompatActivity {

    EditText edusername, edemail, edphone;
    Button btnverify;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_verification);

        edusername = (EditText) findViewById(R.id.resetpassusername);
        edemail = (EditText) findViewById(R.id.resetpassemail);
        edphone = (EditText) findViewById(R.id.resetpassphone);
        btnverify = (Button) findViewById(R.id.btnveri);

    }

    public void VerifyAccount(View view)
    {
        String Username = edusername.getText().toString();
        String Email = edemail.getText().toString();
        String Phone = edphone.getText().toString();

        Worker verifyAcc = new Worker(this);
        verifyAcc.VerifyAccount(Username, Email, Phone);

    }
}
