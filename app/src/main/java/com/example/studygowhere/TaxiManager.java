package com.example.studygowhere;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.maps.GoogleMap;
import com.google.maps.android.geojson.GeoJsonFeature;
import com.google.maps.android.geojson.GeoJsonLayer;
import com.google.maps.android.geojson.GeoJsonMultiPoint;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.MessageFormat;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class TaxiManager extends AsyncTask<Void,Void,String> {

    private final String USER_AGENT = "Mozilla/5.0";
    private String myResponse;


    public void onRunTaxi(double latitude, double longitude) throws Exception {
        double[][] ans = NearestTaxi(8,longitude,latitude);

    }

    @Override
    protected String doInBackground(Void... voids) {


        OkHttpClient client = new OkHttpClient();
        String GET_URL = "https://api.data.gov.sg/v1/transport/taxi-availability";

        Request request = new Request.Builder().url(GET_URL).build();

        client.newCall(request).enqueue(new Callback(){

            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()){
                    myResponse = response.body().string();
                }
            }
        });

        return myResponse;

        /*
        String GET_URL = "https://api.data.gov.sg/v1/transport/taxi-availability"; //+ GetDateTime();
        try {
            URL obj = new URL(GET_URL);
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();
            con.setRequestMethod("GET");
            con.setRequestProperty("User-Agent", USER_AGENT);
            int responseCode = con.getResponseCode();
            System.out.println("GET Response Code :: " + responseCode);
            if (responseCode == HttpURLConnection.HTTP_OK) { // success
                BufferedReader in = new BufferedReader(new InputStreamReader(
                        con.getInputStream()));
                String inputLine;
                StringBuffer response = new StringBuffer();

                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();
                System.out.println(response.toString());
                Log.d("hello", "doInBackground() returned: " + response.toString());
                return response.toString();
            } else {
                Log.d("hello", "doInBackground() returned: " + "FAILED");
                return "";
            }
        }
        catch (Exception e){

        }

        */
    }

    public JSONArray GetTaxiInformation() throws Exception {
            String response = doInBackground();
            JSONArray CoordinateList = null;
            try {
                JSONObject myResponse = new JSONObject(response);
                //JSONObject myResponse2 = new JSONObject(myResponse.getString("features[geometry"));
                CoordinateList = (JSONArray) ((JSONObject) ((JSONObject) ((JSONArray) myResponse.get("features")).get(0)).get("geometry")).get("coordinates");
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        return CoordinateList;
    }


    public double[][] NearestTaxi(int NumOfTaxi, double Longitude, double Latitude) throws Exception {

        JSONArray LongLatList = GetTaxiInformation();

        int[] list = new int[NumOfTaxi];
        double[] DistList = new double[LongLatList.length()];
        double[][] result = new double[NumOfTaxi][];


        for (int i = 0; i < LongLatList.length(); i++) {
            JSONArray dummy = LongLatList.getJSONArray(i);
            DistList[i] = CalculateDistanceFromTaxi(dummy.getDouble(0), dummy.getDouble(1), Latitude, Longitude);
            //System.out.println(DistList[i]);
        }

        for (int i = 0; i < NumOfTaxi; i++)
        {
            list[i] = 0;
            for (int j = 0; j < DistList.length; j++)
            {
                if (check(list, j, i))
                {
                    System.out.println(j);
                    continue;
                }
                else if (DistList[j] <= DistList[list[i]] )
                {
                    list[i] = j;
                }
            }
        }

        for (int i = 0; i < NumOfTaxi; i++) {
            result[i] = new double[] {((JSONArray) LongLatList.get(list[i])).getDouble(1),((JSONArray) LongLatList.get(list[i])).getDouble(0)};
            System.out.println(DistList[list[i]]);

            System.out.println(list[i]);
        }
        return result;
    }

    public boolean check(int[] list, int j, int index) {
        for (int i = list.length -1; i >= 0; i--) {
            if (list[i] == j)
                return true;
        }
        return false;
    }

    public double CalculateDistanceFromTaxi(double Longitude, double Latitude, double UserLocationLatitude, double UserLocationLongitude) {
        double r = 6371e3;
        double TaxLat = Math.toRadians(Latitude);
        double UserLat = Math.toRadians(UserLocationLatitude);

        double alpha = Math.toRadians(Latitude - UserLocationLatitude);
        double lamda = Math.toRadians(Longitude - UserLocationLongitude);

        double a = Math.sin(alpha/2) * Math.sin(alpha/2) +
                Math.cos(UserLat) * Math.cos(TaxLat) *
                        Math.sin(lamda/2) * Math.sin(lamda/2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));

        return r * c;

    }



}
