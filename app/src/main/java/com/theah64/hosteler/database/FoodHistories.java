package com.theah64.hosteler.database;

import android.content.Context;

import com.theah64.hosteler.models.FoodHistory;

/**
 * Created by theapache64 on 16/11/17.
 */

public class FoodHistories extends BaseTable<FoodHistory> {

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
}
