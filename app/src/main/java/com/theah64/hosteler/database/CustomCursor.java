package com.theah64.hosteler.database;

import android.database.Cursor;

/**
 * Created by theapache64 on 16/9/17.
 */

public class CustomCursor {

    private final Cursor cursor;

    public CustomCursor(Cursor cursor) {
        this.cursor = cursor;
    }


    public String getStringByColumnIndex(String column) {
        return cursor.getString(cursor.getColumnIndex(column));
    }

    public long getLongByColumnIndex(String column) {
        return cursor.getLong(cursor.getColumnIndex(column));
    }

    public int getIntByColumnIndex(String column) {
        return cursor.getInt(cursor.getColumnIndex(column));
    }

    public boolean getBooleanByColumnIndex(String column) {
        return cursor.getInt(cursor.getColumnIndex(column)) == 1;
    }

    public Cursor getCursor() {
        return cursor;
    }
}
