package com.theah64.hosteler.utils;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import com.theah64.hosteler.receivers.LazyModeReceivers;

import java.util.Calendar;

/**
 * Created by theapache64 on 22/11/17.
 */

public class LazyMode {

    public static void activate(final Context context, final AlarmManager am) {

        //Building intent
        final Intent breakfastIntent = new Intent(context, LazyModeReceivers.class);
        breakfastIntent.putExtra(LazyModeReceivers.KEY_LAZY_MODE_TYPE, LazyModeReceivers.TYPE_LAZY_BREAKFAST);

        final Intent dinnerIntent = new Intent(context, LazyModeReceivers.class);
        dinnerIntent.putExtra(LazyModeReceivers.KEY_LAZY_MODE_TYPE, LazyModeReceivers.TYPE_LAZY_DINNER);

        //Setting time
        final Calendar breakfastTime = Calendar.getInstance();
        breakfastTime.set(Calendar.HOUR_OF_DAY, 7);
        breakfastTime.set(Calendar.MINUTE, 0);
        breakfastTime.set(Calendar.SECOND, 0);

        System.out.println("BTIme: " + breakfastTime.getTime().toString());


        final Calendar dinnerTime = Calendar.getInstance();
        dinnerTime.set(Calendar.HOUR_OF_DAY, 21);
        dinnerTime.set(Calendar.MINUTE, 0);
        dinnerTime.set(Calendar.SECOND, 0);

        System.out.println("DTIme: " + dinnerTime.getTime().toString());


        PendingIntent breakfastPendingIntent = PendingIntent.getBroadcast(context, LazyModeReceivers.RQ_CODE_BREAKFAST, breakfastIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        am.setRepeating(AlarmManager.RTC_WAKEUP, breakfastTime.getTimeInMillis(), AlarmManager.INTERVAL_DAY, breakfastPendingIntent);

        PendingIntent dinnerPendingIntent = PendingIntent.getBroadcast(context, LazyModeReceivers.RQ_CODE_DINNER, dinnerIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        am.setRepeating(AlarmManager.RTC_WAKEUP, dinnerTime.getTimeInMillis(), AlarmManager.INTERVAL_DAY, dinnerPendingIntent);
    }

    public static void deactivate(Context context, AlarmManager am) {
        final Intent breakfastIntent = new Intent(context, LazyModeReceivers.class);
        breakfastIntent.putExtra(LazyModeReceivers.KEY_LAZY_MODE_TYPE, LazyModeReceivers.TYPE_LAZY_BREAKFAST);
        PendingIntent breakfastPendingIntent = PendingIntent.getBroadcast(context, LazyModeReceivers.RQ_CODE_BREAKFAST, breakfastIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        am.cancel(breakfastPendingIntent);

        final Intent dinnerIntent = new Intent(context, LazyModeReceivers.class);
        dinnerIntent.putExtra(LazyModeReceivers.KEY_LAZY_MODE_TYPE, LazyModeReceivers.TYPE_LAZY_DINNER);
        PendingIntent dinnerPendingIntent = PendingIntent.getBroadcast(context, LazyModeReceivers.RQ_CODE_DINNER, dinnerIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        am.cancel(dinnerPendingIntent);
    }
}
