package com.example.manishchawley.commutehelper.provider;

import com.example.manishchawley.commutehelper.model.Commuter;
import com.example.manishchawley.commutehelper.util.Constants;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

/**
 * Created by manishchawley on 07/08/17.
 */

public class CommuterDataProvider {
    public static final int EXPLORE = 1;
    public static final int INVITE = 2;
    public static final int MATCH = 3;

    public static void getCommuter(String commuterId, final CommuterDataListener listener){
        DatabaseReference database = FirebaseDatabase.getInstance().getReference(Constants.COMMUTER_DATABASE_KEY);
        database.child(commuterId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    ArrayList<Commuter> commuters = new ArrayList<Commuter>();
                    commuters.add(dataSnapshot.getValue(Commuter.class));
                    listener.onSuccess(commuters);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                listener.onError(databaseError);
            }
        });
    }


    public static interface CommuterDataListener {
        void onSuccess(ArrayList<Commuter> commuterList);
        void onError(DatabaseError databaseError);
    }
}
