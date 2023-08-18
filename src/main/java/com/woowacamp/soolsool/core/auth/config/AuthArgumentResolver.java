package com.woowacamp.soolsool.core.auth.config;

import com.woowacamp.soolsool.core.auth.dto.LoginUser;
import com.woowacamp.soolsool.core.auth.dto.UserDto;
import javax.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

@Slf4j
@Component
public class AuthArgumentResolver implements HandlerMethodArgumentResolver {

    private static final String PRINCIPAL = "principal";

    @Override
    public boolean supportsParameter(final MethodParameter parameter) {
        return parameter.hasParameterAnnotation(LoginUser.class);
    }

    @Override
    public Long resolveArgument(
        final MethodParameter parameter,
        final ModelAndViewContainer mavContainer,
        final NativeWebRequest webRequest,
        final WebDataBinderFactory binderFactory
    )
        throws Exception {

        final HttpServletRequest servletRequest = (HttpServletRequest) webRequest.getNativeRequest();
        final UserDto principal = (UserDto) servletRequest.getAttribute(PRINCIPAL);
        return Long.parseLong(principal.getSubject());
    }
}
