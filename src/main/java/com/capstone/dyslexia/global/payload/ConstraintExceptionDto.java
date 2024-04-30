package com.capstone.dyslexia.global.payload;

import com.capstone.dyslexia.global.error.ErrorCode;
import jakarta.validation.ConstraintViolationException;

import java.util.ArrayList;
import java.util.List;

public class ConstraintExceptionDto extends ApiResponseTemplate {
    private final List<String> violations;

    public ConstraintExceptionDto(ErrorCode errorCode, String message, ConstraintViolationException exception) {
        super(errorCode.getCode(), message);

        List<String> errors = new ArrayList<>();
        exception.getConstraintViolations().forEach(violation -> {
            errors.add(violation.getRootBeanClass().getSimpleName() + "." + violation.getPropertyPath().toString() + ": " + violation.getMessage());
        });
        this.violations = errors;
    }
}
