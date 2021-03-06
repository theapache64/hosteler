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
    public static final String COLUMN_BREAKFAST = "breakfast";
    public static final String COLUMN_DINNER = "dinner";
    public static final String COLUMN_GUEST_BREAKFAST = "guest_breakfast";
    public static final String COLUMN_GUEST_DINNER = "guest_dinner";
    public static final String COLUMN_ADDITIONAL_CHARGE = "additional_charge";
    private static final String COLUMN_CREATED_AT = "created_at";
    public static final String COLUMN_PAYMENT_HISTORY_ID = "payment_history_id";

    private static final String[] ALL_COLUMNS = new String[]{
            COLUMN_ID,
            COLUMN_DATE,
            COLUMN_DESCRIPTION,
            COLUMN_BREAKFAST,
            COLUMN_DINNER,
            COLUMN_GUEST_BREAKFAST,
            COLUMN_GUEST_DINNER,
            COLUMN_ADDITIONAL_CHARGE,
            COLUMN_PAYMENT_HISTORY_ID,
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
        final String paymentHistoryId = customCursor.getStringByColumnIndex(FoodHistories.COLUMN_PAYMENT_HISTORY_ID);
        final String createdAt = customCursor.getStringByColumnIndex(FoodHistories.COLUMN_CREATED_AT);

        return new FoodHistory(id, date, description, breakfast, dinner, guestBreakfast, guestDinner, additionalCharge, createdAt, paymentHistoryId);

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
        cv.put(COLUMN_PAYMENT_HISTORY_ID, foodHistory.getPaymentHistoryId());
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
        cv.put(COLUMN_PAYMENT_HISTORY_ID, foodHistory.getPaymentHistoryId());
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
        final String query = "SELECT (SUM(fh.breakfast)+SUM(fh.dinner)+SUM(fh.guest_breakfast)+SUM(fh.guest_dinner) + SUM(additional_charge)) AS total_unpaid_amount FROM food_histories fh WHERE fh.payment_history_id is null;";
        final Cursor cursor = this.getReadableDatabase().rawQuery(query, null);
        if (cursor.moveToFirst()) {
            totalUnPaidAmount = cursor.getLong(0);
        }
        cursor.close();
        return totalUnPaidAmount;
    }

    public Bill getBill() {
        Bill bill = null;
        final String query = "SELECT (SELECT COUNT(breakfast) FROM food_histories WHERE payment_history_id is null AND breakfast>0) AS breakfast_count, (SELECT COUNT(dinner) FROM food_histories WHERE payment_history_id is null AND dinner>0) AS dinner_count, (SELECT COUNT(guest_breakfast) FROM food_histories WHERE payment_history_id is null AND guest_breakfast>0) AS guest_breakfast_count, (SELECT COUNT(guest_dinner) FROM food_histories WHERE payment_history_id is null AND guest_dinner>0) AS guest_dinner_count, (SELECT SUM(breakfast) FROM food_histories WHERE payment_history_id is null) AS breakfast_cost, (SELECT SUM(dinner) FROM food_histories WHERE payment_history_id is null ) AS dinner_cost, (SELECT SUM(guest_breakfast) FROM food_histories WHERE payment_history_id is null ) AS guest_breakfast_cost, (SELECT SUM(guest_dinner) FROM food_histories WHERE payment_history_id is null) AS guest_dinner_cost, (SELECT SUM(guest_dinner) FROM food_histories WHERE payment_history_id is null) AS guest_dinner_cost, (SELECT SUM(additional_charge) FROM food_histories WHERE payment_history_id is null) AS total_additional_charge FROM food_histories LIMIT 1;";
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
                    breakfastCost, dinnerCost, guestBreakfastCost, guestDinnerCost, additionalCharges
            );

        } else {
            bill = new Bill(0, 0, 0, 0, 0, 0, 0, 0, 0);
        }
        cursor.close();
        return bill;
    }
}
