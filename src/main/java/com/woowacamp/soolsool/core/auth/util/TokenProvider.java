package com.woowacamp.soolsool.core.auth.util;

import com.woowacamp.soolsool.core.auth.dto.UserDto;
import com.woowacamp.soolsool.core.member.domain.Member;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
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
            .claim(ROLE_TYPE, member.getRole().getName().getEType())
            .setExpiration(validity)
            .signWith(SignatureAlgorithm.HS256, secretKey)
            .compact();
    }

    public UserDto getUserDto(final String token) {
        final Claims body = Jwts.parser()
            .setSigningKey(secretKey)
            .parseClaimsJws(token)
            .getBody();
        final String authority = (String) body.get(ROLE_TYPE);

        return new UserDto(body.getSubject(), authority);
    }

    public boolean validateToken(final String token) {
        try {
            final Jws<Claims> claims = Jwts
                .parser()
                .setSigningKey(secretKey)
                .parseClaimsJws(token);
            return !claims.getBody().getExpiration().before(new Date());
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }

}
