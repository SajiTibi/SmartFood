package com.example.saji.smartfood;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
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

    public View onCreateView(LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.create_review_layout, container, false);
        reviewedID=  getArguments().getInt(Configs.REVIEWED_ID);
        System.out.println("will write review to: "+reviewedID);
        final EditText reviewName =view.findViewById(R.id.review_name_create);
        final EditText reviewDescription = view.findViewById(R.id.review_description);
        Button addRecipeButton = view.findViewById(R.id.add_review_btn);
        Button cancelReviewButton = view.findViewById(R.id.cancel_review_button);
        addRecipeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addReview(reviewedID,reviewName.getText().toString(),reviewDescription.getText().toString());

            }
        });
        cancelReviewButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getDialog().dismiss();
            }
        });
        return view;
    }

    private void addReview(final int reviewedID, final String reviewName, final String reviewDescription) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Configs
                .REVIEW_ADD_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                JSONObject jsonResponse;
                try {
                    jsonResponse = new JSONObject(response);
                    boolean success = jsonResponse.getBoolean("success");
                    if (success) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                        builder.setMessage("Review deleted successfully")
                                .setPositiveButton
                                        ("Ok",null)
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
                params.put(Configs.REVIEWER_ID,String.valueOf(MainActivity.loggedUser.getUserID()));
                params.put(Configs.REVIEW_DESCRIPTION,reviewDescription);
                return params;
            }

        };
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        requestQueue.add(stringRequest);
    }
}
