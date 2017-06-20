package com.example.manishchawley.computehelper.model;

import android.content.SharedPreferences;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.util.Log;

import com.example.manishchawley.computehelper.util.Constants;
import com.facebook.AccessToken;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.net.URI;

/**
 * Created by manishchawley on 22/07/17.
 */

public class User {

    private static final String TAG = User.class.getName();

    private String name, userID;
    private Uri imageURL;
    private boolean loginStatus;

    private FirebaseUser firebaseUser;
    private static User userInstance = new User();

    private User(){
        updateUserData();
    }

    private void updateUserData() {
        Log.e(TAG, "Updating User data");
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        if(firebaseUser!=null){
            setName(firebaseUser.getDisplayName());
            setImageURL(firebaseUser.getPhotoUrl());
            setUserID(firebaseUser.getUid());
            setLoginStatus(true);
        }else{
            handleNullFirebaseUser();
        }
        Log.i(TAG, String.format("User Data - name: %s, userid: %s, imageurl: %s, loginstatus: %s", getName(), getUserID(), getImageURL(), isLogin()));
    }

    private void handleNullFirebaseUser() {
        // TODO: 22/07/17 Handle Null Firebase User
        setLoginStatus(false);
    }

    public static User getUser(){
        return userInstance;
    }

    public static boolean doLogin(AccessToken accessToken, final LoginResponseListner responseListner){

        Log.e(TAG, "Starting login with Firebase");

        final FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        final boolean[] loginSuccess = {false};

        AuthCredential credential = FacebookAuthProvider.getCredential(accessToken.getToken());

        // TODO: 21/07/17 Handle null credentials

        firebaseAuth.signInWithCredential(credential)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Log.e(TAG, "Login Successful");
                            loginSuccess[0] = true;
                            User.getUser().setLoginStatus(true);
                            User.getUser().updateUserData();
                            responseListner.onLoginSuccess();
                        }else {
                            responseListner.onLoginFailure();
                        }

                    }
                });

        //User.getUser().setLoginStatus(loginSuccess[0]);

        return loginSuccess[0];
    }

    public static boolean doLogout(){
        FirebaseAuth.getInstance().signOut();
        User.getUser().setLoginStatus(false);
        return true;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public Uri getImageURL() {
        return imageURL;
    }

    public void setImageURL(Uri imageURL) {
        this.imageURL = imageURL;
    }

    public boolean isLogin() {
        return loginStatus;
    }

    private void setLoginStatus(boolean loginStatus) {
        this.loginStatus = loginStatus;
    }

    public boolean isNewUser(){
        return false;
    }


    public interface LoginResponseListner{
        void onLoginSuccess();
        void onLoginFailure();
    }
}
