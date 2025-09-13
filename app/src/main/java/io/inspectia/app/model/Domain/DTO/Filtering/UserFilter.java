package io.inspectia.app.model.Domain.DTO.Filtering;

import lombok.Builder;
import lombok.Data;
import lombok.AllArgsConstructor;


@Builder
@AllArgsConstructor
@Data
public class UserFilter {
    String id;
    String email;
    String phone;

}
