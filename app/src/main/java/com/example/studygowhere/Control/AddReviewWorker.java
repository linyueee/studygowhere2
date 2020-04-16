package com.example.studygowhere.Control;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

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
 * <h1>Add Review controller</h1>
 * This is a asynchronous class that passes values into php files and the php files return
 * a string value after communicating with the Database.
 * The method stores the parameters into local variables and passes them into the php file.
 * A message will be displayed after the operation has done.
 *
 * @author ILOVESSADMORE
 * @version 1.0
 */
public class AddReviewWorker extends AsyncTask<String, Void, String> {

    /**
     * Instance variable context
     */
    Context context;

    /**
     * constructor with parameter context
     * @param context
     */
    public AddReviewWorker(Context context) {
        this.context = context;
    }


    /**
     * This method is to do background operation on background thread.
     * @param params parameters can be of any types and number
     * @return Json string
     */
    @Override
    protected String doInBackground(String... params) {
        String writeReview_url = "https://studygowhere.000webhostapp.com/Write_review.php";
        try{
            String user_name = params[0];
            String content = params[1];
            String saName = params[2];
            String rating = params[3];
            URL url = new URL(writeReview_url);
            HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setDoOutput(true);
            httpURLConnection.setDoInput(true);
            OutputStream outputStream = httpURLConnection.getOutputStream();
            BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
            String post_data = URLEncoder.encode("user_name","UTF-8")+"="+URLEncoder.encode(user_name,"UTF-8")+"&"
                    +URLEncoder.encode("content","UTF-8")+"="+URLEncoder.encode(content,"UTF-8")+"&"
                    +URLEncoder.encode("saname","UTF-8")+"="+URLEncoder.encode(saName,"UTF-8")+"&"
                    +URLEncoder.encode("rating","UTF-8")+"="+URLEncoder.encode(rating,"UTF-8");
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
     * This method is to display a message after the operation is done.
     * @param s string result returned from the php file
     */
    @Override
    protected void onPostExecute(String s) {
        Toast toast = Toast.makeText(context, "Review added", Toast.LENGTH_SHORT);
        toast.show();

    }
}