package com.woowacamp.soolsool.global.common;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

// TODO : API Response NOARgs 이 없어서 파싱이 안되서, 일단 final을 지웠는데, 고쳐주세요
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ApiResponse<T> {

    private int status;
    private String code;
    private String message;
    private T data;

    private ApiResponse(ResultCode resultCode, T data) {
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
