package com.example.studygowhere.Control;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.widget.Toast;

import com.example.studygowhere.Boundary.ProfileActivity;

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
 * <h1>Customise Bookmark controller</h1>
 * This is a asynchronous class that passes values into php files and the php files return
 * a string value after communicating with the Database.
 * If the type passed in is "Add", the username and the Study Area name values will be stored into
 * local variables user_name and saname and passed into the php file. The php file will do a INSERT sql query on the
 * Database and return a message string accordingly.
 * If the type passed in is "Delete", the username and the Study Area name values will
 * be stored into local variables and then passed into the php file. The php file will do a DELETE query.
 * If the query fails, it means the username and Study Area pair is not in the bookmark Database.
 *
 * @author ILOVESSADMORE
 * @version 1.0
 */
public class CustomiseBookmarkWorker extends AsyncTask<String, Void, String> {
    /**
     * Instance variable context
     */
    Context context;

    /**
     * constructor with parameter context
     * @param ctx
     */
    public CustomiseBookmarkWorker(Context ctx)
    {
        context = ctx;
    }


    /**
     * This method is to do background operation on background thread.
     * @param params parameters can be of any types and number
     * @return
     */
    @Override
    protected String doInBackground(String... params) {
        String username = params[0];
        String saName = params[1];
        String type = params[2];
        String addBookmark_URL = "https://studygowhere.000webhostapp.com/Addbookmark.php";
        String deleteBookmark_URL = "https://studygowhere.000webhostapp.com/Deletebookmark.php";
        URL url = null;
            try {
                if (type.equals("Add")) {
                    url = new URL(addBookmark_URL);
                }
                else if(type.equals("Delete"))
                {
                    url = new URL(deleteBookmark_URL);
                }
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                String post_data = URLEncoder.encode("username", "UTF-8") + "=" + URLEncoder.encode(username, "UTF-8") + "&"
                        + URLEncoder.encode("saname", "UTF-8") + "=" + URLEncoder.encode(saName, "UTF-8");
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


    /**
     * This method is to display a message from the php file
     * Delete bookmark is successful, ProfileActivity will be started.
     * @param result string result returned from the php file
     */
    @Override
    protected void onPostExecute(String result) {
        Toast toast=Toast. makeText(context,result, Toast.LENGTH_LONG);
        toast.show();
        ProfileActivity.bookmarkFlag = false;
        if(result.equals("Deleted from your bookmark"))
        {
            context.startActivity(new Intent(context, ProfileActivity.class));
        }
    }


}
