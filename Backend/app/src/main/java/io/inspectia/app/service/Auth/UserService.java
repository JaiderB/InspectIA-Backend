package io.inspectia.app.service.Auth;

import io.inspectia.app.exceptions.AuthExceptions.RecordAlreadyExistsException;
import io.inspectia.app.model.infra.DTO.Entities.User;
import io.inspectia.app.model.Domain.DTO.Filtering.UserFilter;
import io.inspectia.app.model.Domain.DTO.Jwt.RegisterRequest;
import io.inspectia.app.repository.Firebase.Auth.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutionException;

import static io.grpc.internal.ConscryptLoader.isPresent;

@Service
@AllArgsConstructor
public class UserService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    public Optional<User> register(RegisterRequest userRequest) throws ExecutionException, InterruptedException {

        Optional<List<User>> usersMath = filterAnyByFieldsUser(UserFilter.builder()
                .email(userRequest.email())
                .id(userRequest.id())
                .build());

        if(usersMath.isEmpty() || usersMath.get().isEmpty()){
            User user = User.builder()
                    .id(userRequest.id())
                    .name(userRequest.name())
                    .email(userRequest.email())
                    .password(passwordEncoder.encode(userRequest.password()))
                    .reports(new ArrayList<String>())
                    .refreshToken(null)
                    .build();

            return userRepository.save(user);
        }else{
            throw new RecordAlreadyExistsException();
        }

    }

    public Optional<List<User>> filterAnyByFieldsUser(UserFilter filters) throws ExecutionException, InterruptedException {
        return userRepository.equalsAnyFilter(filters);
    }

    public Optional<List<User>> filterAllByFieldsUser(UserFilter filters) throws ExecutionException, InterruptedException {
        return userRepository.equalsAllFilter(filters);
    }

    public void update(User user){
        userRepository.update(user);
    }

    public Optional<User> getByDocument(String document) throws ExecutionException, InterruptedException {
        return userRepository.getByDocument(document);
    }

    public Optional<User> getByEmail(String email) throws ExecutionException, InterruptedException {
        Optional<List<User>> user = filterAllByFieldsUser(UserFilter.builder()
                .email(email)
                .build());

        return user.map(List::getFirst);
    }
}
