package com.example.manishchawley.commutehelper.component;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.example.manishchawley.commutehelper.R;
import com.example.manishchawley.commutehelper.app.AppController;
import com.example.manishchawley.commutehelper.model.Trip;

import java.util.Calendar;
import java.util.List;

/**
 * Created by manishchawley on 11/07/17.
 */

public class TripListAdapter extends BaseAdapter {

    private final String TAG = TripListAdapter.class.getName();

    private Activity activity;
    private LayoutInflater inflater;
    private List<Trip> tripList;
    ImageLoader imageLoader = AppController.getInstance().getImageLoader();

    public TripListAdapter(Activity activity, List<Trip> tripList) {
        this.activity = activity;
        this.tripList = tripList;
    }

    @Override
    public int getCount() {
        return tripList.size();
    }

    @Override
    public Object getItem(int position) {
        return tripList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        if(inflater==null)
            inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if(convertView==null)
            convertView = inflater.inflate(R.layout.adapter_trip_list_row, null);

        TextView destination = (TextView) convertView.findViewById(R.id.adapter_trip_list_row_destination_textview);
        TextView startdate = (TextView) convertView.findViewById(R.id.adapter_trip_list_row_startdate_textview);
        TextView noofpeople = (TextView) convertView.findViewById(R.id.adapter_trip_list_row_noofpeople_textview);

        Trip trip = tripList.get(position);

        destination.setText(trip.getDestinationPlace());
        Calendar c = Calendar.getInstance();
        c.setTimeInMillis(trip.getTripStart());
        startdate.setText(c.toString());
        noofpeople.setText(String.valueOf(trip.getNoOfCommuters()));
        
        //// TODO: 11/07/17 create various elements of the row
        
        return convertView;
    }
}
