package com.example.saji.smartfood;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
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
import org.w3c.dom.Text;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import static android.content.Context.MODE_PRIVATE;

public class AboutTab extends Fragment {
    private Location lastLocation;
    @SuppressLint("ClickableViewAccessibility")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View view = inflater.inflate(R.layout.about_tab, container, false);
        TextView userID = view.findViewById(R.id.user_id);
        TextView userEmail = view.findViewById(R.id.user_email);
        TextView userType = view.findViewById(R.id.user_type);
        userID.setText(userID.getText() + String.valueOf(MainActivity.loggedUser.userID));
        userEmail.setText(userEmail.getText() + MainActivity.loggedUser.emailAddress);
        userType.setText(MainActivity.loggedUser.getUserType().getName());

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
            // TODO: Consider calling
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

        final Button updateMyLocationButton = view.findViewById(R.id.update_location);
        updateMyLocationButton.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                    updateMyLocationButton.setBackground(getContext().getDrawable(R.drawable.button_clicked_drawable));
                    updateLocation(lastLocation,view);
                } else if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                    updateMyLocationButton.setBackground(getContext().getDrawable(R.drawable.button_unclicked_drawable));
                }
                return false;
            }
        });
        final Button logoutButton = view.findViewById(R.id.logout_button);
        logoutButton.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    logoutButton.setBackground(getContext().getDrawable(R.drawable.button_clicked_drawable));
                } else if (event.getAction() == MotionEvent.ACTION_UP) {
                    logoutButton.setBackground(getContext().getDrawable(R.drawable.button_unclicked_drawable));
                    //Deleting saved user & password
                    final String NONE = "";
                    final String PREF = "User";
                    SharedPreferences prefs = getContext().getSharedPreferences(PREF, MODE_PRIVATE);
                    SharedPreferences.Editor editor = prefs.edit();
                    editor.putString("Username", NONE);
                    editor.putString("Key", NONE);
                    editor.putString("Token", NONE);
                    editor.commit();

                    Intent loginIntent = new Intent(getActivity(), LoginActivity.class);
                    getActivity().startActivity(loginIntent);
                    getActivity().finish();
                }
                return false;
            }
        });
        return view;
    }

    private void updateLocation(final Location location, final View view) {
        StringRequest recipesRequest = new StringRequest(Request.Method.POST, Configs
                .UPDATE_LOCATION_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                // we got response as json array within nesed json array so we iterate
                // throught them all to get all recipes
                try {
                    JSONObject jsonResponse = new JSONObject(response);
                    System.out.println(response);
                    boolean success = jsonResponse.getBoolean("success");
                    if (success) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                        builder.setMessage("Location updated").setPositiveButton("Ok",null)
                                .create()
                                .show();
                    }

                } catch (JSONException e) {

                }
            }
        }, null) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put(Configs.USER_ID, String.valueOf(MainActivity.loggedUser.userID));
                params.put(Configs.USER_LONGITUDE, String.valueOf(location.getLongitude()));
                params.put(Configs.USER_LATITUDE, String.valueOf(location.getLatitude()));
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        requestQueue.add(recipesRequest);
    }
}

