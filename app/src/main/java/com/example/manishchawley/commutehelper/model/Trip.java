package com.example.manishchawley.commutehelper.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import com.example.manishchawley.commutehelper.util.Constants;
import com.example.manishchawley.commutehelper.util.mLatLng;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

/**
 * Created by manishchawley on 22/10/16.
 */
public class Trip implements Parcelable{

    private static final String TAG = Trip.class.getName();

    public static final String TRIP_ID_KEY                  = "TRIP_ID";
    public static final String START_LOCATION_KEY           = "START_LOCATION";
    public static final String DESTINATION_LOCATION_KEY     = "DESTINATION_LOCATION";
    public static final String DESTINATION_PLACE_KEY        = "DESTINATION_PLACE";
    public static final String START_PLACE_KEY              = "START_PLACE";
    public static final String MODE_OF_TRAVEL_KEY           = "MODE_OF_TRAVEL";
    public static final String TRIP_START_KEY               = "TRIP_START";
    public static final String TRIP_END_KEY                 = "TRIP_END";
    public static final String TRIP_COMMUTERS_KEY           = "TRIP_COMMUTERS";
    public static final String MAX_COMMUTER_ALLOWED_KEY     = "MAX_COMMUTER_ALLOWED";
    public static final String TRIP_STATUS_KEY              = "TRIP_STATUS";
    public static final String TRIP_TYPE_KEY                = "TRIP_TYPE";

    private mLatLng originLocation;             //starting location of the trip
    private mLatLng destinationLocation;       //destination location of the trip
    private String destinationPlace;             //destination place
    private String originPlace;                   //starting place
    private String destinationPlaceID;
    private String originPlaceID;
    private int modeOfTravel;                   //mode of travel
    private String tripId;                         //unique trip id
    private long tripStart;                     //trip origin date
    private long tripEnd;                       //trip end date
    private Commuter tripOwner;
    private ArrayList<Commuter> tripCommuters;            //list of commuters
    private int maxCommuterAllowed;              //max number of commuter allowed
    private int tripStatus;                     //numerical status of the trip
    private int typeOfTrip;                      //numerical typeoftrip
    private String tripDescription;
    private String imageURL;

    public Trip() {
    }

    public Trip(mLatLng originLocation, mLatLng destinationLocation, String destinationPlace, String originPlace, String destinationPlaceID, String originPlaceID, int modeOfTravel, String tripId, long tripStart, long tripEnd, Commuter tripOwner, ArrayList<Commuter> tripCommutersId, int maxCommuterAllowed, int tripStatus, int typeOfTrip, String tripDescription, String imageURL) {
        this.originLocation = originLocation;
        this.destinationLocation = destinationLocation;
        this.destinationPlace = destinationPlace;
        this.originPlace = originPlace;
        this.destinationPlaceID = destinationPlaceID;
        this.originPlaceID = originPlaceID;
        this.modeOfTravel = modeOfTravel;
        this.tripId = tripId;
        this.tripStart = tripStart;
        this.tripEnd = tripEnd;
        this.tripOwner = tripOwner;
        this.tripCommuters = tripCommutersId;
        this.maxCommuterAllowed = maxCommuterAllowed;
        this.tripStatus = tripStatus;
        this.typeOfTrip = typeOfTrip;
        this.tripDescription = tripDescription;
        this.imageURL = imageURL;
    }

    protected Trip(Parcel in) {
        originLocation = in.readParcelable(mLatLng.class.getClassLoader());
        destinationLocation = in.readParcelable(mLatLng.class.getClassLoader());
        destinationPlace = in.readString();
        originPlace = in.readString();
        destinationPlaceID = in.readString();
        originPlaceID = in.readString();
        modeOfTravel = in.readInt();
        tripId = in.readString();
        tripStart = in.readLong();
        tripEnd = in.readLong();
        tripOwner = in.readParcelable(Commuter.class.getClassLoader());
        tripCommuters = in.createTypedArrayList(Commuter.CREATOR);
        maxCommuterAllowed = in.readInt();
        tripStatus = in.readInt();
        typeOfTrip = in.readInt();
        tripDescription = in.readString();
        imageURL = in.readString();
    }

    public static final Creator<Trip> CREATOR = new Creator<Trip>() {
        @Override
        public Trip createFromParcel(Parcel in) {
            return new Trip(in);
        }

        @Override
        public Trip[] newArray(int size) {
            return new Trip[size];
        }
    };

    public mLatLng getOriginLocation() {
        return originLocation;
    }

    public void setOriginLocation(mLatLng originLocation) {
        this.originLocation = originLocation;
    }

    public mLatLng getDestinationLocation() {
        return destinationLocation;
    }

    public void setDestinationLocation(mLatLng destinationLocation) {
        this.destinationLocation = destinationLocation;
    }

    public String getDestinationPlace() {
        return destinationPlace;
    }

    public void setDestinationPlace(String destinationPlace) {
        this.destinationPlace = destinationPlace;
    }

    public String getOriginPlace() {
        return originPlace;
    }

    public void setOriginPlace(String originPlace) {
        this.originPlace = originPlace;
    }

    public String getDestinationPlaceID() {
        return destinationPlaceID;
    }

    public void setDestinationPlaceID(String destinationPlaceID) {
        this.destinationPlaceID = destinationPlaceID;
    }

    public String getOriginPlaceID() {
        return originPlaceID;
    }

    public void setOriginPlaceID(String originPlaceID) {
        this.originPlaceID = originPlaceID;
    }

    public int getModeOfTravel() {
        return modeOfTravel;
    }

    public void setModeOfTravel(int modeOfTravel) {
        this.modeOfTravel = modeOfTravel;
    }

    public String getTripId() {
        return tripId;
    }

    public void setTripId(String tripId) {
        this.tripId = tripId;
    }

    public long getTripStart() {
        return tripStart;
    }

    public void setTripStart(long tripStart) {
        this.tripStart = tripStart;
    }

    public long getTripEnd() {
        return tripEnd;
    }

    public void setTripEnd(long tripEnd) {
        this.tripEnd = tripEnd;
    }

    public Commuter getTripOwner() {
        return tripOwner;
    }

    public void setTripOwner(Commuter tripOwner) {
        this.tripOwner = tripOwner;
    }

    public ArrayList<Commuter> getTripCommuters() {
        return tripCommuters;
    }

    public void setTripCommuters(ArrayList<Commuter> tripCommuters) {
        this.tripCommuters = tripCommuters;
    }

    public int getMaxCommuterAllowed() {
        return maxCommuterAllowed;
    }

    public void setMaxCommuterAllowed(int maxCommuterAllowed) {
        this.maxCommuterAllowed = maxCommuterAllowed;
    }

    public int getTripStatus() {
        return tripStatus;
    }

    public void setTripStatus(int tripStatus) {
        this.tripStatus = tripStatus;
    }

    public int getTypeOfTrip() {
        return typeOfTrip;
    }

    public void setTypeOfTrip(int typeOfTrip) {
        this.typeOfTrip = typeOfTrip;
    }

    public String getTripDescription() {
        return tripDescription;
    }

    public void setTripDescription(String tripDescription) {
        this.tripDescription = tripDescription;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageUrl) {
        this.imageURL = imageUrl;
    }

    public static void getTrip(String tripId, ValueEventListener listener){

        DatabaseReference database = FirebaseDatabase.getInstance().getReference(Constants.TRIP_DATABASE_KEY);

        database.child(tripId).addValueEventListener(listener);

    }

    public void setTrip(ValueEventListener listener){
        DatabaseReference database = FirebaseDatabase.getInstance().getReference(Constants.TRIP_DATABASE_KEY);

        if(getTripId()==null)
            setTripId(database.push().getKey());

        Log.i(TAG, "Uploading Trip id: " + getTripId());

        database.child(getTripId()).setValue(this);

        database.child(getTripId()).addValueEventListener(listener);

    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(originLocation, flags);
        dest.writeParcelable(destinationLocation, flags);
        dest.writeString(destinationPlace);
        dest.writeString(originPlace);
        dest.writeString(destinationPlaceID);
        dest.writeString(originPlaceID);
        dest.writeInt(modeOfTravel);
        dest.writeString(tripId);
        dest.writeLong(tripStart);
        dest.writeLong(tripEnd);
        dest.writeParcelable(tripOwner, flags);
        dest.writeTypedList(tripCommuters);
        dest.writeInt(maxCommuterAllowed);
        dest.writeInt(tripStatus);
        dest.writeInt(typeOfTrip);
        dest.writeString(tripDescription);
        dest.writeString(imageURL);
    }

    public int getNoOfCommuters() {
        if(tripCommuters==null)
            return 0;
        return tripCommuters.size();
    }
}
