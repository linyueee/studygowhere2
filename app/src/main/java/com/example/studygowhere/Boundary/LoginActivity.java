package com.example.studygowhere.Boundary;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.example.studygowhere.Control.AccountWorker;
import com.example.studygowhere.Control.Worker;
import com.example.studygowhere.R;


/**
 * <h1>Login UI</h1>
 * This user interface allows user to input his Username and Password
 * If the Username exists in the Database and the Password is correct, login successful.
 *
 * @author ILOVESSADMORE
 * @version 1.0
 */
public class LoginActivity extends AppCompatActivity {

    /**
     * Instance variable where EditText username in the XML file will be assigned to.
     */
    EditText etUsername;

    /**
     * Instance variable where EditText password in the XML file will be assigned to.
     */
    EditText etPassword;

    /**
     * Instance variable where Button lonin in the XML file will be assigned to.
     */
    Button btnLogin;

    /**
     * Instance variable where Button register in the XML file will be assigned to.
     */
    Button btnRegister;

    /**
     * Instance variable where Button btnToMap in the XML file will be assigned to.
     */
    Button btnToMap;

    /**
     * Instance variable where Button btnresetpass in the XML file will be assigned to.
     */
    Button btnreset;

    /**
     * Static variable that store the Username of the user.
     * It is also an important parameter to determine whether the user has logged in.
     * If Un is null which indicates the user has yet to log in, ProfileActivity class will not be started
     * and the user has no access to features like bookmarks and reviews.
     */
    static private String Un = null;

    /**
     * Static variable that store the previous Username when the user switch account.
     * Since the list of bookmarks is only read from the Database when there is an update to the list,
     * variable currentUserName is needed to inform the application to read the list of bookmarks from the Database due to a switch of account.
     */
    static private String currentUserName;


    /**
     * Override method to assign value to instance variables.
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        etUsername = (EditText) findViewById(R.id.username);
        etPassword = (EditText) findViewById(R.id.password);
        btnLogin = (Button) findViewById(R.id.login);
        btnRegister = (Button) findViewById(R.id.register);
        btnToMap = (Button) findViewById(R.id.btnToMap);
        btnreset = (Button) findViewById(R.id.btnresetpass);

        btnRegister.setOnClickListener(new View.OnClickListener() {
            /**
             * Upon clicking btnRegister, RegisterActivity class will be started.
             * @param v
             */
            @Override
            public void onClick(View v) {
                Intent i = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(i);
            }
        });

        btnToMap.setOnClickListener(new View.OnClickListener() {
            /**
             * Upon clicking btnToMap, MapsActivity class will be started.
             * @param v
             */
            @Override
            public void onClick(View v) {
                Intent i = new Intent(LoginActivity.this, MapsActivity.class);
                startActivity(i);
            }
        });

        btnreset.setOnClickListener(new View.OnClickListener() {
            /**
             * Upon clicking btnreset, AccountVerificationActivity class will be started.
             * @param v
             */
            @Override
            public void onClick(View v) {
                Intent i = new Intent(LoginActivity.this, AccountVerificationActivity.class);
                startActivity(i);
            }
        });
    }


    /**
     * This method is listener method of Button btnLogin.
     * This method passed the input values into local variables username and password
     * If either the username or the password is left empty, Un will be set to null instead of left as an empty string.
     * If niether the username nor the password is empty, Un will be set to the username entered.
     * The type will be set to "login" and is passed to the Login method together with the username and password entered.
     * @param view
     * @throws InterruptedException
     */
    public void OnLogin(View view) throws InterruptedException {
        String username = etUsername.getText().toString();
        String password = etPassword.getText().toString();
        if(username.equals("") || password.equals(""))
            setUn(null);
        else
             setUn(username);
        String type = "login";
        Worker loginworker = new Worker(this);
        loginworker.Login(type, username, password);
    }

    /**
     * Getter method of Un.
     * @return static Un
     */
    static public String getUn()
    {
        return Un;
    }

    /**
     * Setter method of Un.
     * @param username
     */
    static public void setUn(String username)
    {
        Un = username;
    }

    /**
     * Getter method of CurrentUserName.
     * @return
     */
    public static String getCurrentUserName() {
        return currentUserName;
    }

    /**
     * Setter method of CurrentUserName.
     * @param cun
     */
    public static void setCurrentUserName(String cun) {
        currentUserName = cun;
    }
}