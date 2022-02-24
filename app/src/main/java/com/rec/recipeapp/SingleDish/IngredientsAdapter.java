package com.rec.recipeapp.SingleDish;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.rec.recipeapp.Feed.FeedAdapter;
import com.rec.recipeapp.Models.Ingredients;
import com.rec.recipeapp.Models.Recipe;
import com.rec.recipeapp.R;

import java.util.List;

public class IngredientsAdapter extends  RecyclerView.Adapter<IngredientsAdapter.ViewHolder>  {
    private final List<Ingredients> ingredientsList;

    public IngredientsAdapter(List<Ingredients> ingredientsList)
    {
        this.ingredientsList = ingredientsList;
    }

    @NonNull
    @Override
    public IngredientsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.item_ingredients,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull IngredientsAdapter.ViewHolder holder, int position) {
        Ingredients ingredients = ingredientsList.get(position);
        holder.ingredientName.setText(ingredients.getNameOfIngredient());
        holder.ingredientQuantity.setText(ingredients.getQuantityOfIngredient());

    }

    @Override
    public int getItemCount() {
        return ingredientsList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView ingredientName, ingredientQuantity;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ingredientName = itemView.findViewById(R.id.item_ingredient_name);
            ingredientQuantity = itemView.findViewById(R.id.item_ingredient_quantity);
        }
    }
}
