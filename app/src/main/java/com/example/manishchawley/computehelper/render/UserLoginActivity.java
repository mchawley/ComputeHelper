package com.example.manishchawley.computehelper.render;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

import com.example.manishchawley.computehelper.R;
import com.example.manishchawley.computehelper.model.User;
import com.example.manishchawley.computehelper.util.Constants;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class UserLoginActivity extends AppCompatActivity {
    private final String TAG = UserLoginActivity.class.getName();

     @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_user_login);

        Log.e(TAG, "User Login started");

        Button button = (Button) findViewById(R.id.activity_user_login_login_button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doUserLogin();
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        if(User.getUser().isLogin())
            gotoNextActivity();
    }

    private void gotoNextActivity() {
        Log.e(TAG, "User already logged in, starting Trip list activity");
        Intent intent = new Intent(this, TripListActivity.class);
        startActivity(intent);
    }

    private void doUserLogin() {
            //calling Facebook login activity
            Log.e(TAG, "User not logged in, starting Facebook Login activity");
            Intent intent = new Intent(this, FacebookLoginActivity.class);
            startActivityForResult(intent, Constants.FACEBOOK_LOGIN_REQUEST_KEY);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.e(TAG, "Getting result back from Facebook login, requestcode: " + requestCode + " & resultcode: " + resultCode);
        //check the request type
        if(requestCode == Constants.FACEBOOK_LOGIN_REQUEST_KEY && resultCode == RESULT_OK){
            //// TODO: 10/07/17 do next steps after login
            //check if it is a new signup
            if(User.getUser().isNewUser()){
                //// TODO: 10/07/17 send information to server on new user creation
            }
            if(data.getExtras()!=null) {
                Log.e(TAG, "User current login status: " + User.getUser().isLogin());
                if(User.getUser().isLogin())
                    gotoNextActivity();
            }

        }else{
            //// TODO: 10/07/17 show error

        }
    }


}
