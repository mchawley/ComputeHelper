package com.example.manishchawley.commutehelper.render;

import android.app.ProgressDialog;
import android.graphics.Color;
import android.support.annotation.ColorInt;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.example.manishchawley.commutehelper.R;
import com.example.manishchawley.commutehelper.app.AppController;
import com.example.manishchawley.commutehelper.component.CommuterRecyclerAdapter;
import com.example.manishchawley.commutehelper.model.Commuter;
import com.example.manishchawley.commutehelper.model.CommuterWithTrips;
import com.example.manishchawley.commutehelper.provider.CommuterDataProvider;
import com.example.manishchawley.commutehelper.util.Constants;
import com.example.manishchawley.commutehelper.util.JSONUtils;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

public class CommuterListActivity extends AppCompatActivity {

    private final String TAG = CommuterListActivity.class.getName();

    private ImageView noCommuter;
    private TextView explore, invite, match;
    private ListView commuterListView;
    private RecyclerView recyclerView;
    private ArrayList<CommuterWithTrips> commuterList = new ArrayList<CommuterWithTrips>();
    private static final String url = "https://api.androidhive.info/json/movies.json";
    private ProgressDialog pDialog;
//    private CommuterListAdapter commuterListAdapter;
    private CommuterRecyclerAdapter commuterRecyclerAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_commuter_list);

        noCommuter = (ImageView) findViewById(R.id.activity_commuter_list_nocommuter_imageview);
        explore = (TextView) findViewById(R.id.activity_commuter_list_explore);
        invite = (TextView) findViewById(R.id.activity_commuter_list_invite);
        match = (TextView) findViewById(R.id.activity_commuter_list_match);

//        commuterListView = (ListView) findViewById(R.id.activity_commuter_list_listview);
        recyclerView = (RecyclerView) findViewById(R.id.activity_commuter_list_recyclerview);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);

        addSwipeAction();
        addFilterAction();

//        commuterListAdapter = new CommuterListAdapter(this, commuterList);
        commuterRecyclerAdapter = new CommuterRecyclerAdapter(this, commuterList);

        if(getIntent().getExtras()!=null){
            if(getIntent().hasExtra(Constants.COMMUTER_LIST_FILTER))
                fetchData(getIntent().getIntExtra(Constants.COMMUTER_LIST_FILTER, CommuterDataProvider.EXPLORE));
            // TODO: 14/07/17 get details from server
        }else {
            showNoCommuterError(true);
        }

    }

    private void addFilterAction() {
        explore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(TAG, "Explore clicked");
                fetchData(CommuterDataProvider.EXPLORE);
            }
        });
        invite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(TAG, "Invite clicked");
                fetchData(CommuterDataProvider.INVITE);
            }
        });
        match.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(TAG, "Match clicked");
                fetchData(CommuterDataProvider.MATCH);
            }
        });
    }

    private void toggleFilters(int filterType) {
        explore.setBackgroundColor(Color.BLUE);
        match.setBackgroundColor(Color.BLUE);
        invite.setBackgroundColor(Color.BLUE);
        switch (filterType){
            case CommuterDataProvider.EXPLORE:
                explore.setBackgroundColor(Color.GRAY);
                break;
            case CommuterDataProvider.INVITE:
                invite.setBackgroundColor(Color.GRAY);
                break;
            case CommuterDataProvider.MATCH:
                match.setBackgroundColor(Color.GRAY);
                break;
        }
    }

    private void fetchData(int filterType) {
        // TODO: 07/08/17 fetch data based on the filter
        toggleFilters(filterType);
        showCommuterList();
    }

    private void addSwipeAction() {
        ItemTouchHelper.SimpleCallback simpleCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT|ItemTouchHelper.RIGHT)
        {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                Log.i(TAG, String.format("Swipped commuter: %s, direction: %s", viewHolder.getAdapterPosition(), direction));
                commuterList.remove(viewHolder.getAdapterPosition());
                commuterRecyclerAdapter.notifyItemRemoved(viewHolder.getAdapterPosition());
            }
        };

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleCallback);
        itemTouchHelper.attachToRecyclerView(recyclerView);
    }

    private void showCommuterList() {
        showNoCommuterError(false);

//        commuterListView.setAdapter(commuterListAdapter);
        recyclerView.setAdapter(commuterRecyclerAdapter);

//        commuterListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                CommuterWithTrips commuter = commuterList.get(position);
//                // TODO: 14/07/17 Start User detail activity
//            }
//        });
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
                        Log.i(TAG, "Response received");
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
//                        commuterListAdapter.notifyDataSetChanged();
                        commuterRecyclerAdapter.notifyDataSetChanged();
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
//            commuterListView.setEnabled(false);
//            commuterListView.setVisibility(View.INVISIBLE);
        }else{
            noCommuter.setVisibility(View.INVISIBLE);
            noCommuter.setEnabled(false);
//            commuterListView.setEnabled(true);
//            commuterListView.setVisibility(View.VISIBLE);
        }
    }
}
