package com.example.manishchawley.commutehelper.util;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.ImageView;

/**
 * Created by manishchawley on 06/08/17.
 */

public class PlaceImageView extends android.support.v7.widget.AppCompatImageView {

    public static final String TAG = PlaceImageView.class.getName();

    private String placeId;

    public PlaceImageView(Context context) {
        super(context);
    }

    public PlaceImageView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public PlaceImageView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public String getPlaceId() {
        return placeId;
    }

    public PlaceImageView setPlaceId(String placeId) {
        this.placeId = placeId;
        Log.i(TAG, "Place ID set: " + placeId);
        return this;
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    public void refreshImage(){
        final ImageView imageView = this;
        if(getPlaceId()==null){
            Log.e(TAG, "set Place Id first");
            return;
        }
        try {
            new PhotoTask(imageView.getHeight()==0? Constants.PLACE_IMAGE_VIEW_DEFAULT_HEIGHT:getHeight(),
                    imageView.getWidth()==0?Constants.PLACE_IMAGE_VIEW_DEFAULT_WIDTH:imageView.getWidth()) {
                @Override
                protected void onPostExecute(AttributePhoto attributePhoto) {
                    if (attributePhoto != null) {
                        imageView.setImageBitmap(attributePhoto.bitmap);
                    } else {
                        Log.e(TAG, "Null response for Place Id: " + getPlaceId());
                    }
                }

                @Override
                protected void onPreExecute() {
                    // TODO: 07/08/17
                    Log.i(TAG, "Executing image fetch");
                }
            }.execute(getPlaceId());
        }catch (Exception e){
            e.printStackTrace();
            Log.e(TAG, e.getClass().getName() +": "+e.getMessage());
        }
    }

}
