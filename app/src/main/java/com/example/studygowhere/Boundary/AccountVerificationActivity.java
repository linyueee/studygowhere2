package com.example.studygowhere.Boundary;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import com.example.studygowhere.Control.Worker;
import com.example.studygowhere.R;

/**
 * <h1>User Account verification UI</h1>
 * This is a user interface allows user to input a Username, an email and a phone number and passes them to controller class
 * @author ILOVESSADMORE
 * @version 1.0
 */
public class AccountVerificationActivity extends AppCompatActivity {

    /**
     * Instance variable where EditText resetpassusername in the XML file will be assigned to.
     */
    EditText edusername;

    /**
     * Instance variable where EditText resetpassemail in the XML file will be assigned to.
     */
    EditText edemail;

    /**
     * Instance variable where EditText resetpassphone in the XML file will be assigned to.
     */
    EditText edphone;

    /**
     * Instance variable where Button btnveri in the XML file will be assigned to.
     */
    Button btnverify;

    /**
     * This is a override method that set XML file activity_account_verification to be the
     * content view of the class and assign value to instance variables
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_verification);

        edusername = (EditText) findViewById(R.id.resetpassusername);
        edemail = (EditText) findViewById(R.id.resetpassemail);
        edphone = (EditText) findViewById(R.id.resetpassphone);
        btnverify = (Button) findViewById(R.id.btnveri);

    }

    /**
     * This method is called upon clicking Button btnverify.
     * This method passes the input value into local variables Username, Email and Phone
     * Those local variables are later passed to VerifyAccount method as parameters.
     * @param view
     */

    public void VerifyAccount(View view)
    {
        String Username = edusername.getText().toString();
        String Email = edemail.getText().toString();
        String Phone = edphone.getText().toString();

        Worker verifyAcc = new Worker(this);
        verifyAcc.VerifyAccount(Username, Email, Phone);

    }
}
