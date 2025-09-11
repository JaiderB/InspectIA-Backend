package io.inspectia.app.controller.Auth;

import io.inspectia.app.exceptions.AuthExceptions.RecordAlreadyExistsException;
import io.inspectia.app.model.infra.DTO.Entities.User;
import io.inspectia.app.model.Domain.DTO.Jwt.LoginRequest;
import io.inspectia.app.model.Domain.DTO.Jwt.TokenResponse;
import io.inspectia.app.model.Domain.DTO.Jwt.RegisterRequest;
import io.inspectia.app.model.infra.DTO.Jwt.Token;
import io.inspectia.app.service.Auth.AuthService;
import io.inspectia.app.service.Auth.UserService;
import io.inspectia.app.service.Manager.JwtManager;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Optional;
import java.util.concurrent.ExecutionException;

@RequiredArgsConstructor
@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = "http://localhost:4200")
public class AuthController {

    private final UserService userService;
    private final AuthService authService;
    @PostMapping("/register")
    private ResponseEntity<TokenResponse> register (@RequestBody final RegisterRequest request) throws ExecutionException, InterruptedException {

        try{
            final Optional<User> savedUser = userService.register(request);
            TokenResponse token = authService.authenticateRegister(savedUser.get()); // Ispresent?
            return new ResponseEntity<TokenResponse>(token, HttpStatus.CREATED);
        }catch (RecordAlreadyExistsException e){
            return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
        }

    }

    @PostMapping("/login")
    private ResponseEntity<TokenResponse> login (@RequestBody final LoginRequest loginRequest) throws ExecutionException, InterruptedException {

        TokenResponse tokenResponse = authService.authenticateLogin(loginRequest);
        return ResponseEntity.ok(tokenResponse);

    }

    @PostMapping("/refresh")
    private ResponseEntity<TokenResponse> refresh(@CookieValue("refresh_token") String refreshTokenCookie) throws ExecutionException, InterruptedException {

        TokenResponse tokenResponse = authService.refreshToken(refreshTokenCookie);
        return ResponseEntity.ok(tokenResponse);

    }

}
