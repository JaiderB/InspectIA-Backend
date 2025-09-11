package io.inspectia.app.model.infra.DTO.DB;

import com.google.cloud.firestore.FieldValue;
import io.inspectia.app.model.infra.DTO.Entities.AnalysisFile;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class AnalysisDB {


    String uuid;
    FieldValue validationDate;
    Double score;
    List<AnalysisFile> files;
    String fingerprintEmail;


}
