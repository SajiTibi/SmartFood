package com.example.saji.smartfood;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class ReviewWriteDialog extends DialogFragment {
    int reviewedID;

    @SuppressLint("ClickableViewAccessibility")
    public View onCreateView(LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.create_review_layout, container, false);
        reviewedID = getArguments().getInt(Configs.REVIEWED_ID);
        System.out.println("will write review to: " + reviewedID);
        final EditText reviewDescription = view.findViewById(R.id.review_description);
        final Button addReviewButton = view.findViewById(R.id.add_review_btn);
        final Button cancelReviewButton = view.findViewById(R.id.cancel_review_button);
        addReviewButton.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                    addReviewButton.setBackground(getActivity().getDrawable(R.drawable.dialog_button_clicked_drawable));
                    addReview(reviewedID, reviewDescription.getText().toString());
                } else if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                    addReviewButton.setBackground(getActivity().getDrawable(R.drawable.dialog_button_unclicked_drawable));
                }
                return false;
            }
        });

        cancelReviewButton.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                    cancelReviewButton.setBackground(getActivity().getDrawable(R.drawable.dialog_button_clicked_drawable));
                } else if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                    cancelReviewButton.setBackground(getActivity().getDrawable(R.drawable.dialog_button_unclicked_drawable));
                    getDialog().dismiss();
                }
                return false;
            }
        });
        return view;
    }

    private void addReview(final int reviewedID, final String reviewDescription) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Configs
                .REVIEW_ADD_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                JSONObject jsonResponse;
                try {
                    System.out.printf(response);
                    jsonResponse = new JSONObject(response);
                    boolean success = jsonResponse.getBoolean("success");
                    if (success) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                        builder.setMessage("Review Added Successfully")
                                .setPositiveButton
                                        ("Ok", null)
                                .create()
                                .show();
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
                params.put(Configs.REVIEWED_ID, String.valueOf(reviewedID));
                params.put(Configs.REVIEWER_ID, String.valueOf(MainActivity.loggedUser.getUserID()));
                params.put(Configs.REVIEW_DESCRIPTION, reviewDescription);
                return params;
            }

        };
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        requestQueue.add(stringRequest);
    }
}
