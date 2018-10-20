//package com.example.saji.smartfood;
//
//import android.annotation.SuppressLint;
//import android.content.DialogInterface;
//import android.os.Bundle;
//import android.support.v4.app.Fragment;
//import android.support.v7.widget.LinearLayoutManager;
//import android.support.v7.widget.RecyclerView;
//import android.view.LayoutInflater;
//import android.view.MotionEvent;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.Button;
//import android.widget.Toast;
//
//import com.android.volley.Request;
//import com.android.volley.RequestQueue;
//import com.android.volley.Response;
//import com.android.volley.toolbox.StringRequest;
//import com.android.volley.toolbox.Volley;
//
//import org.json.JSONException;
//import org.json.JSONObject;
//
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.Iterator;
//import java.util.Map;
//
//public class CookerSchedule extends Fragment {
//    RecyclerView availabilityRecyclerView;
//    RecyclerView.Adapter availabilityRecyclerViewAdapter;
//    ArrayList<AvailabilityModel> availabilityModelArrayList;
//
//    @SuppressLint("ClickableViewAccessibility")
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        View view = inflater.inflate(R.layout.cooker_availability, container, false);
//        availabilityRecyclerView = view.findViewById(R.id.recipes_recycler_view);
//        availabilityModelArrayList = new ArrayList<>();
//        loadAvailabileTimes();
//        availabilityRecyclerViewAdapter = new AvailabilityAdapter(getContext(), availabilityModelArrayList, new CookerSchedule.CustomListener());
//
//        availabilityRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
//        availabilityRecyclerView.setAdapter(availabilityRecyclerViewAdapter);
//        final Button scheduleButton = view.findViewById(R.id.schedule_time);
//        scheduleButton.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View view, MotionEvent motionEvent) {
//                if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
//
//                    scheduleButton.setBackground(getContext().getDrawable(R.drawable.button_clicked_drawable));
//                } else if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
//                    scheduleButton.setBackground(getContext().getDrawable(R.drawable.button_unclicked_drawable));
//                }
//                return false;
//            }
//        });
//        return view;
//    }
//
//    // TODO maybe to add ability to save recipes locale instead of loading each time from server
//    private void loadAvailabileTimes() {
//        StringRequest recipesRequest = new StringRequest(Request.Method.POST, Configs
//                .RECIPES_RETRIEVAL_URL, new Response.Listener<String>() {
//            @Override
//            public void onResponse(String response) {
//                try {
//                    // we got response as json array within nesed json array so we iterate
//                    // through them all to get all recipes
//                    JSONObject jsonResponse = new JSONObject(response);
//                    boolean success = jsonResponse.getBoolean("success");
//                    if (success) {
//                        availabilityModelArrayList.clear();
//                        Iterator keyz = jsonResponse.keys();
//                        keyz.next();
//                        while (keyz.hasNext()) {
//                            JSONObject key = jsonResponse.getJSONObject((String) keyz.next());
//                            String timeID = ""; // todo get the ID time;
//                            String time = ""; // todo get the availability;
//                            String[] dishesList = {""}; // todo get the array of dishes ids which is compatible with the recipe;
//                            // recipe id will be needed if cook wishes to modify/delete recipe
//                            int recipeID = key.getInt(Configs.RECIPE_ID);
//
//                            // this is temporary, there is no need for recipe cooker since we are
//                            // loading our own recipes, however recipe cooker needed for showing
//                            // menu for other users
//                            RegularRecipe newRecipe = new AvailabilityModel(timeID, time, dishesList,
//                                    MainActivity.loggedUser);
//
//                            availabilityModelArrayList.add(newRecipe);
//                            availabilityRecyclerViewAdapter.notifyDataSetChanged();
//                        }
//                    }
//                } catch (JSONException e) {
//
//                }
//            }
//        }, null) {
//            @Override
//            protected Map<String, String> getParams() {
//                Map<String, String> params = new HashMap<>();
//                params.put(Configs.RECIPE_COOKER_ID, String.valueOf(MainActivity.loggedUser.userID));
//                return params;
//            }
//        };
//        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
//        requestQueue.add(recipesRequest);
//    }
//
//    //Add it to Delete the current Time.
//    public class CustomListener implements View.OnLongClickListener {
//        private String[] dishDetails = {"", "", "", ""};
//
//        @Override
//        public boolean onLongClick(View view) {
//            EditRecipeDialog editRecipeDialog = new EditRecipeDialog();
//            editRecipeDialog.setDishDetails(dishDetails);
//            editRecipeDialog.show(getFragmentManager(), "EditRecipeDialog");
//            editRecipeDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
//                @Override
//                public void onDismiss(DialogInterface dialogInterface) {
//                    loadAvailabileTimes();
//                }
//            });
//            return false;
//        }
//
//        public void setDishDetails(String[] details) {
//            dishDetails = details;
//        }
//    }
//
//}
