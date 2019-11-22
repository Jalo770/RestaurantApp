/*
 * Copyright (C) 2017
 *
 * Created by Jason on 13/10/2017.
 *
 * This version of the application is fully copyrighted
 * for educational purpose and may not be used for any
 * reasons.
 *
 */

package com.mad.restaurantapp.Model;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mad.restaurantapp.R;

import java.util.List;

/**
 * Model class for Order that has a ViewHolder and BindView to place objects from
 * Firebase into a RecyclerView.
 */

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.ViewHolder> {

    private List<Order> orderList;
    public OrderAdapter(List<Order> order) { this.orderList = order; }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView name, altName, cost, tableNumber, orderNumber;

        public ViewHolder(View view) {
            super(view);
            name = (TextView) view.findViewById(R.id.item_order_name_string);
            altName = (TextView) view.findViewById(R.id.item_order_altname_string);
            cost = (TextView) view.findViewById(R.id.item_order_cost_int);
            tableNumber = (TextView) view.findViewById(R.id.item_order_table_int);
            orderNumber = (TextView) view.findViewById(R.id.item_order_order_int);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_order, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Order order = orderList.get(position);
        holder.name.setText(order.getName());
        holder.altName.setText(order.getAltName());
        holder.cost.setText(order.getCost());
        holder.tableNumber.setText(order.getTableNumber());
        holder.orderNumber.setText(order.getOrderNumber());
    }

    @Override
    public int getItemCount() { return orderList.size(); }

}