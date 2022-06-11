package com.myapp.asar_.adapter;

import android.app.Activity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.myapp.asar_.R;
import com.myapp.asar_.database.entities.Places;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PlacesAdapter extends RecyclerView.Adapter<PlacesAdapter.PlacesViewHolder> implements Filterable{

    List<Places> placesList;
    List<Places> placesListFull;

    Activity activity;
    List<Integer> images = new ArrayList<>();
    OnItemClickListener mItemClickListener;
    View.OnClickListener onAddToGoList;

    public PlacesAdapter(List<Places> placesList, Activity activity, View.OnClickListener onAddToGoList) {
        this.placesList = placesList;
        placesListFull = new ArrayList<>(placesList);

        this.activity = activity;
        this.onAddToGoList = onAddToGoList;


        images.add(R.drawable.boulevard_riyadh);
        images.add(R.drawable.almask);
        images.add(R.drawable.almask);
        images.add(R.drawable.king_fahad_lib);
        images.add(R.drawable.riyadh_zoo);
        images.add(R.drawable.national_musem);


    }

    @NonNull
    @Override
    public PlacesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.tourist_places_layout, parent, false);
        return new PlacesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PlacesViewHolder holder, int position) {
        Places place = placesList.get(position);

        Log.d("TAG", "onBindViewHolder: "+place.isAddedToGoList());
        if(place.isAddedToGoList()){

            holder.addToGoList.setVisibility(View.GONE);
            holder.alreadyAdded.setVisibility(View.VISIBLE);
        }
//        holder.touristImage.setImageBitmap(BitmapFactory.decodeByteArray(place.get(),0,place.getProfileImage().length));

        holder.touristImage.setImageResource(images.get(position));
        holder.touristTitle.setText(place.getTitle());

        holder.addToGoList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.setTag(place);
                onAddToGoList.onClick(v);
            }
        });
    }

    @Override
    public int getItemCount() {
        return placesList.size();
    }

    public void setmItemClickListener(OnItemClickListener mItemClickListener) {
        this.mItemClickListener = mItemClickListener;
    }

    @Override
    public Filter getFilter() {
        return exampleFilter;
    }


    public interface OnItemClickListener {
        void onItemClick(View v, int position);
    }

    public class PlacesViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.img_tourist)
        ImageView touristImage;

        @BindView(R.id.tv_tourist_title)
        TextView touristTitle;

        @BindView(R.id.addToGoList)
        CardView addToGoList;

        @BindView(R.id.addedToGoList)
        CardView alreadyAdded;


        public PlacesViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (mItemClickListener != null) {
                mItemClickListener.onItemClick(v, getAdapterPosition());
            }
        }
    }

    private Filter exampleFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<Places> filteredList = new ArrayList<>();

            if (constraint == null || constraint.length() == 0) {
                filteredList.addAll(placesListFull);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();

                for (Places item : placesListFull) {
                    if (item.getTitle().toLowerCase().contains(filterPattern)) {
                        filteredList.add(item);
                    }
                }
            }

            FilterResults results = new FilterResults();
            results.values = filteredList;

            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            placesList.clear();
            placesList.addAll((List) results.values);
            notifyDataSetChanged();
        }
    };


}
