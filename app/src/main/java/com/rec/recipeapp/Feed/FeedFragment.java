package com.rec.recipeapp.Feed;

import android.os.Bundle;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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
import com.rec.recipeapp.Models.Recipe;
import com.rec.recipeapp.R;

import java.util.ArrayList;
import java.util.List;


public class FeedFragment extends Fragment {
RecyclerView dishFeedRecyclerView;
List<Recipe> recipeList;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_feed_screen, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        dishFeedRecyclerView = view.findViewById(R.id.dish_feed_recycler_view);

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        viewData();

    }

    private void viewData() {
       recipeList = new ArrayList<>();
        DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference().child("Recipe");
        rootRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot ds : snapshot.getChildren())
                {
                    Recipe recipe = ds.getValue(Recipe.class);
                    recipeList.add(recipe);
                }
                FeedAdapter feedAdapter = new FeedAdapter(recipeList);
                dishFeedRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                dishFeedRecyclerView.setAdapter(feedAdapter);


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                throw error.toException();
            }
        });
    }
}