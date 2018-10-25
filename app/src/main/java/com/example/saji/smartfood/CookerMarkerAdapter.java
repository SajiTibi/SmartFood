package com.example.saji.smartfood;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class CookerMarkerAdapter extends RecyclerView.Adapter<CookerMarkerAdapter.ViewHolder> {
    private LayoutInflater mInflater;
    ArrayList<RegularRecipe> dishesModelArrayList;
    private int cookerID;

    public CookerMarkerAdapter(Context context, ArrayList<RegularRecipe> dishesModelArrayList, int cookerID) {
        this.dishesModelArrayList = dishesModelArrayList;
        mInflater = LayoutInflater.from(context);
        this.cookerID = cookerID;
    }

    @Override
    public com.example.saji.smartfood.CookerMarkerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.cooker_marker_row, parent, false);

        return new com.example.saji.smartfood.CookerMarkerAdapter.ViewHolder(view);
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public void onBindViewHolder(final com.example.saji.smartfood.CookerMarkerAdapter.ViewHolder holder, int position) {
        RegularRecipe currentRecipe = dishesModelArrayList.get(position);
        holder.dishName.setText(currentRecipe.getRecipeName());
        holder.dishDescription.setText(currentRecipe.getRecipeDescription());
        holder.dishCooker.setText(currentRecipe.getRecipeCooker().getEmailAddress());
        holder.dishPrice.setText(String.valueOf(currentRecipe.getRecipePrice()));
        holder.dishID = currentRecipe.getRecipeID();
        holder.purchaseButton.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                    holder.purchaseButton.setBackground(mInflater.getContext().getDrawable(R.drawable.button_clicked_drawable));
                    placeAnOrder(holder.dishID, MainActivity.loggedUser.getUserID(), cookerID);
                } else if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                    holder.purchaseButton.setBackground(mInflater.getContext().getDrawable(R.drawable.button_unclicked_drawable));
                }
                return false;
            }
        });
    }

    @Override
    public int getItemCount() {
        return dishesModelArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView dishName;
        TextView dishCooker;
        TextView dishDescription;
        int dishID;
        TextView dishPrice;
        Button purchaseButton;

        public ViewHolder(View itemView) {
            super(itemView);
            dishName = itemView.findViewById(R.id.recipe_name);
            dishCooker = itemView.findViewById(R.id.recipe_cooker);
            dishDescription = itemView.findViewById(R.id.recipe_description);
            dishPrice = itemView.findViewById(R.id.recipe_price);
            purchaseButton = itemView.findViewById(R.id.recipe_order_button);
        }
    }

    private void placeAnOrder(final int recipeID, final int recipePurchaserID, final int recipeCookerID) {
        StringRequest addRecipeSR = new StringRequest(Request.Method.POST, Configs
                .PLACE_ORDER_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                JSONObject jsonResponse;
                try {
                    jsonResponse = new JSONObject(response);
                    boolean success = jsonResponse.getBoolean("success");
                    if (success) {
                        System.out.println("Recipe added successfully");
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, null) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put(Configs.RECIPE_ID, String.valueOf(recipeID));
                params.put(Configs.RECIPE_COOKER_ID, String.valueOf(recipeCookerID));
                params.put(Configs.RECIPE_PURCHASER_ID, String.valueOf(recipePurchaserID));
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(mInflater.getContext());
        requestQueue.add(addRecipeSR);
    }

}
