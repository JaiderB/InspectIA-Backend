package io.inspectia.app.model.infra.DTO.Entities;


import com.google.cloud.firestore.FieldValue;
import com.google.cloud.firestore.annotation.ServerTimestamp;
import com.google.firebase.database.ServerValue;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Data
public class Analysis {

    String uuid;
    @ServerTimestamp Date validationDate;
    Double score;
    List<AnalysisFile> files;
    String fingerprintEmail;

}
