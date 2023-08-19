package com.woowacamp.soolsool.core.auth.config;

import com.woowacamp.soolsool.core.auth.dto.NoAuth;
import com.woowacamp.soolsool.core.auth.dto.UserDto;
import com.woowacamp.soolsool.core.auth.dto.Vendor;
import com.woowacamp.soolsool.core.auth.util.AuthorizationExtractor;
import com.woowacamp.soolsool.core.auth.util.TokenProvider;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtInterceptor implements HandlerInterceptor {

    private final AuthorizationExtractor authorizationExtractor;
    private final TokenProvider tokenProvider;

    @Override
    public boolean preHandle(
        final HttpServletRequest request,
        final HttpServletResponse response,
        final Object handler
    ) {
        final HandlerMethod handlerMethod = (HandlerMethod) handler;

        if (handlerMethod.getMethodAnnotation(NoAuth.class) != null) {
            return true;
        }

        final String token = authorizationExtractor.extractToken(request);
        tokenProvider.validateToken(token);

        final UserDto principal = tokenProvider.getUserDto(token);
        return checkVendorClass(handlerMethod, Vendor.class, principal.getAuthority());
    }

    private boolean checkVendorClass(
        final HandlerMethod handlerMethod,
        final Class<Vendor> vendorClass,
        final String authority
    ) {
        final String vendorClassName = vendorClass.getSimpleName().toUpperCase();
        return handlerMethod.getMethodAnnotation(vendorClass) == null
            || authority.equals(vendorClassName);
    }
}
