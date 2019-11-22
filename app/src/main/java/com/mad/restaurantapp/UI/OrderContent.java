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
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.mad.restaurantapp.Model.Order;
import com.mad.restaurantapp.Model.OrderAdapter;
import com.mad.restaurantapp.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Obtains data from Firebase and places it into a RecyclerView through
 * the OrderAdapter and displays it in the RecyclerView.
 */

public class OrderContent extends AppCompatActivity {

    private DatabaseReference mDatabaseReference;

    private List<Order> orderList = new ArrayList<>();
    private OrderAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_content);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        final RecyclerView recyclerViewMenu = (RecyclerView) findViewById(R.id.content_order_content_recyclerview);

        mDatabaseReference = FirebaseDatabase.getInstance().getReference().child("Order");

        mAdapter = new OrderAdapter(orderList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerViewMenu.setLayoutManager(mLayoutManager);
        recyclerViewMenu.setItemAnimator(new DefaultItemAnimator());
        recyclerViewMenu.setAdapter(mAdapter);

        mDatabaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                showData(dataSnapshot);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(OrderContent.this, OrderCreateItem.class);
                startActivityForResult(intent, 1);
            }
        });
    }

    /**
     * Refresh the UI when the data is added.
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if (resultCode == OrderCreateItem.RESULT_OK) {
                Toast.makeText(OrderContent.this, "Order Created", Toast.LENGTH_SHORT).show();

                finish();
                startActivity(getIntent());
            }
        }
    }

    /**
     * Gets data from Firebase then adds into the local data and
     * updates the adapter.
     */
    private void showData(DataSnapshot dataSnapshot) {
        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
            Map<String, Object> map = (Map<String, Object>) snapshot.getValue();
            String first = (String) map.get("Name");
            String second = (String) map.get("AltName");
            String third = (String) map.get("Cost");
            String fourth = (String) map.get("OrderNumber");
            String fifth = (String) map.get("TableNumber");

            // Check if data is correctly downloaded
            Log.d("child", "onChildAdded1: " + first);
            Log.d("child", "onChildAdded2: " + second);
            Log.d("child", "onChildAdded3: " + third);
            Log.d("child", "onChildAdded4: " + fourth);
            Log.d("child", "onChildAdded5: " + fifth);

            // Adds data received from Firebase into a arraylist for RecyclerView
            Order order = new Order(first, second, third, fourth, fifth);
            orderList.add(order);
            mAdapter.notifyDataSetChanged();
        }
    }
}