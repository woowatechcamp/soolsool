package com.woowacamp.soolsool.global.auth.util;

import static com.woowacamp.soolsool.global.auth.code.AuthErrorCode.TOKEN_ERROR;

import com.woowacamp.soolsool.core.member.domain.Member;
import com.woowacamp.soolsool.global.auth.dto.UserDto;
import com.woowacamp.soolsool.global.exception.SoolSoolException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.util.Date;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class TokenProvider {

    private static final String ROLE_TYPE = "ROLE_TYPE";
    private final String secretKey;
    private final long validityInMilliseconds;

    public TokenProvider(
        @Value("${security.jwt.token.secret-key}") final String secretKey,
        @Value("${security.jwt.token.expire-length}") final long validityInMilliseconds
    ) {
        this.secretKey = secretKey;
        this.validityInMilliseconds = validityInMilliseconds;
    }

    public String createToken(final Member member) {
        final Date now = new Date();
        final Date validity = new Date(now.getTime() + validityInMilliseconds);

        return Jwts.builder()
            .setSubject(member.getId().toString())
            .setIssuedAt(now)
            .claim(ROLE_TYPE, member.getRole().getName().toString())
            .setExpiration(validity)
            .signWith(SignatureAlgorithm.HS256, secretKey)
            .compact();
    }

    public void validateToken(final String token) {
        final Claims body = parseClaimBody(token);
        if (body.getExpiration().before(new Date())) {
            throw new SoolSoolException(TOKEN_ERROR);
        }
    }

    private Claims parseClaimBody(final String token) {
        if (token == null || token.isEmpty()) {
            throw new SoolSoolException(TOKEN_ERROR);
        }
        try {
            return Jwts
                .parser()
                .setSigningKey(secretKey)
                .parseClaimsJws(token).getBody();
        } catch (final JwtException | IllegalArgumentException e) {
            throw new SoolSoolException(TOKEN_ERROR);
        }
    }

    public UserDto getUserDto(final String token) {
        final Claims body = parseClaimBody(token);

        // TODO: String이 아닌 MemberRoleType 활용?
        final String authority = (String) body.get(ROLE_TYPE);

        return new UserDto(body.getSubject(), authority);
    }
}
