package com.capstone.dyslexia.global.error.exceptions;

public class InternalServerException extends BaseRuntimeException {
    public InternalServerException(ErrorCode errorCode, String message) {
        super(errorCode, message);
    }
}
