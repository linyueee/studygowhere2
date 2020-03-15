package com.example.studygowhere;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.app.ProgressDialog;
import android.app.assist.AssistStructure;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.maps.android.geojson.GeoJsonFeature;
import com.google.maps.android.geojson.GeoJsonLayer;
import com.google.maps.android.geojson.GeoJsonPoint;

import org.json.JSONException;

import java.io.IOException;

import java.util.ArrayList;
import java.util.List;
import static com.example.studygowhere.Ccdatahandler.addccObjectFlag;
import static com.example.studygowhere.Librarydatahandler.addLibObjectFlag;


import static com.example.studygowhere.LoginActivity.getUn;
import static com.example.studygowhere.Mcdonaldsdatahandler.addmacObjectFlag;
import static com.example.studygowhere.SchoolDatahandler.addschoolObjectFlag;
import static com.example.studygowhere.Starbucksdatahandler.addsbObjectFlag;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        LocationListener
{
    private GoogleMap mMap;
    private GoogleApiClient googleApiClient;
    private LocationRequest locationRequest;
    private Location lastLocation;
    private Marker currentUserLocationMarker;
    private static final int Request_User_Location_Code = 99;
    Button btnAcc, btnsgw;

    static public Intent viewOnMapIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
        {
            checkUserLocationPermission();
        }
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        btnAcc = (Button) findViewById(R.id.user_icon);
        btnsgw = (Button) findViewById(R.id.sgw);
        if(getUn() != null) {
            btnAcc.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(MapsActivity.this, ProfileActivity.class);
                    startActivity(i);
                }
            });
        }
        else{
            btnAcc.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(MapsActivity.this, LoginActivity.class);
                    startActivity(i);
                }
            });
        }
        btnsgw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MapsActivity.this, SGWActivity.class);
                startActivity(i);
            }
        });
    }

    private double distance(double lat1, double lon1, double lat2, double lon2) {
        double theta = lon1 - lon2;
        double dist = Math.sin(deg2rad(lat1))
                * Math.sin(deg2rad(lat2))
                + Math.cos(deg2rad(lat1))
                * Math.cos(deg2rad(lat2))
                * Math.cos(deg2rad(theta));
        dist = Math.acos(dist);
        dist = rad2deg(dist);
        dist = dist * 60 * 1.1515;
        return (dist);
    }

    private double deg2rad(double deg) {
        return (deg * Math.PI / 180.0);
    }

    private double rad2deg(double rad) {
        return (rad * 180.0 / Math.PI);
    }

/*    public float[] distanceAway(LatLng position1, LatLng position2){
        //calculates distance away from user location
        // The computed distance is stored in results[0].
        //If results has length 2 or greater, the initial bearing is stored in results[1].
        //If results has length 3 or greater, the final bearing is stored in results[2].
        float[] results = new float[1];
        Location.distanceBetween(position1.latitude, position1.longitude,
                position2.latitude, position2.longitude, results);
        return results;
    }*/

    public static void viewOnMap(Intent intent) throws IOException, JSONException {
        viewOnMapIntent = intent;
/*
        Marker clickedMarker;
        String nameClicked = intent.getStringExtra("Name");
        Log.i("GeoJsonClick", "name clicked: " + nameClicked);
        Log.i("TestClick", "" + Datahandler.studyAreaList.size());
        //Toast toast = Toast.makeText(context, "clicked", Toast.LENGTH_LONG);
        //toast.show();
        for (int i = 0; i < Datahandler.studyAreaList.size(); i++) {
            StudyArea temp = (StudyArea) Datahandler.studyAreaList.get(i);
            String tempName = temp.getName();
            //Log.i("GeoJsonClick", "Feature clicked: " + "hello");
            //Log.i("GeoJsonClick", "looping through name: " + tempName);
            if (tempName.equals(nameClicked)) {
                Log.i("GeoJsonClick", "LatLng"+temp.getLatLng());
                LatLng clickedLatLng = temp.getLatLng();
                Log.i("GeoJsonClick", "clickedLatLng"+clickedLatLng);

                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(clickedLatLng, 14));
                // creating a marker with a infowindow
*/
/*                clickedMarker = mMap.addMarker(new MarkerOptions().position(clickedLatLng)
                        .snippet("distance2")
                        .title(nameClicked));
                clickedMarker.showInfoWindow();*//*

                Log.i("GeoJsonClick", "Success!");
                */
/*Toast toast = Toast.makeText(context, tempName, Toast.LENGTH_LONG);
                toast.show();*//*

            }
        }
*/
    }

    public void infoWindow(LatLng latLng, String name, String distance){
/*        for (GeoJsonFeature feature : layer.getFeatures()) {
            //type casting from GeoJsonGeometry to GeoJsonPoint to getCoordinates of Point
            GeoJsonPoint point = (GeoJsonPoint) feature.getGeometry();
            point.getCoordinates();
            //Log.i("GeoJsonClick", "Feature clicked: " + point.getCoordinates());

            //placing coordinates into a LatLng variable
            LatLng latLng = point.getCoordinates();
            //Log.i("GeoJsonClick", "Feature clicked: " + latLng.latitude());*/

            // creating a marker with a infowindow
            Marker marker = mMap.addMarker(new MarkerOptions().position(latLng)
                    .snippet(distance + " km")
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

            GeoJsonLayer librariesLayer = new GeoJsonLayer(mMap, R.raw.libraries, this);
            //librariesLayer.addLayerToMap();
            //infoWindow(librariesLayer);

            GeoJsonLayer ccLayer = new GeoJsonLayer(mMap, R.raw.communityclubs, this);
            //ccLayer.addLayerToMap();
            //infoWindow(ccLayer);

            GeoJsonLayer schoolsLayer = new GeoJsonLayer(mMap, R.raw.schools, this);
            //schoolsLayer.addLayerToMap();
            //infoWindow(schoolsLayer);

            GeoJsonLayer mcdonaldsLayer = new GeoJsonLayer(mMap, R.raw.mcdonalds, this);
            //mcdonaldsLayer.addLayerToMap();
            //infoWindow(mcdonaldsLayer);

            GeoJsonLayer starbucksLayer = new GeoJsonLayer(mMap, R.raw.starbucks, this);
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


            for(int i = 0; i < Datahandler.studyAreaList.size(); i++){
                StudyArea studyArea = (StudyArea) Datahandler.studyAreaList.get(i);
                String name = studyArea.getName();
                LatLng latLng = studyArea.getLatLng();

/*                double distance;
                distance = distance(latLng.latitude,latLng.longitude,userCurrentLatLng.latitude,userCurrentLatLng.longitude);
                Log.i("distance conversion", "distance: " + distance);

                String distanceStr = String.valueOf(distance);*/
                infoWindow(latLng, name, "distance");
            }

            if(viewOnMapIntent!=null){
                String nameClicked = viewOnMapIntent.getStringExtra("Name");
                Log.i("GeoJsonClick", "name clicked: " + nameClicked);
                Log.i("TestClick", "" + Datahandler.studyAreaList.size());
                for (int i = 0; i < Datahandler.studyAreaList.size(); i++) {
                    StudyArea temp = (StudyArea) Datahandler.studyAreaList.get(i);
                    String tempName = temp.getName();
                    //Log.i("GeoJsonClick", "Feature clicked: " + "hello");
                    //Log.i("GeoJsonClick", "looping through name: " + tempName);
                    if (tempName.equals(nameClicked)) {
                        Log.i("GeoJsonClick", "LatLng"+temp.getLatLng());
                        LatLng clickedLatLng = temp.getLatLng();
                        Log.i("GeoJsonClick", "clickedLatLng"+clickedLatLng);
                        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(clickedLatLng, 14));
                        //mMap.animateCamera(CameraUpdateFactory.zoomBy(14));
                        Log.i("GeoJsonClick", "Success!");
                    }
                }

            }


            // phone app will start up with a infowindow near africa at the south atlantic ocean

        } catch (JSONException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
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
        if(currentUserLocationMarker != null)
        {
            currentUserLocationMarker.remove();
        }
        LatLng latLng = new LatLng(location.getLatitude(),location.getLongitude());
        MarkerOptions markerOption = new MarkerOptions();
        markerOption.position(latLng);
        markerOption.title("user current location");
        markerOption.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE));
        currentUserLocationMarker = mMap.addMarker(markerOption);
        mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
        mMap.animateCamera(CameraUpdateFactory.zoomBy(14));
        if(googleApiClient != null)
        {
            LocationServices.FusedLocationApi.removeLocationUpdates(googleApiClient, this);
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

}
