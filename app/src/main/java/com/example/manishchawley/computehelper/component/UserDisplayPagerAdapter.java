package com.example.manishchawley.computehelper.component;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.view.View;

import com.example.manishchawley.computehelper.model.Commuter;

import java.util.ArrayList;

/**
 * Created by manishchawley on 29/11/16.
 */

public class UserDisplayPagerAdapter extends FragmentStatePagerAdapter {

    private static final String TAG = UserDisplayPagerAdapter.class.getName();
    private ArrayList<Commuter> commuterArrayList;

    public UserDisplayPagerAdapter(FragmentManager fm, ArrayList<Commuter> commuterArrayList) {
        super(fm);
        this.commuterArrayList = commuterArrayList;
    }


    @Override
    public Fragment getItem(int position) {
        return UserDisplayFragment.newInstance(commuterArrayList.get(position));
    }

    @Override
    public int getCount() {
        return commuterArrayList.size();
    }


}
