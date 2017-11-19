package com.theah64.hosteler.database.tables;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.theah64.hosteler.database.CustomCursor;
import com.theah64.hosteler.models.Bill;
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


    private static final String COLUMN_ID = "id";
    public static final String COLUMN_DATE = "_date";
    private static final String COLUMN_DESCRIPTION = "description";
    private static final String COLUMN_BREAKFAST = "breakfast";
    private static final String COLUMN_DINNER = "dinner";
    private static final String COLUMN_GUEST_BREAKFAST = "guest_breakfast";
    private static final String COLUMN_GUEST_DINNER = "guest_dinner";
    private static final String COLUMN_ADDITIONAL_CHARGE = "additional_charge";
    private static final String COLUMN_CREATED_AT = "created_at";
    private static final String COLUMN_IS_PAID = "is_paid";

    private static final String[] ALL_COLUMNS = new String[]{
            COLUMN_ID,
            COLUMN_DATE,
            COLUMN_DESCRIPTION,
            COLUMN_BREAKFAST,
            COLUMN_DINNER,
            COLUMN_GUEST_BREAKFAST,
            COLUMN_GUEST_DINNER,
            COLUMN_ADDITIONAL_CHARGE,
            COLUMN_IS_PAID,
            COLUMN_CREATED_AT
    };
    private static final String COLUMN_AS_BREAKFAST_COUNT = "breakfast_count";
    private static final String COLUMN_AS_DINNER_COUNT = "dinner_count";
    private static final String COLUMN_AS_GUEST_BREAKFAST_COUNT = "guest_breakfast_count";
    private static final String COLUMN_AS_GUEST_DINNER_COUNT = "guest_dinner_count";

    private static final String COLUMN_AS_BREAKFAST_COST = "breakfast_cost";
    private static final String COLUMN_AS_DINNER_COST = "dinner_cost";
    private static final String COLUMN_AS_GUEST_BREAKFAST_COST = "guest_breakfast_cost";
    private static final String COLUMN_AS_GUEST_DINNER_COST = "guest_dinner_cost";
    private static final String COLUMN_AS_TOTAL_ADDITIONAL_CHARGES = "total_additional_charge";

    @SuppressLint("StaticFieldLeak")
    private static FoodHistories instance;


    private FoodHistories(Context context) {
        super(context, "food_histories");
    }

    public static FoodHistories getInstance(Context context) {
        if (instance == null) {
            instance = new FoodHistories(context);
        } else {
            instance.setContext(context);
        }

        return instance;
    }

    @Override
    public FoodHistory get(String column, String value) {

        FoodHistory foodHistory = null;

        final Cursor cursor = this.getReadableDatabase().query(getTableName(), ALL_COLUMNS, column + " = ?", new String[]{value}, null, null, null, null);


        if (cursor.moveToFirst()) {
            foodHistory = parseFromCursor(cursor);
            cursor.close();
        }

        return foodHistory;
    }

    public static FoodHistory parseFromCursor(Cursor cursor) {

        final CustomCursor customCursor = new CustomCursor(cursor);
        final String id = customCursor.getStringByColumnIndex(FoodHistories.COLUMN_ID);
        final String date = customCursor.getStringByColumnIndex(FoodHistories.COLUMN_DATE);
        final String description = customCursor.getStringByColumnIndex(FoodHistories.COLUMN_DESCRIPTION);
        final int breakfast = customCursor.getIntByColumnIndex(FoodHistories.COLUMN_BREAKFAST);
        final int dinner = customCursor.getIntByColumnIndex(FoodHistories.COLUMN_DINNER);
        final int guestBreakfast = customCursor.getIntByColumnIndex(FoodHistories.COLUMN_GUEST_BREAKFAST);
        final int guestDinner = customCursor.getIntByColumnIndex(FoodHistories.COLUMN_GUEST_DINNER);
        final int additionalCharge = customCursor.getIntByColumnIndex(FoodHistories.COLUMN_ADDITIONAL_CHARGE);
        final boolean isPaid = customCursor.getBooleanByColumnIndex(FoodHistories.COLUMN_IS_PAID);
        final String createdAt = customCursor.getStringByColumnIndex(FoodHistories.COLUMN_CREATED_AT);

        return new FoodHistory(id, date, description, breakfast, dinner, guestBreakfast, guestDinner, additionalCharge, createdAt, isPaid);

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
        cv.put(COLUMN_IS_PAID, foodHistory.isPaid());
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
        cv.put(COLUMN_IS_PAID, foodHistory.isPaid());
        cv.put(COLUMN_ADDITIONAL_CHARGE, foodHistory.getAdditionalCharge());

        return this.getWritableDatabase().update(getTableName(), cv, COLUMN_ID + " = ?", new String[]{foodHistory.getId()}) > 0;
    }

    @Override
    public List<FoodHistory> getAll() {
        final List<FoodHistory> foodHistories = new ArrayList<>();
        final Cursor cursor = getReadableDatabase().query(getTableName(), ALL_COLUMNS, null, null, null, null, null);
        if (cursor.moveToFirst()) {
            do {
                foodHistories.add(parseFromCursor(cursor));
            } while (cursor.moveToNext());
        }
        cursor.close();
        return foodHistories;
    }

    public long getTotalUnPaidAmount() {
        long totalUnPaidAmount = 0;
        final String query = "SELECT (SUM(fh.breakfast)+SUM(fh.dinner)+SUM(fh.guest_breakfast)+SUM(fh.guest_dinner) + SUM(additional_charge)) AS total_unpaid_amount FROM food_histories fh WHERE fh.is_paid=0;";
        final Cursor cursor = this.getReadableDatabase().rawQuery(query, null);
        if (cursor.moveToFirst()) {
            totalUnPaidAmount = cursor.getLong(0);
        }
        cursor.close();
        return totalUnPaidAmount;
    }

    public Bill getBill() {
        Bill bill = null;
        final String query = "SELECT COUNT(fh.breakfast) AS breakfast_count, SUM(fh.breakfast) AS breakfast_cost, COUNT(fh.dinner) AS dinner_count, SUM(fh.dinner) AS dinner_cost, COUNT(fh.guest_breakfast) AS guest_breakfast_count, SUM(fh.guest_breakfast) AS guest_breakfast_cost, COUNT(fh.guest_dinner) AS guest_dinner_count, SUM(fh.guest_dinner) AS guest_dinner_cost, SUM(fh.additional_charge) AS total_additional_charge FROM food_histories fh WHERE fh.is_paid = 0;";
        final Cursor cursor = this.getReadableDatabase().rawQuery(query, null);
        if (cursor.moveToFirst()) {
            final CustomCursor customCursor = new CustomCursor(cursor);

            final int breakfastCount = customCursor.getIntByColumnIndex(COLUMN_AS_BREAKFAST_COUNT);
            final int dinnerCount = customCursor.getIntByColumnIndex(COLUMN_AS_DINNER_COUNT);
            final int guestBreakfastCount = customCursor.getIntByColumnIndex(COLUMN_AS_GUEST_BREAKFAST_COUNT);
            final int guestDinnerCount = customCursor.getIntByColumnIndex(COLUMN_AS_GUEST_DINNER_COUNT);

            final int breakfastCost = customCursor.getIntByColumnIndex(COLUMN_AS_BREAKFAST_COST);
            final int dinnerCost = customCursor.getIntByColumnIndex(COLUMN_AS_DINNER_COST);
            final int guestBreakfastCost = customCursor.getIntByColumnIndex(COLUMN_AS_GUEST_BREAKFAST_COST);
            final int guestDinnerCost = customCursor.getIntByColumnIndex(COLUMN_AS_GUEST_DINNER_COST);

            final int additionalCharges = customCursor.getIntByColumnIndex(COLUMN_AS_TOTAL_ADDITIONAL_CHARGES);

            bill = new Bill(
                    breakfastCount, dinnerCount, guestBreakfastCount, guestDinnerCount,
                    breakfastCost, dinnerCost, guestBreakfastCost, guestDinnerCost, additionalCharges, 0, 0
            );

        }
        cursor.close();
        return bill;
    }
}
