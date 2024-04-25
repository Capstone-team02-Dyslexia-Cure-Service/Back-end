package com.capstone.dyslexia.global.error.exceptions;

public abstract class BaseRuntimeException extends RuntimeException {
    private final ErrorCode errorCode;
    private final String message;

    public BaseRuntimeException(final ErrorCode errorCode, final String message) {
        this.errorCode = errorCode;
        this.message = message;
    }
}
