package com.theah64.hosteler.database.tables;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;

import com.theah64.hosteler.database.CustomCursor;
import com.theah64.hosteler.models.PaymentHistory;

/**
 * Created by theapache64 on 19/11/17.
 */

public class PaymentHistories extends BaseTable<PaymentHistory> {

    private static final String COLUMN_DATE = "_date";
    private static final String COLUMN_PENDING_AMOUNT = "pending_amount";
    private static final String COLUMN_ADVANCE_AMOUNT = "advance_amount";
    private static final String COLUMN_AMOUNT_PAID = "amount_paid";
    @SuppressLint("StaticFieldLeak")
    private static PaymentHistories instance;


    private PaymentHistories(Context context) {
        super(context, "payment_histories");
    }

    public static PaymentHistories getInstance(Context context) {
        if (instance == null) {
            instance = new PaymentHistories(context);
        } else {
            instance.setContext(context);
        }

        return instance;
    }

    @Override
    public long add(PaymentHistory newInstance) {
        return super.add(newInstance);
    }

    public PaymentHistory getLastPaymentHistory() {
        PaymentHistory paymentHistory = null;
        final String query = "SELECT ph.id, ph._date,ph.amount_paid, ph.pending_amount, ph.advance_amount,ph.created_at FROM payment_histories ph ORDER BY ph.id DESC LIMIT 1";
        final Cursor cursor = getReadableDatabase().rawQuery(query, null);
        if (cursor.moveToFirst()) {
            final CustomCursor customCursor = new CustomCursor(cursor);
            final String id = customCursor.getStringByColumnIndex(COLUMN_ID);
            final String date = customCursor.getStringByColumnIndex(COLUMN_DATE);
            final long amountPaid = customCursor.getLongByColumnIndex(COLUMN_AMOUNT_PAID);
            final long pendingAmount = customCursor.getLongByColumnIndex(COLUMN_PENDING_AMOUNT);
            final long advanceAmount = customCursor.getLongByColumnIndex(COLUMN_ADVANCE_AMOUNT);
            final String createdAt = customCursor.getStringByColumnIndex(COLUMN_CREATED_AT);

            paymentHistory = new PaymentHistory(id, amountPaid, advanceAmount, pendingAmount, date, createdAt);
        }
        cursor.close();
        return paymentHistory;
    }
}
