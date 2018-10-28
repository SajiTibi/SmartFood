package com.awesome.food_app.smartfood;

import android.Manifest;
import android.annotation.SuppressLint;
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

import java.util.HashMap;
import java.util.Map;

public class AddRecipeDialog extends DialogFragment {
    View view;
    private Location lastLocation;
    private DialogInterface.OnDismissListener onDismissListener;

    @SuppressLint("ClickableViewAccessibility")
    public View onCreateView(LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.add_recipe_dialog, container, false);
        final TextView recipeName = view.findViewById(R.id.recipe_name);
        final TextView recipeDescription = view.findViewById(R.id.recipe_description);
        final TextView recipePrice = view.findViewById(R.id.recipe_price);
        final Button addButton = view.findViewById(R.id.add_recipe);
        final Button cancelButton = view.findViewById(R.id.cancel_recipe);
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
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.

            // no need of any of above since adding is second fragment and we already have
            // permission on first fragment (Map)
            return null;
        }
        lastLocation = locationManager.getLastKnownLocation(provider);
        cancelButton.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                    cancelButton.setBackground(getContext().getDrawable(R.drawable.dialog_button_clicked_drawable));
                } else if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                    cancelButton.setBackground(getContext().getDrawable(R.drawable.dialog_button_unclicked_drawable));
                    getDialog().dismiss();
                }
                return false;
            }
        });

        addButton.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                    addButton.setBackground(getContext().getDrawable(R.drawable.dialog_button_clicked_drawable));
                    addRecipe(recipeName.getText().toString(), recipeDescription.getText().toString(),
                            Double.valueOf(recipePrice.getText().toString()));
                } else if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                    addButton.setBackground(getContext().getDrawable(R.drawable.dialog_button_unclicked_drawable));
                }
                return false;
            }
        });

        return view;
    }

    public void setOnDismissListener(DialogInterface.OnDismissListener onDismissListener) {
        this.onDismissListener = onDismissListener;
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        if (onDismissListener != null) {
            onDismissListener.onDismiss(dialog);
        }
    }

    private void addRecipe(final String recipeName, final String recipeDescription, final Double recipePrice) {
        StringRequest addRecipeSR = new StringRequest(Request.Method.POST, Configs
                .RECIPES_ADD_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                JSONObject jsonResponse;
                try {
                    System.out.println(response);
                    jsonResponse = new JSONObject(response);
                    boolean success = jsonResponse.getBoolean("success");
                    if (success) {
                        Snackbar.make(getActivity().findViewById(android.R.id.content),
                                "Added successfully", Toast.LENGTH_SHORT).show();
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
                params.put(Configs.RECIPE_NAME, recipeName);
                params.put(Configs.RECIPE_DESCRIPTION, recipeDescription);
                params.put(Configs.RECIPE_PRICE, String.valueOf(recipePrice));
                params.put(Configs.RECIPE_COOKER, MainActivity.loggedUser.getEmailAddress());
                params.put(Configs.RECIPE_COOKER_ID, String.valueOf(MainActivity.loggedUser.getUserID()));
                params.put(Configs.RECIPE_LONGITUDE, String.valueOf(lastLocation.getLongitude()));
                params.put(Configs.RECIPE_LATITUDE, String.valueOf(lastLocation.getLatitude()));

                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        requestQueue.add(addRecipeSR);
    }
}
