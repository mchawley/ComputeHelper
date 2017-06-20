package com.example.manishchawley.computehelper.component;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.example.manishchawley.computehelper.R;
import com.example.manishchawley.computehelper.app.AppController;
import com.example.manishchawley.computehelper.model.Commuter;

import java.util.List;

/**
 * Created by manishchawley on 25/11/16.
 */

public class UserDisplayAdapter extends BaseAdapter {

    private Activity activity;
    private LayoutInflater layoutInflater;
    private List<Commuter> commuterList;
    ImageLoader imageLoader = AppController.getInstance().getImageLoader();

    public UserDisplayAdapter(Activity activity, List<Commuter> commuterList){
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

        if (layoutInflater==null)
            layoutInflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (convertView==null)
            convertView = layoutInflater.inflate(R.layout.adapter_user_display, null);
        if (imageLoader==null)
            imageLoader = AppController.getInstance().getImageLoader();

        NetworkImageView userimage = (NetworkImageView) convertView.findViewById(R.id.adapter_user_display_network_image_view);
        TextView username = (TextView) convertView.findViewById(R.id.adapter_user_display_textview_name);
        TextView userage = (TextView) convertView.findViewById(R.id.adapter_user_display_textview_age);
        TextView usergender = (TextView) convertView.findViewById(R.id.adapter_user_display_textview_gender);
        TextView userdistancefromuser = (TextView) convertView.findViewById(R.id.adapter_user_display_textview_distance_from_user);
        TextView usertripdetails = (TextView) convertView.findViewById(R.id.adapter_user_display_textview_trip_details);

        Commuter commuter = commuterList.get(position);

//        userimage.setImageUrl(commuter.getImageURL(), imageLoader);
        username.setText(commuter.getCommuterName());
        userage.setText(commuter.getCommuterAge());
        usergender.setText(commuter.getCommuterGender());
        //userdistancefromuser.setText(commuter.getStringDistancefromuser());
        usertripdetails.setText(commuter.getCommuterDescription());

        return convertView;
    }
}
