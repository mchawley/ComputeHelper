package com.example.manishchawley.commutehelper.component;

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

import com.example.manishchawley.commutehelper.R;
import com.example.manishchawley.commutehelper.model.Trip;
import com.example.manishchawley.commutehelper.render.ChatListActivity;
import com.example.manishchawley.commutehelper.render.TripDetailsActivity;
import com.example.manishchawley.commutehelper.util.Constants;
import com.example.manishchawley.commutehelper.util.PlaceImageView;
import com.squareup.picasso.Picasso;

import java.util.List;

import jp.wasabeef.picasso.transformations.CropCircleTransformation;

/**
 * Created by manishchawley on 20/07/17.
 */

public class TripRecyclerAdapter extends RecyclerView.Adapter<TripRecyclerAdapter.TripCardViewHolder> {

    private final String TAG = TripRecyclerAdapter.class.getName();

    private Context context;
    private List<Trip> tripList;

    public TripRecyclerAdapter(Context context, List<Trip> tripList) {
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
        //holder.origin.setText(trip.getStartPlace());
        //holder.currentCount.setText(trip.getNoOfCommuters());
        holder.startDate.setText(DateUtils.getRelativeDateTimeString(context, trip.getTripStart(), DateUtils.DAY_IN_MILLIS, DateUtils.WEEK_IN_MILLIS, 0));
        //holder.endDate.setText(trip.getTripEnd().toString());
        //holder.params.setText(trip.getDescription());
        //Glide.with(context).load(trip.getImageURL()).into(holder.tripImage);
//        Log.e(TAG, "Loading image at position: " + position + " with image: " + trip.getImageURL());
//        Picasso.with(context).load(trip.getImageURL()).fit().into(holder.tripImage);
        if(trip.getOriginPlace()!=null)
            holder.origin.setText(trip.getOriginPlace());

        holder.tripImage.setPlaceId(trip.getDestinationPlaceID()).refreshImage();

        if(trip.getTripOwner()!=null)
            Picasso.with(context).load(trip.getTripOwner().getImageURL())
                    .fit().transform(new CropCircleTransformation()).into(holder.ownerimage);
        else
            Picasso.with(context).load(R.drawable.com_facebook_profile_picture_blank_portrait)
                    .fit().transform(new CropCircleTransformation()).into(holder.ownerimage);

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

    public class TripCardViewHolder extends RecyclerView.ViewHolder{
        public TextView destination, origin, startDate, endDate, currentCount, params;
        public PlaceImageView tripImage;
        public ImageView chat, editDetails, ownerimage;
        public String tripId;

        public TripCardViewHolder(View itemView) {
            super(itemView);
            // TODO: 20/07/17 Define xml and assign views

            destination = (TextView) itemView.findViewById(R.id.adapter_trip_card_destination);
            startDate = (TextView) itemView.findViewById(R.id.adapter_trip_card_startdate);
            tripImage = (PlaceImageView) itemView.findViewById(R.id.adapter_trip_card_tripimage);
            chat = (ImageView) itemView.findViewById(R.id.adapter_trip_card_chat);
            editDetails = (ImageView) itemView.findViewById(R.id.adapter_trip_card_editdetails);
            ownerimage = (ImageView) itemView.findViewById(R.id.adapter_trip_card_ownerimage);
            origin = (TextView) itemView.findViewById(R.id.adapter_trip_card_start);
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
