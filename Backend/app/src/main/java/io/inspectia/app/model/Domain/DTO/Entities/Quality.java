package io.inspectia.app.model.Domain.DTO.Entities;

import io.inspectia.app.model.infra.POJO.Enums.Entites.QualityCategory;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@NoArgsConstructor
@AllArgsConstructor
@Data
public class Quality {
    QualityCategory qualityCategory;
    List<Defect> defects;
    Integer Score;
}
