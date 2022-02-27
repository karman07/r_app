package com.rec.recipeapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.rec.recipeapp.Models.Ingredients;
import com.rec.recipeapp.Models.Recipe;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

public class UploadRecipeFragment extends Fragment {
    Button uploadRecipe;
    LinearLayout ingredientLayout, stepsLayout;
    AutoCompleteTextView txtCuisine, txtVegNonVeg, txtBestFor;
    EditText txtRecipeName;
    ImageView recipeImage;
    Uri imageUri;
    String imageUrl;
    TextView addIngredient,addSteps;
    StorageReference storageReference;
    ArrayList<Ingredients> ingredientsArrayList = new ArrayList<>();
    ArrayList<String> stepsList = new ArrayList<>();
    ProgressDialog dialog;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_publish_recipe, container, false);
        uploadRecipe = view.findViewById(R.id.btn_upload_recipe);
        ingredientLayout = view.findViewById(R.id.layout_ingredients);
        stepsLayout = view.findViewById(R.id.layout_steps);
        txtCuisine = view.findViewById(R.id.edt_select_cuisine);
        txtVegNonVeg = view.findViewById(R.id.edt_veg_nonveg);
        txtBestFor = view.findViewById(R.id.edt_select_best_for);
        txtRecipeName = view.findViewById(R.id.edt_upload_name);
        recipeImage = view.findViewById(R.id.btn_upload_image);
        addIngredient = view.findViewById(R.id.txt_add_ingredient);
        addSteps = view.findViewById(R.id.txt_add_steps);

        dialog = new ProgressDialog(getContext());
        dialog.setMessage("Please wait...");

        ArrayAdapter cuisineAdapter = new ArrayAdapter(requireContext(), R.layout.dropdown_item, getResources().getStringArray(R.array.Cuisine));
        ArrayAdapter vegNonvegAdapter = new ArrayAdapter(requireContext(), R.layout.dropdown_item, getResources().getStringArray(R.array.Veg_NonVeg));
        ArrayAdapter bestForAdapter = new ArrayAdapter(requireContext(), R.layout.dropdown_item, getResources().getStringArray(R.array.Best_For));

        txtBestFor.setAdapter(bestForAdapter);
        txtCuisine.setAdapter(cuisineAdapter);
        txtVegNonVeg.setAdapter(vegNonvegAdapter);

        recipeImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.show();
                selectImage();
            }
        });

        uploadRecipe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String recipe = txtRecipeName.getText().toString();
                String vegNonVeg = txtVegNonVeg.getText().toString();
                String cuisine = txtCuisine.getText().toString();
                String bestFor = txtBestFor.getText().toString();
                getIngredientList();
                 String author = Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getDisplayName();
                getStepsList();
                if (recipe.isEmpty() && vegNonVeg.isEmpty() && cuisine.isEmpty() && bestFor.isEmpty())
                {
                    Toast.makeText(getContext(),"Please Fill All The Fields",Toast.LENGTH_SHORT).show();
                }
                else{
                    dialog.show();
                    uploadRecipe(recipe,author,bestFor,cuisine,stepsList,ingredientsArrayList,vegNonVeg);

                }
            }
        });
        addIngredient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addIngredient();
            }
        });
        addSteps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addSteps();
            }
        });
        return view;
    }


    void uploadImage() {
        storageReference = FirebaseStorage.getInstance().getReference("recipeImages/" + UUID.randomUUID());
        storageReference.putFile(imageUri).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                if (task.isSuccessful())
                {
                    storageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            imageUrl = uri.toString();
                            dialog.dismiss();

                        }
                    });
                }
            }
        });
    }

    void uploadRecipe(String recipe_name, String author_name, String best_for, String cuisine, List<String> steps, List<Ingredients> ingredientList, String veg_nonVeg) {

        uploadRecipe.setVisibility(View.INVISIBLE);
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();

        Recipe recipe = new Recipe();
        recipe.setRecipeName(recipe_name);
        recipe.setAuthorName(author_name);
        recipe.setBestFor(best_for);
        recipe.setCuisine(cuisine);
        recipe.setIngredients(ingredientList);
        recipe.setImageUrl(imageUrl);
        recipe.setType(veg_nonVeg);
        recipe.setSteps(steps);


        DatabaseReference mRef = FirebaseDatabase.getInstance().getReference("Recipe");
        mRef.push().setValue(recipe).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                uploadRecipe.setVisibility(View.VISIBLE);
                Toast.makeText(getContext(),"Hurray!! Recipe Uploaded",Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                uploadRecipe.setVisibility(View.VISIBLE);
                Toast.makeText(getContext(),"Problem Occurred Please Retry",Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            }
        });
    }

    private void selectImage() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, 100);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100 && data != null && data.getData() != null) {
            imageUri = data.getData();
            recipeImage.setImageURI(imageUri);
            uploadImage();
        }
    }

    private void addIngredient() {

        final View ingredientView = getLayoutInflater().inflate(R.layout.add_ingredient_row,null,false);

        ImageButton removeIngredient = (ImageButton)ingredientView.findViewById(R.id.btn_delete_ingredient);

        removeIngredient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                removeIngredient(ingredientView);
            }
        });

        ingredientLayout.addView(ingredientView);

    }

    private void removeIngredient(View view){

        ingredientLayout.removeView(view);

    }

    private void addSteps() {

        final View stepsView = getLayoutInflater().inflate(R.layout.add_step_row,null,false);

        ImageButton removeSteps = (ImageButton)stepsView.findViewById(R.id.btn_delete_step);

        removeSteps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                removeStep(stepsView);
            }
        });

        stepsLayout.addView(stepsView);

    }

    private void removeStep(View view){

        stepsLayout.removeView(view);

    }

    private boolean getIngredientList() {
        ingredientsArrayList.clear();
        boolean result = true;

        for(int i=0;i<ingredientLayout.getChildCount();i++){

            View ingredientView = ingredientLayout.getChildAt(i);

            EditText edtIngredient = (EditText)ingredientView.findViewById(R.id.edt_add_ingredient);
            EditText qtyIngredient = (EditText)ingredientView.findViewById(R.id.edt_add_quantity);

            Ingredients ingredient = new Ingredients();

            if(!edtIngredient.getText().toString().equals("") && !qtyIngredient.getText().toString().equals(""))  {
                ingredient.setNameOfIngredient(edtIngredient.getText().toString());
                ingredient.setQuantityOfIngredient(qtyIngredient.getText().toString());
            }else {
                result = false;
                break;
            }
            ingredientsArrayList.add(ingredient);
        }
        if(ingredientsArrayList.size()==0){
            result = false;
            Toast.makeText(getContext(), "Add Cricketers First!", Toast.LENGTH_SHORT).show();
        }else if(!result){
            Toast.makeText(getContext(), "Enter All Details Correctly!", Toast.LENGTH_SHORT).show();
        }
        return result;
    }

    private boolean getStepsList() {
        stepsList.clear();
        String step;
        boolean result = true;

        for(int i=0;i<stepsLayout.getChildCount();i++){

            View stepsView = stepsLayout.getChildAt(i);
            EditText edtStep = (EditText)stepsView.findViewById(R.id.edt_add_step);

            if (!edtStep.getText().toString().equals(""))
            {
                 step = edtStep.getText().toString();
                stepsList.add(step);
            }


        }
        if(stepsList.size()==0){
            result = false;
            Toast.makeText(getContext(), "Add Stepss First!", Toast.LENGTH_SHORT).show();
        }else if(!result){
            Toast.makeText(getContext(), "Enter All Details Correctly!", Toast.LENGTH_SHORT).show();
        }
        return result;
    }


}
