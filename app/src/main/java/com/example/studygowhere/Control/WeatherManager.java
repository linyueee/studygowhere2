package com.example.studygowhere.Control;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <h1>Weather Information controller</h1>
 * This is a class that parses weather data and finds the weather of the area closest to the location provided.
 * @author ILOVESSADMORE
 * @version 1.0
 */
public class WeatherManager {
    /**
     * This method parses weather data JSONObject obtained from API URL.
     * @param jo JSONObject to be parsed
     * @return Return a HashMap with area name mapped to an array containing its latitude,longitude, and weather
     */
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

    /**
     * This method finds the weather of the nearest area from a given Latitude and Longitude
     * @param weatherDetails This is the HashMap containing weather details
     * @param Latitude This double variable contains the latitude of the location of which weather details is requested
     * @param Longitude This double variable contains the Longitude of the location of which weather details is requested
     * @return Return a String containing weather of the nearest area from given Latitude and Longitude
     */
    public String getNearestAreaWeather(HashMap<String,List<String>> weatherDetails,double Latitude, double Longitude) throws Exception {
        double closestDist=0.0;
        String closestWeather="";
        List<List<String>> distList = new ArrayList<List<String>>();
        double[][] result = new double[1][];
        for (Map.Entry<String,List<String>> mapElement : weatherDetails.entrySet()) {
            String key = (String)mapElement.getKey();

            List<String> values= mapElement.getValue();
            double areaLat=Double.parseDouble(values.get(0));
            double areaLng=Double.parseDouble(values.get(1));
            String weather=values.get(2);
            String distance=String.valueOf(calculateDistanceFromArea(areaLat,areaLng,Latitude,Longitude));
            List<String> detailsList=new ArrayList<String>();
            detailsList.add(key);
            detailsList.add(distance);
            detailsList.add(weather);
            distList.add(detailsList);
        }
        String closestArea=distList.get(0).get(0);
        closestDist=Double.parseDouble(distList.get(0).get(1));
        closestWeather=distList.get(0).get(2);
        for (int i = 0; i < distList.size(); i++) {
            double dist = Double.parseDouble(distList.get(i).get(1));
            if (dist <= closestDist) {
                closestDist = dist;
                closestArea = distList.get(i).get(0);
                closestWeather = distList.get(i).get(2);
            }
        }
        return closestWeather;
    }
    /**
     * This method calculates the distance between two locations given their latitudes and longitudes
     * @param latitude This double variable contains the Latitude of the area being checked for distance from location
     * @param longitude This double variable contains the Longitude of the area being checked for distance from location
     * @param locationLatitude This double variable contains the Latitude of the location of which weather details is requested
     * @param locationLongitude This is contains the Longitude of the location of which weather details is requested
     * @return Return a double containing the distance between a given area and the current location
     */
    public double calculateDistanceFromArea(double latitude, double longitude, double locationLatitude, double locationLongitude) {
        double r = 6371e3;
        double areaLat = Math.toRadians(latitude);
        double userLat = Math.toRadians(locationLatitude);

        double alpha = Math.toRadians(latitude - locationLatitude);
        double lamda = Math.toRadians(longitude - locationLongitude);

        double a = Math.sin(alpha / 2) * Math.sin(alpha / 2) + Math.cos(userLat) * Math.cos(areaLat) * Math.sin(lamda / 2) * Math.sin(lamda / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        return r * c;

    }
}
