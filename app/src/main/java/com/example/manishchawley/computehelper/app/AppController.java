package com.example.manishchawley.computehelper.app;

import android.app.Application;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;
import com.example.manishchawley.computehelper.util.LruBitmapCache;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.places.GeoDataApi;
import com.google.android.gms.location.places.Places;

/**
 * Created by manishchawley on 15/11/16.
 */

public class AppController extends Application {
    private final String TAG = AppController.class.getName();

    private RequestQueue mRequestQueue;
    private ImageLoader mImageLoader;
    private GoogleApiClient mGoogleApiClient;


    private static AppController mInstance;

    @Override
    public void onCreate(){
        super.onCreate();
        mInstance = this;
        buildGoogleApiClient();
    }

    private void buildGoogleApiClient() {
        GoogleApiConnectionCallbacks googleApiConnectionCallbacks = new GoogleApiConnectionCallbacks();
        mGoogleApiClient = new GoogleApiClient.Builder(getApplicationContext())
                .addApi(Places.GEO_DATA_API)
                .addApi(Places.PLACE_DETECTION_API)
                .addConnectionCallbacks(googleApiConnectionCallbacks)
                .addOnConnectionFailedListener(googleApiConnectionCallbacks)
                .build();
        mGoogleApiClient.connect();
    }

    public static synchronized AppController getInstance(){
        return mInstance;
    }

    public GoogleApiClient getGoogleApiClient(){
        if(mGoogleApiClient==null)
            buildGoogleApiClient();
        return mGoogleApiClient;
    }

    public RequestQueue getRequestQueue(){
        if(mRequestQueue==null)
            mRequestQueue = Volley.newRequestQueue(getApplicationContext());
        return mRequestQueue;
    }

    public ImageLoader getImageLoader(){
        getRequestQueue();
        if(mImageLoader==null)
            mImageLoader = new ImageLoader(this.mRequestQueue, new LruBitmapCache());
        return this.mImageLoader;
    }

    public <T> void addToRequestQueue(Request<T> req, String tag) {
        // set the default tag if tag is empty
        req.setTag(TextUtils.isEmpty(tag) ? TAG : tag);
        getRequestQueue().add(req);
    }

    public <T> void addToRequestQueue(Request<T> req) {
        req.setTag(TAG);
        getRequestQueue().add(req);
    }

    public void cancelPendingRequests(Object tag) {
        if (mRequestQueue != null) {
            mRequestQueue.cancelAll(tag);
        }
    }

    private class GoogleApiConnectionCallbacks implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

        @Override
        public void onConnected(@Nullable Bundle bundle) {
            Log.i(TAG, "Google API client connected");
        }

        @Override
        public void onConnectionSuspended(int i) {
            Log.e(TAG, "Google API client suspended");
        }

        @Override
        public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
            Log.e(TAG, "Google API client connection failed");
        }
    }
}
