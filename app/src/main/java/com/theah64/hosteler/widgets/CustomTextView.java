package com.theah64.hosteler.widgets;

import android.content.Context;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;

import com.theah64.hosteler.R;


/**
 * Created by theapache64 on 15/9/17.
 */

public class CustomTextView extends AppCompatTextView {

    public CustomTextView(Context context) {
        super(context);
        init(null);
    }

    public CustomTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public CustomTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }


    private void init(AttributeSet attrs) {
        new CustomWidgetUtils(
                R.styleable.CustomTextView,
                R.styleable.CustomTextView_iconLeft,
                R.styleable.CustomTextView_iconLeftColor,
                R.styleable.CustomTextView_iconLeftSize,
                R.styleable.CustomTextView_iconLeftPadding,
                R.styleable.CustomTextView_isStrikeThrough
        ).init(this, attrs);
    }


}
