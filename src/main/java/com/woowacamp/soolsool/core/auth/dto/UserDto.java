package com.woowacamp.soolsool.core.auth.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class UserDto {

    private final String subject;
    private final String authority;
}
