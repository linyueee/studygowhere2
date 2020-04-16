package com.example.studygowhere.Boundary;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.example.studygowhere.Control.Worker;
import com.example.studygowhere.R;


/**
 * <h1>Register UI</h1>
 * This is an user interface that allows user to input his Username, Password, Email and Phone number and this information
 * is stored in the Database such that the user can use it for future login activity.
 *
 * @author ILOVESSADMORE
 * @version 1.0
 */
public class RegisterActivity extends AppCompatActivity {
    /**
     * Instance variable where EditText username in the XML file will be assigned to.
     */
    EditText name;

    /**
     * Instance variable where EditText password in the XML file will be assigned to.
     */
    EditText password;

    /**
     * Instance variable where EditText email in the XML file will be assigned to.
     */
    EditText email;

    /**
     * Instance variable where EditText hp in the XML file will be assigned to.
     */
    EditText mobile;


    /**
     * Override method to assign value to instance variables.
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        name = (EditText)findViewById(R.id.username);
        password = (EditText)findViewById(R.id.password);
        email = (EditText)findViewById(R.id.email);
        mobile = (EditText)findViewById(R.id.hp);
    }


    /**
     * This method is a listener method of button Register
     * This method passed the input values into local variables str_name, str_password, str_email, str_mobile.
     * The type will be set to "register" and is passed to the Register method together with str_name, str_password, str_email, str_mobile.
     * @param view
     */
    public void OnReg(View view)
    {
        String str_name = name.getText().toString();
        String str_password = password.getText().toString();
        String str_email = email.getText().toString();
        String str_mobile = mobile.getText().toString();

        String type = "register";
        Worker registerWorker = new Worker(this);
        registerWorker.Register(type, str_name, str_password, str_email, str_mobile);
    }
}