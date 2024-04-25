package com.capstone.dyslexia.global.error.exceptions;

import lombok.Getter;

@Getter
public enum ErrorCode {
    /**
     * 400 Bad Request
     */
    ROW_DOES_NOT_EXIST(4000),

    /**
     * 401 Unauthorized
     */

    /**
     * 500 Internal Server Error
     */
    INTERNAL_SERVER_ERROR(5000),

    /**
     * 503 Service Unavailable Error
     */
    SERVICE_UNAVAILABLE(5000);

    private int code;

    ErrorCode(int code) {
        this.code = code;
    }
}
