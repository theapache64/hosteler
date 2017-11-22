package com.theah64.hosteler.activities;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.DrawableRes;
import android.support.annotation.StringRes;
import android.support.v4.content.ContextCompat;
import android.widget.ImageView;
import android.widget.TextView;

import com.theah64.hosteler.R;


/**
 * Created by theapache64 on 20/8/17.
 */

public abstract class BaseSplashActivity extends BaseAppCompatActivity {

    public abstract void onSplashStart();

    public abstract long getSplashDuration();

    public abstract void onSplashFinished();

    @DrawableRes
    public int getLogo() {
        return -1;
    }

    @StringRes
    public int getTextLogo() {
        return -1;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        if (getLogo() != -1) {
            ((ImageView) findViewById(R.id.ivSplashLogo)).setImageResource(getLogo());
        }


        if (getTextLogo() != -1) {
            final TextView tvTextLogo = findViewById(R.id.tvTextLogo);
            tvTextLogo.setText(getTextLogo());
            tvTextLogo.setTextSize(getTextLogoSize());
            tvTextLogo.setTextColor(ContextCompat.getColor(this, getTextLogoColor()));
        }

        onSplashStart();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                onSplashFinished();
            }
        }, getSplashDuration());
    }

    public float getTextLogoSize() {
        return 18;
    }

    public int getTextLogoColor() {
        return android.R.color.white;
    }
}
