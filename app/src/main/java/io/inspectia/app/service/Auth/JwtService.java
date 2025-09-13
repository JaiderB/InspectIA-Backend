package io.inspectia.app.service.Auth;

import io.inspectia.app.model.infra.DTO.Entities.User;
import io.inspectia.app.model.infra.DTO.Jwt.Token;
import io.inspectia.app.model.infra.POJO.Enums.Jwt.State;
import io.inspectia.app.service.Manager.JwtManager;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class JwtService {

    @Value("${application.security.jwt.expiration}")
    private Long tokenExpiration;
    @Value("${application.security.jwt.refresh-token.expiration}")
    private Long refreshTokenExpiration;
    private final JwtManager jwtManager;


    public String generateToken(final User user){
        return jwtManager.buildToken(user, tokenExpiration);
    }

    public String generateRefreshToken(final User user){
        return jwtManager.buildToken(user, refreshTokenExpiration);
    }

    public Boolean isValidToken(final String token, final User user, Token tokenDB){
        final String username = jwtManager.extractEmail(token);
        return username.equals(user.getEmail()) && !isTokenExpired(token) && tokenDB.getState().equals(State.ACTIVE);
    }

    public Boolean isValidToken(final String token, final User user){
        final String username = jwtManager.extractEmail(token);
        final boolean expired = !isTokenExpired(token);
        return username.equals(user.getEmail()) && expired;
    }



    private Boolean isTokenExpired(String token){
        return jwtManager.extractExpiration(token).before(new Date());
    }


}