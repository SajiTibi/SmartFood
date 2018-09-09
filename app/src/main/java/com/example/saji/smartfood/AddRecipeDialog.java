package com.example.saji.smartfood;

import android.app.Fragment;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class AddRecipeDialog extends DialogFragment {
    View view;
    public View onCreateView(LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.add_recipe_dialog, container, false);
        final TextView recipeName = view.findViewById(R.id.recipe_name);
        final TextView recipeDescription = view.findViewById(R.id.recipe_description);
        final TextView recipePrice = view.findViewById(R.id.recipe_price);
        Button addButton = view.findViewById(R.id.add_recipe);
        Button cancelButton = view.findViewById(R.id.cancel_recipe);
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getDialog().dismiss();
            }
        });
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addRecipe(recipeName.getText().toString(), recipeDescription.getText().toString(),
                        Double.valueOf(recipePrice.getText().toString()));
            }
        });
        return view;
    }

    private void addRecipe(final String recipeName, final String recipeDescription, final Double recipePrice) {
        StringRequest addRecipeSR = new StringRequest(Request.Method.POST, Configs
                .RECIPES_ADD_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                JSONObject jsonResponse;
                try {
                    jsonResponse = new JSONObject(response);
                    boolean success = jsonResponse.getBoolean("success");
                    if (success){
                        // TODO show message that added successfully
                       // Snackbar.make(view,"Added successfully", Toast.LENGTH_SHORT);
                        getDialog().dismiss();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, null) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put(Configs.RECIPE_NAME,recipeName);
                params.put(Configs.RECIPE_DESCRIPTION,recipeDescription);
                params.put(Configs.RECIPE_PRICE,String.valueOf(recipePrice));
                params.put(Configs.RECIPE_COOKER,MainActivity.loggedUser.getEmailAddress());
                params.put(Configs.RECIPE_COOKER_ID, String.valueOf(MainActivity.loggedUser.getUserID()));
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        requestQueue.add(addRecipeSR);
    }
}
