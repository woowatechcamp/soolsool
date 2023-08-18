package com.woowacamp.soolsool.global.exception;

import lombok.Getter;

@Getter
public class SoolSoolException extends RuntimeException {

    private final ErrorCode errorCode;

    public SoolSoolException(final ErrorCode errorCode) {
        this.errorCode = errorCode;
    }

    public int getStatus() {
        return errorCode.getStatus();
    }

    public String getCode() {
        return errorCode.getCode();
    }

    @Override
    public String getMessage() {
        return errorCode.getMessage();
    }
}
