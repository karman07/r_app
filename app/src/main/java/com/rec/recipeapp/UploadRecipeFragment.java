package com.rec.recipeapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.rec.recipeapp.Models.Ingredients;
import com.rec.recipeapp.Models.Recipe;

import java.util.ArrayList;
import java.util.List;

public class UploadRecipeFragment extends Fragment {
Button uploadRecipe;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_publish_recipe, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        uploadRecipe = view.findViewById(R.id.btn_upload_recipe);
        uploadRecipe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Recipe recipe = new Recipe();
                recipe.setRecipeName("Butter Paneer");
                recipe.setAuthorName("Bharat");
                recipe.setBestFor("Lunch");
                recipe.setCuisine("Indian");
                Ingredients ingredient = new Ingredients();
                List<Ingredients> ingredientsList = new ArrayList<Ingredients>();
                ingredient.setNameOfIngredient("Paneer");
                ingredient.setQuantityOfIngredient("20");
                ingredientsList.add(ingredient);
                ingredient.setNameOfIngredient("Butter");
                ingredient.setQuantityOfIngredient("50");
                ingredientsList.add(ingredient);
                recipe.setIngredients(ingredientsList);
                recipe.setImageUrl("ImageUrl");
                recipe.setType("Veg");
                List<String> recipeStepsList = new ArrayList<>();
                recipeStepsList.add("Step1");
                recipeStepsList.add("Step2");
                recipeStepsList.add("Step3");
                recipe.setSteps(recipeStepsList);


                DatabaseReference mRef = FirebaseDatabase.getInstance().getReference("Recipe");
                mRef.push().setValue(recipe);

            }
        });
    }
}
