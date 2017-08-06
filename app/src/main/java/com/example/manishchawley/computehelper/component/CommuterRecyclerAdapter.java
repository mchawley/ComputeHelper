package com.example.manishchawley.computehelper.component;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.manishchawley.computehelper.R;
import com.example.manishchawley.computehelper.model.Commuter;
import com.example.manishchawley.computehelper.model.CommuterWithTrips;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by manishchawley on 06/08/17.
 */

public class CommuterRecyclerAdapter extends RecyclerView.Adapter<CommuterRecyclerAdapter.CommuterCardViewHolder> {

    private static final String TAG = CommuterRecyclerAdapter.class.getName();

    private Context context;
    private ArrayList<CommuterWithTrips> commuterList;

    public CommuterRecyclerAdapter(Context context, ArrayList<CommuterWithTrips> commuterList) {
        this.context = context;
        this.commuterList = commuterList;
    }

    @Override
    public CommuterCardViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View commuterView = inflater.inflate(R.layout.adapter_commuter_list_row, parent, false);
        Log.i(TAG, "Commuter view starting");
        return new CommuterCardViewHolder(commuterView);
    }

    @Override
    public void onBindViewHolder(CommuterCardViewHolder holder, int position) {
        CommuterWithTrips commuter = commuterList.get(position);

        holder.name.setText(commuter.getCommuterName());
        if(commuter.getTripList()!=null)
            if(commuter.getTripList().size()>0)
                holder.destination.setText(commuter.getTripList().get(0).getDestinationPlace());
        holder.distance.setText("5 Kms");
        Picasso.with(context).load(commuter.getImageURL()).fit().into(holder.image);
    }

    @Override
    public int getItemCount() {
        return commuterList.size();
    }

    public class CommuterCardViewHolder extends RecyclerView.ViewHolder {
        public ImageView image;
        public TextView name, destination, distance;

        public CommuterCardViewHolder(View itemView) {
            super(itemView);
            image = (ImageView) itemView.findViewById(R.id.adapter_commuter_list_row_image_imageview);
            name = (TextView) itemView.findViewById(R.id.adapter_commuter_list_row_name_textview);
            destination = (TextView) itemView.findViewById(R.id.adapter_commuter_list_row_destination_textview);
            distance = (TextView) itemView.findViewById(R.id.adapter_commuter_list_row_distance_textview);

        }
    }
}
