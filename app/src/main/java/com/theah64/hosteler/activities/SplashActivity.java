package com.theah64.hosteler.activities;

import com.theah64.hosteler.R;

public class SplashActivity extends BaseSplashActivity {

    @Override
    public void onSplashStart() {

    }

    @Override
    public long getSplashDuration() {
        return 1500;
    }

    @Override
    public void onSplashFinished() {
        MainActivity.start(this, null);
        finish();
    }

    @Override
    public int getTextLogo() {
        return R.string.app_name;
    }

    @Override
    public float getTextLogoSize() {
        return 28;
    }

    @Override
    public int getTextLogoColor() {
        return R.color.colorAccent;
    }
}
