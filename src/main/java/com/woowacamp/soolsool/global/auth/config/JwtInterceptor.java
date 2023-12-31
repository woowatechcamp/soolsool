package com.woowacamp.soolsool.global.auth.config;

import com.woowacamp.soolsool.core.member.domain.vo.MemberRoleType;
import com.woowacamp.soolsool.global.auth.code.AuthErrorCode;
import com.woowacamp.soolsool.global.auth.dto.NoAuth;
import com.woowacamp.soolsool.global.auth.dto.Vendor;
import com.woowacamp.soolsool.global.auth.util.AuthorizationExtractor;
import com.woowacamp.soolsool.global.auth.util.TokenProvider;
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

    private final AuthorizationExtractor authorizationExtractor;
    private final TokenProvider tokenProvider;

    @Override
    public boolean preHandle(
        final HttpServletRequest request,
        final HttpServletResponse response,
        final Object handler
    ) {

        if ("OPTIONS".equals(request.getMethod())) {
            response.setStatus(HttpServletResponse.SC_ACCEPTED);
            return true;
        }
        final HandlerMethod handlerMethod = (HandlerMethod) handler;

        if (handlerMethod.hasMethodAnnotation(NoAuth.class)) {
            return true;
        }

        // TODO: 토큰이 없을 때 메시지
        // TODO: subject에서 권한만 VENDOR로 바꾸면 판매자처럼 행동 가능한 버그 수정

        final String token = authorizationExtractor.extractToken(request);
        tokenProvider.validateToken(token); // TODO: getUserDto 안으로 이동해도 될 듯
        final String authority = tokenProvider.getUserDto(token).getAuthority();

        validateVendorMethod(handlerMethod, authority);

        return true;
    }

    private void validateVendorMethod(final HandlerMethod handlerMethod, final String authority) {
        if (handlerMethod.hasMethodAnnotation(Vendor.class) &&
            !authority.equals(MemberRoleType.VENDOR.getType())) {
            throw new SoolSoolException(AuthErrorCode.INVALID_AUTHORITY);
        }
    }
}
