package com.woowacamp.soolsool.global.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(SoolSoolException.class)
    protected ResponseEntity<ErrorResponse> handleShoppingException(final SoolSoolException e) {
        log.error("message : {}", e.getMessage());

        final ErrorCode errorCode = e.getErrorCode();
        final ErrorResponse errorResponse = ErrorResponse.from(errorCode);

        return new ResponseEntity<>(errorResponse, HttpStatus.valueOf(errorCode.getStatus()));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    protected ResponseEntity<ErrorResponse> handleValidationException(
        final MethodArgumentNotValidException exception
    ) {
        log.error("Validation 에러 : {}", exception.getMessage());

        final ErrorCode errorCode = DefaultErrorCode.DEFAULT_VALIDATION_ERROR;
        final ErrorResponse errorResponse = ErrorResponse.from(errorCode);

        return new ResponseEntity<>(errorResponse, HttpStatus.valueOf(errorCode.getStatus()));
    }
}
