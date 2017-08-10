package com.example.manishchawley.commutehelper.provider;

import com.example.manishchawley.commutehelper.model.Trip;
import com.example.manishchawley.commutehelper.util.Constants;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by manishchawley on 04/08/17.
 */

public class TripDataProvider {

    public static final String TAG = TripDataProvider.class.getName();

    public static final int NO_FILTER = 0;

    public static void getTripsOfCommuter(String commuterId, TripDataListener listener) throws NullCommuterIdException {
        getTripsOfCommuter(commuterId, listener, NO_FILTER);
    }

    public static void getTripsOfCommuter(String commuterId, final TripDataListener listener, int filter) throws NullCommuterIdException {
        if(commuterId==null) throw new NullCommuterIdException();
        // TODO: 04/08/17 add switch case based on filter

        DatabaseReference database = FirebaseDatabase.getInstance().getReference(Constants.COMMUTER_WITH_TRIP_DATABASE_KEY);
        database.child(commuterId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                ArrayList<Trip> trips = new ArrayList<Trip>();
//                Log.i(TAG, dataSnapshot.toString());
                if(dataSnapshot.exists()){
                    if(dataSnapshot.hasChild(Constants.COMMUTER_WITH_TRIP_TRIPLIST_KEY)) {
                        Iterator<DataSnapshot> iterator = dataSnapshot.child(Constants.COMMUTER_WITH_TRIP_TRIPLIST_KEY).getChildren().iterator();
                        while (iterator.hasNext()) {
                            trips.add(iterator.next().getValue(Trip.class));
                        }
                    }
                }
                listener.onSuccess(trips);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                listener.onError(databaseError);
            }
        });
    }

    public static class NullCommuterIdException extends Throwable{
        @Override
        public String getMessage() {
            return "Trip Id can't be Null";
        }
    }

    public interface TripDataListener {
        void onSuccess(ArrayList<Trip> trips);
        void onError(DatabaseError databaseError);
    }
}
