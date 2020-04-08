package com.example.studygowhere.Boundary;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentActivity;

import com.example.studygowhere.Control.DirectionsJSONParser;
import com.example.studygowhere.Control.WeatherManager;
import com.example.studygowhere.R;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static com.example.studygowhere.Boundary.LoginActivity.getUn;

/**
        * <h1>Directions UI</h1>
        * This is a user interface that is responsible for displaying a google map and the travel
        * route between two locations
        *
        * @author ILOVESSADMORE
        * @version 1.0
        */
public class DirectionsActivity extends FragmentActivity implements OnMapReadyCallback,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        LocationListener, NavigationView.OnNavigationItemSelectedListener
{
    /**
     * Instance variable mMap.
     */
    private GoogleMap mMap;
    /**
     * Instance variable googleApiClient.
     */
    private GoogleApiClient googleApiClient;
    /**
     * Instance variable locationRequest.
     */
    private LocationRequest locationRequest;
    /**
     * Instance variable currentUserLocationMarker.
     * This is the marker to mark the user current location.
     */
    private Marker currentUserLocationMarker;
    /**
     * Static constant containing User Location Code Request
     */
    private static final int Request_User_Location_Code = 99;
    /**
     * Instance variable btnDetails
     * Refers to the button detail in the XML Layout
     */
    Button btnDetails;
    /**
     * Instance variable btnExit
     * Refers to the button exit in the XML Layout
     */
    Button btnExit;
    /**
     * Instance variable mOrigin
     * Contains latitude and longitude of the origin
     */
    private LatLng mOrigin;
    /**
     * Instance variable mDestination
     * Contains latitude and longitude of the destination
     */
    private LatLng mDestination;
    /**
     * Instance variable mPolyLine
     * Refers to the polyline for the route
     */
    private Polyline mPolyline;
    /**
     * Instance variable mode
     * Refers to the mode of travel passed with the intent
     */
    private String mode;
    /**
     * Instance variable drawer which will be used for displaying the navigation drawer view
     */
    DrawerLayout drawer;
    /**
     * Instance variable where NavigationView navigationView in the XML file will be assigned to.
     */
    NavigationView navigationView;
    /**
     * Instance variable that is used to open and close the navigation drawer.
     */
    ActionBarDrawerToggle toggle;
    /**
     * Instance variable where Toolbar toolbar in the XML file will be assigned to.
     * This is used mainly to contain the navigation drawer toggle.
     */
    Toolbar toolbar;
    /**
     * Instance variable called later to parse weather data
     */
    private WeatherManager weatherManager = new WeatherManager();
    /**
     * Static constant for Request Code
     */
    private static final int REQUEST_CODE = 101;
    /**
     * Contains the currentLocation details
     */
    Location currentLocation;
    /**
     * Instance variable intent
     * Refers to the intent that started this activity
     */
    Intent intent;
    /**
     * ArrayList containing strings of travel instructions
     */
    ArrayList<ArrayList<String>> inst;
    /**
     * Instance variable fusedLocationProviderClient
     */
    FusedLocationProviderClient fusedLocationProviderClient;
    /**
     * Override method to initialize DirectionsActivity
     *
     * @param savedInstanceState The last saved instance state
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_directions);

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
        {
            checkUserLocationPermission();
        }
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            buildGoogleApiClient();
        }
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        fusedLocationProviderClient=LocationServices.getFusedLocationProviderClient(this);

        btnDetails = (Button) findViewById(R.id.detail);
        btnExit = (Button) findViewById(R.id.exit);

        mapFragment.getMapAsync(this);
        intent=getIntent();
        mode=intent.getStringExtra("Mode");
        if (mode.compareToIgnoreCase("mode=transit")!=0){
            btnDetails.setVisibility(View.GONE);
        }else {
            btnDetails.setVisibility(View.VISIBLE);
        }

        btnDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("html_instructions",""+inst);
                if(inst.toString().equals("[]")) {
                    Toast.makeText(getApplicationContext(),"No route is found", Toast.LENGTH_LONG).show();
                }
                else {
                    Intent i = new Intent(DirectionsActivity.this, RouteDetailsActivity.class);
                    i.putStringArrayListExtra("details", inst.get(0));
                    startActivity(i);
                }
            }
        });

        btnExit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent i = new Intent(DirectionsActivity.this,MapsActivity.class);
                        startActivity(i);
                    }
                });
        drawer = findViewById(R.id.drawer);
        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("StudyGoWhere");
        toolbar.setTitleTextColor(255-255-255);
        navigationView = findViewById(R.id.navigationView);
        navigationView.setNavigationItemSelectedListener(this);
        toggle = new ActionBarDrawerToggle(this,drawer,toolbar,R.string.open,R.string.close);
        drawer.addDrawerListener(toggle);
        toggle.setDrawerIndicatorEnabled(true);
        toggle.syncState();

    }
    /**
     * This method is to marker the user current location with a marker and title "user current location"
     * This method is also used to draw route from user location to destination and update weather details.
     * @param location user current location
     */
    public void onLocationChanged(Location location) {
        if (currentUserLocationMarker != null) {
            currentUserLocationMarker.remove();
        }
        LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
        mOrigin=new LatLng(location.getLatitude(),location.getLongitude());
        MarkerOptions markerOption = new MarkerOptions();
        markerOption.position(mOrigin);
        markerOption.title("user current location");
        markerOption.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE));
        currentUserLocationMarker = mMap.addMarker(markerOption);
        mMap.moveCamera(CameraUpdateFactory.newLatLng(mOrigin));
        mMap.animateCamera(CameraUpdateFactory.zoomBy(14));
        if (googleApiClient != null) {
            LocationServices.FusedLocationApi.removeLocationUpdates(googleApiClient, this);
        }

        mDestination=intent.getParcelableExtra("LatLng");
        String saName = intent.getStringExtra("Name");
        if (saName == null) {
            saName = "Destination";
        }
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(mDestination, 14));
        MarkerOptions markerOption2 = new MarkerOptions();
        markerOption2.position(mDestination);
        markerOption2.title(saName);
        markerOption2.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_CYAN));
        mMap.addMarker(markerOption2);

        drawRoute();
        updateWeather();
    }

    /**
     * Override method to call after onCreate() to connect to googleApiClient

     */
    @Override
    protected void onStart() {
        super.onStart();
        // Connect the client.
        googleApiClient.connect();
    }
    /**
     * A method to update weather text
     * @param originWeather Weather details of current location
     * @param destinationWeather Weather details of destination location

     */
    public void updateTextView(String originWeather,String destinationWeather) {
        TextView weather = (TextView) findViewById(R.id.weather);
        String toThis="Weather:\n"+"Current Location:\n"+originWeather+"\nDestination:\n"+destinationWeather;
        weather.setText(toThis);
    }
    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setMyLocationEnabled(true);
        currentLocation=LocationServices.FusedLocationApi.getLastLocation(googleApiClient);
    }

    /**
     * This method is to check whether GPS permission is granted for the application.
     * @return True if permission is granted.
     */
    public boolean checkUserLocationPermission()
    {
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED)
        {
            if(ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_FINE_LOCATION))
            {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, Request_User_Location_Code);
            }
            else
            {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, Request_User_Location_Code);
            }
            return false;
        }
        else
        {
            return true;
        }
    }
    /**
     * This method is to check whether Google API permission is granted.
     * The permission will only be granted with a authentic Google API key
     * @param requestCode
     * @param permissions
     * @param grantResults
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch(requestCode)
        {
            case Request_User_Location_Code:
                if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                {
                    if(ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED)
                    {
                        if(googleApiClient == null)
                        {
                            buildGoogleApiClient();
                        }
                        mMap.setMyLocationEnabled(true);
                    }
                }
                else
                {
                    Toast.makeText(this,"Permission Denied", Toast.LENGTH_SHORT).show();
                }
                return;
        }
    }
    /**
     * This method is to buildGoogleApiClient.
     */
    protected synchronized void buildGoogleApiClient()
    {
        googleApiClient = new GoogleApiClient.Builder(this).addConnectionCallbacks(this).addOnConnectionFailedListener(this).addApi(LocationServices.API).build();
        //googleApiClient.connect();
    }

    /**
     * This method is to send location request in a fixed time interval
     * @param bundle
     */
    @Override
    public void onConnected(@Nullable Bundle bundle) {
        locationRequest = new LocationRequest();
        locationRequest.setInterval(1100);
        locationRequest.setFastestInterval(1100);
        locationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED)
        {
            LocationServices.FusedLocationApi.requestLocationUpdates(googleApiClient, locationRequest, this);

        }
        Location location = LocationServices.FusedLocationApi.getLastLocation(
                googleApiClient);
        if (location != null) {
            currentLocation=location;
            MapFragment mapFragment = (MapFragment) getFragmentManager()
                    .findFragmentById(R.id.map);
            //mapFragment.getMapAsync(this);
        }
    }

    /**
     * Override method
     * @param i
     */
    @Override
    public void onConnectionSuspended(int i) {

    }

    /**
     * Override method
     * @param connectionResult
     */
    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }
    /**
     * A method to draw route between two locations
     */
    private void drawRoute(){
        // Getting URL to the Google Directions API
        String url = getDirectionsUrl(mOrigin, mDestination);


        DownloadTask downloadTask = new DownloadTask();

        // Start downloading json data from Google Directions API
        downloadTask.execute(url);
    }
    /**
     * A method to update weather details
     */
    private void updateWeather(){
        Log.d("heree","updateWeather");
        WeatherDownloadTask downloadTask =new WeatherDownloadTask();
        downloadTask.execute();
        Log.d("heree","updateWeather2");
    }

    /**
     * A method to obtain the URL to download data from
     * @param origin Latitude and Longitude of current location
     * @param dest Latitude and Longitude of destination location
     * @return Returns string of URL to download data from
     */
    private String getDirectionsUrl(LatLng origin,LatLng dest){

        // Origin of route
        String str_origin = "origin="+origin.latitude+","+origin.longitude;


        // Destination of route
        String str_dest = "destination="+dest.latitude+","+dest.longitude;

        // Key
        String key = "key=" + "AIzaSyDjb74W-DWzZrc9jOWD92ujMrGehhCIV3U";

        // Building the parameters to the web service
        String parameters = str_origin+"&"+str_dest+"&"+key+"&"+mode;

        // Output format
        String output = "json";

        // Building the url to the web service
        String url = "https://maps.googleapis.com/maps/api/directions/"+output+"?"+parameters;
        Log.d("URL","url"+url) ;
        return url;
    }

    /**
     *  A method to download json data from url
     *  @param strUrl URL to download data from
     *  @return Returns string of json data obtained
     */
    private String downloadUrl(String strUrl) throws IOException {
        String data = "";
        InputStream iStream = null;
        HttpURLConnection urlConnection = null;
        try{
            URL url = new URL(strUrl);

            // Creating an http connection to communicate with url
            urlConnection = (HttpURLConnection) url.openConnection();

            // Connecting to url
            urlConnection.connect();

            // Reading data from url
            iStream = urlConnection.getInputStream();

            BufferedReader br = new BufferedReader(new InputStreamReader(iStream));

            StringBuffer sb  = new StringBuffer();

            String line = "";
            while( ( line = br.readLine())  != null){
                sb.append(line);
            }

            data = sb.toString();

            br.close();

        }catch(Exception e){
            Log.d("Exception on download", e.toString());
        }finally{
            iStream.close();
            urlConnection.disconnect();
        }
        return data;
    }

    /** A class to download data from Google Directions URL */
    private class DownloadTask extends AsyncTask<String, Void, String> {

        // Downloading data in non-ui thread
        /**
         * This method is to do background operation on background thread.
         * @param url String containing the url
         * @return JSONObject string received from URL
         */
        @Override
        protected String doInBackground(String... url) {

            // For storing data from web service
            String data = "";

            try{
                // Fetching the data from web service
                data = downloadUrl(url[0]);
                Log.d("DownloadTask","DownloadTask : " + data);
            }catch(Exception e){
                Log.d("Background Task",e.toString());
            }
            return data;
        }

        // Executes in UI thread, after the execution of
        // doInBackground()
        /**
         * This method parses the JSONObject obtained
         * @param result The result from URl in background task
         */
        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            ParserTask parserTask = new ParserTask();

            // Invokes the thread for parsing the JSON data
            parserTask.execute(result);
        }
    }

    /** A class to parse the Google Directions in JSON format */
    private class ParserTask extends AsyncTask<String, Integer, List<List<HashMap<String,String>>> > {

        // Parsing the data in non-ui thread
        /**
         * This method is to do background operation on background thread.
         * @param jsonData JSONObject to be parsed
         * @return List of parsed direction details
         */
        @Override
        protected List<List<HashMap<String, String>>> doInBackground(String... jsonData) {

            JSONObject jObject;
            List<List<HashMap<String, String>>> routes = null;

            try{
                jObject = new JSONObject(jsonData[0]);
                DirectionsJSONParser parser = new DirectionsJSONParser();

                // Starts parsing data
                routes = parser.parse(jObject);
                inst= parser.getInstructions();
            }catch(Exception e){
                e.printStackTrace();
            }
            return routes;
        }

        // Executes in UI thread, after the parsing process
        /**
         * This method is to update the route with obtained route details
         * @param result List of polyline lists parsed from JSON Object
         */
        @Override
        protected void onPostExecute(List<List<HashMap<String, String>>> result) {
            ArrayList<LatLng> points = null;
            PolylineOptions lineOptions = null;

            // Traversing through all the routes
            for(int i=0;i<result.size();i++){
                points = new ArrayList<LatLng>();
                lineOptions = new PolylineOptions();

                // Fetching i-th route
                List<HashMap<String, String>> path = result.get(i);

                // Fetching all the points in i-th route
                for(int j=0;j<path.size();j++){
                    HashMap<String,String> point = path.get(j);

                    double lat = Double.parseDouble(point.get("lat"));
                    double lng = Double.parseDouble(point.get("lng"));
                    LatLng position = new LatLng(lat, lng);

                    points.add(position);
                }

                // Adding all the points in the route to LineOptions
                lineOptions.addAll(points);
                lineOptions.width(8);
                lineOptions.color(Color.BLUE);
            }

            // Drawing polyline in the Google Map for the i-th route
            if(lineOptions != null) {
                if(mPolyline != null){
                    mPolyline.remove();
                }
                mPolyline = mMap.addPolyline(lineOptions);

            }else
                Toast.makeText(getApplicationContext(),"No route is found", Toast.LENGTH_LONG).show();
        }
    }
    /** An asynchronous class to download data from Weather API URL */
    private class WeatherDownloadTask extends AsyncTask<Void, Void, String> {

        // Downloading data in non-ui thread
        /**
         * This method is to do background operation on background thread.
         * @param params Void in this case
         * @return JSONObject string received from URL
         */
        @Override
        protected String doInBackground(Void... params) {

            // For storing data from web service

            String data = "";

            try{
                // Fetching the data from web service
                data = downloadUrl("https://api.data.gov.sg/v1/environment/2-hour-weather-forecast");
                Log.d("DownloadTask","DownloadTask : " + data);
            }catch(Exception e){
                Log.d("Background Task",e.toString());
            }
            return data;
        }

        // Executes in UI thread, after the execution of
        // doInBackground()
        /**
         * This method parses the JSONObject obtained
         * @param result The result from URl in background task
         */
        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            WeatherParserTask parserTask=new WeatherParserTask();

            // Invokes the thread for parsing the JSON data
            parserTask.execute(result);
        }
    }
    /** An asynchronous class to parse the Weather Data in JSON format */
    private class WeatherParserTask extends AsyncTask<String, Integer, HashMap<String, List<String>> > {

        // Parsing the data in non-ui thread
        /**
         * This method is to do background operation on background thread.
         * @param jsonData JSONObject to be parsed
         * @return HashMap of parsed weather details
         */
        @Override
        protected HashMap<String, List<String>> doInBackground(String... jsonData) {

            JSONObject jObject;
            HashMap<String, List<String>> weatherArray = null;

            try {
                jObject = new JSONObject(jsonData[0]);
                // Starts parsing data
                weatherArray = weatherManager.weatherParse(jObject);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return weatherArray;
        }

        // Executes in UI thread, after the parsing process
        /**
         * This method is to update the TextView with current weather details
         * @param result HashMap result parsed from JSON Object
         */
        @Override
        protected void onPostExecute(HashMap<String, List<String>> result) {
            try {
                String originWeather=weatherManager.getNearestAreaWeather(result,mOrigin.latitude,mOrigin.longitude);
                String destinationWeather=weatherManager.getNearestAreaWeather(result,mDestination.latitude,mDestination.longitude);
                updateTextView(originWeather,destinationWeather);
            } catch (Exception e) {
                e.printStackTrace();
            }

        }

    }

    /**
     * This method is listener method of the navigation drawer.
     * If button StudyGoWhere is clicked, SGWActivity will be started
     * If button account is clicked, the value of Un will be checked.
     * If Un is null, it means that the user has not logged in. LoginActivity will be started.
     * If Um is not null, ProfileActivity will be started.
     * The drawer will be closed after pressing the back button even the new activity is opened from the drawer.
     * @param menuItem Refers to the menu item selected
     * @return
     */

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        int id=menuItem.getItemId();
        switch (id){
            case R.id.StudyGoWhere:
                Intent i = new Intent(DirectionsActivity.this, SGWActivity.class);
                startActivity(i);
                break;
            case R.id.account:
                if(getUn() != null)
                {
                    Intent k = new Intent(DirectionsActivity.this, ProfileActivity.class);
                    startActivity(k);
                }
                else
                {
                    Intent j = new Intent(DirectionsActivity.this, LoginActivity.class);
                    startActivity(j);
                }
                break;
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}