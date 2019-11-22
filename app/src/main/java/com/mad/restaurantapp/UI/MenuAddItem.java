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



package com.mad.restaurantapp.UI;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.mad.restaurantapp.R;

/**
 * Returns item to add into the menu and store through Firebase
 * Data is then able to be accessed through Firebase.
 */

public class MenuAddItem extends AppCompatActivity {

    private DatabaseReference mDatabaseReference;

    private final String NAME = "Name";
    private final String ALTNAME = "AltName";
    private final String MENUID = "MenuID";
    private final String CATEOGRY = "Category";
    private final String COST = "Cost";
    private final String GST = "GST";
    private final String DESCRIPTION = "Description";
    private final String INGREDIENTS = "Ingredients";

    EditText name, altName, menuId, category, cost, gst, description, ingredients;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_add_item);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mDatabaseReference = FirebaseDatabase.getInstance().getReference();

        name = (EditText) findViewById(R.id.content_menu_add_item_name_string);
        altName = (EditText) findViewById(R.id.content_menu_add_item_alt_name_string);
        menuId = (EditText) findViewById(R.id.content_menu_add_item_menu_id_int);
        category = (EditText) findViewById(R.id.content_menu_add_item_category_string);
        cost = (EditText) findViewById(R.id.content_menu_add_item_total_cost_int);
        gst = (EditText) findViewById(R.id.content_menu_add_item_gst_int);
        description = (EditText) findViewById(R.id.content_menu_add_item_description_string);
        ingredients = (EditText) findViewById(R.id.content_menu_add_item_ingredients_string);

        // Create objects that is entered in the EditView and is stored in Firebase.
        Button addButton = (Button) findViewById(R.id.content_menu_add_item_add_button);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String nameEntry = name.getText().toString();
                String altNameEntry = altName.getText().toString();
                String menuIdEntry = menuId.getText().toString();
                String categoryEntry = category.getText().toString();
                String costEntry = cost.getText().toString();
                String gstEntry = gst.getText().toString();
                String descriptionEntry = description.getText().toString();
                String ingredientsEntry = ingredients.getText().toString();

                DatabaseReference menu = mDatabaseReference.child("Menu").push();

                menu.child(NAME).setValue(nameEntry);
                menu.child(ALTNAME).setValue(altNameEntry);
                menu.child(MENUID).setValue(menuIdEntry);
                menu.child(CATEOGRY).setValue(categoryEntry);
                menu.child(COST).setValue(costEntry);
                menu.child(GST).setValue(gstEntry);
                menu.child(DESCRIPTION).setValue(descriptionEntry);
                menu.child(INGREDIENTS).setValue(ingredientsEntry);

                Intent intent = new Intent(MenuAddItem.this, MenuContent.class);
                setResult(RESULT_OK, intent);
                finish();
            }
        });

        // Returns to the MenuContent Screen.
        Button cancelbutton = (Button) findViewById(R.id.content_menu_add_item_cancel_button);
        cancelbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Intent intent = new Intent(MenuAddItem.this, MenuContent.class);
                finish();
            }
        });
    }

}