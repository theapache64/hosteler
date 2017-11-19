package com.theah64.hosteler.models;

/**
 * Created by theapache64 on 19/11/17.
 */
public class Bill {

    private final int breakfastCount;
    private final int dinnerCount;
    private final int guestBreakfastCount;
    private final int guestDinnerCount;

    private final long totalBreakfastCost;
    private final long totalDinnerCost;
    private final long totalGuestBreakfastCost;
    private final long totalGuestDinnerCost;

    private final long additionalCharge;
    private final long pendingAmount;
    private final long advanceAmount;

    Bill(int breakfastCount, int dinnerCount, int guestBreakfastCount, int guestDinnerCount, long totalBreakfastCost, long totalDinnerCost, long totalGuestBreakfastCost, long totalGuestDinnerCost, long additionalCharge, long pendingAmount, long advanceAmount) {
        this.breakfastCount = breakfastCount;
        this.dinnerCount = dinnerCount;
        this.guestBreakfastCount = guestBreakfastCount;
        this.guestDinnerCount = guestDinnerCount;
        this.totalBreakfastCost = totalBreakfastCost;
        this.totalDinnerCost = totalDinnerCost;
        this.totalGuestBreakfastCost = totalGuestBreakfastCost;
        this.totalGuestDinnerCost = totalGuestDinnerCost;
        this.additionalCharge = additionalCharge;
        this.pendingAmount = pendingAmount;
        this.advanceAmount = advanceAmount;
    }

    public int getBreakfastCount() {
        return breakfastCount;
    }

    public int getDinnerCount() {
        return dinnerCount;
    }

    public int getGuestBreakfastCount() {
        return guestBreakfastCount;
    }

    public int getGuestDinnerCount() {
        return guestDinnerCount;
    }

    public long getTotalBreakfastCost() {
        return totalBreakfastCost;
    }

    public long getTotalDinnerCost() {
        return totalDinnerCost;
    }

    public long getTotalGuestBreakfastCost() {
        return totalGuestBreakfastCost;
    }

    public long getTotalGuestDinnerCost() {
        return totalGuestDinnerCost;
    }

    public long getAdditionalCharge() {
        return additionalCharge;
    }

    public long getPendingAmount() {
        return pendingAmount;
    }

    public long getAdvanceAmount() {
        return advanceAmount;
    }

    public long getGrandTotal() {
        return (totalBreakfastCost + totalDinnerCost + totalGuestBreakfastCost + totalGuestDinnerCost + additionalCharge + pendingAmount) - advanceAmount;
    }
}
