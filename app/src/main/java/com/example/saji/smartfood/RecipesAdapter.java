package com.example.saji.smartfood;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Build;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.zip.Inflater;

public class RecipesAdapter extends RecyclerView.Adapter<RecipesAdapter.ViewHolder> {
    private LayoutInflater mInflater;
    private CookerRecipes.CustomListener mlistener;
    ArrayList<RecipeModel> recipeModelArrayList;

    public RecipesAdapter(Context context, ArrayList<RecipeModel> recipeModelArrayList, CookerRecipes.CustomListener listener) {
        this.recipeModelArrayList = recipeModelArrayList;
        mInflater = LayoutInflater.from(context);
        mlistener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.recipe_row, parent, false);

        return new ViewHolder(view);
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        RecipeModel currentRecipe = recipeModelArrayList.get(position);
        holder.recipeName.setText(currentRecipe.getRecipeName());
        holder.recipeDescription.setText(currentRecipe.getRecipeDescription());
        holder.recipeCooker.setText(currentRecipe.getRecipeCooker().getEmailAddress());
        holder.recipePurchase.setText(String.valueOf(currentRecipe.getRecipePrice()));
        holder.recipeID = currentRecipe.getRecipeID();

        holder.recipePurchase.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                String[] details = {String.valueOf(holder.recipeName.getText()), String.valueOf(holder.recipePurchase.getText()),
                        String.valueOf(holder.recipeDescription.getText()), String.valueOf(holder.recipeID)};
                mlistener.setDishDetails(details);
                mlistener.onLongClick(view);
                return false;
            }
        });
    }

    @Override
    public int getItemCount() {
        return recipeModelArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView recipeName;
        TextView recipeCooker;
        TextView recipeDescription;
        int recipeID;
        Button recipePurchase;

        public ViewHolder(View itemView) {
            super(itemView);
            recipeName = itemView.findViewById(R.id.recipe_name);
            recipeCooker = itemView.findViewById(R.id.recipe_cooker);
            recipeDescription = itemView.findViewById(R.id.recipe_description);
            recipePurchase = itemView.findViewById(R.id.recipe_purchase);
        }
    }

}