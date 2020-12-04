package com.fdvmlab.forfoodiesbyfoodies.models;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.fdvmlab.foodbyfoodiesforfoodies.R;

import java.util.List;

public class FoodPlaceAdapter extends RecyclerView.Adapter<FoodPlaceAdapter.ViewHolder> {

    private List<FoodPlace> foodPlaceList;

    // constructor
    public FoodPlaceAdapter(List<FoodPlace> foodPlaceList) {
        this.foodPlaceList = foodPlaceList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        // Inflate the items cards and return it
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.food_place_recycleview_item_cardview, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.ivFoodPlacePhoto.setImageURI(foodPlaceList.get(position).getPhoto());
        holder.tvFoodPlaceName.setText(foodPlaceList.get(position).getName());
        holder.tvFoodPlaceCuisine.setText(foodPlaceList.get(position).getName());
        holder.rFoodPlaceRating.setNumStars((int) foodPlaceList.get(position).getRating().getNumberOfStarts());
    }

    @Override
    public int getItemCount() {
        return foodPlaceList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView ivFoodPlacePhoto;
        private TextView tvFoodPlaceName, tvReviewNumber, tvFoodPlaceCuisine;
        private RatingBar rFoodPlaceRating;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            ivFoodPlacePhoto = itemView.findViewById(R.id.ivCardViewPhoto);
            tvFoodPlaceName = itemView.findViewById(R.id.tvCardViewRestaurantName);
            rFoodPlaceRating = itemView.findViewById(R.id.ratingBarCardView);
            tvReviewNumber = itemView.findViewById(R.id.tvCardViewReviews);
            tvFoodPlaceCuisine = itemView.findViewById(R.id.tvCardViewCuisine);
        }
    }
}
