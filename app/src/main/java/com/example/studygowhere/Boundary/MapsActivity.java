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

    Button btnAllLayer;
    ImageButton btnSchLayer, btnCCLayer, btnLibLayer, btnCafeLayer, btnTaxi, btnTaxiOff;
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
        Log.i("viewOnMapIntent",""+intent);
    }

    public void infoWindow(List<StudyArea> list){
        // creating a marker with a infowindow
        for (int i = 0; i < list.size(); i++) {
            StudyArea studyArea = (StudyArea) list.get(i);
            String name = studyArea.getName();
            LatLng latLng = studyArea.getLatLng();
            String distance = studyArea.getDistance();
            Marker marker = mMap.addMarker(new MarkerOptions().position(latLng)
                    .snippet(distance + " km")
                    .title(name));
            //marker.showInfoWindow();
        }
    }

    public void infoWindow(LatLng latLng, String name, double distance){
        // creating a marker with a infowindow
        double inkm = distance/1000;
        double round = Math.round(inkm * 100.0)/100.0;
            Marker marker = mMap.addMarker(new MarkerOptions().position(latLng)
                    .snippet(round + " km")
                    .title(name));
            //marker.showInfoWindow();
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
//        LatLng SINGAPORE = new LatLng(1.354822, 103.867685);
//        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(SINGAPORE,10));
//        //mMap.animateCamera(CameraUpdateFactory.zoomBy(5));
        try{
            // read geojson layers
            final GeoJsonLayer librariesLayer = new GeoJsonLayer(mMap, R.raw.libraries, this);
            final GeoJsonLayer ccLayer = new GeoJsonLayer(mMap, R.raw.communityclubs, this);
            final GeoJsonLayer schoolsLayer = new GeoJsonLayer(mMap, R.raw.schools, this);
            final GeoJsonLayer mcdonaldsLayer = new GeoJsonLayer(mMap, R.raw.mcdonalds, this);
            final GeoJsonLayer starbucksLayer = new GeoJsonLayer(mMap, R.raw.starbucks, this);

            //apply geojson layers on map
            if(!addLibObjectFlag) {
                LibraryDataHandler ldh = new LibraryDataHandler();
                ldh.addObject(librariesLayer);
                addLibObjectFlag = true;
            }
            if(!addCCObjectFlag) {
                CcDataHandler ccdh = new CcDataHandler();
                ccdh.addObject(ccLayer);
                addCCObjectFlag = true;
            }
            if(!addSchoolObjectFlag) {
                SchoolDataHandler sdh = new SchoolDataHandler();
                sdh.addObject(schoolsLayer);
                addSchoolObjectFlag = true;
            }
            if(!addMacObjectFlag) {
                McDonaldsDataHandler macdh = new McDonaldsDataHandler();
                macdh.addObject(mcdonaldsLayer);
                addMacObjectFlag = true;
            }
            if(!addSBObjectFlag) {
                StarbucksDataHandler sbdh = new StarbucksDataHandler();
                sbdh.addObject(starbucksLayer);
                addSBObjectFlag = true;
            }

            //set filter clicker
            btnAllLayer.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mMap.clear();
                    infoWindow(studyAreaList);
                }
            });
           btnLibLayer.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mMap.clear();
                    infoWindow(libList);
                }
            });

           btnCCLayer.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mMap.clear();
                    infoWindow(ccList);
                }
            });

            btnCafeLayer.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mMap.clear();
                    infoWindow(cafeList);
                }
            });

            btnSchLayer.setOnClickListener(new View.OnClickListener() {
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