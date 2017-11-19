package com.theah64.hosteler.database.tables;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.Nullable;
import android.util.Log;


import com.theah64.hosteler.utils.FileUtils;

import java.io.IOException;
import java.util.List;

/**
 * Created by shifar on 11/5/16.
 */
public class BaseTable<T> extends SQLiteOpenHelper {

    public static final String FALSE = "0";

    public static final String COLUMN_ID = "id";
    public static final String TRUE = "1";
    static final String COLUMN_CREATED_AT = "created_at";
    private static final int DATABASE_VERSION = 1;
    private static final String X = BaseTable.class.getSimpleName();
    private static final String FATAL_ERROR_UNDEFINED_METHOD = "Undefined method";
    protected Context context;
    private final String tableName;

    protected BaseTable(final Context context, String tableName) {
        super(context, context.getPackageName() + ".db", null, DATABASE_VERSION);
        this.context = context;
        this.tableName = tableName;
    }


    public void setContext(Context context) {
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        try {
            //Creating database structure
            final String[] createStatements = FileUtils.readTextualAsset(context, "database.sql").split(";");

            for (final String stmt : createStatements) {
                if (!stmt.trim().isEmpty()) {
                    Log.d(X, "Statement : " + stmt);
                    db.execSQL(stmt + ";");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            throw new IllegalArgumentException(e.getMessage());
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS tracks");
        onCreate(db);
    }

    public void cleanDb() {
        onUpgrade(this.getWritableDatabase(), 0, 0);
    }

    public T get(final String column, final String value) {
        throw new IllegalArgumentException(FATAL_ERROR_UNDEFINED_METHOD);
    }


    public long add(T newInstance) {
        throw new IllegalArgumentException(FATAL_ERROR_UNDEFINED_METHOD);
    }

    public String get(final String whereColumn, final String whereColumnValue, final String columnToReturn) {
        return get(whereColumn, whereColumnValue, null, null, columnToReturn);
    }

    public String get(final String whereColumn1, final String whereColumnValue1, @Nullable final String whereColumn2, @Nullable final String whereColumnValue2, final String columnToReturn) {

        String valueToReturn = null;

        final Cursor cur;
        if (whereColumn2 == null || whereColumnValue2 == null) {
            cur = this.getWritableDatabase().query(getTableName(), new String[]{columnToReturn}, whereColumn1 + " = ? ", new String[]{whereColumnValue1}, null, null, COLUMN_ID + " DESC", "1");
        } else {
            cur = this.getWritableDatabase().query(getTableName(), new String[]{columnToReturn}, whereColumn1 + " = ? AND " + whereColumn2 + " = ? ", new String[]{whereColumnValue1, whereColumnValue2}, null, null, COLUMN_ID + " DESC", "1");
        }

        if (cur.moveToFirst()) {
            valueToReturn = cur.getString(cur.getColumnIndex(columnToReturn));
        }

        cur.close();

        return valueToReturn;
    }

    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);
        if (!db.isReadOnly()) {
            // Enable foreign key constraints
            db.execSQL("PRAGMA foreign_keys='ON';");
        }
    }


    protected boolean update(String whereColumn, String whereColumnValue, String columnToUpdate, String valueToUpdate) {
        final SQLiteDatabase db = this.getWritableDatabase();
        final ContentValues cv = new ContentValues(1);
        cv.put(columnToUpdate, valueToUpdate);
        return db.update(tableName, cv, whereColumn + " = ? ", new String[]{whereColumnValue}) > 0;
    }


    public boolean update(T t) {
        throw new IllegalArgumentException(FATAL_ERROR_UNDEFINED_METHOD);
    }

    public T get(final String column1, final String value1, final String column2, final String value2) {
        throw new IllegalArgumentException(FATAL_ERROR_UNDEFINED_METHOD);
    }

    public List<T> getAll() {
        throw new IllegalArgumentException(FATAL_ERROR_UNDEFINED_METHOD);
    }

    public void deleteAll() {
        this.getWritableDatabase().delete(getTableName(), null, null);
    }

    public boolean delete(final String whereColumn, final String whereColumnValue) {
        return this.getWritableDatabase().delete(getTableName(), whereColumn + " = ?", new String[]{whereColumnValue}) == 1;
    }


    public Context getContext() {
        return context;
    }

    public String getTableName() {
        return tableName;
    }

    public final int getCount() {

        int count = 0;

        final Cursor c = this.getReadableDatabase().rawQuery("SELECT COUNT(id) FROM " + getTableName(), null);

        if (c != null && c.moveToFirst()) {
            count = c.getInt(0);
        }

        if (c != null) {
            c.close();
        }

        return count;

    }

    protected boolean updateLastRow(String whereColumn, String whereColumnValue, String updateColumn, String updateColumnValue) {
        final SQLiteDatabase db = this.getWritableDatabase();
        final ContentValues cv = new ContentValues(1);
        cv.put(updateColumn, updateColumnValue);
        return db.update(tableName, cv, whereColumn + " = ? AND " + String.format("id = (SELECT MAX(id) FROM %s WHERE %s='%s')", getTableName(), whereColumn, whereColumnValue), new String[]{whereColumnValue}) > 0;
    }
}