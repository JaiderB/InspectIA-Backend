package io.inspectia.app.config.Firebase;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import io.inspectia.app.AppApplication;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Objects;

@NoArgsConstructor
@Configuration
public class FirebaseAuth {

    @Value("${firebase.database.service-account-path}")
    private String firebaseServiceAccountKey;

    public void authenticate() throws IOException {

        //reads the data from the file
        FileInputStream serviceAccount =
                new FileInputStream(firebaseServiceAccountKey);

        FirebaseOptions options = new FirebaseOptions.Builder()
                .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                .build();

        FirebaseApp.initializeApp(options);
    }

}
