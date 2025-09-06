package io.inspectia.app.model.Domain.DTO.Entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class UserResponse {

    String uuid;
    String id;
    String name;
    String email;
    String phone;
    List<String> reports;



}
