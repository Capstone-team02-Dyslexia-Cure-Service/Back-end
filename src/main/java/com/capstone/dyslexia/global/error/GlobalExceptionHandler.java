package com.capstone.dyslexia.global.error;

import com.capstone.dyslexia.global.error.exceptions.BadRequestException;
import com.capstone.dyslexia.global.error.exceptions.ServiceUnavailableException;
import com.capstone.dyslexia.global.error.exceptions.UnauthorizedException;
import com.capstone.dyslexia.global.payload.ConstraintExceptionDto;
import com.capstone.dyslexia.global.payload.ErrorResponseTemplate;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.nio.file.AccessDeniedException;

@Component
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(value = {BadRequestException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponseTemplate handleBadRequestException(BadRequestException  exception) {
        GlobalExceptionHandler.log.error("error massage", exception);
        return ErrorResponseTemplate.error(exception.getErrorCode(), exception.getMessage());
    }

    @ExceptionHandler(value = {ConstraintViolationException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ConstraintExceptionDto handleConstraintViolationException(ConstraintViolationException exception) {
        GlobalExceptionHandler.log.error("error message", exception);
        return new ConstraintExceptionDto(ErrorCode.INVALID_PARAMETER, exception.getMessage(), exception);
    }

    @ExceptionHandler(value = {IllegalArgumentException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponseTemplate handleIllegalArgumentException(IllegalArgumentException exception) {
        GlobalExceptionHandler.log.error("error message", exception);
        return ErrorResponseTemplate.error(ErrorCode.INVALID_PARAMETER, exception.getMessage());
    }

    @ExceptionHandler(value = {HttpRequestMethodNotSupportedException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponseTemplate handleBadHttpRequestMethodException(HttpRequestMethodNotSupportedException exception) {
        GlobalExceptionHandler.log.error("error message", exception);
        return ErrorResponseTemplate.error(ErrorCode.INVALID_HTTP_METHOD, "Invalid request http method (GET, POST, PUT, DELETE)");
    }

    @ExceptionHandler(value = {UnauthorizedException.class})
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ErrorResponseTemplate handleUnauthorizedException(UnauthorizedException exception) {
        GlobalExceptionHandler.log.error("error message", exception);
        return ErrorResponseTemplate.error(exception.getErrorCode(), exception.getMessage());
    }

    @ExceptionHandler(value = {ServiceUnavailableException.class})
    @ResponseStatus(HttpStatus.SERVICE_UNAVAILABLE)
    public ErrorResponseTemplate handleServiceUnavailableException(ServiceUnavailableException exception) {
        GlobalExceptionHandler.log.error("error message", exception);
        return ErrorResponseTemplate.error(exception.getErrorCode(), exception.getMessage());
    }

    @ExceptionHandler(value = {AccessDeniedException.class})
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ErrorResponseTemplate handleAccessDeniedException(AccessDeniedException exception) {
        GlobalExceptionHandler.log.error("error message", exception);
        return ErrorResponseTemplate.error(ErrorCode.API_NOT_ACCESSIBLE, exception.getMessage());
    }

    @ExceptionHandler(value = {Exception.class})
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResponseTemplate unknownException(Exception exception) {
        GlobalExceptionHandler.log.error("error message", exception);
        return ErrorResponseTemplate.error(ErrorCode.INTERNAL_SERVER, "Internal server error");
    }
}
