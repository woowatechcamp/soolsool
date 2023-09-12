package com.woowacamp.soolsool.global.exception;

import com.woowacamp.soolsool.global.code.DefaultErrorCode;
import com.woowacamp.soolsool.global.code.ErrorCode;
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
    protected ResponseEntity<ErrorResponse> handleSoolSoolException(final SoolSoolException e) {
        final ErrorCode errorCode = e.getErrorCode();
        final ErrorResponse errorResponse = ErrorResponse.from(errorCode);

        if (isInternalServerError(errorCode)) {
            log.error("!!! SoolSoolException INTERNAL SERVER ERROR !!! | code : {} | message : {}",
                errorCode.getCode(), e.getMessage());
        } else {
            log.warn("SoolSoolException | code : {} | message : {}",
                errorCode.getCode(), e.getMessage());
        }

        return new ResponseEntity<>(errorResponse, HttpStatus.valueOf(errorCode.getStatus()));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    protected ResponseEntity<ErrorResponse> handleValidationException(
        final MethodArgumentNotValidException exception
    ) {
        final ErrorCode errorCode = DefaultErrorCode.DEFAULT_VALIDATION_ERROR;
        final ErrorResponse errorResponse = ErrorResponse.from(errorCode);

        log.warn("Validation Error | code : {} | message : {}",
            errorCode.getCode(), exception.getMessage());

        return new ResponseEntity<>(errorResponse, HttpStatus.valueOf(errorCode.getStatus()));
    }

    @ExceptionHandler(Exception.class)
    protected ResponseEntity<ErrorResponse> handleException(final Exception e) {
        final ErrorCode errorCode = DefaultErrorCode.UNEXPECTED_ERROR;
        final ErrorResponse errorResponse = ErrorResponse.from(errorCode);

        log.error("!!! 예상치 못한 예외가 발생했습니다 !!! | code : {} | message : {} | log : {}",
            errorCode.getCode(), e.getMessage(), e.getStackTrace());
        e.printStackTrace();
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private boolean isInternalServerError(final ErrorCode errorCode) {
        return errorCode.getStatus() >= HttpStatus.INTERNAL_SERVER_ERROR.value();
    }
}
