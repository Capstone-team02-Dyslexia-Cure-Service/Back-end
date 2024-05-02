package com.capstone.dyslexia.global.error.exceptions;

import com.capstone.dyslexia.global.error.BaseRuntimeException;
import com.capstone.dyslexia.global.error.ErrorCode;

public class UnauthorizedException extends BaseRuntimeException {
    public UnauthorizedException(ErrorCode errorCode, String message) {
        super(errorCode, message);
    }

    public UnauthorizedException(ErrorCode errorCode) {
        super(errorCode);
    }
}
