package com.woowacamp.soolsool.global.common;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.Getter;

@Getter
public class ApiResponse<T> {

    private final int status;
    private final String code;
    private final String message;
    private final T data;

    // TODO: Jackson으로 줄일 순 없을까?
    @JsonCreator
    public ApiResponse(
        final int status,
        final String code,
        final String message,
        final T data
    ) {
        this.status = status;
        this.code = code;
        this.message = message;
        this.data = data;
    }

    private ApiResponse(final ResultCode resultCode, final T data) {
        this(
            resultCode.getStatus(),
            resultCode.getCode(),
            resultCode.getMessage(),
            data
        );
    }

    public static <T> ApiResponse<T> of(final ResultCode resultCode, final T data) {
        return new ApiResponse<>(resultCode, data);
    }
}
