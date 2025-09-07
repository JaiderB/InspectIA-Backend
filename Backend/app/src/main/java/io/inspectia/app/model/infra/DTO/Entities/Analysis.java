package io.inspectia.app.model.infra.DTO.Entities;

import com.google.type.DateTime;
import lombok.Data;

import java.util.List;

@Data
public class Analysis {

    String uuid;
    String validationDate;
    Double score;
    List<AnalysisFile> files;
    String fingerprintEmail;

}
