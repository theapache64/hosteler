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

    public Bill(int breakfastCount, int dinnerCount, int guestBreakfastCount, int guestDinnerCount, long totalBreakfastCost, long totalDinnerCost, long totalGuestBreakfastCost, long totalGuestDinnerCost, long additionalCharge) {
        this.breakfastCount = breakfastCount;
        this.dinnerCount = dinnerCount;
        this.guestBreakfastCount = guestBreakfastCount;
        this.guestDinnerCount = guestDinnerCount;
        this.totalBreakfastCost = totalBreakfastCost;
        this.totalDinnerCost = totalDinnerCost;
        this.totalGuestBreakfastCost = totalGuestBreakfastCost;
        this.totalGuestDinnerCost = totalGuestDinnerCost;
        this.additionalCharge = additionalCharge;
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

    public long getTotalAdditionalCharge() {
        return additionalCharge;
    }


    public long getGrandTotal() {
        return (totalBreakfastCost + totalDinnerCost + totalGuestBreakfastCost + totalGuestDinnerCost + additionalCharge);
    }

    @Override
    public String toString() {
        return "Bill{" +
                "breakfastCount=" + breakfastCount +
                ", dinnerCount=" + dinnerCount +
                ", guestBreakfastCount=" + guestBreakfastCount +
                ", guestDinnerCount=" + guestDinnerCount +
                ", totalBreakfastCost=" + totalBreakfastCost +
                ", totalDinnerCost=" + totalDinnerCost +
                ", totalGuestBreakfastCost=" + totalGuestBreakfastCost +
                ", totalGuestDinnerCost=" + totalGuestDinnerCost +
                ", additionalCharge=" + additionalCharge +
                '}';
    }
}
