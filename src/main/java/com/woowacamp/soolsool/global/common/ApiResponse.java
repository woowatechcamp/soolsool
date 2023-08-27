package com.woowacamp.soolsool.global.common;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor(onConstructor = @__(@JsonCreator))
public class ApiResponse<T> {

    private final int status;
    private final String code;
    private final String message;
    private final T data;

    private ApiResponse(final ResultCode resultCode, final T data) {
        this(
            resultCode.getStatus(),
            resultCode.getCode(),
            resultCode.getMessage(),
            data
        );
    }

    private ApiResponse(final ResultCode resultCode) {
        this(
            resultCode.getStatus(),
            resultCode.getCode(),
            resultCode.getMessage(),
            null
        );
    }

    public static <T> ApiResponse<T> of(final ResultCode resultCode, final T data) {
        return new ApiResponse<>(resultCode, data);
    }

    public static <T> ApiResponse<T> from(final ResultCode resultCode) {
        return new ApiResponse<>(resultCode);
    }
}
