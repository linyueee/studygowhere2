package com.example.studygowhere;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;

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

public class CustomisebookmarkWorker extends AsyncTask<String, Void, String> {

    Context context;

    CustomisebookmarkWorker(Context ctx)
    {
        context = ctx;
    }
    @Override
    protected String doInBackground(String... params) {
        String username = params[0];
        String saname = params[1];
        String type = params[2];
        String addbookmark_url = "http://studygowhere.000webhostapp.com/Addbookmark.php";
        String deletebookmark_url = "http://studygowhere.000webhostapp.com/Deletebookmark.php";
        URL url = null;
            try {
                if (type.equals("Add")) {
                    url = new URL(addbookmark_url);
                }
                else if(type.equals("Delete"))
                {
                    url = new URL(deletebookmark_url);
                }
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                String post_data = URLEncoder.encode("username", "UTF-8") + "=" + URLEncoder.encode(username, "UTF-8") + "&"
                        + URLEncoder.encode("saname", "UTF-8") + "=" + URLEncoder.encode(saname, "UTF-8");
                bufferedWriter.write(post_data);
                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();
                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "iso-8859-1"));
                String result = "";
                String line = "";
                while ((line = bufferedReader.readLine()) != null) {
                    result += line;
                }
                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();
                return result;
            }catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        
        return null;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected void onPostExecute(String result) {
        Toast toast=Toast. makeText(context,result, Toast.LENGTH_LONG);
        toast.show();
        ProfileActivity.bookmarkflag = false;
        if(result.equals("Deleted from your bookmark"))
        {
            context.startActivity(new Intent(context, ProfileActivity.class));
        }
    }

    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }
}
