package com.rec.recipeapp;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.rec.recipeapp.Feed.FeedAdapter;
import com.rec.recipeapp.Models.Recipe;
import com.rec.recipeapp.utils.Utils;

import java.util.ArrayList;
import java.util.List;

public class SingleDishFragment extends Fragment {
    Recipe recipe;
    TextView dishName,authorName,cuisine,bestFor;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_show_recipe, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
            String recipeJson = getArguments().getString("recipe","null");
            recipe = Utils.getGsonParser().fromJson(recipeJson,Recipe.class);

        Log.d("TAG", "onCreate: " + recipe.toString());
        viewData();

    }

    private void viewData() {

    }
}