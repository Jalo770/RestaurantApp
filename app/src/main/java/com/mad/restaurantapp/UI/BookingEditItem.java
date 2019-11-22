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


package com.mad.restaurantapp.UI;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.mad.restaurantapp.Database.Database;
import com.mad.restaurantapp.R;


/**
 *  Onclick booking items will be able to updated or deleted
 */

public class BookingEditItem extends AppCompatActivity {

    private static final String TAG = "NewOrderEdit";

    Database mDatabase;

    private EditText orderName;
    private Button updateButton, deleteButton, cancelButton;

    private int selectedID;
    private String selectedName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking_edit_item);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mDatabase = new Database(this);

        orderName = (EditText) findViewById(R.id.content_booking_edit_item_booking_detail_string);
        updateButton = (Button) findViewById(R.id.content_booking_edit_item_update_button);
        deleteButton = (Button) findViewById(R.id.content_booking_edit_item_delete_button);
        cancelButton = (Button) findViewById(R.id.content_booking_edit_item_cancel_button);

        Intent intent = getIntent();

        selectedID = intent.getIntExtra("id", -1);

        selectedName = intent.getStringExtra("name");

        orderName.setText(selectedName);

        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String entry = orderName.getText().toString();
                if (!entry.equals("")){
                    mDatabase.updateBooking(entry, selectedID, selectedName);
                    toastMessage("Updated Booking");

                    Intent intent = new Intent(BookingEditItem.this, MainActivity.class);
                    setResult(RESULT_OK, intent);
                    finish();
                }else{
                    toastMessage("Enter a order name");
                }
            }
        });

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDatabase.deleteBooking(selectedID, selectedName);
                orderName.setText("");
                toastMessage("Deleted from database");
                Intent intent = new Intent(BookingEditItem.this, MainActivity.class);
                setResult(RESULT_OK, intent);
                finish();
            }
        });

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    /**
     * Creates a Toast Message.
     */
    private void toastMessage(String message) {
        Toast.makeText(BookingEditItem.this, message, Toast.LENGTH_SHORT).show();
    }
}
