package com.example.manishchawley.computehelper.component;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.bumptech.glide.Glide;
import com.example.manishchawley.computehelper.R;
import com.example.manishchawley.computehelper.app.AppController;
import com.example.manishchawley.computehelper.model.Trip;
import com.example.manishchawley.computehelper.render.ChatListActivity;
import com.example.manishchawley.computehelper.render.TripDetailsActivity;
import com.example.manishchawley.computehelper.util.Constants;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by manishchawley on 20/07/17.
 */

public class TripCardAdapter extends RecyclerView.Adapter<TripCardAdapter.TripCardViewHolder> {

    private final String TAG = TripCardAdapter.class.getName();

    private Context context;
    private List<Trip> tripList;
    ImageLoader imageLoader = AppController.getInstance().getImageLoader();

    public TripCardAdapter(Context context, List<Trip> tripList) {
        this.context = context;
        this.tripList = tripList;
    }

    @Override
    public TripCardViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View tripView = inflater.inflate(R.layout.adapter_trip_card, parent, false);
        Log.i(TAG, "View Create started for position: " + ((TextView)tripView.findViewById(R.id.adapter_trip_card_destination)).getText());
        return new TripCardViewHolder(tripView);
    }

    @Override
    public void onBindViewHolder(TripCardViewHolder holder, final int position) {
        Trip trip = tripList.get(position);

        Log.v(TAG, String.format("View bind started for position: %s - %s, W: %s H: %s", position, tripList.get(position).getDestinationPlace(), holder.itemView.getWidth(), holder.tripImage.getHeight()));

//        holder.setIsRecyclable(true);

        holder.destination.setText(trip.getDestinationPlace());
        //holder.start.setText(trip.getStartPlace());
        //holder.currentCount.setText(trip.getNoOfCommuters());
        holder.startDate.setText(DateUtils.getRelativeDateTimeString(context, trip.getTripStart(), DateUtils.DAY_IN_MILLIS, DateUtils.WEEK_IN_MILLIS, 0));
        //holder.endDate.setText(trip.getTripEnd().toString());
        //holder.params.setText(trip.getDescription());
        //Glide.with(context).load(trip.getImageURL()).into(holder.tripImage);
//        Log.e(TAG, "Loading image at position: " + position + " with image: " + trip.getImageURL());
        Picasso.with(context).load(trip.getImageURL()).fit().into(holder.tripImage);
        holder.tripId = trip.getTripId();
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, TripDetailsActivity.class);
                intent.putExtra(Constants.TRIP_DETAILS_TRIP_KEY, tripList.get(position));
                v.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return tripList.size();
    }

    @Override
    public void onViewRecycled(TripCardViewHolder holder) {
        super.onViewRecycled(holder);
        holder.tripImage.setVisibility(View.VISIBLE);
        Log.v(TAG, String.format("onViewRecycled: %s - %s, %s", holder.getAdapterPosition(), holder.destination.getText(), holder.tripImage.getHeight()));
    }

    @Override
    public void onViewAttachedToWindow(TripCardViewHolder holder) {
        super.onViewAttachedToWindow(holder);
        holder.tripImage.setVisibility(View.VISIBLE);
        Log.v(TAG, String.format("onViewAttachedToWindow: %s - %s, %s", holder.getAdapterPosition(), holder.destination.getText(), holder.tripImage.getHeight()));
    }

    @Override
    public void onViewDetachedFromWindow(TripCardViewHolder holder) {
        super.onViewDetachedFromWindow(holder);
        holder.tripImage.setVisibility(View.INVISIBLE);
        Log.v(TAG, String.format("onViewDetachedFromWindow: %s - %s, %s", holder.getAdapterPosition(), holder.destination.getText(), holder.tripImage.getHeight()));
    }


    public class TripCardViewHolder extends RecyclerView.ViewHolder{
        public TextView destination, start, startDate, endDate, currentCount, params;
        public ImageView tripImage;
        public ImageView chat, editDetails;
        public String tripId;

        public TripCardViewHolder(View itemView) {
            super(itemView);
            // TODO: 20/07/17 Define xml and assign views

            destination = (TextView) itemView.findViewById(R.id.adapter_trip_card_destination);
            startDate = (TextView) itemView.findViewById(R.id.adapter_trip_card_startdate);
            tripImage = (ImageView) itemView.findViewById(R.id.adapter_trip_card_tripimage);
            chat = (ImageView) itemView.findViewById(R.id.adapter_trip_card_chat);
            editDetails = (ImageView) itemView.findViewById(R.id.adapter_trip_card_editdetails);
            Log.v(TAG, String.format("Image view height: %s", tripImage.getHeight()));

            chat.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, ChatListActivity.class);
                    intent.putExtra(Constants.TRIP_DETAILS_TRIP_KEY, tripId);
                    v.getContext().startActivity(intent);
                }
            });

//            editDetails.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    Intent intent = new Intent(context, TripDetailsActivity.class);
//                    intent.putExtra(Constants.TRIP_DETAILS_TRIP_KEY, tripId);
//                }
//            });


//            itemView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    Intent intent = new Intent(context, TripDetailsActivity.class);
//                    intent.putExtra(Constants.TRIP_DETAILS_TRIP_KEY, tripId);
//                    v.getContext().startActivity(intent);
//                }
//            });
        }
    }

}
