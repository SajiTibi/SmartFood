package com.example.saji.smartfood;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.DragEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

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

public class OrdersAdapter extends RecyclerView.Adapter<OrdersAdapter.ViewHolder> {
    ArrayList<OrderModule> ordersModelArrayList;
    private LayoutInflater mInflater;
    private Context context;
    public OrdersAdapter(Context context, ArrayList<OrderModule> ordersModelArrayList) {
        this.ordersModelArrayList = ordersModelArrayList;
        mInflater = LayoutInflater.from(context);
        this.context= context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.order_row, parent, false);

        return new ViewHolder(view);
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        final OrderModule orderModule = ordersModelArrayList.get(position);
        int state = orderModule.getState();
        // TODO rony handle rejected, db already know if its rejected only left to change here
        // when its loaded in adapter. also see File:Configs ORDER_STATUS_ACCEPTED and other
        // variables if its matches your definition (as 0,1,2).

        final Integer ACCE_REJ_BUTTON_VISIBILITY = (state == 0) ? View.VISIBLE: View.GONE;
        final Integer FINISH_BUTTON_VISIBILITY = (state == 1) ? View.VISIBLE: View.GONE;
        final Integer DONE_BUTTON_VISIBILITY = (state == 2) ? View.VISIBLE: View.GONE;

        holder.acceptOrder.setVisibility(ACCE_REJ_BUTTON_VISIBILITY);
        holder.rejectOrder.setVisibility(ACCE_REJ_BUTTON_VISIBILITY);
        holder.finishOrder.setVisibility(FINISH_BUTTON_VISIBILITY);
        holder.doneOrder.setVisibility(DONE_BUTTON_VISIBILITY);


        holder.orderCreator.setText(String.valueOf(orderModule.getRecipePurchaserID()));
        holder.orderPurchaseTime.setText(orderModule.getPurchaseTime());


        holder.acceptOrder.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                    holder.acceptOrder.setBackground(mInflater.getContext().getDrawable(R.drawable.button_clicked_drawable));
                    holder.acceptOrder.setVisibility(View.GONE);
                    holder.rejectOrder.setVisibility(View.GONE);
                    holder.finishOrder.setVisibility(View.VISIBLE);
                    updateOrderStatus(orderModule.getOrderID(),Configs.ORDER_STATUS_ACCEPTED);
                } else {
                    holder.acceptOrder.setBackground(mInflater.getContext().getDrawable(R.drawable.button_unclicked_drawable));
                }
                return false;
            }
        });

        holder.rejectOrder.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                    holder.rejectOrder.setBackground(mInflater.getContext().getDrawable(R.drawable.button_clicked_drawable));
                    updateOrderStatus(orderModule.getOrderID(),Configs.ORDER_STATUS_REJECTED);

                } else {
                    holder.rejectOrder.setBackground(mInflater.getContext().getDrawable(R.drawable.button_unclicked_drawable));
                }
                return false;
            }
        });

        holder.finishOrder.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                    holder.finishOrder.setBackground(mInflater.getContext().getDrawable(R.drawable.dialog_button_clicked_drawable));
                    holder.finishOrder.setVisibility(View.GONE);
                    holder.doneOrder.setVisibility(View.VISIBLE);
                    updateOrderStatus(orderModule.getOrderID(),Configs.ORDER_STATUS_FINISHED);

                } else {
                    holder.finishOrder.setBackground(mInflater.getContext().getDrawable(R.drawable.dialog_button_clicked_drawable));
                    AlertDialog.Builder builder = new AlertDialog.Builder(mInflater.getContext());
                    builder.setMessage("Long Click to Approve That The Customer Got his/her Order!").setPositiveButton
                            ("Ok",null)
                            .create()
                            .show();
                }
                return false;
            }
        });

        holder.doneOrder.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                //todo Saji Delete From Data Base/ Meaning that the customer took his Order.
                //@Rony: maybe keep it as history?
                return false;
            }
        });

    }

    private void updateOrderStatus(final int orderID, final String newOrderStatus) {
        StringRequest recipesRequest = new StringRequest(Request.Method.POST, Configs
                .UPDATE_ORDER_STATUS_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonResponse = new JSONObject(response);
                    boolean success = jsonResponse.getBoolean("success");
                    if (success) {
                        System.out.println("success");
                    }
                } catch (JSONException e) {

                }
            }
        }, null) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put(Configs.ORDER_ID, String.valueOf(orderID));
                params.put(Configs.ORDER_STATUS, newOrderStatus);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(recipesRequest);
    }


    @Override
    public int getItemCount() {
        return ordersModelArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView orderCreator;
        TextView orderPurchaseTime;
        Button finishOrder;
        Button acceptOrder;
        Button rejectOrder;
        Button doneOrder;
        public ViewHolder(View itemView) {
            super(itemView);
            orderCreator = itemView.findViewById(R.id.order_creator);
            orderPurchaseTime = itemView.findViewById(R.id.order_purchase_time);
            finishOrder = itemView.findViewById(R.id.finish_order);
            rejectOrder = itemView.findViewById(R.id.reject_order);
            acceptOrder = itemView.findViewById(R.id.accept_order);
            doneOrder = itemView.findViewById(R.id.done_order);
        }

        @Override
        public void onClick(View view) {

        }
    }

}
