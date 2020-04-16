package com.example.studygowhere.Control;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;

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

import android.widget.Toast;

import com.example.studygowhere.Boundary.LoginActivity;
import com.example.studygowhere.Boundary.ProfileActivity;


/**
 * <h1>login and register controller</h1>
 * This is a asynchronous class that passes values into php files and the php files return
 * a string value after communicating with the Database.
 * If the type passed in is "login", the username and the password values will be stored into
 * local variables user_name and pw and passed into the php file. The php file will do a sql query on the
 * Database and return a string message accordingly.
 * If the type passed in is "register", the username, the password, the email and the phone number will
 * be stored into local variables and then passed into the php file. The php file will do a INSERT query.
 * If the query fails, it means the username has already existed.
 *
 * @author ILOVESSADMORE
 * @version 1.0
 */
public class AccountWorker extends AsyncTask<String, Void, String>
    {
        /**
         * Instance variable context
         */
        Context context;

        /**
         * Instance variable alertdialog
         */
        AlertDialog alertDialog;


        /**
         * constructor with parameter context
         * @param ctx
         */
        public AccountWorker(Context ctx)
        {
            context = ctx;
        }


        /**
         * This method is to do background operation on background thread.
         * @param params parameters can be of any types and number
         * @return Json string
         */
        @Override
        protected String doInBackground(String... params) {
            String type = params[0];
            String register_url = "https://studygowhere.000webhostapp.com/register.php";
            String login_url = "https://studygowhere.000webhostapp.com/Login.php";
            if(type.equals("login"))
            {
                try{
                    String user_name = params[1];
                    String pw = params[2];
                    URL url = new URL(login_url);
                    HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
                    httpURLConnection.setRequestMethod("POST");
                    httpURLConnection.setDoOutput(true);
                    httpURLConnection.setDoInput(true);
                    OutputStream outputStream = httpURLConnection.getOutputStream();
                    BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                    String post_data = URLEncoder.encode("user_name","UTF-8")+"="+URLEncoder.encode(user_name,"UTF-8")+"&"
                            +URLEncoder.encode("pw","UTF-8")+"="+URLEncoder.encode(pw,"UTF-8");
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

            }else if(type.equals("register"))
            {
                try{
                    String name = params[1];
                    String password = params[2];
                    String email = params[3];
                    String mobile = params[4];
                    URL url = new URL(register_url);
                    HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
                    httpURLConnection.setRequestMethod("POST");
                    httpURLConnection.setDoOutput(true);
                    httpURLConnection.setDoInput(true);
                    OutputStream outputStream = httpURLConnection.getOutputStream();
                    BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                    String post_data = URLEncoder.encode("name","UTF-8")+"="+URLEncoder.encode(name,"UTF-8")+"&"
                            +URLEncoder.encode("password","UTF-8")+"="+URLEncoder.encode(password,"UTF-8")+"&"
                            +URLEncoder.encode("email","UTF-8")+"="+URLEncoder.encode(email,"UTF-8")+"&"
                            +URLEncoder.encode("mobile","UTF-8")+"="+URLEncoder.encode(mobile,"UTF-8");
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

            }
            return null;
        }


        /**
         * This method is to display a alert dialog before the main thread begins
         */
        @Override
        protected void onPreExecute() {
            alertDialog = new AlertDialog.Builder(context).create();
            alertDialog.setTitle("LoginActivity Status");
        }


        /**
         * This method is to display message in the alert dialog and update the UI accordingly
         * @param result string result returned from the php file
         */
        @Override
        protected void onPostExecute(String result) {
            alertDialog.setMessage(result);
            alertDialog.show();


            if(result.equals("Welcome"))
            {
                context.startActivity(new Intent(context, ProfileActivity.class));
            }

            if(result.equals("register successful"))
            {
                Toast toast = Toast.makeText(context, "Account created", Toast.LENGTH_SHORT);
                toast.show();
                context.startActivity(new Intent(context, LoginActivity.class));
            }
        }

    }