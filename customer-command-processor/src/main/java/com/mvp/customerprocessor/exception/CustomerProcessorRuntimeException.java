package com.mvp.customerprocessor.exception;

public class CustomerProcessorRuntimeException extends RuntimeException {

    public CustomerProcessorRuntimeException() {
    }

    public CustomerProcessorRuntimeException(String message) {
        super(message);
    }

    public CustomerProcessorRuntimeException(String message, Throwable cause) {
        super(message, cause);
    }
}
