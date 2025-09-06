package io.inspectia.app.config.Firebase;

import com.google.cloud.firestore.Firestore;
import com.google.firebase.cloud.FirestoreClient;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;

@AllArgsConstructor
@Configuration
public class FirebaseConfig {

    FirebaseAuth firebaseAuth;

    @Bean
    public Firestore firestoreDatabase() throws IOException {
        firebaseAuth.authenticate();
        return FirestoreClient.getFirestore();
    }

}
