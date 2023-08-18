package com.woowacamp.soolsool.unit.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.woowacamp.soolsool.core.auth.AuthorizationExtractor;
import org.apache.http.HttpHeaders;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpServletRequest;

class AuthorizationExtractorTest {

    private AuthorizationExtractor authorizationExtractor;

    @BeforeEach
    public void init() {
        authorizationExtractor = new AuthorizationExtractor();
    }

    @Test
    @DisplayName("토큰을 정상적으로 추출한다.")
    void extract() {
        /* given */
        final MockHttpServletRequest request = new MockHttpServletRequest();
        request.addHeader(HttpHeaders.AUTHORIZATION, "Bearer 123");

        /* when */
        final String extract = authorizationExtractor.extract(request);

        /* then */
        assertThat(extract).isEqualTo("123");
    }

}
