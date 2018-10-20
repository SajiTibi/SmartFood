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
import android.support.v7.app.AlertDialog;
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
    private String[] dishDetails = {"","", "", ""};

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
                if(motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                    editButton.setBackground(getContext().getDrawable(R.drawable.dialog_button_clicked_drawable));
                    final String newRecipeName = (recipeName.getText().equals(""))?
                            dishDetails[0] : String.valueOf(recipeName.getText());
                    final String newRecipePrice = (recipeName.getText().equals(""))?
                            dishDetails[1] : String.valueOf(recipePrice.getText());
                    final String newRecipeDescription = (recipeName.getText().equals(""))?
                            dishDetails[2] : String.valueOf(recipeDescription.getText());
                    final String recipeID =dishDetails[3];
                    StringRequest stringRequest = new StringRequest(Request.Method.POST, Configs
                            .UPDATE_RECIPE_URL, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            JSONObject jsonResponse;
                            try {
                                jsonResponse = new JSONObject(response);
                                boolean success = jsonResponse.getBoolean("success");
                                if (success) {
                                    AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                                    builder.setMessage("Recipe updated successfully").setPositiveButton
                                            ("Ok",null)
                                            .create()
                                            .show();;
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }, null) {
                        @Override
                        protected Map<String, String> getParams() {
                            Map<String, String> params = new HashMap<>();
                            params.put(Configs.RECIPE_ID, recipeID);
                            params.put(Configs.RECIPE_NAME, newRecipeName);
                            params.put(Configs.RECIPE_PRICE, newRecipePrice);
                            params.put(Configs.RECIPE_DESCRIPTION, newRecipeDescription);

                            return params;
                        }

                    };
                    RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
                    requestQueue.add(stringRequest);
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
                if(motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                    deleteButton.setBackground(getContext().getDrawable(R.drawable.dialog_button_clicked_drawable));
                    final String recipeID =dishDetails[3];
                    StringRequest stringRequest = new StringRequest(Request.Method.POST, Configs
                            .DELETE_RECIPE_URL, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            JSONObject jsonResponse;
                            try {
                                jsonResponse = new JSONObject(response);
                                boolean success = jsonResponse.getBoolean("success");
                                if (success) {
                                    AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                                    builder.setMessage("Recipe deleted successfully")
                                            .setPositiveButton
                                            ("Ok",null)
                                            .create()
                                            .show();;
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }, null) {
                        @Override
                        protected Map<String, String> getParams() {
                            Map<String, String> params = new HashMap<>();
                            params.put(Configs.RECIPE_ID, recipeID);

                            return params;
                        }

                    };
                    RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
                    requestQueue.add(stringRequest);
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
