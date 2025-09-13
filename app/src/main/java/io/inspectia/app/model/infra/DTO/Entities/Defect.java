package io.inspectia.app.model.infra.DTO.Entities;

import io.inspectia.app.model.infra.POJO.Enums.Entites.Severity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class Defect {

    Integer line;
    Severity severity;

}
