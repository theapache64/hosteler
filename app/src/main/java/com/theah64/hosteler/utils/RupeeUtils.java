package com.theah64.hosteler.utils;

import com.joanzapata.iconify.widget.IconTextView;

/**
 * Created by theapache64 on 20/11/17.
 */

public class RupeeUtils {

    private static final String PRIMARY_AMOUNT_FORMAT = "{fa-rupee 17sp @color/grey_400} %s";

    public static void setRupeeText(IconTextView itv, long cost) {
        itv.setText(String.format(PRIMARY_AMOUNT_FORMAT, cost));
    }

}
