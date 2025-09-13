package io.inspectia.app.model.Mapper;
import io.inspectia.app.model.Domain.DTO.Entities.AnalysisFileResponse;
import io.inspectia.app.model.infra.DTO.Entities.AnalysisFile;
import java.util.List;
import java.util.stream.Collectors;

public class AnalysisFilesMapper {

    private AnalysisFilesMapper() {}

    public static AnalysisFileResponse fromAnalysisFile(AnalysisFile analysis) {
        if (analysis == null) {
            return null;
        }
        return AnalysisFileResponse.builder()
                .filename(analysis.getFilename())
                .build();
    }

    public static List<AnalysisFileResponse> fromEntities(List<AnalysisFile> entities) {
        return entities.stream()
                .map(AnalysisFilesMapper::fromAnalysisFile)
                .collect(Collectors.toList());
    }

}
