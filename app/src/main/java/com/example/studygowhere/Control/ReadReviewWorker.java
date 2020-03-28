package com.example.studygowhere.Control;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

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

public class ReadReviewWorker extends AsyncTask <String, Void, String> {

    Context context;

    public ReadReviewWorker(Context context) {
        this.context = context;
    }

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

    @Override
    protected void onPostExecute(String s) {
        //Log.i("check", "content"+ s);
        //DetailActivity.DisplayReviewMgr displayReviewMgr = new DetailActivity.DisplayReviewMgr(s);
        DetailActivity.DisplayReview(s);
    }
}
