package com.example.manishchawley.computehelper.util;

import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.util.Log;

import com.example.manishchawley.computehelper.app.AppController;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.places.PlacePhotoMetadata;
import com.google.android.gms.location.places.PlacePhotoMetadataBuffer;
import com.google.android.gms.location.places.PlacePhotoMetadataResult;
import com.google.android.gms.location.places.Places;

/**
 * Created by manishchawley on 24/07/17.
 */

public abstract class PhotoTask extends AsyncTask<String, Void, PhotoTask.AttributePhoto> {

    private final String TAG = PhotoTask.class.getName();

    private int height, width;

    public PhotoTask(int height, int width) {
        this.height = height;
        this.width = width;
    }

    //Loads photo for for a place ID, Place ID should be only parameter
    @Override
    protected AttributePhoto doInBackground(String... params) {
        if(params.length!=1)
            return null;
        final String placeID = params[0];
        AttributePhoto attributePhoto = null;

        GoogleApiClient googleApiClient = AppController.getInstance().getGoogleApiClient();

        if(!googleApiClient.isConnected())
            googleApiClient.connect();

        Log.i(TAG, "Google API client connected: " + googleApiClient.isConnected());

        PlacePhotoMetadataResult result = Places.GeoDataApi.getPlacePhotos(googleApiClient, placeID).await();

        Log.i(TAG, "Getting image result: " + result.getStatus().getStatusMessage());

        if(result.getStatus().isSuccess()){
            PlacePhotoMetadataBuffer photoMetadataBuffer = result.getPhotoMetadata();
            if(photoMetadataBuffer.getCount()>0 && !isCancelled()){
                PlacePhotoMetadata photo = photoMetadataBuffer.get(0);
                CharSequence attribution = photo.getAttributions();
                Bitmap image = photo.getScaledPhoto(googleApiClient, width, height).await().getBitmap();

                attributePhoto = new AttributePhoto(attribution, image);
            }
            photoMetadataBuffer.release();
        }
        return attributePhoto;
    }

    //Holder for Image and attributes
    public class AttributePhoto {
        public final CharSequence attribution;
        public final Bitmap bitmap;

        public AttributePhoto(CharSequence attribution, Bitmap bitmap) {
            this.attribution = attribution;
            this.bitmap = bitmap;
        }
    }
}
