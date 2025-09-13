package io.inspectia.app.model.infra.DTO.Entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class User {

    String uuid;
    String id;
    String name;
    String email;
    String password;
    String phone;
    List<String> reports;
    String refreshToken;

}
