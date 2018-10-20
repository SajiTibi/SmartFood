package com.example.saji.smartfood;


import android.annotation.SuppressLint;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.util.ArrayList;

public class CookerDishesDialog extends DialogFragment {
    View view;
    private Location lastLocation;
    RecyclerView dishesRecyclerView;
    RecyclerView.Adapter dishesRecyclerViewAdapter;
    private ArrayList<RegularRecipe> cookerDishes = new ArrayList<>();
    int cookerID = 0;


    @SuppressLint("ClickableViewAccessibility")
    public View onCreateView(LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.cooker_marker_dialog, container, false);
        view.setMinimumWidth(1200);
        dishesRecyclerView = view.findViewById(R.id.dishes_recycler_view);
        final Button reviewButton = view.findViewById(R.id.check_review);
        Criteria criteria = new Criteria();
        criteria.setAccuracy(Criteria.ACCURACY_FINE);
        criteria.setPowerRequirement(Criteria.POWER_LOW);
        criteria.setAltitudeRequired(false);
        criteria.setBearingRequired(false);
        criteria.setSpeedRequired(false);
        criteria.setCostAllowed(true);
        LocationManager locationManager = (LocationManager) getActivity().getSystemService
                (getContext().LOCATION_SERVICE);
        dishesRecyclerViewAdapter = new CookerMarkerAdapter(getContext(), cookerDishes, cookerID);
        dishesRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        dishesRecyclerView.setAdapter(dishesRecyclerViewAdapter);

        //todo add the change to see Reviews and to add reviews!
        reviewButton.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                    reviewButton.setBackground(getContext().getDrawable(R.drawable.dialog_button_clicked_drawable));
                } else {
                    reviewButton.setBackground(getContext().getDrawable(R.drawable.dialog_button_unclicked_drawable));
                }
                return false;
            }
        });
        return view;
    }

    public void setCookerID(int id) {
        cookerID = id;
    }

    public void setCookerDishes(ArrayList<RegularRecipe> cookDishes) {
        cookerDishes = cookDishes;
    }
}
