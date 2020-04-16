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
    EditText edUsername;

    /**
     * Instance variable where EditText resetpassemail in the XML file will be assigned to.
     */
    EditText edEmail;

    /**
     * Instance variable where EditText resetpassphone in the XML file will be assigned to.
     */
    EditText edPhone;

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

        edUsername = (EditText) findViewById(R.id.resetPassUsername);
        edEmail = (EditText) findViewById(R.id.resetPassEmail);
        edPhone = (EditText) findViewById(R.id.resetPassPhone);
        btnverify = (Button) findViewById(R.id.btnVerification);

    }

    /**
     * This method is called upon clicking Button btnverify.
     * This method passes the input value into local variables Username, Email and Phone
     * Those local variables are later passed to VerifyAccount method as parameters.
     * @param view
     */

    public void VerifyAccount(View view)
    {
        String Username = edUsername.getText().toString();
        String Email = edEmail.getText().toString();
        String Phone = edPhone.getText().toString();

        Worker verifyAcc = new Worker(this);
        verifyAcc.VerifyAccount(Username, Email, Phone);

    }
}
