package com.example.manishchawley.commutehelper.util;

/**
 * Created by manishchawley on 06/11/16.
 */

public final class Constants {


    public static final int PLACE_PICKER_REQUEST = 1;
    public static final int SUCCESS_RESULT = 1;
    public static final int FAILURE_RESULT = 0;
    public static final String PACKAGE_NAME = "com.example.manishchawley.computehelper";
    //Constants.class.getPackage().getName();

    public static final String RECEIVER = PACKAGE_NAME + ".RECEIVER";
    public static final String RESULT_DATA_KEY = PACKAGE_NAME + ".RESULT_DATA_KEY";
    public static final String LOCATION_DATA_EXTRA = PACKAGE_NAME + ".LOCATION_DATA_EXTRA";
    public static final String LOCATION_RESULT_DATA_KEY = PACKAGE_NAME + ".LCOATION_RESULT_DATA_KEY";

    public static final String CURRENT_LOCATION_FRAGMENT = PACKAGE_NAME + ".CURRENT_LOCATION_FRAGMENT";
    public static final int LOCATION_UPDATE_INTERVAL_SLOW = 100000;
    public static final String CURRENT_LOCATION_KEY = PACKAGE_NAME + ".CURRENT_LOCATION_KEY";
    public static final float LOCATION_CHANGE_THRESHOLD = 100; //in meters
    public static final int FACEBOOK_LOGIN_REQUEST_KEY = 101;
    public static final String FACEBOOK_USER_ID_KEY = PACKAGE_NAME + ".FACEBOOK_USER_ID";
    public static final String FACEBOOK_TOKEN_KEY = PACKAGE_NAME + ".FACEBOOK_TOKEN";
    public static final String FACEBOOK_LOGIN_STATUS = PACKAGE_NAME + ".FACEBOOK_LOGIN_STATUS";
    public static final String FACEBOOK_LOGIN_MESSAGE = PACKAGE_NAME + ".FACEBOOK_LOGIN_MESSAGE";
    public static final String FACEBOOK_PROFILE_KEY = PACKAGE_NAME + ".FACEBOOK_PROFILE";
    public static final String FACEBOOK_TOKEN_STRING_KEY = PACKAGE_NAME + ".FACEBOOK_TOKEN_STRING";

    public static final String FACEBOOK_IMAGE_TYPE = "small";

    public static final String CURRENT_COMMUTER_KEY = PACKAGE_NAME + ".CURRENT_COMMUTER";
    public static final String COMMUTER_NAME_KEY = PACKAGE_NAME + ".COMMUTER_NAME";
    public static final String COMMUTER_AGE_KEY = PACKAGE_NAME + ".COMMUTER_AGE";
    public static final String COMMUTER_GENDER_KEY = PACKAGE_NAME + ".COMMUTER_GENDER";
    public static final String COMMUTER_IMAGE_URL_KEY = PACKAGE_NAME + ".COMMUTER_IMAGE_URL";
    public static final String COMMUTER_DISTANCE_FROM_USER_KEY = PACKAGE_NAME + ".COMMUTER_DISTANCE_FROM_USER";
    public static final String COMMUTER_TRIP_KEY = PACKAGE_NAME + ".COMMUTER_TRIP";
    public static final String COMMUTER_ID_KEY = PACKAGE_NAME + ".COMMUTER_ID";


    public static final String[] TRIP_TRAVEL_MODES = {"Unknown", "Car", "Bike", "Cab", "Bus", "Auto", "Tram", "Walk", "Subway", "Train", "Flight", "Ferry"};
    public static final String TRIP_MODE_OF_TRAVEL_KEY = PACKAGE_NAME + ".MODE_OF_TRAVEL";
    public static final String TRIP_START_LOCATION_KEY = PACKAGE_NAME + ".TRIP_START_LOCATION";
    public static final String TRIP_DESTINATION_LOCATION_KEY = PACKAGE_NAME + ".TRIP_DESTINATION_LOCATION";
    public static final String TRIP_DESTINATION_PLACE_KEY = PACKAGE_NAME + ".TRIP_DESTINATION_PLACE";

    public static final String SHARED_PREFERENCE_NAME = PACKAGE_NAME;
    public static final int SHARED_PREFERENCE_MODE = 0;

    public static final String FRAGMENT_COMMUTER_KEY = PACKAGE_NAME + ".FRAGMENT_COMMUTER_KEY";

    public static final String RESULT_COMMUTER_NAME_KEY = "title";
    public static final String RESULT_COMMUTER_IMAGE_URL_KEY = "image";
    public static final String RESULT_COMMUTER_AGE_KEY = "releaseYear";
    public static final String RESULT_COMMUTER_GENDER_KEY = "title";
    public static final String RESULT_COMMUTER_DISTANCE_FROM_USER_KEY = "rating";
    public static final String COMMUTER_WITH_TRIP_TRIPLIST_KEY = "tripList";


    public static final boolean DEFAULT_TRIP_DETAILS_EDITABLE = false;
    public static final String TRIP_DETAILS_EDITABLE_KEY = PACKAGE_NAME + ".TRIP_DETAILS_EDITABLE_KEY";
    public static final String TRIP_DETAILS_TRIP_KEY = PACKAGE_NAME + ".TRIP_DETAILS_TRIP_KEY";
    public static final String TRIP_DETAILS_DEFAULT_ORIGIN = "Current Location";
    public static final String TRIP_DETAILS_DEFAULT_DESTINATION = "Select Destination";
    public static final String TRIP_DETAILS_DEFAULT_START = "Leaving Now";
    public static final String TRIP_DETAILS_DEFAULT_END = "Not Selected";
    public static final String TRIP_DETAILS_TYPE_KEY = PACKAGE_NAME + ".TRIP_DETAILS_TYPE_KEY";
    public static final int TRIP_DETAILS_TYPE_R = 1;
    public static final int TRIP_DETAILS_TYPE_RW = 2;
    public static final int TRIP_DETAILS_TYPE_N = 3;
    public static final String TRIP_LIST_KEY = PACKAGE_NAME + "TRIP_LIST_KEY";

    public static final int PLACE_PICKER_REQUEST_ORIGIN = 101;
    public static final int PLACE_PICKER_REQUEST_DESTINATION = 102;
    public static final int ANDROID_YEAR_ADJUSTMENT = 0000;

    public static final String TRIP_DATABASE_KEY = (PACKAGE_NAME + ".trips").replace(".", "_");
    public static final String COMMUTER_DATABASE_KEY = (PACKAGE_NAME + ".commuters").replace(".", "_");
    public static final String COMMUTER_WITH_TRIP_DATABASE_KEY = (PACKAGE_NAME + ".commuters.with.trips").replace(".", "_");

    public static final int ACCESS_FINE_LOCATION_REQUEST_CODE = 1;

    public static final int PLACE_IMAGE_VIEW_DEFAULT_HEIGHT = 500;
    public static final int PLACE_IMAGE_VIEW_DEFAULT_WIDTH = 500;

    public static final String COMMUTER_LIST_FILTER = PACKAGE_NAME + ".COMMUTER_LIST_FILTER";
}
