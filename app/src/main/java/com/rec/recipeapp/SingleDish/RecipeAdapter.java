package com.rec.recipeapp.SingleDish;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.rec.recipeapp.Models.Ingredients;
import com.rec.recipeapp.R;

import java.util.List;

public class RecipeAdapter extends  RecyclerView.Adapter<RecipeAdapter.ViewHolder>  {
    private final List<String> steps;

    public RecipeAdapter(List<String> steps)
    {
        this.steps = steps;
    }

    @NonNull
    @Override
    public RecipeAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.item_step,parent,false);
        return new RecipeAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecipeAdapter.ViewHolder holder, int position) {
        String step = steps.get(position);
        holder.stepNum.setText("Step "+position);
        holder.stepString.setText(step);

    }

    @Override
    public int getItemCount() {
        return steps.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView stepNum, stepString;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            stepNum = itemView.findViewById(R.id.item_step_number);
            stepString = itemView.findViewById(R.id.item_step);
        }
    }
}
