package com.theah64.hosteler.utils;

import android.app.Application;

import com.joanzapata.iconify.Iconify;
import com.joanzapata.iconify.fonts.FontAwesomeModule;
import com.theah64.hosteler.R;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

/**
 * Created by theapache64 on 16/11/17.
 */

public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        Iconify.with(new FontAwesomeModule());

        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath("fonts/Lato-Regular.ttf")
                .setFontAttrId(R.attr.fontPath)
                .build()
        );
    }

}
