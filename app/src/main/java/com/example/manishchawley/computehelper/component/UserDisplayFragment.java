package com.example.manishchawley.computehelper.component;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.example.manishchawley.computehelper.R;
import com.example.manishchawley.computehelper.app.AppController;
import com.example.manishchawley.computehelper.model.Commuter;
import com.example.manishchawley.computehelper.util.Constants;

/**
 * A simple {@link Fragment} subclass.
 */
public class UserDisplayFragment extends Fragment {

    private static final String TAG = UserDisplayFragment.class.getName();
    private Commuter commuter;
    ImageLoader imageLoader = AppController.getInstance().getImageLoader();

    public UserDisplayFragment() {
        // Required empty public constructor
    }

    public static UserDisplayFragment newInstance(Commuter commuter){
        Log.i(TAG, "New Fragment Created for commuter: " + commuter.getCommuterName());
        UserDisplayFragment userDisplayFragment = new UserDisplayFragment();
        Bundle args = new Bundle();
        args.putParcelable(Constants.FRAGMENT_COMMUTER_KEY, commuter);
        userDisplayFragment.setArguments(args);
        return userDisplayFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_user_display, container, false);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i(TAG, "Has key " + Constants.FRAGMENT_COMMUTER_KEY +"? " + getArguments().containsKey(Constants.FRAGMENT_COMMUTER_KEY));
        if(getArguments().containsKey(Constants.FRAGMENT_COMMUTER_KEY))
            commuter = getArguments().getParcelable(Constants.FRAGMENT_COMMUTER_KEY);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        NetworkImageView userimage = (NetworkImageView) getView().findViewById(R.id.fragment_user_display_network_image_view);
        TextView username = (TextView) getView().findViewById(R.id.fragment_user_display_textview_name);
        TextView userage = (TextView) getView().findViewById(R.id.fragment_user_display_textview_age);
        TextView usergender = (TextView) getView().findViewById(R.id.fragment_user_display_textview_gender);
        TextView userdistancefromuser = (TextView) getView().findViewById(R.id.fragment_user_display_textview_distance_from_user);
        TextView usertripdetails = (TextView) getView().findViewById(R.id.fragment_user_display_textview_trip_details);

        if(imageLoader==null)
            imageLoader = AppController.getInstance().getImageLoader();

//        userimage.setImageUrl(commuter.getImageURL(), imageLoader);
        username.setText(commuter.getCommuterName());
        userage.setText(String.valueOf(commuter.getCommuterAge()));
        usergender.setText(String.valueOf(commuter.getCommuterGender()));
        //userdistancefromuser.setText(String.valueOf(commuter.getStringDistancefromuser()));
        //usertripdetails.setText(commuter.getCurrenttrip().toString());
    }
}
