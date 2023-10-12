package com.hireme.product.assignment.enums;

public enum AssignmentType {
    LOOKING_FOR_TUITION("Looking for Tuition Service"),
    PROVIDING_TUITION("Providing Tuition Service");

    private final String displayName;

    AssignmentType(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
