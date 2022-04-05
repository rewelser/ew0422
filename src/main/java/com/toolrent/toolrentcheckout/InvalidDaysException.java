package com.toolrent.toolrentcheckout;

public class InvalidDaysException extends RuntimeException {
    public InvalidDaysException(String message) {
        super(message);
    }

    public InvalidDaysException(String message, Throwable cause) {
        super(message, cause);
    }
}
