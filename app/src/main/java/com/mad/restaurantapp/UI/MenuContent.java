/*
 * Copyright (C) 2017
 *
 * Created by Jason on 13/10/2017..
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
import com.mad.restaurantapp.Model.Menu;
import com.mad.restaurantapp.Model.MenuAdapter;
import com.mad.restaurantapp.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


/**
 * Obtains data from Firebase and places it into a RecyclerView through
 * the MenuAdapter and displays it in the RecyclerView.
 */

public class MenuContent extends AppCompatActivity {

    private DatabaseReference mDatabaseReference;

    private List<Menu> menuList = new ArrayList<>();
    private MenuAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_content);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final RecyclerView recyclerViewMenu = (RecyclerView) findViewById(R.id.content_menu_content_recyclerview);

        mDatabaseReference = FirebaseDatabase.getInstance().getReference().child("Menu");

        mDatabaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                showData(dataSnapshot);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        mAdapter = new MenuAdapter(menuList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerViewMenu.setLayoutManager(mLayoutManager);
        recyclerViewMenu.setItemAnimator(new DefaultItemAnimator());
        recyclerViewMenu.setAdapter(mAdapter);


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MenuContent.this, MenuAddItem.class);
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
            if (resultCode == MenuAddItem.RESULT_OK) {
                Toast.makeText(MenuContent.this, "Menu Added", Toast.LENGTH_SHORT).show();

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
            String first = (String) map.get("MenuID");
            String second = (String) map.get("Name");
            String third = (String) map.get("AltName");
            String fourth = (String) map.get("Category");
            String fifth = (String) map.get("Cost");

            //Check if data is correctly downloaded
            Log.d("child", "onChildAdded1: " + first);
            Log.d("child", "onChildAdded2: " + second);
            Log.d("child", "onChildAdded3: " + third);
            Log.d("child", "onChildAdded4: " + fourth);
            Log.d("child", "onChildAdded5: " + fifth);

            //add
            Menu menu = new Menu(first, second, third, fourth, fifth);
            menuList.add(menu);
            mAdapter.notifyDataSetChanged();
        }
    }
}