package org.prd.securityexample.service.auth;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.prd.securityexample.model.entity.RefreshToken;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.crypto.SecretKey;
import java.security.Key;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.Map;
import java.util.UUID;

@Slf4j
@Component
public class JwtService {

    @Value("${security.jwt.expiration-in-minutes}")
    private Long EXPIRATION_IN_MINUTES;

    @Value("${security.jwt.secret-key}")
    private String SECRET_KEY;


    public String generateToken(UserDetails user, Map<String, Object> extraClaims) {

        Date issuedAt = new Date(System.currentTimeMillis());
        Date expiration = new Date( (EXPIRATION_IN_MINUTES * 60 * 1000) + issuedAt.getTime() );

        return Jwts.builder()
                .header().type("JWT").and()
                .subject(user.getUsername())
                .issuedAt(issuedAt)
                .expiration(expiration)
                .claims(extraClaims)
                .signWith(generateKey(), Jwts.SIG.HS256)
                .compact();
    }

    public String generateRefreshToken(UserDetails user) {

        Date issuedAt = new Date(System.currentTimeMillis());
        Date expiration = new Date( (EXPIRATION_IN_MINUTES * 4 * 60 * 1000) + issuedAt.getTime() );

        return Jwts.builder()
                .header().type("JWT").and()
                .subject(user.getUsername())
                .issuedAt(issuedAt)
                .expiration(expiration)
                .id(UUID.randomUUID().toString())
                .claims(
                        Map.of("type_token", "refresh")
                )
                .signWith(generateKey(), Jwts.SIG.HS256)
                .compact();
    }

    public RefreshToken rfJwtToRefreshToken(String refresh) {
        Claims claims = extractAllClaims(refresh);
        return RefreshToken.builder()
                .jti(claims.getId())
                .expiresAt(claims.getExpiration())
                .createdAt(claims.getIssuedAt())
                .token(refresh)
                .build();
    }

    private Claims extractAllClaims(String jwt) {
        try{
            return Jwts.parser().verifyWith(generateKey()).build()
                    .parseSignedClaims(jwt).getPayload();
        } catch (ExpiredJwtException e) {
            log.warn("token expired");
        } catch (UnsupportedJwtException e) {
            log.warn("token unsupported");
        } catch (MalformedJwtException e) {
            log.warn("token malformed");
        } catch (SignatureException e) {
            log.warn("bad signature");
        } catch (IllegalArgumentException e) {
            log.warn("illegal args");
        }
        throw new RuntimeException("Invalid token");
    }

    public String extractUsername(String jwt) {
        return extractAllClaims(jwt).getSubject();
    }

    public String extractJti(String jwt) {
        return extractAllClaims(jwt).getId();
    }

    public String extractJwtFromRequest(HttpServletRequest request) {
        String authorizationHeader = request.getHeader("Authorization");//Bearer jwt
        if(!StringUtils.hasText(authorizationHeader) || !authorizationHeader.startsWith("Bearer ")){
            return null;
        }
        return authorizationHeader.split(" ")[1];
    }


    private SecretKey generateKey() {
        byte[] passwordDecoded = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(passwordDecoded);
    }
}