package com.example.manishchawley.commutehelper.util;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import java.io.IOException;
import java.util.Locale;

/**
 * Created by manishchawley on 14/07/17.
 */

public class PlaceUtils {

    public static void getCurrentLocation(Activity activity, final LocationListener locationListener) {

        FusedLocationProviderClient fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(activity);

        if (ActivityCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.

        }
        fusedLocationProviderClient.getLastLocation().addOnCompleteListener(activity, new OnCompleteListener<Location>() {
            @Override
            public void onComplete(@NonNull Task<Location> task) {
                if(task.isSuccessful() && task.getResult()!=null){
                    locationListener.onSuccess(task.getResult());
                }else {
                    locationListener.onError(task.getException());
                }
            }
        });
    }

    public static Address getAddressFromLocation(Context context, Location location){
        return getAddressFromLocation(context, location.getLatitude(), location.getLongitude());
    }

    private static Address getAddressFromLocation(Context context, double latitude, double longitude) {
        Geocoder geocoder = new Geocoder(context, Locale.getDefault());
        try {
            return geocoder.getFromLocation(latitude, longitude, 1).get(0);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }


    public interface LocationListener{
        public void onSuccess(Location location);
        public void onError(Exception e);
    }

}
