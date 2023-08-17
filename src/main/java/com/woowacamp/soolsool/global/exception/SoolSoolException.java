package com.woowacamp.soolsool.global.exception;


import lombok.Getter;

@Getter
public class SoolSoolException extends RuntimeException {

    private final ErrorCode errorCode;

    public SoolSoolException(final ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }
}
