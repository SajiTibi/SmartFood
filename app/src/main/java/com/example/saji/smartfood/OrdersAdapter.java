package com.example.saji.smartfood;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

public class OrdersAdapter extends RecyclerView.Adapter<OrdersAdapter.ViewHolder> {
    ArrayList<OrderModule> ordersModelArrayList;
    private LayoutInflater mInflater;

    public OrdersAdapter(Context context, ArrayList<OrderModule> ordersModelArrayList) {
        this.ordersModelArrayList = ordersModelArrayList;
        mInflater = LayoutInflater.from(context);

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.order_row, parent, false);

        return new ViewHolder(view);
    }
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        OrderModule orderModule = ordersModelArrayList.get(position);
        holder.orderCreator.setText(String.valueOf(orderModule.getRecipePurchaserID()));
        holder.orderPurchaseTime.setText(orderModule.getPurchaseTime());

    }

    @Override
    public int getItemCount() {
        return ordersModelArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView orderCreator;
        TextView orderPurchaseTime;
        Button finishOrder;
        Button rejectOrder;
        public ViewHolder(View itemView) {
            super(itemView);
            orderCreator = itemView.findViewById(R.id.order_creator);
            orderPurchaseTime = itemView.findViewById(R.id.order_purchase_time);
            finishOrder = itemView.findViewById(R.id.finish_order);
            rejectOrder = itemView.findViewById(R.id.reject_order);
        }

        @Override
        public void onClick(View view) {

        }
    }

}
