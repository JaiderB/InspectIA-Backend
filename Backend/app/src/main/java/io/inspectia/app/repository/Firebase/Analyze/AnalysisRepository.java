package io.inspectia.app.repository.Firebase.Analyze;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import io.inspectia.app.model.infra.DTO.DB.AnalysisDB;
import io.inspectia.app.model.infra.DTO.Entities.Analysis;
import io.inspectia.app.model.infra.DTO.Entities.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutionException;

@RequiredArgsConstructor
@Repository
public class AnalysisRepository {

    private String COLLECTION_NAME = "Analysis";
    private final Firestore firestoreDatabase;


    public AnalysisDB save(AnalysisDB analysis) throws ExecutionException, InterruptedException {

        ApiFuture<DocumentReference> documentReference = this.firestoreDatabase.collection(COLLECTION_NAME)
                .add(analysis);

        String documentId = documentReference.get().getId();

        documentReference.get().update("uuid", documentId);
        analysis.setUuid(documentId);

        return analysis;
    }

    public Optional<List<Analysis>> getByUserEmail(String userEmail) throws ExecutionException, InterruptedException {
        QuerySnapshot querySnapshot = this.firestoreDatabase.collection(COLLECTION_NAME)
                .whereEqualTo("fingerprintEmail", userEmail)
                .orderBy("validationDate", Query.Direction.DESCENDING)
                .get()
                .get();

        if(!querySnapshot.isEmpty()){
            List<Analysis> analysis = querySnapshot.toObjects(Analysis.class);
            return Optional.of(analysis);
        }

        return Optional.empty();
    }

    public void delete(String hashId) throws RuntimeException {
        ApiFuture<WriteResult> result = this.firestoreDatabase.collection(COLLECTION_NAME)
                .document(hashId)
                .delete();

        try{
            System.out.println("Analysis deleted successfully at: " + result.get().getUpdateTime());
        } catch (ExecutionException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
