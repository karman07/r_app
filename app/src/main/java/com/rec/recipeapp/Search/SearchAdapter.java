package com.rec.recipeapp.Search;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.imageview.ShapeableImageView;
import com.rec.recipeapp.Models.Recipe;
import com.rec.recipeapp.R;
import com.rec.recipeapp.SingleDish.SingleDishFragment;
import com.rec.recipeapp.utils.Utils;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.ViewHolder> implements Filterable {
    private final ArrayList<Recipe> recipes;
    ArrayList<Recipe>  recipesFull;


    public SearchAdapter(ArrayList<Recipe> recipes)
    {
        this.recipesFull =  recipes;
        this.recipes = new ArrayList<>(recipesFull);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflating the layout to the recycler View
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View item = layoutInflater.inflate(R.layout.item_search_feed,parent,false);
        return new ViewHolder(item);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final Recipe recipe = recipes.get(position);
        holder.searchDishName.setText(recipe.getRecipeName());
        holder.searchAuthorName.setText("By "+recipe.getAuthorName());

        Picasso.get()
                .load(recipe.getImageUrl())
                .into(holder.searchDishImage);
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

    @Override
    public Filter getFilter() {
        return dishFilter;
    }

    private final Filter dishFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
            ArrayList<Recipe> filteredRecipeList = new ArrayList<>();
            if (charSequence  == null || charSequence.length() == 0 )
            {
                filteredRecipeList.addAll(recipesFull);
            }
            else {
                String filterPattern = charSequence.toString().toLowerCase().trim();
                for (Recipe recipe : recipesFull)
                {
                    if (recipe.getRecipeName().toLowerCase().contains(filterPattern))
                        filteredRecipeList.add(recipe);

                }
            }

            FilterResults results = new FilterResults();
            results.values = filteredRecipeList;
            results.count = filteredRecipeList.size();
            return results;
        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
            recipes.clear();
            recipes.addAll((ArrayList) filterResults.values);
            notifyDataSetChanged();
        }
    };

    public static class ViewHolder extends RecyclerView.ViewHolder{

        public ShapeableImageView searchDishImage;
        public TextView searchDishName, searchAuthorName;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            // getting the id's of Views in a Single Item
            this.searchDishImage = itemView.findViewById(R.id.search_dish_image);
            this.searchDishName = itemView.findViewById(R.id.search_dish_name);
            this.searchAuthorName = itemView.findViewById(R.id.search_dish_author);

        }
    }
}
