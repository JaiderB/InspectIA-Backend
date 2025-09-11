package io.inspectia.app.model.Mapper;

import io.inspectia.app.model.Domain.DTO.Entities.UserResponse;
import io.inspectia.app.model.infra.DTO.Entities.User;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component("userMapper")
public final class UserMapper {

    private UserMapper() {}

    public static UserResponse fromUser(User user) {
        if (user == null) {
            return null;
        }
        return UserResponse.builder()
                .email(user.getEmail())
                .id(user.getId())
                .name(user.getName())
                .phone(user.getPhone() != null ? user.getPhone(): "")
                .reports(user.getReports())
                .uuid(user.getUuid())
                .build();
    }

    public static List<UserResponse> fromEntities(List<User> entities) {
        return entities.stream()
                .map(UserMapper::fromUser)
                .collect(Collectors.toList());
    }
}