package com.example.saji.smartfood;

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

import java.util.ArrayList;

@SuppressLint("ValidFragment")
public class ReviewsMarkerFragment extends Fragment {
    RecyclerView reviewsRecyclerView;
    RecyclerView.Adapter reviewsRecyclerViewAdapter;
    private ArrayList<ReviewModel> reviewsList = new ArrayList<>();
    int reviewedID;
    int buttonVisibility;

    public ReviewsMarkerFragment(int reviewedID) {
        reviewedID = reviewedID;
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

    private void loadReviews(){
        //todo load the reviews by the param reviewedID  and update the recycler!
    }


}
