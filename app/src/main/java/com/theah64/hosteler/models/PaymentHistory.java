package com.theah64.hosteler.models;

/**
 * Created by theapache64 on 19/11/17.
 */

public class PaymentHistory {

    private final String id;
    private final long amountPaid, advanceAmount, pendingAmount;
    private final String date, createdAt;

    public PaymentHistory(String id, long amountPaid, long advanceAmount, long pendingAmount, String date, String createdAt) {
        this.id = id;
        this.amountPaid = amountPaid;
        this.advanceAmount = advanceAmount;
        this.pendingAmount = pendingAmount;
        this.date = date;
        this.createdAt = createdAt;
    }

    public String getId() {
        return id;
    }

    public long getAmountPaid() {
        return amountPaid;
    }

    public long getAdvanceAmount() {
        return advanceAmount;
    }

    public long getPendingAmount() {
        return pendingAmount;
    }

    public String getDate() {
        return date;
    }

    public String getCreatedAt() {
        return createdAt;
    }
}
