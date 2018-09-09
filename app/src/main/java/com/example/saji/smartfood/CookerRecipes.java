package com.example.saji.smartfood;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.saji.smartfood.Configs;
import com.example.saji.smartfood.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class CookerRecipes extends Fragment {
    RecyclerView recipeRecyclerView;
    RecyclerView.Adapter recipesRecyclerViewAdapter;
    ArrayList<RecipeModel> recipeModelArrayList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View view = inflater.inflate(R.layout.cooker_recipes,container,false);
        recipeRecyclerView = view.findViewById(R.id.recipes_recycler_view);
        recipeModelArrayList = new ArrayList<>();
        loadRecipes();
        recipesRecyclerViewAdapter = new RecipesAdapter(getContext(),recipeModelArrayList);
        recipeRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recipeRecyclerView.setAdapter(recipesRecyclerViewAdapter);
        final Button addRecipeButton = view.findViewById(R.id.add_recipe);
        addRecipeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AddRecipeDialog addRecipeDialog = new AddRecipeDialog();
                addRecipeDialog.show(getFragmentManager(),"AddRecipeDialog");
            }
        });
        return  view ;
    }
    // TODO maybe to add ability to save recipes locale instead of loading each time from server
    private void loadRecipes() {
        StringRequest recipesRequest = new StringRequest(Request.Method.POST, Configs
                .RECIPES_RETRIEVAL_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    // we got response as json array within nesed json array so we iterate
                    // throught them all to get all recipes
                    JSONObject jsonResponse = new JSONObject(response);
                    boolean success = jsonResponse.getBoolean("success");
                    if (success) {
                        Iterator keyz = jsonResponse.keys();
                        keyz.next();
                        while (keyz.hasNext()) {
                            JSONObject key = jsonResponse.getJSONObject((String) keyz.next());
                            String recipeName = key.getString(Configs.RECIPE_NAME);
                            String recipeDescription = key.getString(Configs.RECIPE_DESCRIPTION);
                            double recipePrice = key.getDouble(Configs.RECIPE_PRICE);
                            // recipe id will be needed if cook wishes to modify/delete recipe
                            int recipeID = key.getInt(Configs.RECIPE_ID);

                            // this is temporary, there is no need for recipe cooker since we are
                            // loading our own recipes, however recipe cooker needed for showing
                            // menu for other users
                            RecipeModel newRecipe = new RecipeModel(recipeName, MainActivity
                                    .loggedUser,recipeDescription,recipePrice);
                            recipeModelArrayList.add(newRecipe);
                            recipesRecyclerViewAdapter.notifyDataSetChanged();
                            }
                    }
                }catch (JSONException e){

                }
            }
        },null){
            @Override
            protected Map<String,String> getParams(){
                Map<String,String> params = new HashMap<>();
                params.put("recipe_cooker_id", String.valueOf(MainActivity.loggedUser.userID));
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        requestQueue.add(recipesRequest);
    }
}