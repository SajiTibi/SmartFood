package com.example.saji.smartfood;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.FragmentManager;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class EditRecipeDialog extends DialogFragment {
    View view;
    private Location lastLocation;
    private DialogInterface.OnDismissListener onDismissListener;
    private String[] dishDetails = {"", "", ""};

    @SuppressLint("ClickableViewAccessibility")
    public View onCreateView(LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.edit_recipe_dialog, container, false);
        final TextView recipeName = view.findViewById(R.id.recipe_name);
        final TextView recipeDescription = view.findViewById(R.id.recipe_description);
        final TextView recipePrice = view.findViewById(R.id.recipe_price);
        recipeName.setText(dishDetails[0]);
        recipePrice.setText(dishDetails[1]);
        recipeDescription.setText(dishDetails[2]);
        final Button editButton = view.findViewById(R.id.edit_recipe);
        final Button deleteButton = view.findViewById(R.id.delete_recipe);
        Criteria criteria = new Criteria();
        criteria.setAccuracy(Criteria.ACCURACY_FINE);
        criteria.setPowerRequirement(Criteria.POWER_LOW);
        criteria.setAltitudeRequired(false);
        criteria.setBearingRequired(false);
        criteria.setSpeedRequired(false);
        criteria.setCostAllowed(true);
        LocationManager locationManager = (LocationManager) getActivity().getSystemService
                (getContext().LOCATION_SERVICE);

        String provider = locationManager.getBestProvider(criteria, true);
        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) !=
                PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(),
                Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return null;
        }
        lastLocation = locationManager.getLastKnownLocation(provider);
        editButton.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                //todo Saji do the editing in the FB
                if(motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                    editButton.setBackground(getContext().getDrawable(R.drawable.dialog_button_clicked_drawable));
                } else if(motionEvent.getAction() == MotionEvent.ACTION_UP) {
                    editButton.setBackground(getContext().getDrawable(R.drawable.dialog_button_unclicked_drawable));
                }
                getDialog().dismiss();
                return false;
            }
        });

        deleteButton.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                //todo Saji delete in the FB
                if(motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                    deleteButton.setBackground(getContext().getDrawable(R.drawable.dialog_button_clicked_drawable));
                } else if(motionEvent.getAction() == MotionEvent.ACTION_UP) {
                    deleteButton.setBackground(getContext().getDrawable(R.drawable.dialog_button_unclicked_drawable));
                }
                getDialog().dismiss();
                return false;
            }
        });
        return view;
    }

    public void setOnDismissListener(DialogInterface.OnDismissListener onDismissListener) {
        this.onDismissListener = onDismissListener;
    }

    public void setDishDetails(String[] details) {
        dishDetails = details;
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        if (onDismissListener != null) {
            onDismissListener.onDismiss(dialog);
        }
    }
}
