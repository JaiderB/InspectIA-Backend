package io.inspectia.app.config.Security.Models;

import io.inspectia.app.model.Domain.DTO.Filtering.UserFilter;
import io.inspectia.app.model.infra.DTO.Entities.User;
import io.inspectia.app.service.Auth.UserService;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.concurrent.ExecutionException;

@AllArgsConstructor
@Service
public class JwtUserDetailsService implements UserDetailsService {

    private final UserService userService;


    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        try {
            User dbUser = userService.getByEmail(email)
                            .get();

            return org.springframework.security.core.userdetails.User.builder()
                    .username(dbUser.getEmail())
                    .password(dbUser.getPassword())
                    .build();
        } catch (ExecutionException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

}
