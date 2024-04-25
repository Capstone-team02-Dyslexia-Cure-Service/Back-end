package com.capstone.dyslexia.global.error.exceptions;

public class BadRequestException extends BaseRuntimeException {
    public BadRequestException(ErrorCode errorCode, String message) {
        super(errorCode, message);
    }
}
