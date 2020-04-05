package com.example.studygowhere.Control;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.example.studygowhere.Boundary.LoginActivity;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;



/**
 * <h1>Reset Password controller</h1>
 * This is a asynchronous class that passes values into php files and the php files return
 * a string value after communicating with the Database.
 * The method stores the parameters into local variables and passes them into the php file.
 * THe php file returns a String containing a message.
 *
 * @author ILOVESSADMORE
 * @version 1.0
 */
public class ResetPasswordWorker extends AsyncTask<String, Void, String> {

    /**
     * Instance variable context
     */
    Context context;

    /**
     * constructor with parameter context
     * @param context
     */
    public ResetPasswordWorker(Context context) {
        this.context = context;
    }


    /**
     * This method is to do background operation on background thread.
     * @param params parameters can be of any types and number
     * @return
     */
    @Override
    protected String doInBackground(String... params) {
        String verifyAcc_URL = "https://studygowhere.000webhostapp.com/Resetpassword.php";
        try{
            String username = params[0];
            String password = params[1];
            URL url = new URL(verifyAcc_URL);
            HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setDoOutput(true);
            httpURLConnection.setDoInput(true);
            OutputStream outputStream = httpURLConnection.getOutputStream();
            BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
            String post_data = URLEncoder.encode("username","UTF-8")+"="+URLEncoder.encode(username,"UTF-8")+"&"
                    +URLEncoder.encode("password","UTF-8")+"="+URLEncoder.encode(password,"UTF-8");
            bufferedWriter.write(post_data);
            bufferedWriter.flush();
            bufferedWriter.close();
            outputStream.close();
            InputStream inputStream = httpURLConnection.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "iso-8859-1"));
            String result = "";
            String line = "";
            while((line = bufferedReader.readLine())!= null) {
                result += line;
            }
            bufferedReader.close();
            inputStream.close();
            httpURLConnection.disconnect();
            return result;
        } catch (MalformedURLException e){
            e.printStackTrace();
        }catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * This method is to display the message returned from the php file.
     * If the message is "Password reset", LoginActivity will be started.
     * @param s string result returned from the php file
     */
    @Override
    protected void onPostExecute(String s) {
        Toast toast = Toast.makeText(context, s, Toast.LENGTH_SHORT);
        toast.show();
        if(s.equals("Password reset"))
        {
            context.startActivity(new Intent(context, LoginActivity.class));
        }
    }
}
