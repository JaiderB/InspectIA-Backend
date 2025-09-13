package io.inspectia.app.service.Manager;

import io.inspectia.app.model.infra.DTO.Entities.User;
import io.inspectia.app.model.infra.POJO.Enums.Jwt.State;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.Map;

@Component("jwtManager")
public class JwtManager{

    @Value("${application.security.jwt.secret-key}")
    private String secretKey;

    public String getTokenFromBearer(String bearerAuth){
        if (bearerAuth == null || !bearerAuth.startsWith("Bearer ")){
            throw new IllegalArgumentException("Invalid token.");
        }

        return bearerAuth.substring(7);
    }

    public String extractEmail(String token){
        return getClaims(token)
                .getSubject();
    }

    public Date extractExpiration(final String token){
        return getClaims(token)
                .getExpiration();
    }

    public Claims getClaims(final String token){
        return getJwtParser()
                .parseSignedClaims(token)
                .getPayload();
    }

    public String buildToken(final User user, final long expiration){
        return Jwts.builder()
                .id(user.getId())
                .claims(Map.of("name", user.getName()))
                .subject(user.getEmail())
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(getSignInKey())
                .compact();
    }

    private JwtParser getJwtParser(){
        return Jwts.parser()
                .verifyWith(getSignInKey())
                .build();
    }

    protected SecretKey getSignInKey(){
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}