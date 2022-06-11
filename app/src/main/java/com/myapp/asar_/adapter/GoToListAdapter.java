package com.myapp.asar_.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.gms.maps.model.LatLng;
import com.myapp.asar_.R;
import com.myapp.asar_.database.entities.Places;
import com.myapp.asar_.utils.CustomMethods;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class GoToListAdapter extends RecyclerView.Adapter<GoToListAdapter.GoToListViewHolder> {

    List<Places> goToList;
    Context activity;

    List<Integer> images = new ArrayList<>();
    public GoToListAdapter(List<Places> goToList, Context activity) {
        this.goToList = goToList;
        this.activity = activity;


        images.add(R.drawable.boulevard_riyadh);
        images.add(R.drawable.almask);
        images.add(R.drawable.almask);
        images.add(R.drawable.king_fahad_lib);
        images.add(R.drawable.riyadh_zoo);
        images.add(R.drawable.national_musem);
    }

    @NonNull
    @Override
    public GoToListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.gotolist_layout, parent, false);
        return new GoToListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GoToListViewHolder holder, int position) {
        Places places = goToList.get(position);
        String loc = CustomMethods.getLocationFromLatLng(activity, new LatLng(places.getLatitude(), places.getLongitude()));
        holder.title.setText(places.getTitle());
        holder.location.setText(loc);
        holder.description.setText(places.getDescription());
        holder.number.setText(places.getContactNum());

        Glide.with(activity).load(places.getImgURL()).placeholder(R.drawable.loading_spinner).into(holder.image);

    }

    @Override
    public int getItemCount() {
        return goToList.size();
    }

    public class GoToListViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.place_image)
        ImageView image;

        @BindView(R.id.place_title)
        TextView title;

        @BindView(R.id.place_loc)
        TextView location;

        @BindView(R.id.place_description)
        TextView description;

        @BindView(R.id.place_number)
        TextView number;

        public GoToListViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
