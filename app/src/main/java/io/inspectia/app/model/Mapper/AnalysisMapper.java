package io.inspectia.app.model.Mapper;

import com.google.cloud.firestore.FieldValue;
import io.inspectia.app.model.Domain.DTO.Entities.AnalysisResponse;
import io.inspectia.app.model.Domain.DTO.Entities.UserResponse;
import io.inspectia.app.model.infra.DTO.DB.AnalysisDB;
import io.inspectia.app.model.infra.DTO.Entities.Analysis;
import io.inspectia.app.model.infra.DTO.Entities.User;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Component("analysisResponseMapper")
public class AnalysisMapper {


    private AnalysisMapper() {}

    public static AnalysisResponse fromAnalysisToResponse(Analysis analysis) {
        if (analysis == null) {
            return null;
        }
        return AnalysisResponse.builder()
                .uuid(analysis.getUuid())
                .score(analysis.getScore())
                .fingerprintEmail(analysis.getFingerprintEmail())
                .validationDate(analysis.getValidationDate())
                .files(AnalysisFilesMapper.fromEntities(analysis.getFiles()))
                .build();
    }

    public static List<AnalysisResponse> fromEntitiesToResponse(List<Analysis> entities) {
        return entities.stream()
                .map(AnalysisMapper::fromAnalysisToResponse)
                .collect(Collectors.toList());
    }

    public static AnalysisDB fromAnalysisToDB(Analysis analysis) {
        if (analysis == null) {
            return null;
        }
        return AnalysisDB.builder()
                .score(analysis.getScore())
                .fingerprintEmail(analysis.getFingerprintEmail())
                .validationDate(FieldValue.serverTimestamp())
                .files(analysis.getFiles())
                .build();
    }

    public static List<AnalysisDB> fromEntitiesToDB(List<Analysis> entities) {
        return entities.stream()
                .map(AnalysisMapper::fromAnalysisToDB)
                .collect(Collectors.toList());
    }

}
