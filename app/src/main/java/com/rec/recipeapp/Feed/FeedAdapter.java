package com.rec.recipeapp.Feed;

import android.os.Bundle;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.imageview.ShapeableImageView;
import com.rec.recipeapp.Models.Recipe;
import com.rec.recipeapp.R;
import com.rec.recipeapp.SingleDishFragment;
import com.rec.recipeapp.utils.Utils;
import com.squareup.picasso.Picasso;

import java.util.List;

public class FeedAdapter extends RecyclerView.Adapter<FeedAdapter.ViewHolder> {
    private final List<Recipe> recipes;

    public FeedAdapter(List<Recipe> recipes)
    {
        this.recipes = recipes;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflating the layout to the recycler View
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View item = layoutInflater.inflate(R.layout.item_dish_feed,parent,false);
        return new ViewHolder(item);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final Recipe recipe = recipes.get(position);
        holder.dishFeedName.setText(recipe.getRecipeName());
        holder.dishFeedProfileName.setText(recipe.getAuthorName());

        Picasso.get()
                .load(recipe.getImageUrl())
                .into(holder.dishFeedImage);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SingleDishFragment singleDishFragment =new SingleDishFragment();
                Bundle b = new Bundle();
                String recipeJsonString = Utils.getGsonParser().toJson(recipe);
                b.putString("recipe", recipeJsonString);
                singleDishFragment.setArguments(b);

                AppCompatActivity activity = (AppCompatActivity) view.getContext();
                activity.getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container,singleDishFragment).addToBackStack(null).commit(); }
        });

    }

    // returns Size of the list
    @Override
    public int getItemCount() {
        return recipes.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{

        public ShapeableImageView dishFeedImage,dishFeedProfileImage;
        public TextView dishFeedName, dishFeedProfileName;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            // getting the id's of Views in a Single Item
            this.dishFeedProfileImage = itemView.findViewById(R.id.dish_feed_profile_pic);
            this.dishFeedImage = itemView.findViewById(R.id.dish_feed_dish_image);
            this.dishFeedProfileName = itemView.findViewById(R.id.dish_feed_profile_name);
            this.dishFeedName = itemView.findViewById(R.id.dish_feed_dish_name);

        }
    }
}
