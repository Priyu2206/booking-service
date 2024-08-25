package com.demo.booking.train.enums;

public enum Section {
    A, B;

    public static int getSectionCount() {
        return values().length;
    }
}
