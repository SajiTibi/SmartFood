package com.example.saji.smartfood;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Camera;
import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class FoodMap extends Fragment implements OnMapReadyCallback, LocationListener {
    private static final String TAG = FoodMap.class.getSimpleName();
    List<MarkerOptions> mapMarkers = new ArrayList<>();

    private GoogleMap mMap;
    private boolean mLocationPermissionGranted;
    private Location mLastKnownLocation;
    private FusedLocationProviderClient mFusedLocationProviderClient;
    private ArrayList<RegularRecipe> loadedRecipes;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View view = inflater.inflate(R.layout.activity_map, container, false);
        SupportMapFragment mapFragment = (SupportMapFragment) this.getChildFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(getActivity());
        loadedRecipes = new ArrayList<>();
        updateRecipes();
        return view;
    }

    private void updateRecipes() {
        StringRequest recipesRequest = new StringRequest(Request.Method.POST, Configs
                .ALL_RECIPES_RETRIEVAL_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    System.out.println(response);

                    // we got response as json array within nesed json array so we iterate
                    // throught them all to get all recipes
                    JSONObject jsonResponse = new JSONObject(response);
                    boolean success = jsonResponse.getBoolean("success");
                    if (success) {
                        Iterator keyz = jsonResponse.keys();
                        keyz.next();
                        while (keyz.hasNext()) {
                            JSONObject key = jsonResponse.getJSONObject((String) keyz.next());
                            double recipeLongitude = key.getDouble(Configs.RECIPE_LONGITUDE);
                            double recipeLatitude = key.getDouble(Configs.RECIPE_LATITUDE);
                            String recipeName = key.getString(Configs.RECIPE_NAME);
                            String recipeDescription = key.getString(Configs.RECIPE_DESCRIPTION);
                            double recipePrice = key.getDouble(Configs.RECIPE_PRICE);
                            int recipeCookerID = key.getInt(Configs.RECIPE_COOKER_ID);
                            int recipeID = key.getInt(Configs.RECIPE_ID);
                            UserModel cooker = findCooker(recipeCookerID);
                            if (cooker==null){
                                System.out.println("Cant find cooker in all users");
                                return;
                            }
                            RegularRecipe newRecipe = new RegularRecipe(recipeID,
                                    recipeCookerID,recipeName, cooker, recipeDescription, recipePrice,
                                    recipeLongitude,
                                    recipeLatitude);
                            loadedRecipes.add(newRecipe);
                        }
                        updateMarkersOnMap();
                    }
                } catch (JSONException e) {

                }
            }
        }, null);
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        requestQueue.add(recipesRequest);

    }

    private UserModel findCooker(int recipeCookerID) {
        for (UserModel user : MainActivity.allUsers){
            if (user.userID==recipeCookerID){
                return user;
            }
        }
        return null;
    }

    private void updateMarkersOnMap() {
        mMap.clear();
        for (RegularRecipe recipe : loadedRecipes) {
            LatLng latLng = new LatLng(recipe.getRecipeLongitude(), recipe.getRecipeLatitude());
            String recipeDetails = createInfoString(recipe);
            mapMarkers.add(new MarkerOptions().position(latLng).title(recipe.getRecipeName()).snippet
                    (recipeDetails));
        }
        for (MarkerOptions markerOptions : mapMarkers) {
            mMap.addMarker(markerOptions);
        }
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {

        mMap = googleMap;
        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) !=
                PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(),
                Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission
                    .ACCESS_FINE_LOCATION}, 1);
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission
                    .ACCESS_COARSE_LOCATION}, 1);

            /*
             * Request location permission, so that we can get the location of the
             * device. The result of the permission request is handled by a callback,
             * onRequestPermissionsResult.
             */
            getLocationPermission();
        }
        mLocationPermissionGranted = true;
        mMap.setMyLocationEnabled(true);
        mMap.setInfoWindowAdapter(new MyInfoAdapter());

        getDeviceLocation();
    }

    @Override
    public void onLocationChanged(Location location) {
        LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());

        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(latLng, 15);
        mMap.moveCamera(cameraUpdate);
    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

    }

    @Override
    public void onProviderEnabled(String s) {

    }

    @Override
    public void onProviderDisabled(String s) {

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String permissions[],
                                           @NonNull int[] grantResults) {
        mLocationPermissionGranted = false;
        switch (requestCode) {
            case 1: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    mLocationPermissionGranted = true;
                }
            }
        }
        updateLocationUI();
    }


    private void getDeviceLocation() {
        /*
         * Get the best and most recent location of the device, which may be null in rare
         * cases when a location is not available.
         */
        try {

            if (mLocationPermissionGranted) {
                Task locationResult = mFusedLocationProviderClient.getLastLocation();
                locationResult.addOnCompleteListener(getActivity(), new OnCompleteListener() {
                    @Override
                    public void onComplete(@NonNull Task task) {
                        if (task.isSuccessful()) {
                            // Set the map's camera position to the current location of the device.
                            mLastKnownLocation = (Location) task.getResult();
                            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(
                                    new LatLng(mLastKnownLocation.getLatitude(),
                                            mLastKnownLocation.getLongitude()), 15));
                        } else {
                            Log.d(TAG, "Current location is null. Using defaults.");
                            Log.e(TAG, "Exception: %s", task.getException());
                            mMap.getUiSettings().setMyLocationButtonEnabled(false);
                        }
                    }
                });
            }
        } catch (SecurityException e) {
            Log.e("Exception: %s", e.getMessage());
        }
    }

    private void updateLocationUI() {
        if (mMap == null) {
            return;
        }
        try {
            if (mLocationPermissionGranted) {
                mMap.setMyLocationEnabled(true);
                mMap.getUiSettings().setMyLocationButtonEnabled(true);
            } else {
                mMap.setMyLocationEnabled(false);
                mMap.getUiSettings().setMyLocationButtonEnabled(false);
                mLastKnownLocation = null;
                getLocationPermission();
            }
        } catch (SecurityException e) {
            Log.e("Exception: %s", e.getMessage());
        }
    }

    private void getLocationPermission() {
        /*
         * Request location permission, so that we can get the location of the
         * device. The result of the permission request is handled by a callback,
         * onRequestPermissionsResult.
         */
        if (ContextCompat.checkSelfPermission(getActivity().getApplicationContext(),
                android.Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            mLocationPermissionGranted = true;
        } else {
            ActivityCompat.requestPermissions(getActivity(),
                    new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, 1);
        }
    }

    // This is done since marker can have only snippet which is String so we build a String that
    // contains all of our information then we extract it
    private String createInfoString(RegularRecipe recipeModel) {

        String recipeInfo = "";
        recipeInfo += recipeModel.getRecipeName() + Configs.SEPARATOR;
        recipeInfo += recipeModel.getRecipeCooker().getEmailAddress() + Configs.SEPARATOR;
        recipeInfo += recipeModel.getRecipeDescription() + Configs.SEPARATOR;
        recipeInfo += recipeModel.getRecipePrice() + Configs.SEPARATOR;
        recipeInfo += recipeModel.getRecipeID()+Configs.SEPARATOR;
        recipeInfo += recipeModel.getRecipeCookerID();
        return recipeInfo;
    }


    private HashMap<String, String> stringToInfo(String string) {
        HashMap<String, String> infoMap = new HashMap<>();
        String[] stringSplit = string.split(Configs.SEPARATOR);
        infoMap.put(Configs.RECIPE_NAME, stringSplit[0]);
        infoMap.put(Configs.RECIPE_COOKER, stringSplit[1]);
        infoMap.put(Configs.RECIPE_DESCRIPTION, stringSplit[2]);
        infoMap.put(Configs.RECIPE_PRICE, stringSplit[3]);
        infoMap.put(Configs.RECIPE_ID,stringSplit[4]);
        infoMap.put(Configs.RECIPE_COOKER_ID,stringSplit[5]);

        return infoMap;
    }

    private class MyInfoAdapter implements GoogleMap.InfoWindowAdapter {
        private final View myContestView;

        MyInfoAdapter() {
            myContestView = getActivity().getLayoutInflater().inflate(R.layout
                    .custom_recipe_info, null);
        }

        @Override
        public View getInfoWindow(Marker marker) {
            return null;
        }

        @Override
        public View getInfoContents(Marker marker) {
            HashMap<String, String> recipeDetails = stringToInfo(marker.getSnippet());

            String recipeName = recipeDetails.get(Configs.RECIPE_NAME);
            String recipeCooker = recipeDetails.get(Configs.RECIPE_COOKER);
            String recipeDescription = recipeDetails.get(Configs.RECIPE_DESCRIPTION);
            double recipePrice = Double.parseDouble(recipeDetails.get(Configs.RECIPE_PRICE));
            final int recipeID = Integer.parseInt(recipeDetails.get(Configs.RECIPE_ID));
            final int recipeCookerID = Integer.parseInt(recipeDetails.get(Configs.RECIPE_COOKER_ID));
            TextView recipeNameTV = myContestView.findViewById(R.id.recipe_name);
            TextView recipeCookerTV = myContestView.findViewById(R.id.recipe_cooker);
            TextView recipeDescriptionTV = myContestView.findViewById(R.id
                    .recipe_description);
            Button recipePurchaseBTN = myContestView.findViewById(R.id.purchase_recipe);
            recipeNameTV.setText(recipeName);
            recipeCookerTV.setText(recipeCooker);
            recipeDescriptionTV.setText(recipeDescription);
            recipePurchaseBTN.setText(String.valueOf(recipePrice));
            mMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
                @Override
                public void onInfoWindowClick(Marker marker) {
                    placeAnOrder(recipeID,MainActivity.loggedUser.getUserID(),recipeCookerID);
                }
            });
            return myContestView;
        }
    }
    private void placeAnOrder(final int recipeID, final int recipePurchaserID, final int recipeCookerID){
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
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        requestQueue.add(addRecipeSR);
    }
}
