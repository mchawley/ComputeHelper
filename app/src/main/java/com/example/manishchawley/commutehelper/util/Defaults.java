package com.example.manishchawley.commutehelper.util;

import com.example.manishchawley.commutehelper.model.Commuter;
import com.example.manishchawley.commutehelper.model.Trip;
import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by manishchawley on 16/07/17.
 */

public class Defaults {


    public static Trip getDefaultTrip() {
        Trip trip = new Trip();

        ArrayList<Commuter> commuters = new ArrayList<>();
        //for(int i=0; i<commuters.length; i++)
        //    commuters[i] = getDefaultCommuter();


        //trip.setTripId("304");
        trip.setDestinationLocation(mLatLng.convert(getDefaultLocation()));
        trip.setDestinationPlace("Destination Place");
        trip.setOriginLocation(mLatLng.convert(getDefaultLocation()));
        trip.setOriginPlace("Start Place");
        trip.setModeOfTravel(1);
        trip.setTripStart(Calendar.getInstance().getTimeInMillis());
        trip.setTripEnd(Calendar.getInstance().getTimeInMillis());
        trip.setMaxCommuterAllowed(3);
        trip.setTripStatus(1);
        trip.setTypeOfTrip(1);
        trip.setTripCommuters(commuters);

        return trip;
    }

    public static Commuter getDefaultCommuter() {
        Commuter commuter = new Commuter();

        // TODO: 16/07/17 Make default commuter

        return commuter;
    }

    public static LatLng getDefaultLocation() {
        return new LatLng(23.53, 97.23);
    }
}
