package com.example.studygowhere;

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
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.List;

import javax.net.ssl.SSLContext;

import static com.example.studygowhere.Ccdatahandler.addccObjectFlag;
import static com.example.studygowhere.Datahandler.cafeList;
import static com.example.studygowhere.Datahandler.ccList;
import static com.example.studygowhere.Datahandler.libList;
import static com.example.studygowhere.Datahandler.schoolList;
import static com.example.studygowhere.Datahandler.studyAreaList;
import static com.example.studygowhere.Librarydatahandler.addLibObjectFlag;


import static com.example.studygowhere.LoginActivity.getUn;
import static com.example.studygowhere.Mcdonaldsdatahandler.addmacObjectFlag;
import static com.example.studygowhere.SchoolDatahandler.addschoolObjectFlag;
import static com.example.studygowhere.Starbucksdatahandler.addsbObjectFlag;


public class MapsActivity extends FragmentActivity implements OnMapReadyCallback,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        LocationListener,
        NavigationView.OnNavigationItemSelectedListener
{
    private static GoogleMap mMap;
    private static GoogleApiClient googleApiClient;
    private LocationRequest locationRequest;
    private Location lastLocation;
    private Marker currentUserLocationMarker;
    private static final int Request_User_Location_Code = 99;

    Button btnalllayer;
    ImageButton btnschlayer, btncclayer, btnliblayer, btncafelayer, btnTaxi, btnTaxiOff;
    static public Intent viewOnMapIntent;
    DrawerLayout drawer;
    NavigationView navigationView;
    ActionBarDrawerToggle toggle;
    Toolbar toolbar;
    View mapView;
    


    private TaxiManager taxiManager = new TaxiManager();
    static int NumOfTaxi = 20;
    static MarkerOptions[] markerList = new MarkerOptions[NumOfTaxi];
    static Marker[] markerListRemove = new Marker[NumOfTaxi];

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


        btncafelayer = (ImageButton) findViewById(R.id.btnreslayer);
        btncclayer = (ImageButton) findViewById(R.id.btncclayer);
        btnschlayer = (ImageButton) findViewById(R.id.btnschoollayer);
        btnliblayer = (ImageButton) findViewById(R.id.btnliblayer);
        btnalllayer = (Button) findViewById(R.id.btnalllayer);
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
        JSONArray x = taxiManager.GetTaxiInformation();


        double[][] ans = taxiManager.NearestTaxi(NumOfTaxi,currentLocation.getLongitude(),currentLocation.getLatitude());

        LatLng latlng;
        MarkerOptions markerOptions;

        for (int i = 0; i < NumOfTaxi; i++){

            markerList[i] = new MarkerOptions();
            markerList[i].position(new LatLng(ans[i][0], ans[i][1]));
            markerList[i].icon(BitmapDescriptorFactory.fromResource(R.drawable.taxi));
            markerListRemove[i] = mMap.addMarker(markerList[i]);
        }
    }

    private String readStream(InputStream in) {
        BufferedReader reader = null;
        StringBuffer response = new StringBuffer();
        try {
            reader = new BufferedReader(new InputStreamReader(in));
            String line = "";
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return response.toString();
    }



    public void removeTaxiFromMap(){
        if(markerListRemove.length!=0) {
            for (int i = 0; i < NumOfTaxi; i++) {
                markerListRemove[i].remove();
            }
            Log.i("removeTaxiFromMap", "taxis removed "+ markerListRemove.length);
        }
        else {
            Log.i("removeTaxiFromMap","No Taxi to remove");
        }
    }

    public static void viewOnMap(Intent intent) throws IOException, JSONException {
        viewOnMapIntent = intent;
    }
    public void infoWindow(List<StudyArea> list){
/*        for (GeoJsonFeature feature : layer.getFeatures()) {
            //type casting from GeoJsonGeometry to GeoJsonPoint to getCoordinates of Point
            GeoJsonPoint point = (GeoJsonPoint) feature.getGeometry();
            point.getCoordinates();
            //Log.i("GeoJsonClick", "Feature clicked: " + point.getCoordinates());

            //placing coordinates into a LatLng variable
            LatLng latLng = point.getCoordinates();
            //Log.i("GeoJsonClick", "Feature clicked: " + latLng.latitude());*/

        // creating a marker with a infowindow
        for (int i = 0; i < list.size(); i++) {
            StudyArea studyArea = (StudyArea) list.get(i);
            String name = studyArea.getName();
            LatLng latLng = studyArea.getLatLng();
            String distance = studyArea.getDistance();
            Marker marker = mMap.addMarker(new MarkerOptions().position(latLng)
                    .snippet(distance + " km")
                    .title(name));
            marker.showInfoWindow();
        }
    }
    public void infoWindow(LatLng latLng, String name, double distance){
/*        for (GeoJsonFeature feature : layer.getFeatures()) {
            //type casting from GeoJsonGeometry to GeoJsonPoint to getCoordinates of Point
            GeoJsonPoint point = (GeoJsonPoint) feature.getGeometry();
            point.getCoordinates();
            //Log.i("GeoJsonClick", "Feature clicked: " + point.getCoordinates());

            //placing coordinates into a LatLng variable
            LatLng latLng = point.getCoordinates();
            //Log.i("GeoJsonClick", "Feature clicked: " + latLng.latitude());*/

            // creating a marker with a infowindow
        double inkm = distance/1000;
        double round = Math.round(inkm * 100.0)/100.0;
            Marker marker = mMap.addMarker(new MarkerOptions().position(latLng)
                    .snippet(round + " km")
                    .title(name));
            marker.showInfoWindow();
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            buildGoogleApiClient();
            mMap.setMyLocationEnabled(true);
        }
        try{

            final GeoJsonLayer librariesLayer = new GeoJsonLayer(mMap, R.raw.libraries, this);
            //librariesLayer.addLayerToMap();
            //infoWindow(librariesLayer);

            final GeoJsonLayer ccLayer = new GeoJsonLayer(mMap, R.raw.communityclubs, this);
            //ccLayer.addLayerToMap();
            //infoWindow(ccLayer);

            final GeoJsonLayer schoolsLayer = new GeoJsonLayer(mMap, R.raw.schools, this);
            //schoolsLayer.addLayerToMap();
            //infoWindow(schoolsLayer);

            final GeoJsonLayer mcdonaldsLayer = new GeoJsonLayer(mMap, R.raw.mcdonalds, this);
            //mcdonaldsLayer.addLayerToMap();
            //infoWindow(mcdonaldsLayer);

            final GeoJsonLayer starbucksLayer = new GeoJsonLayer(mMap, R.raw.starbucks, this);
            //starbucksLayer.addLayerToMap();
            //infoWindow(starbucksLayer);


            if(!addLibObjectFlag) {
                Librarydatahandler ldh = new Librarydatahandler();
                ldh.addObject(librariesLayer);
                addLibObjectFlag = true;
            }
            if(!addccObjectFlag) {
                Ccdatahandler ccdh = new Ccdatahandler();
                ccdh.addObject(ccLayer);
                addccObjectFlag = true;
            }
            if(!addschoolObjectFlag) {
                SchoolDatahandler sdh = new SchoolDatahandler();
                sdh.addObject(schoolsLayer);
                addschoolObjectFlag = true;
            }
            if(!addmacObjectFlag) {
                Mcdonaldsdatahandler macdh = new Mcdonaldsdatahandler();
                macdh.addObject(mcdonaldsLayer);
                addmacObjectFlag = true;
            }

            if(!addsbObjectFlag) {
                Starbucksdatahandler sbdh = new Starbucksdatahandler();
                sbdh.addObject(starbucksLayer);
                addsbObjectFlag = true;
            }

            btnalllayer.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mMap.clear();
                    infoWindow(studyAreaList);
                }
            });
           btnliblayer.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mMap.clear();
                    infoWindow(libList);
                }
            });

           btncclayer.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mMap.clear();
                    infoWindow(ccList);
                }
            });

            btncafelayer.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mMap.clear();
                    infoWindow(cafeList);
                }
            });

            btnschlayer.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mMap.clear();
                    infoWindow(schoolList);
                }
            });

            btnTaxi.setOnClickListener(new View.OnClickListener() {
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
                    @Override
                    public void onClick(View v) {
                        try {
                            removeTaxiFromMap();
                        } catch(Exception e){
                            e.printStackTrace();
                        }
                    }
                });


            if(viewOnMapIntent!=null){
                String nameClicked = viewOnMapIntent.getStringExtra("Name");
/*                Log.i("GeoJsonClick", "name clicked: " + nameClicked);
                Log.i("TestClick", "" + Datahandler.studyAreaList.size());*/
                for (int i = 0; i < Datahandler.studyAreaList.size(); i++) {
                    StudyArea temp = (StudyArea) Datahandler.studyAreaList.get(i);
                    String tempName = temp.getName();
                    //Log.i("GeoJsonClick", "Feature clicked: " + "hello");
                    //Log.i("GeoJsonClick", "looping through name: " + tempName);
                    if (tempName.equals(nameClicked)) {
/*                        Log.i("GeoJsonClick", "LatLng"+temp.getLatLng());*/
                        LatLng clickedLatLng = temp.getLatLng();
/*                        Log.i("GeoJsonClick", "clickedLatLng"+clickedLatLng);*/
                        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(clickedLatLng, 14));
                        //mMap.animateCamera(CameraUpdateFactory.zoomBy(14));
/*                        Log.i("GeoJsonClick", "Success!");*/
                    }
                }
           }
            // phone app will start up with a infowindow near africa at the south atlantic ocean

        } catch (JSONException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        View locationButton = ((View) mapView.findViewById(Integer.parseInt("1")).getParent()).findViewById(Integer.parseInt("2"));
        RelativeLayout.LayoutParams rlp = (RelativeLayout.LayoutParams) locationButton.getLayoutParams();
        // position on right bottom
        rlp.addRule(RelativeLayout.ALIGN_PARENT_TOP, 0);
        rlp.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, RelativeLayout.TRUE);rlp.setMargins(0,0,30,30);
    }

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

    protected synchronized void buildGoogleApiClient()
    {
        googleApiClient = new GoogleApiClient.Builder(this).addConnectionCallbacks(this).addOnConnectionFailedListener(this).addApi(LocationServices.API).build();
        googleApiClient.connect();
    }
    @Override
    public void onLocationChanged(Location location) {


        lastLocation = location;
        if (currentUserLocationMarker != null) {
            currentUserLocationMarker.remove();
        }
        LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
        MarkerOptions markerOption = new MarkerOptions();
        markerOption.position(latLng);
        markerOption.title("user current location");
        markerOption.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE));
        currentUserLocationMarker = mMap.addMarker(markerOption);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 10));
        //mMap.animateCamera(CameraUpdateFactory.zoomBy(14));
        if (googleApiClient != null) {
            LocationServices.FusedLocationApi.removeLocationUpdates(googleApiClient, this);
        }

/*        Log.i("GeoJsonClick", "Feature clicked: " + lastLocation.getLatitude());
        Log.i("GeoJsonClick", "location " + location.getLatitude());*/
        if (location != null) {
            for (int i = 0; i < Datahandler.studyAreaList.size(); i++) {
                StudyArea studyArea = (StudyArea) Datahandler.studyAreaList.get(i);
                String name = studyArea.getName();
                LatLng salatLng = studyArea.getLatLng();
                Location temp = new Location(LocationManager.GPS_PROVIDER);
                temp.setLatitude(salatLng.latitude);
                temp.setLongitude(salatLng.longitude);
                float dis = location.distanceTo(temp);
                infoWindow(salatLng, name, dis);
                studyArea.setDistance(dis);
                studyArea.setDistancedouble(dis);

            }


        }
    }


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

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        int id=menuItem.getItemId();
        switch (id){
            case R.id.sgwhr:
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