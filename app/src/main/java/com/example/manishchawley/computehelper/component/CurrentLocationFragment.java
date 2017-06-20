package com.example.manishchawley.computehelper.component;

import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;


public class CurrentLocationFragment
        extends Fragment
        implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener{

    GoogleApiClient googleapiclient;
    Location mLastLocation;

    public CurrentLocationFragment() {
        // Required empty public constructor
        Log.i(getClass().getName().toString(), "Constructor");
        createGoogleApiClient();
        //Toast.makeText(getActivity(), getClass().getSimpleName() + ": Constructor", Toast.LENGTH_SHORT).show();
    }

    public static CurrentLocationFragment newInstance() {
        CurrentLocationFragment fragment = new CurrentLocationFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        createGoogleApiClient();
        Log.i(getClass().getName().toString(), "onCreate");
        //Toast.makeText(getActivity(), getClass().getSimpleName() + ": onCreate", Toast.LENGTH_SHORT).show();
    }

    private void createGoogleApiClient(){
        Log.i(getClass().getName().toString(), "createGoogleAPIClient");
        //if(isAdded())
            if(googleapiclient==null)
                try {
                    googleapiclient = new GoogleApiClient.Builder(getContext())
                            .addApi(LocationServices.API)
                            .addConnectionCallbacks(this)
                            .addOnConnectionFailedListener(this)
                            .build();
                }catch (Exception e){
                    Log.e(getClass().getName().toString(), e.getMessage().toString());
                }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        //if(isAdded()) {
            if (googleapiclient == null)
                createGoogleApiClient();

            googleapiclient.connect();
            //Toast.makeText(getActivity(), getClass().getSimpleName() + ": onAttach", Toast.LENGTH_SHORT).show();
        //}else{
        //    Log.e(getClass().getName().toString(), "Fragment not added");
        //}
    }

    @Override
    public void onDetach() {
        super.onDetach();
        googleapiclient.disconnect();
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        //Display connection status
        //Toast.makeText(this, "Connected", Toast.LENGTH_SHORT).show();
        Log.i(getClass().getName().toString(), "googleAPIClient Connected");
        if(googleapiclient==null)
            createGoogleApiClient();

        if (ActivityCompat.checkSelfPermission(getActivity(), android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(getActivity(), android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            Toast.makeText(getActivity(), "Error: Location permission not granted", Toast.LENGTH_SHORT).show();
            return;
        }

         setCurrentLocation(LocationServices.FusedLocationApi.getLastLocation(googleapiclient));
        String locationtext;
        if (mLastLocation != null) {
            locationtext = "Lat: " + String.valueOf(mLastLocation.getLatitude()) + " Long: " + String.valueOf(mLastLocation.getLongitude());
        }else{
            locationtext = "Location not found";
        }
        //Toast.makeText(getActivity(), "Location: " + locationtext, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        //Display failed connection reason
        Toast.makeText(getActivity(), "Connection Failure: " + connectionResult.getErrorMessage(), Toast.LENGTH_SHORT).show();
    }

    public Location getCurrentLocation(){
        return mLastLocation;
    }

    private void setCurrentLocation(Location location){
        mLastLocation = location;
    }
}
