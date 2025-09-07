package io.inspectia.app.service.Auth;

import io.inspectia.app.model.Domain.DTO.Filtering.UserFilter;
import io.inspectia.app.model.Domain.DTO.Jwt.LoginRequest;
import io.inspectia.app.model.infra.DTO.Entities.User;
import io.inspectia.app.model.infra.DTO.Jwt.Token;
import io.inspectia.app.model.Domain.DTO.Jwt.TokenResponse;
import io.inspectia.app.model.infra.POJO.Enums.Jwt.State;
import io.inspectia.app.model.infra.POJO.Enums.Jwt.TokenType;
import io.inspectia.app.repository.Firebase.Auth.TokenRepository;
import io.inspectia.app.service.Manager.JwtManager;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.token.TokenService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.atomic.AtomicReference;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final TokenRepository tokenRepository;
    private final UserService userService;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final JwtManager jwtManager;


    public TokenResponse authenticateRegister(User user) throws ExecutionException, InterruptedException {
        return generateTokens(user);
    }

    public TokenResponse authenticateLogin(LoginRequest loginRequest) throws ExecutionException, InterruptedException {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.email(),
                        loginRequest.password()
                )
        );

       Optional<List<User>> usersMath = userService
                .filterAllByFieldsUser(
                        UserFilter.builder()
                        .email(loginRequest.email())
                        .build()
                );

       AtomicReference<User> user = new AtomicReference<>();
               
       usersMath.ifPresent(users ->{
           user.set(users.getFirst());
       });

        cleanUserTokens(user.get());
        return generateTokens(user.get());
    }

    public TokenResponse refreshToken(final String refreshToken) throws ExecutionException, InterruptedException {
        if (refreshToken == null){
               throw new IllegalArgumentException("Invalid Refresh Token");
        }

        final String userEmail = jwtManager.extractEmail(refreshToken);

        if(userEmail == null){
            throw new IllegalArgumentException("Invalid Refresh Token");
        }

        Optional<User> user = userService.getByEmail(userEmail);

        if(user.isEmpty()){
            throw new UsernameNotFoundException("Email in token doesn't exist");
        }

        Optional<Token> tokenDB = tokenRepository.getToken(user.get().getRefreshToken());

        if(tokenDB.isEmpty()){
            throw new UsernameNotFoundException("The token doesn't exist for user");
        }

        if (!jwtService.isValidToken(refreshToken, user.get(), tokenDB.get())){
            throw new IllegalArgumentException("Invalid Refresh Token");
        }

        final String accessToken = jwtService.generateToken(user.get());
        return new TokenResponse(accessToken, refreshToken);
    }

    public void cleanUserTokens(final User user) throws ExecutionException, InterruptedException {
        final Optional<List<Token>> userTokens = tokenRepository.getAllActiveUserTokens(user.getId());

        if (userTokens.isPresent() && !userTokens.get().isEmpty()){
            for(final Token token: userTokens.get()){
                token.setState(State.REVOKED);
                tokenRepository.update(token);
            }
        }
    }


    public void logout(final String token){

        String userEmail = jwtManager.extractEmail(jwtManager.getTokenFromBearer(token));

        try{
            Optional<User> user = userService.getByEmail(userEmail);

            if(user.isPresent()){
                cleanUserTokens(user.get());
            }else{
                throw new UsernameNotFoundException("The user doesn´t exist in DB");
            }

        } catch (ExecutionException | InterruptedException e) {
            System.out.println(Arrays.toString(e.getStackTrace()));
        }
    }


    private void saveUserToken(User user, String jwtToken, TokenType tokenType) throws ExecutionException, InterruptedException {

        if(tokenType.equals(TokenType.REFRESH_TOKEN)){

            Token token = Token.builder()
                    .userId(user.getId())
                    .tokenType(tokenType)
                    .state(State.ACTIVE)
                    .token(jwtToken)
                    .build();

            Optional<Token> savedToken = tokenRepository.save(token);
            User userWithTokens = new User();
            userWithTokens.setRefreshToken(savedToken.get().getHashId());
            userWithTokens.setUuid(user.getUuid());
            userService.update(userWithTokens);

        }
    }

    private TokenResponse generateTokens(User user) throws ExecutionException, InterruptedException {

        String jwtToken = jwtService.generateToken(user);
        String jwtRefreshToken = jwtService.generateRefreshToken(user);
        saveUserToken(user, jwtRefreshToken, TokenType.REFRESH_TOKEN);
        return new TokenResponse(jwtToken, jwtRefreshToken);

    }

}
