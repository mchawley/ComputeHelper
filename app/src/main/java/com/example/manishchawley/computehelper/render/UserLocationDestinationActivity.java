package com.example.manishchawley.computehelper.render;

import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.manishchawley.computehelper.R;
import com.example.manishchawley.computehelper.model.Commuter;
import com.example.manishchawley.computehelper.provider.LocationResultReceiver;
import com.example.manishchawley.computehelper.util.Constants;
import com.example.manishchawley.computehelper.provider.CurrentLocationService;
import com.facebook.AccessToken;
import com.facebook.Profile;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class UserLocationDestinationActivity extends FragmentActivity
        implements OnMapReadyCallback, GoogleMap.OnMyLocationButtonClickListener
        , LocationResultReceiver.Receiver{


    private GoogleMap mGoogleMap;
    private Location currentLocation;
    private Place destinationPlace;
    private Intent mIntent;
    //private CurrentLocationFragment currentLocationFragment;
    private Marker currentmarker, destinationmarker;
    private View mapView;
    private Commuter commuter;
    private boolean updateCurrentLocation;

    public LocationResultReceiver locationresultreceiver;


    private final String TAG = getClass().getName().toString();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        updateValuesFromBundle(savedInstanceState);

        setContentView(R.layout.activity_user_location_destination);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        Log.i(TAG, "Intent has Extras: " + String.valueOf(getIntent().getExtras()!=null));

        if(getIntent().getExtras()!=null){
            if(getIntent().hasExtra(Constants.CURRENT_COMMUTER_KEY))
                commuter = getIntent().getParcelableExtra(Constants.CURRENT_COMMUTER_KEY);
            //if(getIntent().hasExtra(Constants.FACEBOOK_TOKEN_KEY) && getIntent().hasExtra(Constants.FACEBOOK_PROFILE_KEY))
                //commuter = new Commuter((AccessToken)getIntent().getParcelableExtra(Constants.FACEBOOK_TOKEN_KEY), (Profile)getIntent().getParcelableExtra(Constants.FACEBOOK_PROFILE_KEY));
        }

        SupportMapFragment supportMapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.content_user_location_destination_map_view);
        supportMapFragment.getMapAsync(this);
        mapView = supportMapFragment.getView();

        updateCurrentLocation = true;
        locationresultreceiver = new LocationResultReceiver(new Handler());
        locationresultreceiver.setReceiver(this);

        mIntent = new Intent(this, CurrentLocationService.class);
        mIntent.putExtra(Constants.LOCATION_DATA_EXTRA, locationresultreceiver);
        startService(mIntent);

        //TODO change the code to read location from service
        //currentLocationFragment = CurrentLocationFragment.newInstance();
        //getSupportFragmentManager().beginTransaction().add(currentLocationFragment, Constants.CURRENT_LOCATION_FRAGMENT).commit();

    }

    public void onSaveInstanceState(Bundle savedInstanceState){
        if(currentLocation!=null)
            savedInstanceState.putParcelable(Constants.CURRENT_LOCATION_KEY, currentLocation);

        super.onSaveInstanceState(savedInstanceState);
    }

    private void updateValuesFromBundle(Bundle savedInstanceState) {
        if(savedInstanceState!=null){
            if(savedInstanceState.keySet().contains(Constants.CURRENT_LOCATION_KEY)){
                currentLocation = savedInstanceState.getParcelable(Constants.CURRENT_LOCATION_KEY);
            }
        }
    }

    public void selectDestination(View view){
        Log.e(TAG, "Starting destination selection");
        PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();

        try {
            startActivityForResult(builder.build(this), Constants.PLACE_PICKER_REQUEST);
        } catch (GooglePlayServicesRepairableException e) {
            Log.e(TAG, e.getMessage());
            e.printStackTrace();
        } catch (GooglePlayServicesNotAvailableException e) {
            Log.e(TAG, e.getMessage());
            e.printStackTrace();
        }
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.e(TAG, "Activity Result, Request Code: "+ requestCode + "(" + RESULT_OK + "), Result Code: " + resultCode);
        if (requestCode == Constants.PLACE_PICKER_REQUEST) {
            if (resultCode == RESULT_OK) {
                Place place = PlacePicker.getPlace(this, data);
                setDestination(place);
                updateDestination();
                updateMapWithRoute();
                //String toastMsg = String.format("Place: %s", place.getName());
                //Toast.makeText(this, toastMsg, Toast.LENGTH_LONG).show();
            }
        }
    }

    private void updateMapWithRoute() {
        //TODO
        LatLng destinationLatLng = destinationPlace.getLatLng();
        mGoogleMap.addMarker(new MarkerOptions().position(destinationLatLng).title("Destination"));
    }

    public void setDestination(Place place){
        destinationPlace = place;
    }

    public Place getDestination(){
        return destinationPlace;
    }

    public void updateDestination(){
        updateDestination(getDestination());
    }

    public void updateDestination(Place place){
        EditText ed = (EditText)findViewById(R.id.content_user_location_destination_destination_picker);
        if(place == null) {
            ed.setText("Select Destination");
            updateDestinationLocationOnMap();
        }else {
            ed.setText(place.getName().toString());

        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.mGoogleMap = googleMap;
        setupMap();
    }

    @SuppressWarnings("MissingPermission")
    private void setupMap() {
        mGoogleMap.setMyLocationEnabled(true);

        if (mapView != null &&
                mapView.findViewById(Integer.parseInt("1")) != null) {
            // Get the button view
            View locationButton = ((View) mapView.findViewById(Integer.parseInt("1")).getParent()).findViewById(Integer.parseInt("2"));
            // and next place it, on bottom right (as Google Maps app)
            RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams)
                    locationButton.getLayoutParams();
            // position on right bottom
            layoutParams.addRule(RelativeLayout.ALIGN_PARENT_TOP, 0);
            layoutParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, RelativeLayout.TRUE);
            layoutParams.setMargins(0, 0, 30, 30);
        }

        if(currentLocation==null)
            return;
        updateCurrentLocationOnMap();
    }

    private void updateCurrentLocationOnMap(){
        LatLng currrentLatLng = new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude());

        if(currentmarker!=null)
            currentmarker.remove();
        currentmarker = mGoogleMap.addMarker(new MarkerOptions()
                .position(currrentLatLng)
                .draggable(true)
                //.title(commuter.getCommuterName())
                //.icon(BitmapDescriptorFactory.fromBitmap(commuter.getFacebookProfilePicture()))
        );
        mGoogleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(currrentLatLng, 15));
        //Log.i(TAG, commuter.getImageurl());

    }

    private void updateDestinationLocationOnMap(){
        LatLng destinationLatLng = destinationPlace.getLatLng();

        if(destinationmarker!=null)
            destinationmarker.remove();
        destinationmarker = mGoogleMap.addMarker(new MarkerOptions()
                        .position(destinationLatLng)
                        .title(destinationPlace.getName().toString())
                //.icon(BitmapDescriptorFactory.fromBitmap(commuter.getFacebookProfilePicture()))
        );
        mGoogleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(destinationLatLng, 15));
        //Log.i(TAG, commuter.getImageURL());

        // TODO: 15/11/16
        /*
        LatLngBounds.Builder builder = new LatLngBounds.Builder();
for (Marker marker : markers) {
    builder.include(marker.getPosition());
}
LatLngBounds bounds = builder.build();

Then obtain a movement description object by using the factory: CameraUpdateFactory:

int padding = 0; // offset from edges of the map in pixels
CameraUpdate cu = CameraUpdateFactory.newLatLngBounds(bounds, padding);

Finally move the map:

googleMap.moveCamera(cu);

Or if you want an animation:

googleMap.animateCamera(cu);

That's all :)

Clarification 1

Almost all movement methods require the Map object to have passed the layout process. You can wait for this to happen using the addOnGlobalLayoutListener construct.
         */

    }

    @Override
    public void onReceiveResult(int resultCode, Bundle resultData) {
        Log.i(TAG, "Received result from service");
        if(resultCode == Constants.FAILURE_RESULT && currentLocation == null)
            Toast.makeText(this, "Unable to find your location", Toast.LENGTH_SHORT).show();
        if(resultCode == Constants.SUCCESS_RESULT)
            if(currentLocationChanged((Location)resultData.getParcelable(Constants.LOCATION_RESULT_DATA_KEY)))
                updateCurrentLocation();
    }

    private void updateCurrentLocation() {
        updateCurrentLocationOnMap();
        updateCurrentLocation = false;
    }

    private boolean currentLocationChanged(Location location) {

        if(!updateCurrentLocation)
            return false;

        float[] results = new float[2];
        if(currentLocation==null && location==null)
            return false;

        if(location!=null && currentLocation==null){
            currentLocation = location;
            return true;
        }

        Location.distanceBetween(currentLocation.getLatitude(), currentLocation.getLongitude(), 
                location.getLatitude(), location.getLongitude(), results);
        
        if(results[0]>Constants.LOCATION_CHANGE_THRESHOLD) {
            currentLocation = location;
            return true;
        }else
            return false;
        
    }

    @Override
    public boolean onMyLocationButtonClick() {
        // TODO: 10/11/16
        updateCurrentLocation = true;
        return false;
    }


}
