package com.example.studygowhere.Control;

import android.content.Context;
import android.os.AsyncTask;

import com.example.studygowhere.Boundary.DetailActivity;

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
 * <h1>Read Review controller</h1>
 * This is a asynchronous class that passes values into php files and the php files return
 * a string value after communicating with the Database.
 * The method stores the parameters into local variables and passes them into the php file.
 * THe php file returns a JSON String containing username, Study Area name, review and rating combinations where the
 * Study Area name is equal to the name passed in.
 *
 * @author ILOVESSADMORE
 * @version 1.0
 */
public class ReadReviewWorker extends AsyncTask <String, Void, String> {
    /**
     * Instance variable context
     */
    Context context;

    /**
     * constructor with parameter context
     * @param context
     */
    public ReadReviewWorker(Context context) {
        this.context = context;
    }

    /**
     * This method is to do background operation on background thread.
     * @param params parameters can be of any types and number
     * @return
     */
    @Override
    protected String doInBackground(String... params) {
        String readReview_URL = "https://studygowhere.000webhostapp.com/Readreviewindetail.php";
        try{
            String saName = params[0];
            URL url = new URL(readReview_URL);
            HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setDoOutput(true);
            httpURLConnection.setDoInput(true);
            OutputStream outputStream = httpURLConnection.getOutputStream();
            BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
            String post_data = URLEncoder.encode("saname","UTF-8")+"="+URLEncoder.encode(saName,"UTF-8");
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
     * This method is to pass the JSon string returned from the php file in to method displayReview as parameter.
     * @param s string result returned from the php file
     */
    @Override
    protected void onPostExecute(String s) {
        DetailActivity.displayReview(s);
    }
}
