package com.example.manishchawley.commutehelper.util;

import android.location.Location;
import android.support.annotation.Nullable;

import com.example.manishchawley.commutehelper.model.Commuter;
import com.example.manishchawley.commutehelper.model.CommuterWithTrips;
import com.example.manishchawley.commutehelper.model.Trip;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by manishchawley on 14/07/17.
 */

public class JSONUtils {

    private static final String TAG = JSONUtils.class.getName();

    private static final String LAT_KEY = "LAT";
    private static final String LONG_KEY = "LONG";

    public static final int USE_DEFAULT = 1;
    public static final int KEEP_NULL = 0;


    //Trip to JSON conversions

    public static Trip getTripFromJson(JSONObject jsonObject, @Nullable int conversionType) throws JSONException {

        //Log.e(TAG, jsonObject.toString());

        Trip trip;

        switch (conversionType){
            case USE_DEFAULT:
                trip = Defaults.getDefaultTrip();
                break;
            case KEEP_NULL:
                trip = new Trip();
                break;
            default:
                trip = new Trip();
                break;
        }

        if(jsonObject.has(Trip.TRIP_ID_KEY)) trip.setTripId(jsonObject.getString(Trip.TRIP_ID_KEY));
//        if(jsonObject.has(Trip.DESTINATION_LOCATION_KEY)) trip.setDestinationLocation(getLocationFromJson(jsonObject.getJSONObject(Trip.DESTINATION_LOCATION_KEY)));
        if(jsonObject.has(Trip.DESTINATION_PLACE_KEY)) trip.setDestinationPlace(jsonObject.getString(Trip.DESTINATION_PLACE_KEY));
//        if(jsonObject.has(Trip.START_LOCATION_KEY)) trip.setStartLocation(getLocationFromJson(jsonObject.getJSONObject(Trip.START_LOCATION_KEY)));
//        if(jsonObject.has(Trip.START_PLACE_KEY)) trip.setStartPlace(jsonObject.getString(Trip.START_PLACE_KEY));
        if(jsonObject.has(Trip.MODE_OF_TRAVEL_KEY)) trip.setModeOfTravel(jsonObject.getInt(Trip.MODE_OF_TRAVEL_KEY));
//        if(jsonObject.has(Trip.TRIP_START_KEY)) trip.setTripStart(new Date(jsonObject.getString(Trip.TRIP_START_KEY)));
//        if(jsonObject.has(Trip.TRIP_END_KEY)) trip.setTripEnd(new Date(jsonObject.getString(Trip.TRIP_END_KEY)));
        if(jsonObject.has(Trip.MAX_COMMUTER_ALLOWED_KEY)) trip.setMaxCommuterAllowed(jsonObject.getInt(Trip.MAX_COMMUTER_ALLOWED_KEY));
        if(jsonObject.has(Trip.TRIP_STATUS_KEY)) trip.setTripStatus(jsonObject.getInt(Trip.TRIP_STATUS_KEY));
        if(jsonObject.has(Trip.TRIP_TYPE_KEY)) trip.setTypeOfTrip(jsonObject.getInt(Trip.TRIP_TYPE_KEY));
        //if(jsonObject.has(Trip.TRIP_COMMUTERS_KEY)) trip.setTripCommuters(getCommuterFromJson(jsonObject.getJSONArray(Trip.TRIP_COMMUTERS_KEY)));

        // TODO: 16/07/17 remove once server connection is done
        trip.setDestinationPlace(jsonObject.getString("title"));
        trip.setTripStart(Calendar.getInstance().getTimeInMillis());
        String url = jsonObject.getString(Constants.RESULT_COMMUTER_IMAGE_URL_KEY);
        trip.setImageURL(url.replace(":", "s:"));

        return trip;
    }

    public static Trip getTripFromJson(JSONObject jsonObject) throws JSONException {
        return getTripFromJson(jsonObject, USE_DEFAULT);
    }

    public static List<Trip> getTripsFromJson(JSONArray jsonArray, int conversionType) throws JSONException {
        List<Trip> trips = new ArrayList<Trip>();

        for(int i=0; i<jsonArray.length(); i++)
            trips.add(getTripFromJson(jsonArray.getJSONObject(i), conversionType));

        return trips;
    }

    public static List<Trip> getTripsFromJson(JSONArray jsonArray) throws JSONException{
        return getTripsFromJson(jsonArray, KEEP_NULL);
    }

    public static JSONObject getJsonFromTrip(Trip trip) throws JSONException {
        //null check
        if(trip==null)
            return null;

        JSONObject jsonObject = new JSONObject();

        // TODO: 14/07/17 Make JSON parsing
        jsonObject.put(Trip.TRIP_ID_KEY, trip.getTripId());
//        jsonObject.put(Trip.DESTINATION_LOCATION_KEY, getJsonFromLocation(trip.getDestinationLocation()));
        jsonObject.put(Trip.DESTINATION_PLACE_KEY, trip.getDestinationPlace()!=null?trip.getDestinationPlace():"");
//        jsonObject.put(Trip.START_LOCATION_KEY, getJsonFromLocation(trip.getOriginLocation()));
        jsonObject.put(Trip.START_PLACE_KEY, trip.getOriginPlace()!=null?trip.getOriginPlace():"");
        jsonObject.put(Trip.MODE_OF_TRAVEL_KEY, trip.getModeOfTravel());
        jsonObject.put(Trip.TRIP_START_KEY, trip.getTripStart()!=0?trip.getTripStart():"");
        jsonObject.put(Trip.TRIP_END_KEY, trip.getTripEnd()!=0?trip.getTripEnd():"");
        jsonObject.put(Trip.MAX_COMMUTER_ALLOWED_KEY, trip.getMaxCommuterAllowed());
        jsonObject.put(Trip.TRIP_STATUS_KEY, trip.getTripStatus());
        jsonObject.put(Trip.TRIP_TYPE_KEY, trip.getTypeOfTrip());
        //jsonObject.put(Trip.TRIP_COMMUTERS_KEY, trip.getTripCommuters()!=null?getJsonFromCommuter((CommuterWithTrips[]) trip.getTripCommuters()):"");

        return jsonObject;
    }

    public static JSONArray getJsonFromTrip(List<Trip> trips) throws JSONException {
        JSONArray jsonArray = new JSONArray();

        for(int i=0; i<trips.size(); i++)
            jsonArray.put(getJsonFromTrip(trips.get(i)));

        return jsonArray;
    }


    //Commuter to JSON conversions

    private static Commuter[] getCommuterFromJson(JSONArray jsonArray) throws JSONException {
        Commuter[] commuters = new Commuter[jsonArray.length()];

        for(int i=0; i<jsonArray.length(); i++)
            commuters[i] = getCommuterFromJson(jsonArray.getJSONObject(i));

        return commuters;
    }

    private static JSONArray getJsonFromCommuter(CommuterWithTrips[] commuters) throws JSONException {
        JSONArray jsonArray = new JSONArray();
        
        for(int i=0; i<commuters.length; i++)
            jsonArray.put(getJsonFromCommuter(commuters[i]));

        return jsonArray;
    }

    public static CommuterWithTrips getCommuterFromJson(JSONObject jsonObject, int conversionType) throws JSONException {

        // TODO: 11/07/17 parsing jason
        CommuterWithTrips commuter;

        switch (conversionType){
            case USE_DEFAULT:
                commuter = (CommuterWithTrips) Defaults.getDefaultCommuter();
                break;
            case KEEP_NULL:
                commuter = new CommuterWithTrips();
                break;
            default:
                commuter = new CommuterWithTrips();
                break;
        }

        commuter.setCommuterName(jsonObject.getString("title"));
        commuter.setImageURL(jsonObject.getString(Constants.RESULT_COMMUTER_IMAGE_URL_KEY));
        commuter.setCommuterAge(25);
        commuter.setCommuterDistance(5);
        ArrayList<Trip> trip = new ArrayList<>();
        trip.add(new Trip());
        trip.get(0).setDestinationPlace(jsonObject.getString("title"));
        commuter.setTripList(trip);

        String url = jsonObject.getString(Constants.RESULT_COMMUTER_IMAGE_URL_KEY);
        commuter.setImageURL(url.replace(":", "s:"));

        return commuter;
    }

    public static CommuterWithTrips getCommuterFromJson(JSONObject jsonObject) throws JSONException{
        return getCommuterFromJson(jsonObject, KEEP_NULL);
    }

    public static List<CommuterWithTrips> getCommutersFromJson(JSONArray jsonArray, int conversionType) throws JSONException {
        List<CommuterWithTrips> commuters = new ArrayList<>();

        for(int i=0; i<jsonArray.length(); i++)
            commuters.add(getCommuterFromJson(jsonArray.getJSONObject(i), conversionType));

        return commuters;
    }

    public static List<CommuterWithTrips> getCommutersFromJson(JSONArray jsonArray) throws JSONException{
        return getCommutersFromJson(jsonArray, KEEP_NULL);
    }

    public static JSONObject getJsonFromCommuter(CommuterWithTrips commuter){
        JSONObject jsonObject = new JSONObject();

        // TODO: 14/07/17 Make JSON parsing

        return jsonObject;
    }



    //Location to JSON conversions

    public static Location getLocationFromJson(JSONObject jsonObject) throws JSONException {
        Location location = new Location(JSONUtils.class.getName());
        location.setLatitude(jsonObject.has(LAT_KEY)?jsonObject.getDouble(LAT_KEY): null);
        location.setLongitude(jsonObject.has(LONG_KEY)?jsonObject.getDouble(LONG_KEY):null);
        return location;
    }

    public static JSONObject getJsonFromLocation(Location location) throws JSONException {
        JSONObject jsonObject = new JSONObject();
        if(location!=null) {
            jsonObject.put(LAT_KEY, location.getLatitude());
            jsonObject.put(LONG_KEY, location.getLongitude());
        }else{
            jsonObject.put(LAT_KEY, "");
            jsonObject.put(LONG_KEY, "");
        }

        return jsonObject;
    }


    //Params from JSON

    public static Map<String, String> getParamsFromJson(JSONObject jsonObject) throws JSONException {
        Map<String, String> params = new HashMap<String, String>();

        Iterator<String> iterator = jsonObject.keys();

        while(iterator.hasNext()){
            String key = iterator.next();
            params.put(key, String.valueOf(jsonObject.get(key)));
        }

        return params;
    }

}
