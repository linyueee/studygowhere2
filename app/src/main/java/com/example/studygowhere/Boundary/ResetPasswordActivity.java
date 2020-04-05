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


/**
 * <h1>Reset Password UI</h1>
 * This is an user interface that allows user to input a new password twice to ensure it is correct.
 * The password entered will then be updated in the Database.
 *
 * @author ILOVESSADMORE
 * @version 1.0
 */
public class ResetPasswordActivity extends AppCompatActivity {
    /**
     * Instance variable where EditText newpass in the XML file will be assigned to.
     */
    EditText ednewpass;

    /**
     * Instance variable where EditText confirmpass in the XML file will be assigned to.
     */
    EditText edconpass;

    /**
     * Instance variable where Button btnresetdone in the XML file will be assigned to.
     */
    Button btnreset;

    /**
     * Static variable that store the username passed by the previous activity using intent.
     */
    static String username;


    /**
     * Override method to assign value to instance variables.
     * @param savedInstanceState
     */
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



    /**
     * This method is a listener method of button Reset
     * This method passed the input values into local variables newpass and confirmpass.
     * If the 2 entries are different, a toast message will be displayed.
     * If the 2 entries are the same, local variable newpass will be pass to method ResetPassword together will username
     * which is obtained from intent of the previous activity.
     * @param view
     */
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
