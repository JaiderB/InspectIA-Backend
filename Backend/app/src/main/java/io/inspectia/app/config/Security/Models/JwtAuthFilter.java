package io.inspectia.app.config.Security.Models;

import io.inspectia.app.model.infra.DTO.Entities.User;
import io.inspectia.app.model.infra.DTO.Jwt.Token;
import io.inspectia.app.model.infra.POJO.Enums.Jwt.State;
import io.inspectia.app.service.Auth.JwtService;
import io.inspectia.app.service.Auth.UserService;
import io.inspectia.app.service.Manager.JwtManager;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.apache.http.HttpHeaders;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Optional;
import java.util.concurrent.ExecutionException;

@Component
@AllArgsConstructor
public class JwtAuthFilter extends OncePerRequestFilter {

    private final UserDetailsService userDetailsService;
    private final JwtService jwtService;
    private final UserService userService;
    private final JwtManager jwtManager;


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        if (request.getServletPath().contains("/auth")) {
            filterChain.doFilter(request, response);
            return;
        }

        final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        final String jwtToken = jwtManager.getTokenFromBearer(authHeader);
        final String userEmail = jwtManager.extractEmail(jwtToken);

        if(userEmail == null || SecurityContextHolder.getContext().getAuthentication() != null){
            filterChain.doFilter(request, response);
            return;
        }

        final UserDetails userDetails = userDetailsService.loadUserByUsername(userEmail);

        try {
            final Optional<User> user = userService.getByEmail(userDetails.getUsername());

            if (user.isEmpty()){
                return;
            }

            final Boolean isValidToken = jwtService.isValidToken(jwtToken, user.get());

            if(!isValidToken){
                filterChain.doFilter(request, response);
                return;
            }

            final var authToken = new UsernamePasswordAuthenticationToken(
                    userDetails,
                    null,
                    userDetails.getAuthorities()
            );

            authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            SecurityContextHolder.getContext().setAuthentication(authToken);

            filterChain.doFilter(request, response);

        } catch (ExecutionException | InterruptedException e) {
            filterChain.doFilter(request, response);
        }

    }

}
