/*
 * Copyright (C) 2017
 *
 * Created by Jason Lo on 20/09/2017.
 *
 * This version of the application is fully copyrighted
 * for educational purpose and may not be used for any
 * reasons.
 *
 */


package com.mad.restaurantapp.UI;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.mad.restaurantapp.Database.Database;
import com.mad.restaurantapp.R;

import java.util.ArrayList;

/**
 * Main page that has a side menu and displays a ListView.
 * Also has the function to signout of the app.
 */

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    Database mDatabase;

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    private static final String TAG = "ListView MainActivity";

    private ListView mListView;

    private DialogInterface dialogInterface;

    private View mView;
    private EditText mOrderName;
    private Button mAdd, mCancel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mListView = (ListView) findViewById(R.id.content_main_listview);
        mDatabase = new Database(this);

        Log.d(TAG, "ISSUES");
        populateListView();


        //Log out method
        mAuth = FirebaseAuth.getInstance();
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if (firebaseAuth.getCurrentUser() == null){
                    startActivity(new Intent(MainActivity.this, LogIn.class));
                }
            }
        };



        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final AlertDialog.Builder mBuilder = new AlertDialog.Builder(MainActivity.this);
                mView = getLayoutInflater().inflate(R.layout.dialog_new_order, null);
                mOrderName = (EditText) mView.findViewById(R.id.dialog_new_order_order_name);
                mAdd = (Button) mView.findViewById(R.id.dialog_new_order_add_button);
                mCancel = (Button) mView.findViewById(R.id.dialog_new_order_cancel_button);

                mBuilder.setView(mView);
                final AlertDialog dialog = mBuilder.create();

                mAdd.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String newBooking = mOrderName.getText().toString();
                        if(!newBooking.isEmpty()) {
                            addBooking(newBooking);
                            dialog.dismiss();
                            Intent intent = getIntent();
                            startActivityForResult(intent, 1);
                        } else {
                            toastMessage("You must insert order");
                        }
                    }
                });

                mCancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });

                dialog.show();

            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    /**
     * Add order into SQLite database.
     */
    public void addBooking(String newBooking) {
        boolean insert = mDatabase.addBooking(newBooking);

        if (insert) {
            toastMessage("Order made");
        } else {
            toastMessage("Failed to make order.");
        }
    }

    /**
     * Creates a Toast Message.
     */
    private void toastMessage(String message) {
        Toast.makeText(MainActivity.this, message, Toast.LENGTH_SHORT).show();
    }


    /**
     * Iterate through the list and add item into a list view.
     * Also ListView items can be clicked.
     */
    private void populateListView() {
        Log.d(TAG, "Displaying data in the ListView");

        Cursor data = mDatabase.getBooking();
        ArrayList<String> listData = new ArrayList<>();
        while(data.moveToNext()) {
            listData.add(data.getString(1));
        }
        ListAdapter adapter = new ArrayAdapter<>(MainActivity.this, android.R.layout.simple_list_item_1, listData);
        mListView.setAdapter(adapter);


        // Setting ListView items to be clickable
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String name = parent.getItemAtPosition(position).toString();
                Log.d(TAG, "onItemClick: You Clicked on " + name);

                Cursor data = mDatabase.getBookingID(name);
                int itemID = -1;
                while (data.moveToNext()) {
                    itemID = data.getInt(0);
                }
                if (itemID > -1) {
                    Log.d(TAG, "onItemClick: The ID is: " + itemID);

                    Intent intent = new Intent(MainActivity.this, BookingEditItem.class);
                    intent.putExtra("id", itemID);
                    intent.putExtra("name",name);
                    startActivityForResult(intent, 1);
                } else {
                    toastMessage("No ID associated with that name");
                }
            }
        });

    }


    /**
     * Refresh page when result is returned.
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if (resultCode == BookingEditItem.RESULT_OK) {
                finish();
                startActivity(getIntent());
            }
        }
    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_menu) {
            Intent intent = new Intent(MainActivity.this, MenuContent.class);
            startActivity(intent);

        } else if (id == R.id.nav_order) {
            Intent intent = new Intent(MainActivity.this, OrderContent.class);
            startActivity(intent);


        } else if (id == R.id.nav_logout) {
            mAuth.signOut();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    protected void onStart(){
        super.onStart();

        mAuth.addAuthStateListener(mAuthListener);
    }
}