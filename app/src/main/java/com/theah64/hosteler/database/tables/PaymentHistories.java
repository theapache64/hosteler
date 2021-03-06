package com.theah64.hosteler.database.tables;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.theah64.hosteler.database.CustomCursor;
import com.theah64.hosteler.models.PaymentHistory;

import java.util.ArrayList;
import java.util.List;

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
        final ContentValues cv = new ContentValues();
        cv.put(COLUMN_DATE, newInstance.getDate());
        cv.put(COLUMN_AMOUNT_PAID, newInstance.getAmountPaid());
        cv.put(COLUMN_ADVANCE_AMOUNT, newInstance.getAdvanceAmount());
        cv.put(COLUMN_PENDING_AMOUNT, newInstance.getPendingAmount());
        return this.getWritableDatabase().insert(getTableName(), null, cv);
    }

    public PaymentHistory getLastPaymentHistory() {
        PaymentHistory paymentHistory = null;
        final String query = "SELECT ph.id, ph._date,ph.amount_paid, ph.pending_amount, ph.advance_amount,ph.created_at FROM payment_histories ph ORDER BY ph.id DESC LIMIT 1";
        final Cursor cursor = getReadableDatabase().rawQuery(query, null);
        if (cursor.moveToFirst()) {
            paymentHistory = getPaymentHistory(cursor);
        }
        cursor.close();
        return paymentHistory;
    }

    private PaymentHistory getPaymentHistory(Cursor cursor) {
        final CustomCursor customCursor = new CustomCursor(cursor);
        final String id = customCursor.getStringByColumnIndex(COLUMN_ID);
        final String date = customCursor.getStringByColumnIndex(COLUMN_DATE);
        final long amountPaid = customCursor.getLongByColumnIndex(COLUMN_AMOUNT_PAID);
        final long pendingAmount = customCursor.getLongByColumnIndex(COLUMN_PENDING_AMOUNT);
        final long advanceAmount = customCursor.getLongByColumnIndex(COLUMN_ADVANCE_AMOUNT);
        final String createdAt = customCursor.getStringByColumnIndex(COLUMN_CREATED_AT);
        return new PaymentHistory(id, amountPaid, advanceAmount, pendingAmount, date, createdAt);
    }

    @Override
    public List<PaymentHistory> getAll() {
        final List<PaymentHistory> paymentHistories = new ArrayList<>();
        final String query = "SELECT ph.id, ph._date,ph.amount_paid, ph.pending_amount, ph.advance_amount,ph.created_at FROM payment_histories ph ORDER BY ph.id DESC";
        final Cursor cursor = getReadableDatabase().rawQuery(query, null);
        if (cursor.moveToFirst()) {
            do {
                paymentHistories.add(getPaymentHistory(cursor));
            } while (cursor.moveToNext());
        }
        cursor.close();
        return paymentHistories;
    }
}
