package com.example.manishchawley.commutehelper.provider;

import android.Manifest;
import android.app.IntentService;
import android.app.Service;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.os.ResultReceiver;
import android.util.Log;
import android.widget.Toast;

import com.example.manishchawley.commutehelper.util.Constants;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;

public class CurrentLocationService extends IntentService
        implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, LocationListener {

    private final int LOCATION_UPDATE_INTERVAL = Constants.LOCATION_UPDATE_INTERVAL_SLOW;

    private GoogleApiClient googleApiClient;
    private Location mCurrentLocation;
    private LocationRequest locationRequest;

    private final String TAG = getClass().getName().toString();

    protected ResultReceiver mResultReceiver;

    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     *
     *
     */
    public CurrentLocationService() {
        super(Constants.PACKAGE_NAME);
        Log.e(TAG, "Service Started");
        //super(name);
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        Log.e(TAG, "Handle Intent");
        createGoogleApiClient();
        locationRequest = new LocationRequest();
        locationRequest.setInterval(LOCATION_UPDATE_INTERVAL);
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        locationRequest.setFastestInterval(LOCATION_UPDATE_INTERVAL);

        mResultReceiver = intent.getParcelableExtra(Constants.LOCATION_DATA_EXTRA);

        deliverResultToReceiver();
    }

    private void deliverResultToReceiver() {
        Log.e(TAG, "Trying to deliver result to receiver");
        Bundle bundle = new Bundle();
        bundle.putParcelable(Constants.LOCATION_RESULT_DATA_KEY, mCurrentLocation);
        //if(mResultReceiver==null)
        //    return;
        if(mCurrentLocation==null)
            mResultReceiver.send(Constants.FAILURE_RESULT, bundle);
        else
            mResultReceiver.send(Constants.SUCCESS_RESULT, bundle);
        Log.e(TAG, "Delivered result to receiver");
    }


    public void onCreate() {
        Log.e(TAG, "Created");
        createGoogleApiClient();
        locationRequest = new LocationRequest();
        locationRequest.setInterval(LOCATION_UPDATE_INTERVAL);
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        locationRequest.setFastestInterval(LOCATION_UPDATE_INTERVAL);

    }

    public int onStartCommand(Intent intent, int flags, int startID) {
        Log.e(TAG, "Start Command");

        if (!googleApiClient.isConnected())
            googleApiClient.connect();

        mResultReceiver = intent.getParcelableExtra(Constants.LOCATION_DATA_EXTRA);

        return Service.START_NOT_STICKY;
    }

    public void onDestroy(){
        if(googleApiClient.isConnected()) {
            googleApiClient.disconnect();
            LocationServices.FusedLocationApi.removeLocationUpdates(googleApiClient, this);
        }
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        if (googleApiClient == null)
            createGoogleApiClient();
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        LocationServices.FusedLocationApi.requestLocationUpdates(googleApiClient, locationRequest, this);

    }

    private void createGoogleApiClient() {

        if (ActivityCompat.checkSelfPermission(CurrentLocationService.this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(CurrentLocationService.this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            Toast.makeText(CurrentLocationService.this, "Error: Location permission not granted", Toast.LENGTH_SHORT).show();
            return;
        }

        if(googleApiClient==null)
            try {
                googleApiClient = new GoogleApiClient.Builder(CurrentLocationService.this)
                        .addApi(LocationServices.API)
                        .addConnectionCallbacks(this)
                        .addOnConnectionFailedListener(this)
                        .build();
            }catch (Exception e){
                Log.e(this.getClass().getSimpleName(), e.getMessage().toString());
            }
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Toast.makeText(CurrentLocationService.this, "Connection Failure: " + connectionResult.getErrorMessage().toString(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onLocationChanged(Location location) {
        mCurrentLocation = location;
        deliverResultToReceiver();
    }
}
