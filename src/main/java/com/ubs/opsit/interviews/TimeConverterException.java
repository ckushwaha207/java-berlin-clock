package com.ubs.opsit.interviews;

/**
 * This is custom exception class.
 */
public class TimeConverterException extends Exception {

    private static final long serialVersionUID = 7136187777960234623L;

    public TimeConverterException(String message) {
        super(message);
    }
}