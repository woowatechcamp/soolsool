package com.woowacamp.soolsool.core.auth;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;

import java.util.Enumeration;
import javax.servlet.http.HttpServletRequest;
import org.apache.logging.log4j.util.Strings;
import org.springframework.stereotype.Component;

@Component
public class AuthorizationExtractor {

    private static final String EXTRACT_TYPE = "Bearer";

    public String extract(HttpServletRequest request) {
        Enumeration<String> headers = request.getHeaders(AUTHORIZATION);
        while (headers.hasMoreElements()) {
            String value = headers.nextElement();
            if (value.toLowerCase().startsWith(EXTRACT_TYPE.toLowerCase())) {
                return value.substring(EXTRACT_TYPE.length()).trim();
            }
        }
        return Strings.EMPTY;
    }
}
