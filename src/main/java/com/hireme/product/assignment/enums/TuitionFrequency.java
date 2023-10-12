package com.hireme.product.assignment.enums;

public enum TuitionFrequency {
    MONDAY("Monday"),
    TUESDAY("Tuesday"),
    WEDNESDAY("Wednesday"),
    THURSDAY("Thursday"),
    FRIDAY("Friday"),
    SATURDAY("Saturday"),
    SUNDAY("Sunday");

    private final String day;

    TuitionFrequency(String day) {
        this.day = day;
    }

    public String getDay() {
        return day;
    }
}






