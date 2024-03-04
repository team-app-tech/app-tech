package server.apptech.login.infrastructrue;

import io.jsonwebtoken.Header;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import server.apptech.login.domain.UserToken;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Component
public class JwtProvider {

    private final SecretKey secretKey;
    private final Long accessExpirationTime;
    private final Long refreshExpirationTime;

    public JwtProvider(
            @Value("${security.jwt.secret-key}") final String secretKey,
            @Value("${security.jwt.access-expiration-time}") final Long accessExpirationTime,
            @Value("${security.jwt.refresh-expiration-time}") final Long refreshExpirationTime
    ) {
        this.secretKey = Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));
        this.accessExpirationTime = accessExpirationTime;
        this.refreshExpirationTime = refreshExpirationTime;
    }



    public UserToken generateLoginToken(String subject) {

        String accessToken = createToken(subject, accessExpirationTime);
        String refreshToken = createToken("", refreshExpirationTime);
        return UserToken.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .accessExpirationTime(accessExpirationTime)
                .refreshExpirationTime(refreshExpirationTime)
                .build();
    }

    private String createToken(String subject, Long expirationMilliseconds) {
        Date now = new Date();
        Date validityDate = new Date(now.getTime() +  expirationMilliseconds);
        return Jwts.builder()
                .setHeaderParam(Header.TYPE, Header.JWT_TYPE)
                .setSubject(subject)
                .setIssuedAt(now)
                .setExpiration(validityDate)
                .signWith(secretKey, SignatureAlgorithm.HS256)
                .compact();
    }
}
