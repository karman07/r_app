package com.rec.recipeapp.SingleDish;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.imageview.ShapeableImageView;
import com.rec.recipeapp.Models.Recipe;
import com.rec.recipeapp.R;
import com.rec.recipeapp.utils.Utils;
import com.squareup.picasso.Picasso;

;

public class SingleDishFragment extends Fragment {
    Recipe recipe;
    TextView dishName,authorName,cuisine,bestFor;
    ShapeableImageView dishImage;
    RecyclerView recipeList;
    RecyclerView ingredientList;
    ImageView vegNonVeg, addToFavorites;
    View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view =  inflater.inflate(R.layout.fragment_show_recipe, container, false);

        // Finding Ids of Views in the Layout
        dishName = view.findViewById(R.id.single_recipe_name);
        authorName = view.findViewById(R.id.single_recipe_author_name);
        cuisine = view.findViewById(R.id.single_recipe_cuisine);
        bestFor = view.findViewById(R.id.single_recipe_best_for);
        dishImage = view.findViewById(R.id.single_recipe_image);
        ingredientList = view.findViewById(R.id.single_recipe_ingredients_list);
        recipeList = view.findViewById(R.id.single_recipe_steps_list);
        vegNonVeg = view.findViewById(R.id.single_recipe_veg_nonveg);


        String recipeJson = getArguments().getString("recipe","null");
        recipe = Utils.getGsonParser().fromJson(recipeJson,Recipe.class);

        IngredientsAdapter ingredientsAdapter = new IngredientsAdapter(recipe.getIngredients());
        RecipeAdapter recipeAdapter = new RecipeAdapter(recipe.getSteps());

        ingredientList.setLayoutManager(new LinearLayoutManager(getContext()));
        ingredientList.setAdapter( ingredientsAdapter);
        recipeList.setLayoutManager(new LinearLayoutManager(getContext()));
        recipeList.setAdapter(recipeAdapter);
        dishName.setText(recipe.getRecipeName());
        authorName.setText(String.format("By %s", recipe.getAuthorName()));
        cuisine.setText(recipe.getCuisine());
        bestFor.setText(String.format("Best For %s", recipe.getBestFor()));
        if (recipe.getType() == "Non Veg")
        {
            vegNonVeg.setImageResource(R.drawable.ic_non_veg);
        }

        Picasso.get().load(recipe.getImageUrl()).into(dishImage);

        return view;
    }
}