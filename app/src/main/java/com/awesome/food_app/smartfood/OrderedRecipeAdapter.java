package com.awesome.food_app.smartfood;

import android.content.Context;
import android.location.Geocoder;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

public class OrderedRecipeAdapter extends RecyclerView.Adapter<OrderedRecipeAdapter.ViewHolder> {
    ArrayList<OrderModule> orderModuleArrayList;
    private Context context;
    private LayoutInflater mInflater;
    private Geocoder geocoder;
    public static ArrayList<RegularRecipe> allRecipes;

    public OrderedRecipeAdapter(Context context, ArrayList<OrderModule> orderModuleArrayList) {
        this.context = context;
        allRecipes = new ArrayList<>();
        loadAllRecipes();
        mInflater = LayoutInflater.from(context);
        this.orderModuleArrayList = orderModuleArrayList;
        geocoder = new Geocoder(context);

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.my_cart_row, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        OrderModule currOrder = orderModuleArrayList.get(position);
        RegularRecipe currRecipe = findRecipe(currOrder.getRecipeID());
        if (currRecipe == null) {
            System.out.println("Null recipe");
            return;
        }
        String locationName = "";
        try {
            locationName = geocoder.getFromLocation(currRecipe.getRecipeLatitude(), currRecipe
                    .getRecipeLongitude(), 1).get(0).getAddressLine(0);
        } catch (IOException e) {
            e.printStackTrace();
        }
        holder.recipeName.setText(currRecipe.getRecipeName());
        holder.broughtFrom.setText(currRecipe.getRecipeCooker().getEmailAddress());
        holder.broughtTime.setText(currOrder.getPurchaseTime());
        holder.location.setText(locationName);
        switch (currOrder.getState()) {
            case 0:
                holder.status.setText("Pending");
                break;
            case 1:
                holder.status.setText("Accepted");
                break;
            case 2:
                holder.status.setText("Rejected");
                break;
            case 3:
                holder.status.setText("Finished");
        }
    }

    private RegularRecipe findRecipe(int recipeID) {
        System.out.println("SOIZE" + allRecipes.size());
        System.out.println("SOIwwwZE" + MainActivity.allUsers.size());

        for (RegularRecipe recipe : allRecipes) {
            if (recipe.getRecipeID() == recipeID) {
                return recipe;
            }
        }
        return null;
    }

    @Override
    public int getItemCount() {
        return orderModuleArrayList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView recipeName;
        TextView broughtFrom;
        TextView broughtTime;
        TextView status;
        TextView location;

        ViewHolder(View view) {
            super(view);
            recipeName = view.findViewById(R.id.mcr_recipe_name);
            broughtFrom = view.findViewById(R.id.mcr_recipe_brought_from);
            broughtTime = view.findViewById(R.id.mcr_recipe_brought_time);
            status = view.findViewById(R.id.mcr_recipe_status);
            location = view.findViewById(R.id.mcr_recipe_location);

        }
    }
    private void loadAllRecipes() {
        StringRequest recipesRequest = new StringRequest(Request.Method.POST, Configs
                .ALL_RECIPES_RETRIEVAL_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {

                    // we got response as json array within nesed json array so we iterate
                    // throught them all to get all recipes
                    JSONObject jsonResponse = new JSONObject(response);
                    boolean success = jsonResponse.getBoolean("success");
                    if (success) {
                        allRecipes.clear();
                        Iterator keyz = jsonResponse.keys();
                        keyz.next();
                        while (keyz.hasNext()) {
                            JSONObject key = jsonResponse.getJSONObject((String) keyz.next());

                            String recipeName = key.getString(Configs.RECIPE_NAME);
                            String recipeDescription = key.getString(Configs.RECIPE_DESCRIPTION);
                            double recipePrice = key.getDouble(Configs.RECIPE_PRICE);
                            int recipeCookerID = key.getInt(Configs.RECIPE_COOKER_ID);
                            int recipeID = key.getInt(Configs.RECIPE_ID);
                            UserModel cooker = findCooker(recipeCookerID);
                            if (cooker == null) {
                                System.out.println("ID" + recipeCookerID);
                                System.out.println("Cant find cooker in all users");
                                return;
                            }
                            RegularRecipe recipe = new RegularRecipe(recipeID, recipeCookerID,
                                    recipeName, cooker, recipeDescription, recipePrice, cooker.getUserLongitude(),
                                    cooker.getUserLatitude());
                            allRecipes.add(recipe);
                            notifyDataSetChanged();
                        }
                    }
                } catch (JSONException e) {

                }
            }
        }, null);
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(recipesRequest);

    }
    private UserModel findCooker(int recipeCookerID) {
        for (UserModel user : MainActivity.allUsers) {
            if (user.userID == recipeCookerID) {
                return user;
            }
        }
        return null;
    }

}