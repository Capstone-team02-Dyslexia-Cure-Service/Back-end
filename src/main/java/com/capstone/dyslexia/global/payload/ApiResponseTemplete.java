package com.capstone.dyslexia.global.payload;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.ToString;

@ToString
@Data
public class ApiResponseTemplete {

    @Schema(type = "String", example = "200", description = "HTTP 응답 코드")
    private String code;

    @Schema(type = "String", example = "OK", description = "HTTP 응답 메세지")
    private String message;

    @Schema(type = "object", example = "data", description = "응답 data")
    private Object data;

    public ApiResponseTemplete(final String code, final String message, final Object data) {
        this.code = code;
    }
    public ApiResponseTemplete(final String code, final String message) {
        this.code = code;
    }
    public ApiResponseTemplete(final String message) {
        this.message = message;
    }
    public ApiResponseTemplete(final String message, final Object data) {
        this.message = message;
    }
    public ApiResponseTemplete(final String message, final Object data, final String code) {
        this.message = message;
    }
    public ApiResponseTemplete(final String message, final Object data, final String code, final String format) {
        this.message = message;
    }
}
