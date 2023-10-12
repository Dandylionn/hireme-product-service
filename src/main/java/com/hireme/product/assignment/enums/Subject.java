package com.hireme.product.assignment.enums;

public enum Subject {
    // Primary Level Subjects
    PRIMARY_ENGLISH("English"),
    PRIMARY_CHINESE("Chinese"),
    PRIMARY_MALAY("Malay"),
    PRIMARY_TAMIL("Tamil"),
    PRIMARY_MATHEMATICS("Mathematics"),
    PRIMARY_SCIENCE("Science"),
    PRIMARY_ART("Art"),
    PRIMARY_MUSIC("Music"),
    PRIMARY_PHYSICAL_EDUCATION("Physical Education"),
    PRIMARY_SOCIAL_STUDIES("Social Studies"),
    PRIMARY_OTHERS("Others"),

    // Secondary Level Subjects
    SECONDARY_ENGLISH("English"),
    SECONDARY_CHINESE("Chinese"),
    SECONDARY_MALAY("Malay"),
    SECONDARY_TAMIL("Tamil"),
    SECONDARY_MATHEMATICS("Mathematics"),
    SECONDARY_PHYSICS("Physics"),
    SECONDARY_CHEMISTRY("Chemistry"),
    SECONDARY_BIOLOGY("Biology"),
    SECONDARY_HUMANITIES("Humanities"),
    SECONDARY_DESIGN_TECHNOLOGY("Design and Technology"),
    SECONDARY_FOOD_CONSUMER_EDUCATION("Food and Consumer Education"),
    SECONDARY_OTHERS("Others"),

    // JC Level Subjects
    JC_ENGLISH("English"),
    JC_CHINESE("Chinese"),
    JC_MALAY("Malay"),
    JC_TAMIL("Tamil"),
    JC_MATHEMATICS("Mathematics"),
    JC_PHYSICS("Physics"),
    JC_CHEMISTRY("Chemistry"),
    JC_BIOLOGY("Biology"),
    JC_HUMANITIES("Humanities"),
    JC_DESIGN_TECHNOLOGY("Design and Technology"),
    JC_FOOD_CONSUMER_EDUCATION("Food and Consumer Education"),
    JC_OTHERS("Others");

    private final String displayName;

    Subject(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
