package com.example.manishchawley.commutehelper;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.example.manishchawley.commutehelper.model.Commuter;
import com.example.manishchawley.commutehelper.render.UserLoginActivity;


public class MainActivity extends AppCompatActivity {

    private Commuter commuter;
    private final String TAG = getClass().getName().toString();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i(TAG, "Starting Main Activity");
        setContentView(com.example.manishchawley.commutehelper.R.layout.activity_main);
        if((getIntent().getFlags() & Intent.FLAG_ACTIVITY_LAUNCHED_FROM_HISTORY)==0)
            gotoLoginActivity();
    }

    private void gotoLoginActivity() {
        Log.i(TAG, "Starting Login Activity");
        Intent intent = new Intent(this, UserLoginActivity.class);
        startActivity(intent);
    }
}

//previous version
/*
    private void gotoFacebookLoginActivity() {
        Log.i(TAG, "Starting Facebook login activity for result");
        Intent intent = new Intent(this, FacebookLoginActivity.class);
        startActivityForResult(intent, Constants.FACEBOOK_LOGIN_REQUEST_KEY);
    }

    private boolean isUserLogged() {
        boolean loginStatus = false;

        FacebookSdk.sdkInitialize(getApplicationContext());
        AccessToken accessToken = AccessToken.getCurrentAccessToken();

        Log.i(TAG, "Is user AccessToken null? " + String.valueOf(accessToken==null));

        if(accessToken!=null)
            loginStatus = true;

        return loginStatus;
    }


    private void startUserLocationDestinationActivity(Commuter c){
        Log.i(TAG, "startUserLocationDestinationActivity");
        Intent intent = new Intent(this, UserLocationDestinationActivity.class);
        intent.putExtra(Constants.CURRENT_COMMUTER_KEY, c);
        startActivity(intent);
    }

    private void startUserLocationDestinationActivity(AccessToken at, Profile p) {
        Log.i(TAG, "startUserLocationDestinationActivity");
        Intent intent = new Intent(this, UserLocationDestinationActivity.class);
        intent.putExtra(Constants.FACEBOOK_PROFILE_KEY, p);
        intent.putExtra(Constants.FACEBOOK_TOKEN_KEY, at);
        startActivity(intent);
    }

    private void startUserDisplayActivity(){
        Log.i(TAG, "Starting User Display Activity");
        Intent intent = new Intent(this, UserDisplayActivity.class);
        startActivity(intent);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data){
        Toast.makeText(this, "Logged in", Toast.LENGTH_SHORT).show();
        Log.i(TAG, "Request Code: "+ requestCode);
        Log.i(TAG, "Result Code: "+ resultCode);
        Log.i(TAG, "Result Ok? " + String.valueOf(requestCode==Constants.FACEBOOK_LOGIN_REQUEST_KEY && resultCode==Constants.SUCCESS_RESULT));

        if(requestCode==Constants.FACEBOOK_LOGIN_REQUEST_KEY && resultCode==Constants.SUCCESS_RESULT){
            AccessToken at = data.getParcelableExtra(Constants.FACEBOOK_TOKEN_KEY);
            Profile p = data.getParcelableExtra(Constants.FACEBOOK_PROFILE_KEY);
            commuter = new Commuter(at, p);
            startUserLocationDestinationActivity(at, p);
            //startUserDisplayActivity();
        }

        Log.i(getClass().getName().toString(), data.getStringExtra(Constants.FACEBOOK_USER_ID_KEY));
    }


}
*/
