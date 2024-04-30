package com.capstone.dyslexia.global.payload;

import com.capstone.dyslexia.global.error.ErrorCode;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.Map;


@Getter
@JsonNaming(value = PropertyNamingStrategies.SnakeCaseStrategy.class)
public class ErrorResponseTemplate<T> {

    @Schema(description = "HTTP 응답 코드")
    private Integer code;

    @Schema(description = "HTTP 응답 메세지")
    private String message;

    @Schema(description = "타임 스탬프")
    private LocalDateTime timestamp;

    @Schema(description = "에러가 발생한 데이터")
    private final Map<String, T> data;



    private ErrorResponseTemplate(ErrorCode errorCode, String message, Map<String, T> data) {
        this.code = errorCode.getCode();
        this.message = message;
        this.timestamp = LocalDateTime.now();
        this.data = data;
    }

    public static ErrorResponseTemplate<Object> error(ErrorCode errorCode, String message) {
        return error(errorCode, message, Map.of());
    }

    public static <T> ErrorResponseTemplate<T> error(ErrorCode errorCode, String message, Map<String, T> data) {
        return new ErrorResponseTemplate<>(errorCode, message, data);
    }

}
