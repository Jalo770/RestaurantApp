/*
 * Copyright (C) 2017
 *
 * Jason Lo on 06/10/2017.
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
 * Model class for Menu that has a ViewHolder and BindView to place objects from
 * Firebase into a RecyclerView.
 */

public class MenuAdapter extends RecyclerView.Adapter<MenuAdapter.ViewHolder> {

    private List<Menu> menuList;
    public MenuAdapter(List<Menu> menu) { this.menuList = menu; }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView menuId, name, altName, category, cost;

        public ViewHolder(View view) {
            super(view);
            menuId = (TextView) view.findViewById(R.id.menu_item_menu_id_int);
            name = (TextView) view.findViewById(R.id.menu_item_name_string);
            altName = (TextView) view.findViewById(R.id.menu_item_alt_name_string);
            category = (TextView) view.findViewById(R.id.menu_item_category_string);
            cost = (TextView) view.findViewById(R.id.menu_item_cost_int);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_menu, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Menu menu = menuList.get(position);
        holder.menuId.setText(menu.getMenuId());
        holder.name.setText(menu.getName());
        holder.altName.setText(menu.getAltName());
        holder.category.setText(menu.getCategory());
        holder.cost.setText(menu.getCost());
    }

    @Override
    public int getItemCount() {
        return menuList.size();
    }

}