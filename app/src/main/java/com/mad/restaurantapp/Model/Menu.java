/*
 * Copyright (C) 2017
 *
 * Created by Jason on 01/10/2017.
 *
 * This version of the application is fully copyrighted
 * for educational purpose and may not be used for any
 * reasons.
 *
 */

package com.mad.restaurantapp.Model;


public class Menu {

    private String mMenuId;
    private String mName;
    private String mAltName;
    private String mCategory;
    private String mCost;

    public Menu() {

    }

    public Menu(String menuId, String name, String altName, String category, String cost){
        this.mMenuId = menuId;
        this.mName = name;
        this.mAltName = altName;
        this.mCategory = category;
        this.mCost = cost;
    }

    /** Getter methods for Menu Class */

    public String  getMenuId() { return mMenuId; }

    public String getName() { return mName; }

    public String getAltName() { return mAltName; }

    public String getCategory() { return mCategory; }

    public String getCost() { return mCost; }



    /** Setter methods for Menu Class */

    public void setMenuId(String  mMenuId) { this.mMenuId = mMenuId; }

    public void setName(String mName) { this.mName = mName; }

    public void setAltName(String mAltNameName) { this.mAltName = mAltNameName; }

    public void setCategory(String mCategory) { this.mCategory = mCategory; }

    public void setCost(String  mCost) { this.mCost = mCost; }
}
