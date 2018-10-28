package com.awesome.food_app.smartfood;


import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

public class DishesMarkerFragment extends Fragment {
    RecyclerView dishesRecyclerView;
    RecyclerView.Adapter dishesRecyclerViewAdapter;
    private ArrayList<RegularRecipe> cookerDishes = new ArrayList<>();
    int cookerID = 0;


    @SuppressLint("ClickableViewAccessibility")
    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View view = inflater.inflate(R.layout.dishes_marker_layout, container, false);
        dishesRecyclerView = view.findViewById(R.id.dishes_recycler_view);
        dishesRecyclerViewAdapter = new CookerMarkerAdapter(getContext(), cookerDishes, cookerID);
        dishesRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        dishesRecyclerView.setAdapter(dishesRecyclerViewAdapter);
        return view;
    }

    public void setCookerID(int id) {
        cookerID = id;
    }

    public void setCookerDishes(ArrayList<RegularRecipe> cookDishes) {
        cookerDishes = cookDishes;
    }

}
