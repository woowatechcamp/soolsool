package com.woowacamp.soolsool.global.aop;

import java.util.Arrays;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.stream.Collectors;
import javax.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Slf4j
@Aspect
@Component
public class RequestLoggingAspect {

    @Around("@annotation(com.woowacamp.soolsool.global.aop.RequestLogging)")
    public Object logRequest(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        final ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (Objects.isNull(attributes)) {
            return proceedingJoinPoint.proceed();
        }

        final HttpServletRequest request = attributes.getRequest();
        final Map<String, String[]> params = request.getParameterMap();

        log.info("[REQUEST HANDLE] {} => {} {}{}, body: {}",
            request.getRemoteHost(),
            request.getMethod(), request.getRequestURI(), mapToFormatted(params),
            extractBody(proceedingJoinPoint.getArgs())
        );

        return proceedingJoinPoint.proceed();
    }

    private String mapToFormatted(final Map<String, String[]> params) {
        final String formattedParams = params.entrySet().stream()
            .map(this::formatParam)
            .collect(Collectors.joining("&"));

        if (StringUtils.hasText(formattedParams)) {
            return "?" + formattedParams;
        }

        return formattedParams;
    }

    private String extractBody(final Object[] args) {
        return String.join(",", Arrays.toString(args));
    }

    private String formatParam(final Entry<String, String[]> entry) {
        return Arrays.stream(entry.getValue())
            .map(value -> entry.getKey() + "=" + value)
            .collect(Collectors.joining("&"));
    }
}
