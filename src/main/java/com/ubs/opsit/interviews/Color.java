package com.ubs.opsit.interviews;

/**
 * Enum for lamp colors.
 */
public enum Color {
    RED("R"),
    YELLOW("Y");

    private final String code;

    Color(String code) {
        this.code = code;
    }

    public String getCode() {
        return this.code;
    }
}