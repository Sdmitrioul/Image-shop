package ru.skroba.shop.exception;

public class HandlerException extends Exception {
    private final int errorCode;
    
    public HandlerException(final int errorCode, final String message) {
        super(message);
        this.errorCode = errorCode;
    }
    
    public int getErrorCode() {
        return errorCode;
    }
}
