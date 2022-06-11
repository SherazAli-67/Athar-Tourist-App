package com.myapp.asar_.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.myapp.asar_.R;
import com.myapp.asar_.database.entities.PlaceImages;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PlacesImagesAdapter extends RecyclerView.Adapter<PlacesImagesAdapter.PlaceImageViewHolder> {

    List<PlaceImages> imageList;
    Activity activity;

    public PlacesImagesAdapter(List<PlaceImages> imageList, Activity activity) {
        this.imageList = imageList;
        this.activity = activity;
    }


    @NonNull
    @Override
    public PlaceImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.place_images_layout, parent, false);
        return new PlaceImageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PlaceImageViewHolder holder, int position) {
        PlaceImages image = imageList.get(position);

        Glide.with(activity).load(image.getUrl()).placeholder(R.drawable.loading_spinner).into(holder.placeImage);
    }

    @Override
    public int getItemCount() {
        return imageList.size();
    }

    public class PlaceImageViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.placeImage)
        ImageView placeImage;

        public PlaceImageViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
