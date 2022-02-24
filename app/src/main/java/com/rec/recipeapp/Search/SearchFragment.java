package com.rec.recipeapp.Search;



import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.rec.recipeapp.Feed.FeedAdapter;
import com.rec.recipeapp.Models.Recipe;
import com.rec.recipeapp.R;

import java.util.ArrayList;
import java.util.List;

public class SearchFragment extends Fragment  {
    RecyclerView recyclerView;
    SearchView searchView;
    ArrayList<Recipe> recipeList;
    SearchAdapter searchFeedAdapter;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_search__screen, container, false);
        recyclerView = v.findViewById(R.id.recycler);
        viewData();
        searchView = v.findViewById(R.id.search_dish);
        searchView.setMaxWidth(Integer.MAX_VALUE);
        searchView.setQueryHint("Search For Dishes");
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                searchFeedAdapter.getFilter().filter(newText);
                return false;
            }
        });


        return v;
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
                searchFeedAdapter = new SearchAdapter(recipeList);
                recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                recyclerView.setAdapter(searchFeedAdapter);



            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                throw error.toException();
            }
        });



    }

}