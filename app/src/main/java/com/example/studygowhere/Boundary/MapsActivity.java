package com.example.studygowhere.Boundary;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.studygowhere.Control.CcDataHandler;
import com.example.studygowhere.Control.DataHandler;
import com.example.studygowhere.Control.LibraryDataHandler;
import com.example.studygowhere.Control.McDonaldsDataHandler;
import com.example.studygowhere.Control.SchoolDataHandler;
import com.example.studygowhere.Control.StarbucksDataHandler;
import com.example.studygowhere.Control.TaxiManager;
import com.example.studygowhere.Entity.StudyArea;
import com.example.studygowhere.R;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import com.google.android.gms.security.ProviderInstaller;
import com.google.android.material.navigation.NavigationView;

import com.google.maps.android.geojson.GeoJsonLayer;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.BufferedReader;
import java.io.IOException;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.List;

import javax.net.ssl.SSLContext;

import static com.example.studygowhere.Boundary.LoginActivity.getUn;
import static com.example.studygowhere.Control.CcDataHandler.addCCObjectFlag;
import static com.example.studygowhere.Control.DataHandler.cafeList;
import static com.example.studygowhere.Control.DataHandler.ccList;
import static com.example.studygowhere.Control.DataHandler.libList;
import static com.example.studygowhere.Control.DataHandler.schoolList;
import static com.example.studygowhere.Control.DataHandler.studyAreaList;
import static com.example.studygowhere.Control.LibraryDataHandler.addLibObjectFlag;
import static com.example.studygowhere.Control.McDonaldsDataHandler.addMacObjectFlag;
import static com.example.studygowhere.Control.SchoolDataHandler.addSchoolObjectFlag;
import static com.example.studygowhere.Control.StarbucksDataHandler.addSBObjectFlag;


/**
 * <h1>Google Map UI</h1>
 * This is the MainActivity of the application.
 * This is a user interface that is responsible for displaying a google map and markers marking the Study Areas
 *
 * @author ILOVESSADMORE
 * @version 1.0
 */
public class MapsActivity extends FragmentActivity implements OnMapReadyCallback,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        LocationListener,
        NavigationView.OnNavigationItemSelectedListener
{
    /**
     * Static variable mMap.
     */
    private static GoogleMap mMap;

    /**
     * Static variable googleApiClient.
     */
    private static GoogleApiClient googleApiClient;

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
     * Static variable Request_User_Location_Code.
     */
    private static final int Request_User_Location_Code = 99;


    /**
     * Instance variable where Button btnAllLayer in the XML file will be assigned to.
     */
    Button btnAllLayer;

    /**
     * Instance variable where ImageButton btnSchoolLayer in the XML file will be assigned to.
     */
    ImageButton btnSchLayer;

    /**
     * Instance variable where ImageButton btnCCLayer in the XML file will be assigned to.
     */
    ImageButton btnCCLayer;

    /**
     * Instance variable where ImageButton btnLibLayer in the XML file will be assigned to.
     */
    ImageButton btnLibLayer;

    /**
     * Instance variable where ImageButton btnResLayer in the XML file will be assigned to.
     */
    ImageButton btnCafeLayer;

    /**
     * Instance variable where ImageButton btnTaxi in the XML file will be assigned to.
     */
    ImageButton btnTaxi;

    /**
     * Instance variable where ImageButton btnTaxiOff in the XML file will be assigned to.
     */
    ImageButton btnTaxiOff;

    /**
     * Static variable viewOnMapIntent.
     */
    static public Intent viewOnMapIntent;

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
     * Instance variable to display the map fragment.
     */
    View mapView;

    /**
     *
     */

    private TaxiManager taxiManager = new TaxiManager();
    static int NumOfTaxi = 20;
    static MarkerOptions[] markerList = new MarkerOptions[NumOfTaxi];
    static Marker[] markerListRemove = new Marker[NumOfTaxi];


    /**
     * Override method to assign value to instance variables.
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        try {
            // Google Play will install latest OpenSSL
            ProviderInstaller.installIfNeeded(getApplicationContext());
            SSLContext sslContext;
            sslContext = SSLContext.getInstance("TLSv1.2");
            sslContext.init(null, null, null);
            sslContext.createSSLEngine();
        } catch (GooglePlayServicesRepairableException | GooglePlayServicesNotAvailableException
                | NoSuchAlgorithmException | KeyManagementException e) {
            e.printStackTrace();
        }


        setContentView(R.layout.activity_maps);

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
        {
            checkUserLocationPermission();
        }
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapView = mapFragment.getView();
        mapFragment.getMapAsync(this);


        btnCafeLayer = (ImageButton) findViewById(R.id.btnResLayer);
        btnCCLayer = (ImageButton) findViewById(R.id.btnCCLayer);
        btnSchLayer = (ImageButton) findViewById(R.id.btnSchoolLayer);
        btnLibLayer = (ImageButton) findViewById(R.id.btnLibLayer);
        btnAllLayer = (Button) findViewById(R.id.btnAllLayer);
        btnTaxi = (ImageButton) findViewById(R.id.btnTaxi);
        btnTaxiOff = (ImageButton) findViewById(R.id.btnTaxiOff);
        drawer = findViewById(R.id.drawer);
        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("StudyGoWhere");
        toolbar.setTitleTextColor(255-255-255);
        //getSupportActionBar().setTitle("Hello world App");
        navigationView = findViewById(R.id.navigationView);
        navigationView.setNavigationItemSelectedListener(this);
        toggle = new ActionBarDrawerToggle(this,drawer,toolbar,R.string.open,R.string.close);
        drawer.addDrawerListener(toggle);
        toggle.setDrawerIndicatorEnabled(true);
        toggle.syncState();

    }



    public  void onTaxiRun() throws Exception {
        Location currentLocation = LocationServices.FusedLocationApi.getLastLocation(googleApiClient);
        double[][] ans = taxiManager.NearestTaxi(NumOfTaxi,currentLocation.getLongitude(),currentLocation.getLatitude());
        for (int i = 0; i < NumOfTaxi; i++){

            markerList[i] = new MarkerOptions();
            markerList[i].position(new LatLng(ans[i][0], ans[i][1]));
            markerList[i].icon(BitmapDescriptorFactory.fromResource(R.drawable.taxi));
            markerListRemove[i] = mMap.addMarker(markerList[i]);
        }
    }

    /**
     * This method is to remove the taxi markers from the map upon clicking btnTaxiOff.
     * The taxi markers are removed one by one so that removing taxi markers will not affect other markers.
     */
    public void removeTaxiFromMap(){
        if(markerListRemove.length!=0) {
            for (int i = 0; i < NumOfTaxi; i++) {
                markerListRemove[i].remove();
            }
        }
        else {
        }
    }

    /**
     * This method is to assign the instance variable viewOnMapIntent with intent from the previous activity.
     * @param intent intent from the previous activity
     * @throws IOException
     * @throws JSONException
     */
    public static void viewOnMap(Intent intent) throws IOException, JSONException {
        viewOnMapIntent = intent;
    }

    /**
     * This method is to display all Study Areas in the parameter list with a marker on the map and a information window showing the name and distance away.
     * @param list list of Study Area to be displayed with markers
     */

    public void infoWindow(List<StudyArea> list){
        // creating a marker with a infowindow
        for (int i = 0; i < list.size(); i++) {
            StudyArea studyArea = (StudyArea) list.get(i);
            String name = studyArea.getName();
            LatLng latLng = studyArea.getLatLng();
            String distance = studyArea.getDistance();
            mMap.addMarker(new MarkerOptions().position(latLng)
                    .snippet(distance + " km")
                    .title(name));
        }
    }


    /**
     * This method is to display the marker and the information window of a selected location.
     * @param latLng latitude and logitude of the selected location
     * @param name name of the selected location
     * @param distance distance away from user current location
     */
    public void infoWindow(LatLng latLng, String name, double distance){
        double inkm = distance/1000;
        double round = Math.round(inkm * 100.0)/100.0;
        mMap.addMarker(new MarkerOptions().position(latLng)
                    .snippet(round + " km")
                    .title(name));
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */

    /**
     * This method is to check whether location access is enabled and display the map.
     * This method is also responsible for reading the local JSon files and loading the Study Areas created into lists.
     * It also displays different layers of the markers or icons according to user input.
     * @param googleMap
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            buildGoogleApiClient();
            mMap.setMyLocationEnabled(true);
        }
        try{
            // read geojson layers
            final GeoJsonLayer librariesLayer = new GeoJsonLayer(mMap, R.raw.libraries, this);
            final GeoJsonLayer ccLayer = new GeoJsonLayer(mMap, R.raw.communityclubs, this);
            final GeoJsonLayer schoolsLayer = new GeoJsonLayer(mMap, R.raw.schools, this);
            final GeoJsonLayer mcdonaldsLayer = new GeoJsonLayer(mMap, R.raw.mcdonalds, this);
            final GeoJsonLayer starbucksLayer = new GeoJsonLayer(mMap, R.raw.starbucks, this);

            //apply geojson layers on map
            if(!addLibObjectFlag) {
                DataHandler ldh = new LibraryDataHandler();
                ldh.addObject(librariesLayer);
                addLibObjectFlag = true;
            }
            if(!addCCObjectFlag) {
                DataHandler ccdh = new CcDataHandler();
                ccdh.addObject(ccLayer);
                addCCObjectFlag = true;
            }
            if(!addSchoolObjectFlag) {
                DataHandler sdh = new SchoolDataHandler();
                sdh.addObject(schoolsLayer);
                addSchoolObjectFlag = true;
            }
            if(!addMacObjectFlag) {
                DataHandler macdh = new McDonaldsDataHandler();
                macdh.addObject(mcdonaldsLayer);
                addMacObjectFlag = true;
            }
            if(!addSBObjectFlag) {
                DataHandler sbdh = new StarbucksDataHandler();
                sbdh.addObject(starbucksLayer);
                addSBObjectFlag = true;
            }

            //set filter clicker
            btnAllLayer.setOnClickListener(new View.OnClickListener() {
                /**
                 * This method is to display all Study Areas upon clicking btnAllLayer.
                 * @param v
                 */
                @Override
                public void onClick(View v) {
                    mMap.clear();
                    infoWindow(studyAreaList);
                }
            });
           btnLibLayer.setOnClickListener(new View.OnClickListener() {
               /**
                * This method is to display all Study Areas belong to the Library category upon clicking btnLibLayer.
                * @param v
                */
                @Override
                public void onClick(View v) {
                    mMap.clear();
                    infoWindow(libList);
                }
            });

           btnCCLayer.setOnClickListener(new View.OnClickListener() {
               /**
                * This method is to display all Study Areas belong to the Community Center category upon clicking btnCCLayer.
                * @param v
                */
                @Override
                public void onClick(View v) {
                    mMap.clear();
                    infoWindow(ccList);
                }
            });

            btnCafeLayer.setOnClickListener(new View.OnClickListener() {
                /**
                 * This method is to display all Study Areas belong to the Macdonald's and the Starbucks category upon clicking btnCafeLayer.
                 * @param v
                 */
                @Override
                public void onClick(View v) {
                    mMap.clear();
                    infoWindow(cafeList);
                }
            });

            btnSchLayer.setOnClickListener(new View.OnClickListener() {
                /**
                 * This method is to display all Study Areas belong to the School category upon clicking btnSchLayer.
                 * @param v
                 */
                @Override
                public void onClick(View v) {
                    mMap.clear();
                    infoWindow(schoolList);
                }
            });

            btnTaxi.setOnClickListener(new View.OnClickListener() {
                /**
                 * This method is to call onTaxiRun() method upon clicking btnTaxi.
                 * @param v
                 */
                   @Override
                   public void onClick(View v) {
                       try {
                           onTaxiRun();
                       } catch (Exception e) {
                           e.printStackTrace();
                       }
                   }
               });

            btnTaxiOff.setOnClickListener(new View.OnClickListener() {
                /**
                 * This method is to call removeTaxiFromMap() method upon clicking btnTaxi.
                 * @param v
                 */
                    @Override
                    public void onClick(View v) {
                        try {
                            removeTaxiFromMap();
                        } catch(Exception e){
                            e.printStackTrace();
                        }
                    }
                });

            mMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
                /**
                 * This method is to start DetailActivity class upon clicking a information window.
                 * The title of the marker will be put to "Name" and the latitude and longitude of the marker will
                 * be put to "LatLng" under intent and passed to DetailActivity.
                 * The method also check whether the marker clicked is the current location marker.
                 * DetailActivity class will only be started if the marker clicked is not the current location marker.
                 * @param marker the marker that is clicked
                 */
                @Override
                public void onInfoWindowClick(Marker marker) {
                    Intent intent = new Intent(MapsActivity.this , DetailActivity.class);
                    String studyAreaName = marker.getTitle();
                    LatLng studyAreaLatLng = marker.getPosition();
                    intent.putExtra("Name", studyAreaName);
                    intent.putExtra("LatLng",studyAreaLatLng);
                    if(!studyAreaName.contains("user current location")) {
                        startActivity(intent);
                    }
                }
            });

            /**
             * This is to zoom in to the marker that has the same name as the "Name" field in viewOnMapIntent.
             */
            if(viewOnMapIntent!=null){
                String nameClicked = viewOnMapIntent.getStringExtra("Name");
                for (int i = 0; i < DataHandler.studyAreaList.size(); i++) {
                    StudyArea temp = (StudyArea) DataHandler.studyAreaList.get(i);
                    String tempName = temp.getName();
                    if (tempName.equals(nameClicked)) {
                        LatLng clickedLatLng = temp.getLatLng();
                        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(clickedLatLng,14));
                    }
                }
           }
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


        /**
         * This is to shift the current location button from the default location to the right bottom corner of the screen.
         */
        View locationButton = ((View) mapView.findViewById(Integer.parseInt("1")).getParent()).findViewById(Integer.parseInt("2"));
        RelativeLayout.LayoutParams rlp = (RelativeLayout.LayoutParams) locationButton.getLayoutParams();
        // position on right bottom
        rlp.addRule(RelativeLayout.ALIGN_PARENT_TOP, 0);
        rlp.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, RelativeLayout.TRUE);rlp.setMargins(0,0,30,30);
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
        googleApiClient.connect();
    }


    /**
     * This method is to marker the user current location with a marker and title "user current location"
     * This method is also used to calculate the distance of all Study Area away from the user current location.
     * @param location user current location
     */
    @Override
    public void onLocationChanged(Location location) {
        if (currentUserLocationMarker != null) {
            currentUserLocationMarker.remove();
        }
        LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
        MarkerOptions markerOption = new MarkerOptions();
        markerOption.position(latLng);
        markerOption.title("user current location");
        markerOption.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE));
        currentUserLocationMarker = mMap.addMarker(markerOption);
        mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
        mMap.animateCamera(CameraUpdateFactory.zoomBy(14));
        if (googleApiClient != null) {
            LocationServices.FusedLocationApi.removeLocationUpdates(googleApiClient, this);
        }
        if (location != null) {
            for (int i = 0; i < DataHandler.studyAreaList.size(); i++) {
                StudyArea studyArea = (StudyArea) DataHandler.studyAreaList.get(i);
                String name = studyArea.getName();
                LatLng saLatLng = studyArea.getLatLng();
                Location temp = new Location(LocationManager.GPS_PROVIDER);
                temp.setLatitude(saLatLng.latitude);
                temp.setLongitude(saLatLng.longitude);
                float dis = location.distanceTo(temp);
                infoWindow(saLatLng, name, dis);
                studyArea.setDistance(dis);
                studyArea.setDistanceDouble(dis);
            }
        }
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
     * This method is listener method of the navigation drawer.
     * If button StudyGoWhere is clicked, SGWActivity will be started
     * If button account is clicked, the value of Un will be checked.
     * If Un is null, it means that the user has not logged in. LoginActivity will be started.
     * If Um is not null, ProfileActivity will be started.
     * The drawer will be closed after pressing the back button even the new activity is opened from the drawer.
     * @param menuItem
     * @return
     */
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        int id=menuItem.getItemId();
        switch (id){
            case R.id.StudyGoWhere:
                Intent i = new Intent(MapsActivity.this, SGWActivity.class);
                startActivity(i);
                break;
            case R.id.account:
                if(getUn() != null)
                {
                    Intent k = new Intent(MapsActivity.this, ProfileActivity.class);
                    startActivity(k);
                }
                else
                {
                    Intent j = new Intent(MapsActivity.this, LoginActivity.class);
                    startActivity(j);
                }
                break;
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

}