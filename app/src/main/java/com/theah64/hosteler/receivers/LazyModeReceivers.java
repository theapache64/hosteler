package com.theah64.hosteler.receivers;

import android.app.Notification;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v4.app.NotificationCompat;

import com.theah64.hosteler.R;
import com.theah64.hosteler.database.tables.FoodHistories;
import com.theah64.hosteler.models.FoodHistory;
import com.theah64.hosteler.utils.DateUtils;

import java.util.Calendar;
import java.util.Date;

public class LazyModeReceivers extends BroadcastReceiver {

    private static final String TYPE_LAZY_BREAKFAST = "lazy_breakfast";
    private static final String TYPE_LAZY_DINNER = "lazy_dinner";
    private static final String KEY_LAZY_MODE_TYPE = "lazy_mode_type";

    @Override
    public void onReceive(Context context, Intent intent) {

        final Calendar today = Calendar.getInstance();
        final String type = intent.getStringExtra(KEY_LAZY_MODE_TYPE);
        final int dayOfWeek = today.get(Calendar.DAY_OF_WEEK);

        if (dayOfWeek == Calendar.SUNDAY) {
            //sunday nothing
            return;
        }

        if (dayOfWeek == Calendar.MONDAY && type.equals(TYPE_LAZY_BREAKFAST)) {
            //monday no breafast
            return;
        }


        if (dayOfWeek == Calendar.SATURDAY && type.equals(TYPE_LAZY_DINNER)) {
            //saturday no dinner
            return;
        }

        //checking if history exist
        final FoodHistories foodHistoriesTable = FoodHistories.getInstance(context);
        final SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);

        String dateInddMMyyyy = DateUtils.formatWithddMMyyyy(today.getTime());
        FoodHistory foodHistory = foodHistoriesTable.get(FoodHistories.COLUMN_DATE, dateInddMMyyyy);

        if (foodHistory == null) {
            foodHistory = new FoodHistory(null, dateInddMMyyyy, null, 0, 0, 0, 0, 0, null, null);
        }

        if (type.equals(TYPE_LAZY_BREAKFAST)) {
            //Breakfast
            final int breakfastCost = Integer.parseInt(sharedPreferences.getString(context.getString(R.string.breakfast_charge), context.getString(R.string.default_breakfast_cost)));
            foodHistory.setBreakfast(breakfastCost);
        } else {
            //Dinner
            final int dinnerCost = Integer.parseInt(sharedPreferences.getString(context.getString(R.string.dinner_charge), context.getString(R.string.default_dinner_cost)));
            foodHistory.setDinner(dinnerCost);
        }

        if (foodHistory.getId() == null) {
            //New -insert
            foodHistoriesTable.add(foodHistory);
        } else {
            //Old - update
            foodHistoriesTable.update(foodHistory);
        }

        new NotificationCompat()

    }
}