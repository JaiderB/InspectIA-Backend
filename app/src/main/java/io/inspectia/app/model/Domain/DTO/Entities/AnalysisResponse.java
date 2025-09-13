package io.inspectia.app.model.Domain.DTO.Entities;

import io.inspectia.app.model.infra.DTO.Entities.AnalysisFile;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.jackson.Jacksonized;

import java.util.Date;
import java.util.List;

@Jacksonized
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class AnalysisResponse {
    String uuid;
    Date validationDate;
    Double score;
    List<AnalysisFileResponse> files;
    String fingerprintEmail;
}
