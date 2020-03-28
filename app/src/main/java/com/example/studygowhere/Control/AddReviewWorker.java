package com.example.studygowhere.Control;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.widget.Toast;

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

public class AddReviewWorker extends AsyncTask<String, Void, String> {
    Context context;

    String user_name;
    String content;
    String saName;
    String rating;
    public AddReviewWorker(Context context) {
        this.context = context;
    }

    @Override
    protected String doInBackground(String... params) {
        String writeReview_url = "https://studygowhere.000webhostapp.com/Write_review.php";
        try{
            user_name = params[0];
            content = params[1];
            saName = params[2];
            rating = params[3];
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

    @Override
    protected void onPostExecute(String s) {
        Toast toast = Toast.makeText(context, "Review added", Toast.LENGTH_SHORT);
        toast.show();
        Intent intent = new Intent(context, DetailActivity.class);
        intent.putExtra("Name", saName);
        context.startActivity(intent);
    }
}