package com.example.manishchawley.computehelper.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by manishchawley on 14/07/17.
 */

public class CommuterWithTrips extends Commuter implements Parcelable {

    ArrayList<Trip> tripList;

    public CommuterWithTrips() {
        super();
    }

    protected CommuterWithTrips(Parcel in) {
        super(in);
        tripList = in.createTypedArrayList(Trip.CREATOR);
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeTypedList(tripList);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<CommuterWithTrips> CREATOR = new Creator<CommuterWithTrips>() {
        @Override
        public CommuterWithTrips createFromParcel(Parcel in) {
            return new CommuterWithTrips(in);
        }

        @Override
        public CommuterWithTrips[] newArray(int size) {
            return new CommuterWithTrips[size];
        }
    };

    public ArrayList<Trip> getTripList() {
        return tripList;
    }

    public void setTripList(ArrayList<Trip> tripList) {
        this.tripList = tripList;
    }

}
