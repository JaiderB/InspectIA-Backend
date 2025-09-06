package io.inspectia.app.model.Mapper;

import io.inspectia.app.model.Domain.DTO.Entities.UserResponse;
import io.inspectia.app.model.infra.DTO.Entities.User;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component("userResponseMapper")
public final class UserResponseMapper {

    // Constructor privado para evitar que se instancie la clase de utilidad
    private UserResponseMapper() {}

    /**
     * Convierte una entidad de usuario a un DTO de respuesta.
     * @param user La entidad de la base de datos.
     * @return Un DTO listo para ser enviado en la respuesta de la API.
     */
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

    /**
     * Convierte una lista de entidades a una lista de DTOs.
     * @param entities Lista de entidades de la base de datos.
     * @return Lista de DTOs de respuesta.
     */
    public static List<UserResponse> fromEntities(List<User> entities) {
        return entities.stream()
                .map(UserResponseMapper::fromUser)
                .collect(Collectors.toList());
    }
}