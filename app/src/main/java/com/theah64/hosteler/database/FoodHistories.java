package com.theah64.hosteler.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.theah64.hosteler.models.FoodHistory;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by theapache64 on 16/11/17.
 * <p>
 * private final String id, date, description;
 * private final int breakfast, dinner, guestBreakfast, guestDinner, additionalCharge, createdAt;
 */

public class FoodHistories extends BaseTable<FoodHistory> {


    public static final String COLUMN_ID = "id";
    public static final String COLUMN_DATE = "_date";
    public static final String COLUMN_DESCRIPTION = "description";
    public static final String COLUMN_BREAKFAST = "breakfast";
    public static final String COLUMN_DINNER = "dinner";
    public static final String COLUMN_GUEST_BREAKFAST = "guest_breakfast";
    public static final String COLUMN_GUEST_DINNER = "guest_dinner";
    public static final String COLUMN_ADDITIONAL_CHARGE = "additional_charge";
    public static final String COLUMN_CREATED_AT = "created_at";

    private static final String[] ALL_COLUMNS = new String[]{
            COLUMN_ID,
            COLUMN_DATE,
            COLUMN_DESCRIPTION,
            COLUMN_BREAKFAST,
            COLUMN_DINNER,
            COLUMN_GUEST_BREAKFAST,
            COLUMN_GUEST_DINNER,
            COLUMN_ADDITIONAL_CHARGE,
            COLUMN_CREATED_AT
    };

    private static FoodHistories instance;

    protected FoodHistories(Context context) {
        super(context, "food_histories");
    }

    public static FoodHistories getInstance(Context context) {
        if (instance == null) {
            instance = new FoodHistories(context);
        }
        return instance;
    }

    @Override
    public FoodHistory get(String column, String value) {

        FoodHistory foodHistory = null;

        final Cursor cursor = this.getReadableDatabase().query(getTableName(), ALL_COLUMNS, column + " = ?", new String[]{value}, null, null, null, null);


        if (cursor.moveToFirst()) {
            foodHistory = FoodHistory.parseFromCursor(cursor);
            cursor.close();
        }

        return foodHistory;
    }

    @Override
    public long add(FoodHistory foodHistory) {
        final ContentValues cv = new ContentValues();
        cv.put(COLUMN_DATE, foodHistory.getDate());
        cv.put(COLUMN_DESCRIPTION, foodHistory.getDescription());
        cv.put(COLUMN_BREAKFAST, foodHistory.getBreakfast());
        cv.put(COLUMN_DINNER, foodHistory.getDinner());
        cv.put(COLUMN_GUEST_BREAKFAST, foodHistory.getGuestBreakfast());
        cv.put(COLUMN_GUEST_DINNER, foodHistory.getGuestDinner());
        cv.put(COLUMN_ADDITIONAL_CHARGE, foodHistory.getAdditionalCharge());

        return this.getWritableDatabase().insert(getTableName(), null, cv);
    }

    @Override
    public boolean update(FoodHistory foodHistory) {

        final ContentValues cv = new ContentValues();
        cv.put(COLUMN_DESCRIPTION, foodHistory.getDescription());
        cv.put(COLUMN_BREAKFAST, foodHistory.getBreakfast());
        cv.put(COLUMN_DATE, foodHistory.getDate());
        cv.put(COLUMN_DINNER, foodHistory.getDinner());
        cv.put(COLUMN_GUEST_BREAKFAST, foodHistory.getGuestBreakfast());
        cv.put(COLUMN_GUEST_DINNER, foodHistory.getGuestDinner());
        cv.put(COLUMN_ADDITIONAL_CHARGE, foodHistory.getAdditionalCharge());

        return this.getWritableDatabase().update(getTableName(), cv, COLUMN_ID + " = ?", new String[]{foodHistory.getId()}) > 0;
    }

    @Override
    public List<FoodHistory> getAll() {
        final List<FoodHistory> foodHistories = new ArrayList<>();
        final Cursor cursor = getReadableDatabase().query(getTableName(), ALL_COLUMNS, null, null, null, null, null);
        if (cursor.moveToFirst()) {
            do {
                foodHistories.add(FoodHistory.parseFromCursor(cursor));
            } while (cursor.moveToNext());
        }
        cursor.close();
        return foodHistories;
    }
}
