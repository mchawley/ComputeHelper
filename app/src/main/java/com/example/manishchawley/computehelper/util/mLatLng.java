package com.example.manishchawley.computehelper.util;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.android.gms.maps.model.LatLng;

/**
 * Created by manishchawley on 04/08/17.
 */

public class mLatLng implements Parcelable {
    private double latitude, longitude;

    public mLatLng(double lat, double lng) {
        this.latitude = lat;
        this.longitude = lng;
    }

    public mLatLng() {
    }

    protected mLatLng(Parcel in) {
        latitude = in.readDouble();
        longitude = in.readDouble();
    }

    public static final Creator<mLatLng> CREATOR = new Creator<mLatLng>() {
        @Override
        public mLatLng createFromParcel(Parcel in) {
            return new mLatLng(in);
        }

        @Override
        public mLatLng[] newArray(int size) {
            return new mLatLng[size];
        }
    };

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeDouble(latitude);
        dest.writeDouble(longitude);
    }

    public static mLatLng convert(LatLng latLng) {
        return new mLatLng(latLng.latitude, latLng.longitude);
    }
}
