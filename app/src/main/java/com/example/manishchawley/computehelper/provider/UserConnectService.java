package com.example.manishchawley.computehelper.provider;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.content.Intent;
import android.util.Log;

import com.example.manishchawley.computehelper.util.Constants;


/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p>
 * TODO: Customize class - update intent actions and extra parameters.
 */
public class UserConnectService extends IntentService {

    private final String TAG = getClass().getName().toString();

    private NotificationManager notificationManager;
    private Notification notification;

    public UserConnectService() {
        super(Constants.PACKAGE_NAME);
        Log.i(TAG, "Service Started");
    }

    @Override
    protected void onHandleIntent(Intent intent) {

    }

    @Override
    public void onCreate(){
        super.onCreate();
    }




}
