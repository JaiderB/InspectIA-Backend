package io.inspectia.app.model.Domain.DTO.Jwt;

import com.fasterxml.jackson.annotation.JsonProperty;

public record TokenResponse (
    @JsonProperty("access_token")
    String accessToken,
    @JsonProperty("refresh_token")
    String refreshToken
){

}
