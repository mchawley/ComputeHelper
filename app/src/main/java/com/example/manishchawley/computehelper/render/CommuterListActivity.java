package com.example.manishchawley.computehelper.render;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.example.manishchawley.computehelper.R;
import com.example.manishchawley.computehelper.app.AppController;
import com.example.manishchawley.computehelper.component.CommuterListAdapter;
import com.example.manishchawley.computehelper.model.CommuterWithTrips;
import com.example.manishchawley.computehelper.util.JSONUtils;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

public class CommuterListActivity extends AppCompatActivity {

    private final String TAG = CommuterListActivity.class.getName();

    private ImageView noCommuter;
    private ListView commuterListView;
    private List<CommuterWithTrips> commuterList = new ArrayList<CommuterWithTrips>();
    private static final String url = "https://api.androidhive.info/json/movies.json";
    private ProgressDialog pDialog;
    private CommuterListAdapter commuterListAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_commuter_list);

        noCommuter = (ImageView) findViewById(R.id.activity_commuter_list_nocommuter_imageview);
        commuterListView = (ListView) findViewById(R.id.activity_commuter_list_listview);

        commuterListAdapter = new CommuterListAdapter(this, commuterList);

        //if(getIntent().getExtras()!=null){
            showCommuterList();
            // TODO: 14/07/17 get details from server
        //}else {
        //    showNoCommuterError(true);
        //}

    }

    private void showCommuterList() {
        showNoCommuterError(false);

        commuterListView.setAdapter(commuterListAdapter);

        commuterListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                CommuterWithTrips commuter = commuterList.get(position);
                // TODO: 14/07/17 Start User detail activity
            }
        });
        updateListView(url);
    }

    private void updateListView(String url) {
        pDialog = new ProgressDialog(this);
        pDialog.setMessage("Loading...");
        pDialog.show();
        AppController.getInstance().addToRequestQueue(getJSONRequest(url));
    }

    private JsonArrayRequest getJSONRequest(String url) {
        JsonArrayRequest request = new JsonArrayRequest(url,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Log.e(TAG, "Response received");
                        hidePDialog();
                        // TODO: 11/07/17 parsing jason
                        for(int i=0; i<response.length(); i++){
                            try{
                                commuterList.add(JSONUtils.getCommuterFromJson(response.getJSONObject(i)));

                            } catch (JSONException e) {
                                Log.e(TAG, e.getMessage());
                                e.printStackTrace();
                            }
                        }
                        commuterListAdapter.notifyDataSetChanged();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e(TAG, error.getMessage());
                        showNoCommuterError(true);
                    }
                });
        return request;
    }

    private void hidePDialog() {
        if(pDialog!=null) {
            pDialog.dismiss();
            pDialog = null;
        }
    }

    private void showNoCommuterError(boolean toggle) {
        if(toggle) {
            noCommuter.setImageResource(R.drawable.com_facebook_profile_picture_blank_portrait);
            noCommuter.setVisibility(View.VISIBLE);
            noCommuter.setEnabled(true);
            commuterListView.setEnabled(false);
            commuterListView.setVisibility(View.INVISIBLE);
        }else{
            noCommuter.setVisibility(View.INVISIBLE);
            noCommuter.setEnabled(false);
            commuterListView.setEnabled(true);
            commuterListView.setVisibility(View.VISIBLE);
        }
    }
}
