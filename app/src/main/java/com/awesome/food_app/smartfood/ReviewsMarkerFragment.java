package com.awesome.food_app.smartfood;

import android.annotation.SuppressLint;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

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

@SuppressLint("ValidFragment")
public class ReviewsMarkerFragment extends Fragment {
    RecyclerView reviewsRecyclerView;
    RecyclerView.Adapter reviewsRecyclerViewAdapter;
    private ArrayList<ReviewModel> reviewsList = new ArrayList<>();
    int reviewedID;
    int buttonVisibility;

    public ReviewsMarkerFragment(int reviewedID) {
        this.reviewedID = reviewedID;
        buttonVisibility = View.GONE;
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View view = inflater.inflate(R.layout.reviews_marker_layout, container, false);
        reviewsRecyclerView = view.findViewById(R.id.reviews_recycler_view);
        final Button addReviewButton = view.findViewById(R.id.add_review);
        loadReviews();
        reviewsRecyclerViewAdapter = new ReviewsMarkerAdapter(getContext(), reviewedID, reviewsList);
        reviewsRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        reviewsRecyclerView.setAdapter(reviewsRecyclerViewAdapter);
        //todo continue implementing the reviewButton
        addReviewButton.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                    addReviewButton.setBackground(getContext().getDrawable(R.drawable.dialog_button_clicked_drawable));
                    ReviewWriteDialog reviewWriteDialog = new ReviewWriteDialog();
                    Bundle bundle = new Bundle();
                    bundle.putInt(Configs.REVIEWED_ID, reviewedID);
                    reviewWriteDialog.setArguments(bundle);
                    reviewWriteDialog.show(getFragmentManager(), "ReviewWriteDialog");

                } else {
                    addReviewButton.setBackground(getContext().getDrawable(R.drawable.dialog_button_unclicked_drawable));
                }
                return false;
            }
        });
        addReviewButton.setVisibility(buttonVisibility);
        return view;
    }

    @Override
    public void setMenuVisibility(boolean menuVisible) {
        super.setMenuVisibility(menuVisible);
        if (menuVisible) {
            loadReviews();
        }
    }

    public void setButtonVisibility(int visibility) {
        buttonVisibility = visibility;
    }

    private void loadReviews() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Configs
                .REVIEW_RETRIEVER_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                JSONObject jsonResponse;
                try {
                    jsonResponse = new JSONObject(response);
                    reviewsList.clear();
                    boolean success = jsonResponse.getBoolean("success");
                    if (success) {
                        Iterator keyz = jsonResponse.keys();
                        keyz.next();
                        while (keyz.hasNext()) {
                            JSONObject key = jsonResponse.getJSONObject((String) keyz.next());
                            int reviewID = key.getInt(Configs.REVIEW_ID);
                            int reviewerID = key.getInt(Configs.REVIEWER_ID);
                            String reviewDescription = key.getString(Configs.REVIEW_DESCRIPTION);
                            String reviewDate = key.getString(Configs.REVIEW_DATE);
                            UserModel reviewer = findCooker(reviewerID);
                            UserModel reviewed = findCooker(reviewedID);
                            ReviewModel review = new ReviewModel(reviewID, reviewDescription,
                                    reviewDate, reviewer, reviewed);
                            reviewsList.add(review);
                            reviewsRecyclerViewAdapter.notifyDataSetChanged();
                        }

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, null) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put(Configs.REVIEWED_ID, String.valueOf(reviewedID));
                return params;
            }

        };
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        requestQueue.add(stringRequest);
    }

    private UserModel findCooker(int recipeCookerID) {
        for (UserModel user : MainActivity.allUsers) {
            if (user.userID == recipeCookerID) {
                return user;
            }
        }
        return null;
    }

}
