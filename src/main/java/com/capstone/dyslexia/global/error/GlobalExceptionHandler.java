package com.capstone.dyslexia.global.error;

import com.capstone.dyslexia.global.error.exceptions.BadRequestException;
import com.capstone.dyslexia.global.error.exceptions.ServiceUnavailableException;
import com.capstone.dyslexia.global.error.exceptions.UnauthorizedException;
import com.capstone.dyslexia.global.payload.ConstraintExceptionDto;
import com.capstone.dyslexia.global.payload.ErrorResponseTemplate;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.nio.file.AccessDeniedException;
import java.util.HashMap;
import java.util.Map;

@Component
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value = {BadRequestException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ErrorResponseTemplate> handleBadRequestException(BadRequestException exception) {
        GlobalExceptionHandler.log.error("[Error message]", exception);
        return ResponseEntity.status(exception.getErrorCode().getCode()).body(ErrorResponseTemplate.error(exception.getErrorCode(), exception.getMessage()));
    }

    @ExceptionHandler(value = {ConstraintViolationException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ConstraintExceptionDto> handleConstraintViolationException(ConstraintViolationException exception) {
        GlobalExceptionHandler.log.error("[Error message]", exception);
        return ResponseEntity.status(ErrorCode.INVALID_PARAMETER.getCode()).body(new ConstraintExceptionDto(ErrorCode.INVALID_PARAMETER, exception.getMessage(), exception));
    }

    @ExceptionHandler(value = {IllegalArgumentException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ErrorResponseTemplate> handleIllegalArgumentException(IllegalArgumentException exception) {
        GlobalExceptionHandler.log.error("[Error message]", exception);
        return ResponseEntity.status(ErrorCode.INVALID_PARAMETER.getCode()).body(ErrorResponseTemplate.error(ErrorCode.INVALID_PARAMETER, exception.getMessage()));
    }

    @ExceptionHandler(value = {HttpRequestMethodNotSupportedException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ErrorResponseTemplate> handleBadHttpRequestMethodException(HttpRequestMethodNotSupportedException exception) {
        GlobalExceptionHandler.log.error("[Error message]", exception);
        return ResponseEntity.status(ErrorCode.INVALID_PARAMETER.getCode()).body(ErrorResponseTemplate.error(ErrorCode.INVALID_PARAMETER, "Invalid request http method (GET, POST, PUT, DELETE)"));
    }

    @ExceptionHandler(value = {UnauthorizedException.class})
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ResponseEntity<ErrorResponseTemplate> handleUnauthorizedException(UnauthorizedException exception) {
        GlobalExceptionHandler.log.error("[Error message]", exception);
        return ResponseEntity.status(exception.getErrorCode().getCode()).body(ErrorResponseTemplate.error(exception.getErrorCode(), exception.getMessage()));
    }

    @ExceptionHandler(value = {ServiceUnavailableException.class})
    @ResponseStatus(HttpStatus.SERVICE_UNAVAILABLE)
    public ResponseEntity<ErrorResponseTemplate> handleServiceUnavailableException(ServiceUnavailableException exception) {
        GlobalExceptionHandler.log.error("[Error message]", exception);
        return ResponseEntity.status(exception.getErrorCode().getCode()).body(ErrorResponseTemplate.error(exception.getErrorCode(), exception.getMessage()));
    }

    @ExceptionHandler(value = {AccessDeniedException.class})
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ResponseEntity<ErrorResponseTemplate> handleAccessDeniedException(AccessDeniedException exception) {
        GlobalExceptionHandler.log.error("[Error message]", exception);
        return ResponseEntity.status(ErrorCode.API_NOT_ACCESSIBLE.getCode()).body(ErrorResponseTemplate.error(ErrorCode.API_NOT_ACCESSIBLE, exception.getMessage()));
    }

    @ExceptionHandler(value = {Exception.class})
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<ErrorResponseTemplate> unknownException(Exception exception) {
        GlobalExceptionHandler.log.error("[Error message]", exception);
        return ResponseEntity.status(ErrorCode.INTERNAL_SERVER.getCode()).body(ErrorResponseTemplate.error(ErrorCode.INTERNAL_SERVER, exception.getMessage()));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponseTemplate> handleMethodArgumentNotValidException(MethodArgumentNotValidException exception) {
        GlobalExceptionHandler.log.error("[Error message]", exception);
        Map<String, String> errors = new HashMap<>();
        exception.getBindingResult().getAllErrors().forEach((error) -> {
            String filedName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(filedName, errorMessage);
        });
        return ResponseEntity.status(ErrorCode.INVALID_PARAMETER.getCode()).body(ErrorResponseTemplate.error(ErrorCode.INVALID_PARAMETER, "Request Argument가 유효하지 않습니다. 세부 사항은 Response Data를 확인하세요.", errors));
    }
}
