package com.example.manishchawley.computehelper.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.example.manishchawley.computehelper.util.Constants;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

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

    public void setCommuterWithTrips(ValueEventListener listener) throws Commuter.NullCommuterIdException {
        if(this.getCommuterId()==null) throw new Commuter.NullCommuterIdException();

        DatabaseReference database = FirebaseDatabase.getInstance().getReference(Constants.COMMUTER_WITH_TRIP_DATABASE_KEY);
        database.child(this.getCommuterId()).setValue(this);
        database.child(this.getCommuterId()).addValueEventListener(listener);

    }

    public class NullCommuterIdException extends Throwable {
        @Override
        public String getMessage() {
            return "Commuter ID cannot be Null";
        }
    }

}
