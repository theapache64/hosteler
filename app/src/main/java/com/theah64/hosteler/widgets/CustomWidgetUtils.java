package com.theah64.hosteler.widgets;

import android.content.res.TypedArray;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.widget.TextView;

import com.joanzapata.iconify.IconDrawable;
import com.theah64.hosteler.R;


/**
 * Created by theapache64 on 15/9/17.
 */
public class CustomWidgetUtils {

    private final int[] styleableRes;
    private final int iconLeftRes;
    private final int iconLeftColorRes;
    private final int iconLeftSizeRes;
    private final int iconLeftPaddingRes;
    private final int isStrikeThroughRes;

    public CustomWidgetUtils(int[] styleableRes, int iconLeftRes, int iconLeftColorRes, int iconLeftSizeRes, int iconLeftPaddingRes, int isStrikeThroughRes) {
        this.styleableRes = styleableRes;
        this.iconLeftRes = iconLeftRes;
        this.iconLeftColorRes = iconLeftColorRes;
        this.iconLeftSizeRes = iconLeftSizeRes;
        this.iconLeftPaddingRes = iconLeftPaddingRes;
        this.isStrikeThroughRes = isStrikeThroughRes;
    }

    public void init(final TextView v, @Nullable AttributeSet attrs) {

        if (attrs != null) {
            //Collecting custom attrs
            TypedArray ta = v.getContext().obtainStyledAttributes(attrs, styleableRes, 0, 0);

            if (!v.isInEditMode()) {
                final String iconLeft = ta.getString(iconLeftRes);

                if (iconLeft != null) {
                    final int iconLeftColor = ta.getColor(iconLeftColorRes, ContextCompat.getColor(v.getContext(), R.color.grey_300));
                    final int iconLeftSize = (int) ta.getDimension(iconLeftSizeRes, 15);
                    final int iconLeftPadding = (int) ta.getDimension(iconLeftPaddingRes, 10);
                    final IconDrawable icon = new IconDrawable(v.getContext(), iconLeft).color(iconLeftColor).sizeDp(iconLeftSize);
                    v.setCompoundDrawables(icon, null, null, null);
                    v.setCompoundDrawablePadding(iconLeftPadding);
                }
            }


            if (isStrikeThroughRes != -1) {
                final boolean isStrikeThrough = ta.getBoolean(isStrikeThroughRes, false);
                if (isStrikeThrough) {
                    v.setPaintFlags(v.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                }
            }

            ta.recycle();
        }
    }
}
