package com.example.studygowhere;

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
import android.os.Bundle;

public class AccountWorker extends AsyncTask<String, Void, String>
    {
        Context context;
        AlertDialog alertDialog;

        AccountWorker(Context ctx)
        {
            context = ctx;
        }
        @Override
        protected String doInBackground(String... params) {
            String type = params[0];
            String register_url = "http://studygowhere.000webhostapp.com/register.php";
            String login_url = "http://studygowhere.000webhostapp.com/Login.php";
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

        @Override
        protected void onPreExecute() {
            alertDialog = new AlertDialog.Builder(context).create();
            alertDialog.setTitle("LoginActivity Status");
        }

        @Override
        protected void onPostExecute(String result) {
            alertDialog.setMessage(result);
            alertDialog.show();
            if(result.equals("Welcome"))
            {
                context.startActivity(new Intent(context, ProfileActivity.class));
            }
/*            else
            {
                context.startActivity(new Intent(context, LoginActivity.class));
            }*/
            if(result.equals("Register successful"))
            {
                Toast toast = Toast.makeText(context, "Account created", Toast.LENGTH_SHORT);
                toast.show();
                context.startActivity(new Intent(context, LoginActivity.class));

            }




        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }

    }


