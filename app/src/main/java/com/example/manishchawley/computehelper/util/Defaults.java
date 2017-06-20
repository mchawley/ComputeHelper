package com.example.manishchawley.computehelper.util;

import android.location.Location;

import com.example.manishchawley.computehelper.model.Commuter;
import com.example.manishchawley.computehelper.model.Trip;
import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

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
        trip.setDestinationLocation(getDefaultLocation());
        trip.setDestinationPlace("Destination Place");
        trip.setOriginLocation(getDefaultLocation());
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
