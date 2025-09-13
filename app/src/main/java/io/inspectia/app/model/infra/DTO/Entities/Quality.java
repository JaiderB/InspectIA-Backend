package io.inspectia.app.model.infra.DTO.Entities;

import io.inspectia.app.model.infra.POJO.Enums.Entites.QualityCategory;
import lombok.Data;

import java.util.List;

@Data
public class Quality {
    QualityCategory qualityCategory;
    Double score;
    List<Defect> defects;
}
