package com.example.studygowhere.Control;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.widget.Toast;

import com.example.studygowhere.Boundary.ResetPasswordActivity;

import org.json.JSONException;
import org.json.JSONObject;

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
 * <h1>Account Verification controller</h1>
 * This is a asynchronous class that passes values into php files and the php files return
 * a string value after communicating with the Database.
 *
 * @author ILOVESSADMORE
 * @version 1.0
 */
public class VerifyAccountWorker extends AsyncTask<String, Void, String> {

    /**
     * Instance variable context
     */
    Context context;

    /**
     * constructor with parameter context
     * @param context
     */
    public VerifyAccountWorker(Context context) {
        this.context = context;
    }


    /**
     * This method is to do background operation on background thread.
     * @param params parameters can be of any types and number
     * @return json string
     */
    @Override
    protected String doInBackground(String... params) {
        String verifyAcc_URL = "https://studygowhere.000webhostapp.com/VerifyAccount.php";
        try{
            String username = params[0];
            String email = params[1];
            String phone = params[2];
            URL url = new URL(verifyAcc_URL);
            HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setDoOutput(true);
            httpURLConnection.setDoInput(true);
            OutputStream outputStream = httpURLConnection.getOutputStream();
            BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
            String post_data = URLEncoder.encode("username","UTF-8")+"="+URLEncoder.encode(username,"UTF-8")+"&"
                    +URLEncoder.encode("email","UTF-8")+"="+URLEncoder.encode(email,"UTF-8")+"&"
                    +URLEncoder.encode("phone","UTF-8")+"="+URLEncoder.encode(phone,"UTF-8");
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
     * This method is to display the message from the php file.
     * @param s string result returned from the php file
     */
    @Override
    protected void onPostExecute(String s) {
        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject(s);
            String name = jsonObject.getString("name");
            String message = jsonObject.getString("message");
            if(message.equals("Verification successful"))
            {
                Intent intent = new Intent(context, ResetPasswordActivity.class);
                intent.putExtra("username", name);
                context.startActivity(intent);
            }
            else
            {
                Toast toast=Toast. makeText(context,"Incorrect email or phone number", Toast.LENGTH_LONG);
                toast.show();
            }
        }catch (JSONException e) {
            e.printStackTrace();
        }

    }
}
