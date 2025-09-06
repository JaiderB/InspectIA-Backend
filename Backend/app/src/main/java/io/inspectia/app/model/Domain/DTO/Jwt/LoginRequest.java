package io.inspectia.app.model.Domain.DTO.Jwt;

public record LoginRequest(
        String email,
        String password
){
}
