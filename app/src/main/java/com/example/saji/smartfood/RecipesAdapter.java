package com.example.saji.smartfood;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.widget.RecyclerView;
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

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        RecipeModel currentRecipe = recipeModelArrayList.get(position);
        holder.recipeName.setText(currentRecipe.getRecipeName());
        holder.recipeDescription.setText(currentRecipe.getRecipeDescription());
        holder.recipeCooker.setText(currentRecipe.getRecipeCooker().getEmailAddress());
        holder.recipePurchase.setText(String.valueOf(currentRecipe.getRecipePrice()));
        holder.recipePurchase.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {

                if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                    holder.recipePurchase.setBackground(mInflater.getContext().getDrawable(R.drawable.button_clicked_drawable));
                } else if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                    holder.recipePurchase.setBackground(mInflater.getContext().getDrawable(R.drawable.button_unclicked_drawable));
                }
                return false;
            }
        });

        holder.recipePurchase.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                //todo rony666: onLongClick start EditRecipeDialog and on closing it call load recipes from Cooker Recipes to update the array
//                Activity parentActivity = (Activity)mInflater.getContext();
//                EditRecipeDialog editRecipeDialog = new EditRecipeDialog();
//                editRecipeDialog.show(parentActivity.getFragmentManager(),"EditRecipeDialog");
//
//                editRecipeDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
//                    @Override
//                    public void onDismiss(DialogInterface dialogInterface) {
//
//                    }
//                });
                Toast.makeText(mInflater.getContext(), "=========================================", Toast.LENGTH_SHORT).show();
                return false;
            }
        });
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