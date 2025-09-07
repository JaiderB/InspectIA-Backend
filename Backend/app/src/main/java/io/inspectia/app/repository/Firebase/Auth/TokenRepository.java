package io.inspectia.app.repository.Firebase.Auth;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import io.inspectia.app.model.infra.DTO.Jwt.Token;
import io.inspectia.app.model.infra.POJO.Enums.Jwt.State;
import io.inspectia.app.utils.Validations.Interfaces.FirebaseMapBuilder;
import io.inspectia.app.utils.Validations.Interfaces.ValidatorCallback;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.concurrent.ExecutionException;

@Repository
@AllArgsConstructor
@Data
public class TokenRepository {

    final String COLLECTION_NAME= "Sessions";
    ValidatorCallback emptyValidatorCallback;
    Firestore firestoreDatabase;
    FirebaseMapBuilder<Object, Map<String, Object>> callbackValidatorMapBuilder;

    public Optional<Token> getToken(String hashId) throws ExecutionException, InterruptedException {
        ApiFuture<DocumentSnapshot> documentFuture = this.firestoreDatabase.collection("Sessions")
                .document(hashId)
                .get();

        DocumentSnapshot document = documentFuture.get();

        Token token = null;
        if (document.exists()){
            token = document.toObject(Token.class);
            System.out.println("Documento encontrado: " + document.getData());
        }else{
            System.out.println("Documento no encontrado: " + hashId);
        }

        return token == null? Optional.empty(): Optional.of(token);

    }

    public Optional<List<Token>> getAllActiveUserTokens(String userId) throws ExecutionException, InterruptedException {
        return Optional.of(firestoreDatabase.collection(COLLECTION_NAME)
                        .whereEqualTo("userId", userId)
                        .whereEqualTo("state", State.ACTIVE)
                        .get().get()
                        .toObjects(Token.class));
    }

    public void update(Token token){
        try{

            Map<String, Object> tokenMap = new HashMap<>();

            tokenMap = callbackValidatorMapBuilder.init(tokenMap, emptyValidatorCallback)
                    .add((state, tempToken) -> {
                        tempToken.put("state", state);
                        return tempToken;
                    }, token.getState())
                    .build();


            firestoreDatabase.collection(COLLECTION_NAME).document(token.getHashId()).update(tokenMap);

        }catch (Exception e){
            System.out.println(Arrays.toString(e.getStackTrace()));
        }
    }

    public Optional<Token> save(Token token) throws ExecutionException, InterruptedException {
        ApiFuture<DocumentReference> documentReference = this.firestoreDatabase.collection(COLLECTION_NAME)
                .add(token);

        String tokedHashId = documentReference.get().getId();
        documentReference.get().update("hashId", tokedHashId);
        token.setHashId(tokedHashId);

        return Optional.of(token);
    }

}
