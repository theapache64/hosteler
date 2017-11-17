package com.theah64.hosteler.models;

/**
 * Created by theapache64 on 16/11/17.
 */
public class FoodHistory {

    private final String id, date, description, createdAt;
    private final int breakfast, dinner, guestBreakfast, guestDinner, additionalCharge;

    public FoodHistory(String id, String date, String description, int breakfast, int dinner, int guestBreakfast, int guestDinner, int additionalCharge, String createdAt) {
        this.id = id;
        this.date = date;
        this.description = description;
        this.breakfast = breakfast;
        this.dinner = dinner;
        this.guestBreakfast = guestBreakfast;
        this.guestDinner = guestDinner;
        this.additionalCharge = additionalCharge;
        this.createdAt = createdAt;
    }

    public String getId() {
        return id;
    }

    public String getDate() {
        return date;
    }

    public String getDescription() {
        return description;
    }

    public int getBreakfast() {
        return breakfast;
    }

    public int getDinner() {
        return dinner;
    }

    public int getGuestBreakfast() {
        return guestBreakfast;
    }

    public int getGuestDinner() {
        return guestDinner;
    }

    public int getAdditionalCharge() {
        return additionalCharge;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    @Override
    public String toString() {
        return "FoodHistory{" +
                "id='" + id + '\'' +
                ", date='" + date + '\'' +
                ", description='" + description + '\'' +
                ", breakfast=" + breakfast +
                ", dinner=" + dinner +
                ", guestBreakfast=" + guestBreakfast +
                ", guestDinner=" + guestDinner +
                ", additionalCharge=" + additionalCharge +
                ", createdAt=" + createdAt +
                '}';
    }
}
