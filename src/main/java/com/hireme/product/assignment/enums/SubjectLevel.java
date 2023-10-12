package com.hireme.product.assignment.enums;

public enum SubjectLevel {
    PRIMARY("Primary"),
    SECONDARY("Secondary"),
    JC("JC");

    private final String displayName;

    SubjectLevel(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
