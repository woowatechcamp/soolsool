package com.woowacamp.soolsool.global.exception;

import com.woowacamp.soolsool.global.code.ErrorCode;
import lombok.Getter;

@Getter
public class SoolSoolException extends RuntimeException {

    private final transient ErrorCode errorCode;

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
