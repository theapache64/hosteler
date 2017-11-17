package com.theah64.hosteler.widgets;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v7.widget.AppCompatEditText;
import android.util.AttributeSet;


import com.theah64.hosteler.R;

import java.util.regex.Pattern;

/**
 * Created by theapache64 on 15/9/17.
 */

public class CustomEditText extends AppCompatEditText {

    private Pattern regExPattern;
    private int errorMessage;

    public CustomEditText(Context context) {
        super(context);
        init(null);
    }


    public CustomEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public CustomEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    private void init(AttributeSet attrs) {
        new CustomWidgetUtils(
                R.styleable.CustomEditText,
                R.styleable.CustomEditText_iconLeft,
                R.styleable.CustomEditText_iconLeftColor,
                R.styleable.CustomEditText_iconLeftSize,
                R.styleable.CustomEditText_iconLeftPadding,
                -1
        ).init(this, attrs);

    }

    @Nullable
    public String getString() {
        final String data = getText().toString().trim();
        if (data.isEmpty()) {
            return null;
        }
        return data;
    }

    public void setRegEx(final String regEx) {
        this.regExPattern = Pattern.compile(regEx);
    }


    public void setRegEx(final Pattern regEx) {
        this.regExPattern = regEx;
    }

    public void setErrorMessage(@StringRes final int errorMessage) {
        this.errorMessage = errorMessage;
    }

    public boolean isMatch() {


        final String data = getString();

        if (data != null) {

            if (regExPattern != null) {

                //This will match or nothing
                final boolean isMatch = this.regExPattern.matcher(data).matches();

                if (!isMatch) {

                    //Invalid
                    if (errorMessage != -1) {
                        setError(getContext().getString(errorMessage));
                    } else {
                        throw new IllegalArgumentException("REGEX is set, but error message is " + errorMessage);
                    }

                    return false;

                } else {

                    return true;
                }
            } else {
                return true;
            }

        } else {
            //Data is empty

            setError(getContext().getString(R.string.Empty));
            return false;
        }


    }


    /**
     * Created by shifar on 7/2/16.
     */
    public static final class EditTextValidator {

        private final CustomEditText[] customEditTexts;

        public EditTextValidator(CustomEditText... customEditTexts) {
            this.customEditTexts = customEditTexts;
        }


        public boolean isAllValid() {
            boolean isAllValid = true;
            for (final CustomEditText customEditText : customEditTexts) {
                isAllValid = customEditText.isMatch() && isAllValid;
            }
            return isAllValid;
        }

        /**
         * Used to clear all field's data.
         */
        public void clearFields() {
            for (final CustomEditText customEditText : customEditTexts) {
                customEditText.setText(null);
            }
        }

        public boolean isAtLeastOneValid() {

            for (final CustomEditText customEditText : customEditTexts) {
                if (customEditText.isMatch()) {
                    return true;
                }
            }

            return false;
        }
    }
}
