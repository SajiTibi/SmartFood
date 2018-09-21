package com.example.saji.smartfood;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class RecipesAdapter extends RecyclerView.Adapter<RecipesAdapter.ViewHolder> {
    private LayoutInflater mInflater;
    ArrayList<RecipeModel> recipeModelArrayList;

    public RecipesAdapter(Context context, ArrayList<RecipeModel> recipeModelArrayList) {
        this.recipeModelArrayList = recipeModelArrayList;
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.recipe_row, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        RecipeModel currentRecipe = recipeModelArrayList.get(position);
        holder.recipeName.setText(currentRecipe.getRecipeName());
        holder.recipeDescription.setText(currentRecipe.getRecipeDescription());
        holder.recipeCooker.setText(currentRecipe.getRecipeCooker().getEmailAddress());
        holder.recipePurchase.setText(String.valueOf(currentRecipe.getRecipePrice()));
    }

    @Override
    public int getItemCount() {
        return recipeModelArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView recipeName;
        TextView recipeCooker;
        TextView recipeDescription;
        Button recipePurchase;

        public ViewHolder(View itemView) {
            super(itemView);
            recipeName = itemView.findViewById(R.id.recipe_name);
            recipeCooker = itemView.findViewById(R.id.recipe_cooker);
            recipeDescription = itemView.findViewById(R.id.recipe_description);
            recipePurchase = itemView.findViewById(R.id.recipe_purchase);
        }

        @Override
        public void onClick(View view) {

        }
    }

}