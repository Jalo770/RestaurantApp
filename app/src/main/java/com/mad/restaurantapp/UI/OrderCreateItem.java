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
 * Returns item to add into the order and store through Firebase.
 * Data is then able to be accessed through Firebase.
 */

public class OrderCreateItem extends AppCompatActivity {

    private DatabaseReference mDatabaseReference;

    private final String STATUS = "Status";
    private final String NAME = "Name";
    private final String ALTNAME = "AltName";
    private final String COST = "Cost";
    private final String ORDER_NUMBER = "OrderNumber";
    private final String TABLE_NUMBER = "TableNumber";

    EditText name, altName, cost, orderNumber, tableNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_create_item);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mDatabaseReference = FirebaseDatabase.getInstance().getReference();

        name = (EditText) findViewById(R.id.content_order_create_item_name_string);
        altName = (EditText) findViewById(R.id.content_order_create_item_altname_string);
        cost = (EditText) findViewById(R.id.content_order_create_item_cost_int);
        orderNumber = (EditText) findViewById(R.id.content_order_create_item_order_number_int);
        tableNumber = (EditText) findViewById(R.id.content_order_create_item_table_int);

        // Create objects that is entered in the EditView and is stored in Firebase.
        Button createButton = (Button) findViewById(R.id.content_order_create_item_create_button);
        createButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String nameEntry = name.getText().toString();
                String altNameEntry = altName.getText().toString();
                String costEntry = cost.getText().toString();
                String orderNumbnerEntry = orderNumber.getText().toString();
                String tableNumberEntry = tableNumber.getText().toString();

                DatabaseReference order = mDatabaseReference.child("Order").push();

                order.child(NAME).setValue(nameEntry);
                order.child(ALTNAME).setValue(altNameEntry);
                order.child(COST).setValue(costEntry);
                order.child(ORDER_NUMBER).setValue(orderNumbnerEntry);
                order.child(TABLE_NUMBER).setValue(tableNumberEntry);

                Intent intent = new Intent(OrderCreateItem.this, OrderContent.class);
                setResult(RESULT_OK, intent);
                finish();
            }
        });

        // Returns to the MenuContent Screen.
        Button cancelbutton = (Button) findViewById(R.id.content_order_create_item_cancel_button);
        cancelbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Intent intent = new Intent(OrderCreateItem.this, OrderContent.class);
                finish();
            }
        });

    }

}