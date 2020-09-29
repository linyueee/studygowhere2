package com.example.studygowhere.Control;

import android.os.AsyncTask;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * <h1>Taxi Information Controller</h1>
 * This is a asynchronous class that retrieve taxi information from the taxi gov API.
 * It parses the retrieved information and calculates the nearest taxi to user location.
 * @author ILOVESSADMORE
 * @version 1.0
 */
public class TaxiManager extends AsyncTask<Void,Void,String> {
    /**
     * Instance variable myResponse
     *
     */
    private String myResponse;

    /**
     * This is a background method that gets the taxi information from the taxi api
     * @param voids
     * @return returns a string of the response received from the taxi API.
     */
    @Override
    protected String doInBackground(Void... voids) {
        OkHttpClient client = new OkHttpClient();
        String GET_URL = "https://api.data.gov.sg/v1/transport/taxi-availability";
        Request request = new Request.Builder().url(GET_URL).build();
        //HttpConnectionParams.setConnectionTimeout(params, 10000);
        client.newCall(request).enqueue(new Callback() {

            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    myResponse = response.body().string();
                }
            }
        });

        return myResponse;
    }

    /**
     * This method parses and filters the required taxi information from the json data retrieved from the API
     * It filters out the coordinate list of taxis in singapore.
     * @return returns the coordinate list of taxis in singapore
     * @throws Exception
     */

    public JSONArray getTaxiInformation() throws Exception {
        String response = doInBackground();
        JSONArray CoordinateList = null;
        try {
            JSONObject myResponse = new JSONObject(response);
            CoordinateList = (JSONArray) ((JSONObject) ((JSONObject) ((JSONArray) myResponse.get("features")).get(0)).get("geometry")).get("coordinates");
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return CoordinateList;
    }

    /**
     * This method calculates the nearest taxi from the user location
     * @param NumOfTaxi
     * @param Longitude
     * @param Latitude
     * @return returns a 2d array consisting of the longitude and latitude of the nearest taxis to user location
     * @throws Exception
     */
    public double[][] nearestTaxi(int NumOfTaxi, double Longitude, double Latitude) throws Exception {

        JSONArray LongLatList = getTaxiInformation();
        int[] list = new int[NumOfTaxi];
        double[] DistList = new double[LongLatList.length()];
        double[][] result = new double[NumOfTaxi][];

        for (int i = 0; i < LongLatList.length(); i++) {
            JSONArray dummy = LongLatList.getJSONArray(i);
            DistList[i] = calculateDistanceFromTaxi(dummy.getDouble(0), dummy.getDouble(1), Latitude, Longitude);
        }

        for (int i = 0; i < NumOfTaxi; i++) {
            list[i] = 0;
            for (int j = 0; j < DistList.length; j++) {

                if (check(list, j)) {

                    System.out.println(j);
                    continue;
                } else if (DistList[j] <= DistList[list[i]]) {
                    list[i] = j;

                }
            }
        }

        for (int i = 0; i < NumOfTaxi; i++) {
            result[i] = new double[]{((JSONArray) LongLatList.get(list[i])).getDouble(1), ((JSONArray) LongLatList.get(list[i])).getDouble(0)};
            System.out.println(DistList[list[i]]);

            System.out.println(list[i]);
        }
        return result;
    }

    /**
     * This method is used as part of the sorting algorithm
     * It is used to check if the number given is already in the list
     * @param list
     * @param j
     * @return returns true if the number is already in the list, false if any otherwise.
     */
    public boolean check(int[] list, int j) {
        for (int i = list.length - 1; i >= 0; i--) {
            if (list[i] == j)
                return true;
        }
        return false;
    }

    /**
     * This method calculates the distance between 2 points defined by their longitude and latitude.
     * @param Longitude
     * @param Latitude
     * @param UserLocationLatitude
     * @param UserLocationLongitude
     * @return returns distance between 2 points.
     */
    public double calculateDistanceFromTaxi(double Longitude, double Latitude, double UserLocationLatitude, double UserLocationLongitude) {
        double r = 6371e3;
        double TaxLat = Math.toRadians(Latitude);
        double UserLat = Math.toRadians(UserLocationLatitude);

        double alpha = Math.toRadians(Latitude - UserLocationLatitude);
        double lamda = Math.toRadians(Longitude - UserLocationLongitude);

        double a = Math.sin(alpha / 2) * Math.sin(alpha / 2) + Math.cos(UserLat) * Math.cos(TaxLat) * Math.sin(lamda / 2) * Math.sin(lamda / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        return r * c;
    }
}
