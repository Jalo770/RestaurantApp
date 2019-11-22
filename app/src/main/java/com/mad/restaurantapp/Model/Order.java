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


public class Order {

    public String mName;
    public String mAltName;
    public String mCost;
    public String mTableNumber;
    public String mOrderNumber;

    public Order() {}

    public Order( String name, String altName, String cost, String tableNumber, String orderNumber) {
        this.mName = name;
        this.mAltName = altName;
        this.mCost = cost;
        this.mTableNumber = tableNumber;
        this.mOrderNumber = orderNumber;
    }

    /** Getter methods for Order Class */


    public String getName() { return mName; }

    public String getAltName() { return mAltName; }

    public String getCost() { return mCost; }

    public String getTableNumber() { return mTableNumber; }

    public String getOrderNumber() { return mOrderNumber; }


    /** Setter methods for Order Class */

    public void setName(String name) { this.mName = name; }

    public void setAltName(String altName) { this.mAltName = altName; }

    public void setCost(String cost) { this.mCost = cost; }

    public void setTableNumber(String tableNumber) { this.mTableNumber = tableNumber; }

    public void setOrderNumber(String orderNumber) { this.mOrderNumber = orderNumber; }
}