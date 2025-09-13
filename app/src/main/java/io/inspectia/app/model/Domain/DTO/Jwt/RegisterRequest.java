package io.inspectia.app.model.Domain.DTO.Jwt;

public record RegisterRequest(
        String id,
        String email,
        String password,
        String name
) {
}
