package com.toolrent.toolrentcheckout;

public class InvalidToolCodeException extends RuntimeException {
    public InvalidToolCodeException(String message) {
        super(message);
    }

    public InvalidToolCodeException(String message, Throwable cause) {
        super(message, cause);
    }
}