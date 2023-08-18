package com.woowacamp.soolsool.core.auth.config;

import static com.woowacamp.soolsool.global.exception.AuthErrorCode.TOKEN_ERROR;

import com.woowacamp.soolsool.core.auth.dto.UserDto;
import com.woowacamp.soolsool.core.auth.dto.Vendor;
import com.woowacamp.soolsool.core.auth.util.AuthorizationExtractor;
import com.woowacamp.soolsool.core.auth.util.TokenProvider;
import com.woowacamp.soolsool.global.exception.SoolSoolException;
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

    private static final String PRINCIPAL = "principal";
    private final AuthorizationExtractor authorizationExtractor;
    private final TokenProvider tokenProvider;

    @Override
    public boolean preHandle(
        final HttpServletRequest request,
        final HttpServletResponse response,
        final Object handler
    ) throws Exception {

        final String token = authorizationExtractor.extract(request);
        if (token.isEmpty() || !tokenProvider.validateToken(token)) {
            throw new SoolSoolException(TOKEN_ERROR);
        }

        final UserDto principal = tokenProvider.getUserDto(token);
        if (!checkVendorClass(handler, Vendor.class, principal.getAuthority())) {
            return false;
        }
        request.setAttribute(PRINCIPAL, principal);
        return true;
    }

    private boolean checkVendorClass(final Object handler, final Class<Vendor> vendorClass,
        final String authority) {

        final HandlerMethod handlerMethod = (HandlerMethod) handler;
        final String className = vendorClass.getSimpleName().toUpperCase();

        return handlerMethod.getMethodAnnotation(vendorClass) == null || authority.equals(
            className);
    }
}
