package com.example.studygowhere.Control;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WeatherManager {

    public HashMap<String, List<String>> weatherParse(JSONObject jo) throws JSONException {
        JSONArray areaLatLng=null;
        JSONArray areaWeather=null;
        HashMap<String, List<String>> weatherDetails = new HashMap<String, List<String>>();
        areaLatLng=jo.getJSONArray("area_metadata");
        areaWeather=((JSONObject)jo.getJSONArray("items").get(0)).getJSONArray("forecasts");
        for(int i=0;i<areaLatLng.length();i++){
            List<String> detailsList = new ArrayList<String>();
            String name=((JSONObject)areaLatLng.get(i)).getString("name");
            String lat=((JSONObject)areaLatLng.get(i)).getJSONObject("label_location").getString("latitude");
            String lng=((JSONObject)areaLatLng.get(i)).getJSONObject("label_location").getString("longitude");
            String weather=((JSONObject)areaWeather.get(i)).getString("forecast");
            detailsList.add(lat);
            detailsList.add(lng);
            detailsList.add(weather);
            //Log.d("Trying pls",weather);
            weatherDetails.put(name, detailsList);
        }
        return weatherDetails;
    }

    public String getNearestAreaWeather(HashMap<String,List<String>> weatherDetails,double Latitude, double Longitude) throws Exception {
        double closestDist=0.0;
        String closestWeather="";
        List<List<String>> DistList = new ArrayList<List<String>>();
        double[][] result = new double[1][];
        for (Map.Entry<String,List<String>> mapElement : weatherDetails.entrySet()) {
            String key = (String)mapElement.getKey();
            //Log.d("Trying pls 2",key);

            List<String> values= mapElement.getValue();
            //Log.d("Trying pls 3",values.get(0));
            double areaLat=Double.parseDouble(values.get(0));
            double areaLng=Double.parseDouble(values.get(1));
            String weather=values.get(2);
            String distance=String.valueOf(CalculateDistanceFromArea(areaLat,areaLng,Latitude,Longitude));
            List<String> detailsList=new ArrayList<String>();
            detailsList.add(key);
            detailsList.add(distance);
            detailsList.add(weather);
            //Log.d("Trying pls 3.8",key+" "+weather+" "+distance);
            DistList.add(detailsList);
        }
        String closestArea=DistList.get(0).get(0);
        closestDist=Double.parseDouble(DistList.get(0).get(1));
        closestWeather=DistList.get(0).get(2);
        for (int i = 0; i < DistList.size(); i++) {
            double dist = Double.parseDouble(DistList.get(i).get(1));
            if (dist <= closestDist) {
                closestDist = dist;
                closestArea = DistList.get(i).get(0);
                closestWeather = DistList.get(i).get(2);
            }
        }
        //Log.d("closest",closestArea+" "+closestWeather+" "+String.valueOf(closestDist));
        return closestWeather;
    }

    public double CalculateDistanceFromArea(double Latitude, double Longitude, double LocationLatitude, double LocationLongitude) {
        double r = 6371e3;
        double TaxLat = Math.toRadians(Latitude);
        double UserLat = Math.toRadians(LocationLatitude);

        double alpha = Math.toRadians(Latitude - LocationLatitude);
        double lamda = Math.toRadians(Longitude - LocationLongitude);

        double a = Math.sin(alpha / 2) * Math.sin(alpha / 2) + Math.cos(UserLat) * Math.cos(TaxLat) * Math.sin(lamda / 2) * Math.sin(lamda / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        return r * c;

    }
}
