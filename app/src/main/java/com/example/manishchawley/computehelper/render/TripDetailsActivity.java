package com.example.manishchawley.computehelper.render;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.media.MediaBrowserCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.example.manishchawley.computehelper.R;
import com.example.manishchawley.computehelper.app.AppController;
import com.example.manishchawley.computehelper.model.Commuter;
import com.example.manishchawley.computehelper.model.Trip;
import com.example.manishchawley.computehelper.util.Constants;
import com.example.manishchawley.computehelper.util.Defaults;
import com.example.manishchawley.computehelper.util.Dialogs;
import com.example.manishchawley.computehelper.util.PhotoTask;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.PlaceDetectionApi;
import com.google.android.gms.location.places.PlaceLikelihood;
import com.google.android.gms.location.places.PlaceLikelihoodBuffer;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.location.places.ui.PlaceAutocomplete;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.google.android.gms.maps.model.LatLng;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.Calendar;

public class TripDetailsActivity extends AppCompatActivity {

    private final String TAG = TripDetailsActivity.class.getName();

    //    private PlaceAutocompleteFragment tripOrigin, tripDestination;
    private TextView tripOrigin, tripDestination;
    private TextView tripStart, tripEnd, tripMaxNo;
    private FloatingActionButton commuters, chats;
    private Button update, cancel;
    private ImageView tripImage;

    private ProgressDialog progressDialog;

    private Trip trip;
    private int tripType;
    private String destinationPlaceID;

    private GoogleApiClient googleApiClient;

    private boolean isEditable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trip_details);

        //giving default value to isEditable
        setEditable(Constants.DEFAULT_TRIP_DETAILS_EDITABLE);

        getSupportActionBar().hide();

//        tripOrigin = (PlaceAutocompleteFragment) getFragmentManager().findFragmentById(R.id.activity_trip_details_origin);
//        tripDestination = (PlaceAutocompleteFragment) getFragmentManager().findFragmentById(R.id.activity_trip_details_destination);
        tripOrigin = (TextView) findViewById(R.id.activity_trip_details_origin);
        tripDestination = (TextView) findViewById(R.id.activity_trip_details_destination);
        tripStart = (TextView) findViewById(R.id.activity_trip_details_startdate);
        tripEnd = (TextView) findViewById(R.id.activity_trip_details_enddate);

        tripImage = (ImageView) findViewById(R.id.activity_trip_details_tripimage_imageview);

        commuters = (FloatingActionButton) findViewById(R.id.activity_trip_details_commuters);
        chats = (FloatingActionButton) findViewById(R.id.activity_trip_details_chat);

        update = (Button) findViewById(R.id.activity_trip_details_update);
        cancel = (Button) findViewById(R.id.activity_trip_details_cancel);

        //default all buttons as hidden
        commuters.hide();
        chats.hide();
        update.setVisibility(View.INVISIBLE);
        cancel.setVisibility(View.INVISIBLE);

        showProgressDialog();

        if (getIntent().getExtras() != null) {
            if (getIntent().hasExtra(Constants.TRIP_DETAILS_EDITABLE_KEY))
                setEditable(getIntent().getBooleanExtra(Constants.TRIP_DETAILS_EDITABLE_KEY, Constants.DEFAULT_TRIP_DETAILS_EDITABLE));

            if (getIntent().hasExtra(Constants.TRIP_DETAILS_TYPE_KEY))
                tripType = getIntent().getIntExtra(Constants.TRIP_DETAILS_TYPE_KEY, Constants.TRIP_DETAILS_TYPE_R);

            if (getIntent().hasExtra(Constants.TRIP_DETAILS_TRIP_KEY))
                trip = getIntent().getParcelableExtra(Constants.TRIP_DETAILS_TRIP_KEY);
        }

        switch (tripType) {
            case Constants.TRIP_DETAILS_TYPE_N:
                handleNewTripRequest();
                break;
            case Constants.TRIP_DETAILS_TYPE_R:
                handleReadOnlyTripRequest();
                break;
            case Constants.TRIP_DETAILS_TYPE_RW:
                handleReadWriteTripRequest();
                break;
            default:
                handleReadOnlyTripRequest();
        }

//        //starting location service
//        locationResultReceiver = new LocationResultReceiver(new Handler());
//        locationResultReceiver.setReceiver(this);
//
//        Intent intent = new Intent(this, CurrentLocationService.class);
//        intent.putExtra(Constants.LOCATION_DATA_EXTRA, locationResultReceiver);
//        //startService(intent);

    }

    private void showProgressDialog() {
        if(progressDialog==null)
            progressDialog = new ProgressDialog(this);

        progressDialog.setMessage("Please wait");
        progressDialog.show();
    }

    private void hideProgressDialog(){
        if(progressDialog!=null && progressDialog.isShowing())
            progressDialog.hide();
    }

    private void handleReadWriteTripRequest() {

        handleReadOnlyTripRequest();

        showUpdateCancelButtons();

        //adding action listener to cancel button
    }

    private void handleReadOnlyTripRequest() {
        setTripDetails();
        Picasso.with(this).load(trip.getImageURL())
                .placeholder(R.drawable.messenger_button_blue_bg_round)
                .error(R.drawable.com_facebook_close)
                .fit().into(tripImage);
        showFAB();
        setFABActionListeners();
    }

    private void handleNewTripRequest() {

        //Setting trip to default
        Log.i(TAG, "Handling new trip request");

        trip = Defaults.getDefaultTrip();

        trip.setTripOwner(Commuter.createCommuterFromUser());

        updateTripWithCurrentLocation();

        showUpdateCancelButtons();

        addActionListeners();
    }

    private void updateTripWithCurrentLocation() {
        googleApiClient = AppController.getInstance().getGoogleApiClient();
        Log.i(TAG, "Google API client connected? " + googleApiClient.isConnected());
        if (!googleApiClient.isConnected())
            googleApiClient.connect();
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, Constants.ACCESS_FINE_LOCATION_REQUEST_CODE);
        }
        PendingResult<PlaceLikelihoodBuffer> result = Places.PlaceDetectionApi.getCurrentPlace(googleApiClient, null);
        result.setResultCallback(new ResultCallback<PlaceLikelihoodBuffer>() {
            @Override
            public void onResult(@NonNull PlaceLikelihoodBuffer placeLikelihoods) {
                Log.i(TAG, "Result received for origin places: " + placeLikelihoods.getCount());
//                for(PlaceLikelihood placelikelihood : placeLikelihoods){
//                    Log.e(TAG, placelikelihood.getPlace().getName() + " with likelihood " + placelikelihood.getLikelihood());
//                }
                trip.setOriginPlace(placeLikelihoods.get(0).getPlace().getName().toString());
                trip.setOriginPlaceID(placeLikelihoods.get(0).getPlace().getId());
                trip.setOriginLocation(placeLikelihoods.get(0).getPlace().getLatLng());
                setTripDetails();
                hideProgressDialog();
                placeLikelihoods.release();
            }
        });
    }

    private void showFAB() {
        commuters.show();
        chats.show();
    }

    private void showUpdateCancelButtons() {
        if(update.getVisibility()==Button.INVISIBLE)
            update.setVisibility(Button.VISIBLE);
        if(cancel.getVisibility()==Button.INVISIBLE)
            cancel.setVisibility(Button.VISIBLE);

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                trip.setTrip(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if(dataSnapshot.exists())
                            Log.i(TAG, "Uploaded tripID: " + dataSnapshot.getKey());
                        finish();
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        Log.e(TAG, databaseError.getMessage());
                    }
                });
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(tripType==Constants.TRIP_DETAILS_TYPE_N)
                    TripDetailsActivity.super.onBackPressed();
                if(tripType==Constants.TRIP_DETAILS_TYPE_RW)
                    setTripDetails();
            }
        });
    }

    private void setFABActionListeners() {
        commuters.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startCommuterListActivity();
            }
        });
        chats.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startChatListActivity();
            }
        });
    }

    private void startChatListActivity() {
        Intent intent = new Intent(this, ChatListActivity.class);
        startActivity(intent);
    }

    private void startCommuterListActivity() {
        Intent intent = new Intent(this, CommuterListActivity.class);
        startActivity(intent);
    }

    private void setTripDetails() {
        Log.i(TAG, "Is trip set? " + String.valueOf(trip!=null));
        if(trip!=null) {
            tripOrigin.setText(trip.getOriginPlace());
            tripDestination.setText(trip.getDestinationPlace());
            tripStart.setText(DateUtils.getRelativeDateTimeString(this, trip.getTripStart(), DateUtils.DAY_IN_MILLIS, DateUtils.WEEK_IN_MILLIS, 0));
            if (trip.getTripEnd() != 0)
                tripEnd.setText(DateUtils.getRelativeDateTimeString(this, trip.getTripEnd(), DateUtils.DAY_IN_MILLIS, DateUtils.WEEK_IN_MILLIS, 0));
            else
                tripEnd.setEnabled(false);
        }
    }

    private void addActionListeners() {
        // TODO: 12/07/17 Add action listners to content fields

        //date update using date picker dialog

        tripStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialogs.getDatePickerDialog(TripDetailsActivity.this, tripStart);
            }
        });
        tripEnd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialogs.getDatePickerDialog(TripDetailsActivity.this, tripEnd);
            }
        });

        tripOrigin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startPlacePickerActivity(Constants.PLACE_PICKER_REQUEST_ORIGIN);
            }
        });

        tripDestination.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startPlacePickerActivity(Constants.PLACE_PICKER_REQUEST_DESTINATION);
            }
        });

        //Place listener
//        tripOrigin.setOnPlaceSelectedListener(new PlaceSelectionListener() {
//            @Override
//            public void onPlaceSelected(Place place) {
//                Log.e(TAG, "Selected Place: " + place.getName());
//                tripOrigin.setText(place.getName());
//            }
//            @Override
//            public void onError(Status status) {
//                Log.e(TAG, "Error status: " + status.getStatusMessage());
//            }
//        });
//
//        tripDestination.setOnPlaceSelectedListener(new PlaceSelectionListener() {
//            @Override
//            public void onPlaceSelected(Place place) {
//                Log.e(TAG, "Selected Place: " + place.getName());
//                tripDestination.setText(place.getName());
//            }
//            @Override
//            public void onError(Status status) {
//                Log.e(TAG, "Error status: " + status.getStatusMessage());
//            }
//        });
    }

    public boolean isEditable() {
        return isEditable;
    }

    private void setEditable(boolean editable) {
        isEditable = editable;
    }

    private void startPlacePickerActivity(int requestCode){
        try {
            Intent intent = new PlaceAutocomplete.IntentBuilder(PlaceAutocomplete.MODE_OVERLAY).build(this);
            startActivityForResult(intent, requestCode);
        } catch (GooglePlayServicesRepairableException e) {
            e.printStackTrace();
            Log.e(TAG, e.getMessage());
        } catch (GooglePlayServicesNotAvailableException e) {
            e.printStackTrace();
            Log.e(TAG, e.getMessage());
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Place place;

        if(resultCode==RESULT_OK){
            switch (requestCode){
                case Constants.PLACE_PICKER_REQUEST_ORIGIN:
                    place = PlaceAutocomplete.getPlace(this, data);
                    trip.setOriginPlace(place.getName().toString());
                    trip.setOriginLocation(place.getLatLng());
                    trip.setOriginPlaceID(place.getId());
                    tripOrigin.setText(trip.getOriginPlace());
                    break;
                case Constants.PLACE_PICKER_REQUEST_DESTINATION:
                    place = PlaceAutocomplete.getPlace(this, data);
                    trip.setDestinationPlace(place.getName().toString());
                    trip.setDestinationLocation(place.getLatLng());
                    trip.setDestinationPlaceID(place.getId());
                    tripDestination.setText(trip.getDestinationPlace());
                    destinationPlaceID = place.getId();
                    updateDestinationImage();
                    break;
                default:
                    Log.e(TAG, "Unknown request code");
            }
        }else{
            Log.e(TAG, "Result not ok for request code: " + requestCode);
        }
    }

    private void updateDestinationImage(){
        Log.i(TAG, "Getting place image for :" + destinationPlaceID);

        new PhotoTask(tripImage.getWidth(), tripImage.getHeight()){
            @Override
            protected void onPostExecute(AttributePhoto attributePhoto) {
                if(attributePhoto!=null){
                    tripImage.setImageBitmap(attributePhoto.bitmap);
                }
            }
        }.execute(destinationPlaceID);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(Constants.TRIP_DETAILS_TRIP_KEY, trip);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        if(savedInstanceState.containsKey(Constants.TRIP_DETAILS_TRIP_KEY))
            trip = (Trip) savedInstanceState.get(Constants.TRIP_DETAILS_TRIP_KEY);
    }
}
