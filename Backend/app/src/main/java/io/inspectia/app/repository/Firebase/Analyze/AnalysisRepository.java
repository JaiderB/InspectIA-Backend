package io.inspectia.app.repository.Firebase.Analyze;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.Firestore;
import io.inspectia.app.model.infra.DTO.Entities.Analysis;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.concurrent.ExecutionException;

@RequiredArgsConstructor
@Repository
public class AnalysisRepository {

    private String COLLECTION_NAME = "Analysis";
    private final Firestore firestoreDatabase;


    public Analysis save(Analysis analysis) throws ExecutionException, InterruptedException {

        ApiFuture<DocumentReference> documentReference = this.firestoreDatabase.collection(COLLECTION_NAME)
                .add(analysis);

        String documentId = documentReference.get().getId();

        documentReference.get().update("uuid", documentId);
        analysis.setUuid(documentId);

        return analysis;
    }

}
