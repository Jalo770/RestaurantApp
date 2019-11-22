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

package com.mad.restaurantapp.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Stores local data into SQLiteOpenHelper
 */

public class Database extends SQLiteOpenHelper{

    private static final int DATABASE_VERSION = 1;

    private static final String TAG = "Database";

    private static final String TABLE_NAME = "Booking";

    private static final String KEY_ID = "ID";
    private static final String KEY_NAME = "bookings";


    public Database(Context context) {
        super(context, TABLE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE " + TABLE_NAME + " (ID INTEGER PRIMARY KEY AUTOINCREMENT, "
                + KEY_NAME + " TEXT" + ")";
        db.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVerion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }



    public boolean addBooking(String booking) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_NAME, booking);

        Log.d(TAG, "addOrder: Adding " + booking + " to " + TABLE_NAME);

        long result = db.insert(TABLE_NAME, null, values);

        //if it insert incorrectly into database it returns -1
        if (result == -1) {
            return false;
        } else {
            return true;
        }
    }

    public Cursor getBooking() {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_NAME;
        Cursor data = db.rawQuery(query, null);
        return data;
    }

    public Cursor getBookingID(String booking) {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = " SELECT " + KEY_ID + " FROM " + TABLE_NAME +
                " WHERE " + KEY_NAME + " = '" + booking + "'";
        Cursor data = db.rawQuery(query, null);
        return data;
    }

    public void updateBooking(String newBooking, int id, String oldBooking){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "UPDATE " + TABLE_NAME + " SET " + KEY_NAME +
                " = '" + newBooking + "' WHERE " + KEY_ID + " = '" + id + "'" +
                " AND " + KEY_NAME + " = '" +oldBooking + "'";
        Log.d(TAG, "UpdateOrder: query: " + query);
        Log.d(TAG, "UpdateOrder: Setting name to " + newBooking);
        db.execSQL(query);
    }

    public  void deleteBooking(int id, String booking) {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "DELETE FROM " + TABLE_NAME + " WHERE "
                + KEY_ID + " = '" + id + "'" +
                " AND " + KEY_NAME + " = '" + booking + "'";
        Log.d(TAG, "deleteOrder: query: " + query);
        Log.d(TAG, "deleteOrder: Setting name to " + booking + " from database.");
        db.execSQL(query);
    }

    public int getBookingCount() {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + TABLE_NAME;
        Cursor cursor = db.rawQuery(query, null);
        cursor.close();

        return  cursor.getCount();
    }
}

// https://github.com/mitchtabian/SaveReadWriteDeleteSQLite/tree/master/SaveAndDisplaySQL/app/src/main/java/com/tabian/saveanddisplaysql