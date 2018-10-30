package com.awesome.food_app.smartfood;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class MyCart extends Fragment {
    RecyclerView ordersRecyclerView;
    RecyclerView.Adapter ordersRecyclerViewAdapter;
    ArrayList<OrderModule> ordersModelArrayList;


    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View view = inflater.inflate(R.layout.my_cart, container, false);
        ordersRecyclerView = view.findViewById(R.id.my_cart_recycler);
        ordersModelArrayList = new ArrayList<>();
        loadOrders();
        ordersRecyclerViewAdapter = new OrderedRecipeAdapter(getContext(), ordersModelArrayList);
        ordersRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        ordersRecyclerView.setAdapter(ordersRecyclerViewAdapter);
        return view;
    }

    private void loadOrders() {
        StringRequest ordersRequest = new StringRequest(Request.Method.POST, Configs
                .CART_RETRIEVER_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    System.out.println("RES" + response);
                    JSONObject jsonResponse = new JSONObject(response);
                    boolean success = jsonResponse.getBoolean("success");
                    if (success) {
                        ordersModelArrayList.clear();
                        Iterator keyz = jsonResponse.keys();
                        keyz.next();
                        while (keyz.hasNext()) {
                            JSONObject key = jsonResponse.getJSONObject((String) keyz.next());
                            String purchaseTime = (String) key.get(Configs.RECIPE_TIME_ORDER_PLACED);
                            int orderID = key.getInt(Configs.ORDER_ID);
                            int recipeID = key.getInt(Configs.RECIPE_ID);
                            int purchaserID = key.getInt(Configs.RECIPE_PURCHASER_ID);
                            int orderStatus = key.getInt(Configs.ORDER_STATUS);
                            OrderModule newOrder = new OrderModule(orderID, recipeID, purchaserID,
                                    purchaseTime, orderStatus);
                            ordersModelArrayList.add(newOrder);
                            ordersRecyclerViewAdapter.notifyDataSetChanged();
                            System.out.println(orderID);
                            System.out.println("New size:"+ordersModelArrayList.size());
                        }
                    }
                } catch (JSONException e) {

                }
            }
        }, null) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put(Configs.RECIPE_PURCHASER_ID, String.valueOf(MainActivity.loggedUser.getUserID()));
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        requestQueue.add(ordersRequest);
    }
}

