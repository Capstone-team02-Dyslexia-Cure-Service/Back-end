package com.capstone.dyslexia.global.payload;

import com.capstone.dyslexia.global.error.ErrorCode;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.ConstraintViolationException;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class ConstraintExceptionDto<T>  {

    @Schema(description = "HTTP 응답 코드")
    private Integer code;

    @Schema(description = "HTTP 응답 메세지")
    private String message;

    @Schema(description = "타임 스탬프")
    private LocalDateTime timestamp;

    @Schema(description = "응답 data", implementation = Object.class)
    private T data;

    private final List<String> violations;

    public ConstraintExceptionDto(ErrorCode errorCode, String message, ConstraintViolationException exception) {
        this.code = errorCode.getCode();
        this.message = message;
        this.timestamp = LocalDateTime.now();

        List<String> errors = new ArrayList<>();
        exception.getConstraintViolations().forEach(violation -> {
            errors.add(violation.getRootBeanClass().getSimpleName() + "." + violation.getPropertyPath().toString() + ": " + violation.getMessage());
        });
        this.violations = errors;
    }
}
