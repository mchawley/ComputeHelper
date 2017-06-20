package com.example.manishchawley.computehelper.render;

import android.net.Uri;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.example.manishchawley.computehelper.R;
import com.example.manishchawley.computehelper.app.AppController;
import com.example.manishchawley.computehelper.component.UserDisplayPagerAdapter;
import com.example.manishchawley.computehelper.model.Commuter;
import com.example.manishchawley.computehelper.util.Constants;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class UserDisplayActivity extends AppCompatActivity {

    private static final String TAG = UserDisplayActivity.class.getName();

    private ArrayList<Commuter> commuterList = new ArrayList<Commuter>();
    private ViewPager viewPager;
    private UserDisplayPagerAdapter userDisplayPagerAdapter;
    private String url;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_display);

        viewPager = (ViewPager) findViewById(R.id.activity_user_display_pager);
        userDisplayPagerAdapter = new UserDisplayPagerAdapter(getSupportFragmentManager(), commuterList);
        viewPager.setAdapter(userDisplayPagerAdapter);

        updateUserResults();
    }

    private String getQueryUrl(){
        String queryUrl;
        // TODO: 28/11/16
        queryUrl = "http://api.androidhive.info/json/movies.json";
        return queryUrl;
    }

    private void updateUserResults() {
        url = getQueryUrl();
        Log.i(TAG, "Query URL: " + url);
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(url,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Log.i(TAG, response.toString());

                        for(int i=0; i<response.length(); i++){
                            try {
                                JSONObject obj = response.getJSONObject(i);
                                Commuter commuter = new Commuter();
                                commuter.setCommuterName(obj.getString(Constants.RESULT_COMMUTER_NAME_KEY));
                                commuter.setImageURL(obj.getString(Constants.RESULT_COMMUTER_IMAGE_URL_KEY));

                                //commuter.setAge(obj.getInt(Constants.RESULT_COMMUTER_AGE_KEY));
                                //commuter.setGender(obj.getInt(Constants.RESULT_COMMUTER_GENDER_KEY));
                                //commuter.setDistancefromuser((float) obj.getDouble(Constants.RESULT_COMMUTER_DISTANCE_FROM_USER_KEY));
                                //commuter.setCurrenttrip(obj.);

                                commuterList.add(commuter);
                            } catch (JSONException e) {
                                Log.e(TAG, e.getMessage());
                            }
                        }
                        userDisplayPagerAdapter.notifyDataSetChanged();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e(TAG, error.getMessage());
                    }
                });
        AppController.getInstance().addToRequestQueue(jsonArrayRequest);
    }
}
