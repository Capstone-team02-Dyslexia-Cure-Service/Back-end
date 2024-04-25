package com.capstone.dyslexia.global.error.exceptions;

public class ServiceUnavailableException extends BaseRuntimeException {
    public ServiceUnavailableException(ErrorCode errorCode, String message) {
        super(errorCode, message);
    }
}
