package com.example.manishchawley.computehelper.component;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.example.manishchawley.computehelper.R;
import com.example.manishchawley.computehelper.app.AppController;
import com.example.manishchawley.computehelper.model.CommuterWithTrips;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by manishchawley on 14/07/17.
 */

public class CommuterListAdapter extends BaseAdapter {

    private static final String TAG = CommuterListAdapter.class.getName();
    private Activity activity;
    private LayoutInflater layoutInflater;
    private List<CommuterWithTrips> commuterList;

    public CommuterListAdapter(Activity activity, List<CommuterWithTrips> commuterList) {
        this.activity = activity;
        this.commuterList = commuterList;
    }

    @Override
    public int getCount() {
        return commuterList.size();
    }

    @Override
    public Object getItem(int position) {
        return commuterList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(layoutInflater==null)
            layoutInflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if(convertView==null)
            convertView = layoutInflater.inflate(R.layout.adapter_commuter_list_row, null);

        CommuterWithTrips commuter = commuterList.get(position);

        ImageView image = (ImageView) convertView.findViewById(R.id.adapter_commuter_list_row_image_imageview);
        TextView name = (TextView) convertView.findViewById(R.id.adapter_commuter_list_row_name_textview);
        TextView destination = (TextView) convertView.findViewById(R.id.adapter_commuter_list_row_destination_textview);
        TextView distance = (TextView) convertView.findViewById(R.id.adapter_commuter_list_row_distance_textview);

//        Log.e(TAG, String.format("Loading image: %s", commuter.getImageURL()));

        Picasso.with(convertView.getContext()).load(commuter.getImageURL()).fit().placeholder(R.drawable.com_facebook_profile_picture_blank_portrait).into(image);
        name.setText(commuter.getCommuterName()+ ", " + commuter.getCommuterAge());
        destination.setText(commuter.getTripList().get(0).getDestinationPlace());
        distance.setText(String.valueOf(commuter.getCommuterDistance()));

        return convertView;
    }
}
