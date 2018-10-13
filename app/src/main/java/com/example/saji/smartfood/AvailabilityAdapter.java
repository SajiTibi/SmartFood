package com.example.saji.smartfood;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

public class AvailabilityAdapter extends RecyclerView.Adapter<AvailabilityAdapter.ViewHolder> {
    private LayoutInflater mInflater; 
    private CookerSchedule.CustomListener mlistener;//Fix it to listener specifically for Availability
    ArrayList<AvailabilityModel> availabilityModelArrayList;

    public AvailabilityAdapter(Context context, ArrayList<AvailabilityModel> availabilityModelArrayList, CookerSchedule.CustomListener listener) {
        this.availabilityModelArrayList = availabilityModelArrayList;
        mInflater = LayoutInflater.from(context);
        mlistener = listener;
    }

    @Override
    public AvailabilityAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.availability_row, parent, false);

        return new AvailabilityAdapter.ViewHolder(view);
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public void onBindViewHolder(final AvailabilityAdapter.ViewHolder holder, int position) {
        AvailabilityModel currentTime = availabilityModelArrayList.get(position);
        holder.time.setText(currentTime.getTime());
        holder.dishesList = currentTime.getDishesList();
        holder.date.setText(currentTime.getDate());
        holder.availabilityID = currentTime.getAvailabilityID();
        holder.dishesButton.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                return false;
            }
        });



//        holder.recipePurchase.setOnLongClickListener(new View.OnLongClickListener() {
//            @Override
//            public boolean onLongClick(View view) {
//                String[] details = {String.valueOf(holder.recipeName.getText()), String.valueOf(holder.recipePurchase.getText()),
//                        String.valueOf(holder.recipeDescription.getText()), String.valueOf(holder.recipeID)};
//                mlistener.setDishDetails(details);
//                mlistener.onLongClick(view);
//                return false;
//            }
//        });
    }

    @Override
    public int getItemCount() {
        return availabilityModelArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView time;
        String[] dishesList;
        Button dishesButton;
        String availabilityID;
        TextView date;

        public ViewHolder(View itemView) {
            super(itemView);
            time = itemView.findViewById(R.id.time);
            date = itemView.findViewById(R.id.date);
            dishesButton = itemView.findViewById(R.id.dishes_list);
        }
    }
}
