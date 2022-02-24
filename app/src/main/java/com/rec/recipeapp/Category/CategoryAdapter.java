package com.rec.recipeapp.Category;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.imageview.ShapeableImageView;
import com.rec.recipeapp.Models.Category;
import com.rec.recipeapp.R;

import java.util.List;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.ViewHolder> {
    private final List<Category> categoryList;

    public CategoryAdapter(List<Category> categoryList) {
        this.categoryList = categoryList;
    }

    @NonNull
    @Override
    public CategoryAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflating the layout to the recycler View
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View item = layoutInflater.inflate(R.layout.item_category_favorite, parent, false);
        return new CategoryAdapter.ViewHolder(item);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryAdapter.ViewHolder holder, int position) {
        Category category = categoryList.get(position);
        holder.categoryName.setText(category.getCategoryName());
        holder.categoryImage.setImageResource(category.getImageResource());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SingleCategoryListFragment singleCategoryListFragment = new SingleCategoryListFragment();
                Bundle b = new Bundle();
                b.putString("category", category.getCategoryName());
                singleCategoryListFragment.setArguments(b);

                AppCompatActivity activity = (AppCompatActivity) view.getContext();
                activity.getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container, singleCategoryListFragment).addToBackStack(null).commit();
            }
        });

    }

    // returns Size of the list
    @Override
    public int getItemCount() {
        return categoryList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public ShapeableImageView categoryImage;
        public TextView categoryName;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            // getting the id's of Views in a Single Item
            this.categoryImage = itemView.findViewById(R.id.category_image);
            this.categoryName = itemView.findViewById(R.id.category_name);


        }
    }
}
