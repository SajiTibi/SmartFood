package com.example.saji.smartfood;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class ReviewsMarkerAdapter extends RecyclerView.Adapter<ReviewsMarkerAdapter.ViewHolder> {
    private LayoutInflater mInflater;
    ArrayList<ReviewModel> reviewsModelArrayList;
    private int cookerID;

    public ReviewsMarkerAdapter(Context context, int cookerID, ArrayList<ReviewModel> reviewsModelList) {

        this.mInflater = LayoutInflater.from(context);
        this.cookerID = cookerID;
        this.reviewsModelArrayList = reviewsModelList;

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = mInflater.inflate(R.layout.review_row, parent, false);

        return new com.example.saji.smartfood.ReviewsMarkerAdapter.ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        ReviewModel currentRecipe = reviewsModelArrayList.get(position);
        holder.reviewerName.setText(currentRecipe.getReviewer().getEmailAddress());
        holder.reviewBody.setText(currentRecipe.getReviewBody());
        holder.date.setText(currentRecipe.getReviewDate());
    }

    @Override
    public int getItemCount() {
        return reviewsModelArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView reviewerName;
        TextView reviewBody;
        TextView date;

        public ViewHolder(View itemView) {
            super(itemView);
            reviewerName = itemView.findViewById(R.id.review_name_create);
            reviewBody = itemView.findViewById(R.id.review_body);
            date = itemView.findViewById(R.id.review_date);
        }
    }

}
