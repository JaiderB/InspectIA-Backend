package io.inspectia.app.model.Domain.DTO.Entities;

import com.google.cloud.firestore.annotation.DocumentId;
import com.google.type.DateTime;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class Analysis {

    @DocumentId
    String hashId;List<Quality> qualities;
    Integer score;
    DateTime validationDate;

}
