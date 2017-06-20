package com.example.manishchawley.computehelper.render;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.manishchawley.computehelper.R;
import com.example.manishchawley.computehelper.model.User;
import com.example.manishchawley.computehelper.util.Constants;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.Profile;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class FacebookLoginActivity extends AppCompatActivity  {

    private TextView info;
    private LoginButton loginButton;
    private CallbackManager callbackManager;

    private final String TAG = getClass().getName().toString();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Log.e(TAG, "Created Facebook login activity");

        FacebookSdk.sdkInitialize(getApplicationContext());

        callbackManager = CallbackManager.Factory.create();
        Log.e(TAG, "Initialize Callback Manager");

        setContentView(R.layout.activity_facebook_login);

        info = (TextView) findViewById(R.id.info_facebook_login_activity);

        loginButton = (LoginButton) findViewById(R.id.login_button_facebook_login_activity);
        loginButton.setReadPermissions("email", "public_profile");

        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                Log.e(TAG, "Registered onSuccess");
                //info.setText("User ID: " + loginResult.getAccessToken().getUserId() + "\n Token:" + loginResult.getAccessToken().getToken());
                handleFacebookLoginResult(loginResult.getAccessToken());
            }

            @Override
            public void onCancel() {
                Log.e(TAG, "Registered onCancel");
                info.setText("Login Attempt Cancelled");
                Intent intent = new Intent();
                intent.putExtra(Constants.FACEBOOK_LOGIN_MESSAGE, "Login Attempt Cancelled");
                intent.putExtra(Constants.FACEBOOK_LOGIN_STATUS, false);
                setResult(RESULT_CANCELED, intent);
            }

            @Override
            public void onError(FacebookException error) {
                Log.e(TAG, "Registered onError");
                info.setText("Longin Error: " + error.getMessage().toString());
                Intent intent = new Intent();
                intent.putExtra(Constants.FACEBOOK_LOGIN_MESSAGE, "Login Error: " + error.getMessage().toString());
                intent.putExtra(Constants.FACEBOOK_LOGIN_STATUS, false);
                setResult(RESULT_CANCELED, intent);
            }
        });

        AccessToken accessToken = AccessToken.getCurrentAccessToken();
        Log.e(TAG, "Is user AccessToken null? " + String.valueOf(accessToken==null));

        if(accessToken!=null){
            Log.e(TAG, "Already have accessToken");
            handleFacebookLoginResult(accessToken);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        Log.e(TAG, "sending onActivityResult to callbackManager");
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    private void handleFacebookLoginResult(AccessToken accessToken){
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please wait");
        progressDialog.show();

        Log.e(TAG, "Handling Facebook access token: "+ accessToken.toString());

        final Intent intent = new Intent();

        User.doLogin(accessToken, new User.LoginResponseListner() {
            @Override
            public void onLoginSuccess() {
                Log.e(TAG, "Received event Login Success");
                intent.putExtra(Constants.CURRENT_COMMUTER_KEY, User.getUser().getUserID());
                setResult(RESULT_OK, intent);
                progressDialog.dismiss();
                finish();
            }

            @Override
            public void onLoginFailure() {
                Log.e(TAG, "Login failure");
            }
        });
    }

}
