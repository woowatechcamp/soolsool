package com.woowacamp.soolsool.global.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(SoolSoolException.class)
    protected ResponseEntity<ErrorResponse> handleShoppingException(final SoolSoolException e) {
        log.error("message : {}", e.getMessage());

        ErrorCode errorCode = e.getErrorCode();
        ErrorResponse errorResponse = ErrorResponse.from(errorCode);

        return new ResponseEntity<>(errorResponse, HttpStatus.valueOf(errorCode.getStatus()));
    }
}
