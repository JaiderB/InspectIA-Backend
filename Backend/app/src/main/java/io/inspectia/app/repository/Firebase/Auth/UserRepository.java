package io.inspectia.app.repository.Firebase.Auth;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import io.inspectia.app.model.infra.DTO.Entities.User;
import io.inspectia.app.model.Domain.DTO.Filtering.UserFilter;
import io.inspectia.app.utils.Validations.Interfaces.FirebaseMapBuilder;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import io.inspectia.app.utils.Validations.Interfaces.ValidatorCallback;
import org.springframework.web.bind.annotation.RestController;

import javax.swing.text.html.Option;
import java.util.*;
import java.util.concurrent.ExecutionException;

@Repository
@AllArgsConstructor
public class UserRepository {

    final String COLLECTION_NAME = "Users";
    ValidatorCallback emptyValidatorCallback;
    Firestore firestoreDatabase;
    FirebaseMapBuilder<Object, Map<String, Object>> callbackValidatorMapBuilder;

    public Optional<User> save(User user) throws ExecutionException, InterruptedException {
        ApiFuture<DocumentReference> documentReference = this.firestoreDatabase.collection(COLLECTION_NAME)
                .add(user);

        String documentId = documentReference.get().getId();

        documentReference.get().update("uuid", documentId);
        user.setUuid(documentId);

        return Optional.of(user);
    }


    public void update(User user){

        try{
            Map<String, Object> userMap = new HashMap<>();

            userMap = callbackValidatorMapBuilder.init(userMap, emptyValidatorCallback)
                    .add((token, tempUser) -> {
                            tempUser.put("refreshToken", token);
                            return tempUser;
                        }, user.getRefreshToken() )
                    .add((reports, tempUser) -> {
                        if (reports instanceof List<?>) {
                                tempUser.put("reports", FieldValue.arrayUnion(((List<?>) reports).toArray()));
                            }
                            return tempUser;
                        }, user.getReports())
                    .add((name, tempUser) -> {
                        tempUser.put("name", name);
                        return tempUser;
                        }, user.getName())
                    .add((email, tempUser) -> {
                        tempUser.put("email", email);
                        return tempUser;
                    }, user.getEmail())
                    .add((phone, tempUser) -> {
                        tempUser.put("phone", phone);
                        return tempUser;
                    }, user.getPhone())
                    .build();


            firestoreDatabase.collection(COLLECTION_NAME).document(user.getUuid()).update(userMap);

        }catch (Exception e){
            System.out.println(Arrays.toString(e.getStackTrace()));
        }
    }

    private  Map<String, Object> buildFilterMap(Map<String, Object> initialFilterMap, UserFilter filters){
        return callbackValidatorMapBuilder.init(initialFilterMap, emptyValidatorCallback)
                .add((id, filtersMap) -> {
                    filtersMap.put("id", id);
                    return filtersMap;
                }, filters.getId())
                .add((email, filtersMap) -> {
                    filtersMap.put("email", email);
                    return filtersMap;
                }, filters.getEmail())
                .add((phone, filtersMap)-> {
                    filtersMap.put("phone", phone);
                    return filtersMap;
                }, filters.getPhone())
                .build();
    }

    public Optional<List<User>> equalsAnyFilter(UserFilter filters) throws ExecutionException, InterruptedException {

        Map<String, Object> filterMap = new HashMap<>();
        filterMap = buildFilterMap(filterMap, filters);

        List<User> user = null;

        for (Map.Entry<String, Object>filter : filterMap.entrySet()){
            Query query = firestoreDatabase.collection(COLLECTION_NAME).
                    whereEqualTo(filter.getKey(), filter.getValue());
            QuerySnapshot querySnapshot = query.get().get();

            if(!querySnapshot.isEmpty()){
                user = querySnapshot.toObjects(User.class);
                break;
            }
        }

        return user != null? Optional.of(user): Optional.empty();

    }

    public Optional<List<User>> equalsAllFilter(UserFilter filters) throws ExecutionException, InterruptedException {
        Map<String, Object> filterMap = new HashMap<>();

        filterMap = buildFilterMap(filterMap, filters);

        Query query = firestoreDatabase.collection(COLLECTION_NAME);

        for (Map.Entry<String, Object>filter : filterMap.entrySet()){
            query = query.whereEqualTo(filter.getKey(), filter.getValue());
        }

        return Optional.of(query.get().get().toObjects(User.class));
    }

    public Optional<User> getByDocument(String document) throws ExecutionException, InterruptedException {
        return Optional.of(firestoreDatabase.collection(COLLECTION_NAME)
                .whereEqualTo("id", document).get().get().toObjects(User.class).getFirst());
    }

    public void setUser(User user){

    }
}