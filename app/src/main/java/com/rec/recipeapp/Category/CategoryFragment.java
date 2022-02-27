package com.rec.recipeapp.Category;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.rec.recipeapp.Models.Category;
import com.rec.recipeapp.R;

import java.util.ArrayList;

public class CategoryFragment extends Fragment {
    RecyclerView categoryRecyclerView;
    ArrayList<Category> categoryArrayList;
    Category category;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_categories, container, false);
        categoryRecyclerView = view.findViewById(R.id.category_wise_recycler_view);
        categoryRecyclerView.setLayoutManager(new GridLayoutManager(getContext(),2));
        CategoryAdapter categoryAdapter = new CategoryAdapter(createCategoryList());
        categoryRecyclerView.setAdapter(categoryAdapter);


        return view;
    }


    public ArrayList<Category> createCategoryList()
    {
        categoryArrayList = new ArrayList<>();
         category = new Category();
        category.setCategoryName("Asian");
        category.setImageResource(R.drawable.pizza);
        categoryArrayList.add(category);

        category = new Category();
        category.setCategoryName("Indian");
        category.setImageResource(R.drawable.pizza);
        categoryArrayList.add(category);

        category = new Category();
        category.setCategoryName("Chinese");
        category.setImageResource(R.drawable.pizza);
        categoryArrayList.add(category);

        category = new Category();
        category.setCategoryName("Asian");
        category.setImageResource(R.drawable.pizza);
        categoryArrayList.add(category);

        category = new Category();
        category.setCategoryName("Asian");
        category.setImageResource(R.drawable.pizza);
        categoryArrayList.add(category);




        return  categoryArrayList;
    }
}
