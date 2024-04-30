package com.capstone.dyslexia.global.error.exceptions;

import com.capstone.dyslexia.global.error.BaseRuntimeException;
import com.capstone.dyslexia.global.error.ErrorCode;

public class ServiceUnavailableException extends BaseRuntimeException {
    public ServiceUnavailableException(ErrorCode errorCode, String message) {
        super(errorCode, message);
    }
}
