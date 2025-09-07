package io.inspectia.app.model.infra.DTO.Entities;

import lombok.Data;

import java.util.List;

@Data
public class AnalysisFile {

    String filename;
    List<Quality> qualities;

}
