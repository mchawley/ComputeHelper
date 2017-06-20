package com.example.manishchawley.computehelper.provider;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.os.ResultReceiver;

/**
 * Created by manishchawley on 07/11/16.
 */

public class LocationResultReceiver extends ResultReceiver {

    private Receiver mReceiver;

    /**
     * Create a new ResultReceive to receive results.  Your
     * {@link #onReceiveResult} method will be called from the thread running
     * <var>handler</var> if given, or from an arbitrary thread if null.
     *
     * @param handler
     */
    public LocationResultReceiver(Handler handler) {
        super(handler);
    }

    public interface Receiver{
        public void onReceiveResult(int resultCode, Bundle resultData);
    }

    public void setReceiver(Receiver receiver){
        mReceiver = receiver;
    }

    protected void onReceiveResult(int resultCode, Bundle resultData){
        if(mReceiver!=null)
            mReceiver.onReceiveResult(resultCode, resultData);
    }

}
