package com.toolrent.toolrentcheckout;

public class InvalidPercentException extends RuntimeException {
    public InvalidPercentException(String message) {
        super(message);
    }

    public InvalidPercentException(String message, Throwable cause) {
        super(message, cause);
    }
}