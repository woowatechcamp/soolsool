package com.woowacamp.soolsool.global.exception;


import lombok.Getter;

@Getter
public class ShoppingException extends RuntimeException {

    private final ErrorCode errorCode;

    public ShoppingException(final ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }
}
