package io.inspectia.app.model.infra.DTO.Jwt;

import io.inspectia.app.model.infra.POJO.Enums.Jwt.State;
import io.inspectia.app.model.infra.POJO.Enums.Jwt.TokenType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Token {

    String hashId;
    String token;
    State state;
    TokenType tokenType;
    String userId;
}
