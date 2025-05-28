package br.com.rafaelvieira.bytehub.infra.config;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import com.google.cloud.firestore.Firestore;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.cloud.FirestoreClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.messaging.FirebaseMessaging;

@Configuration(enforceUniqueMethods = false)
@EnableConfigurationProperties(FirebaseProperties.class)
public class FirebaseConfiguration {

    public final static String FIREBASE_NAME = "firebaseCustomName";

    @Value("classpath:/private-key.json")
    private Resource privateKey;

    private final FirebaseProperties firebaseProperties;

    public FirebaseConfiguration(FirebaseProperties firebaseProperties) {
        this.firebaseProperties = firebaseProperties;
    }

    @Bean
    public FirebaseApp firebaseApp() throws IOException {
        InputStream credentials = new ByteArrayInputStream(privateKey.getContentAsByteArray());
        FirebaseOptions firebaseOptions = FirebaseOptions.builder()
                .setCredentials(GoogleCredentials.fromStream(credentials))
                .build();
        return FirebaseApp.initializeApp(firebaseOptions);
    }

    @Bean
    public FirebaseAuth firebaseAuth(FirebaseApp firebaseApp) {
        return FirebaseAuth.getInstance(firebaseApp);
    }

    @Bean
    public Firestore firestore() throws IOException {
        InputStream credentials = new ByteArrayInputStream(privateKey.getContentAsByteArray());
        FirebaseOptions firebaseOptions = FirebaseOptions.builder()
                .setCredentials(GoogleCredentials.fromStream(credentials))
                .build();

        FirebaseApp firebaseApp = FirebaseApp.initializeApp(firebaseOptions);
        return FirestoreClient.getFirestore(firebaseApp);
    }

    @Bean
    public Firestore firestore(FirebaseApp firebaseApp) {
        return FirestoreClient.getFirestore(firebaseApp);
    }

    @Bean
    public GoogleCredentials googleCredentials() {
        try {
            if (firebaseProperties.getServiceAccount() != null) {
                try( InputStream is = firebaseProperties.getServiceAccount().getInputStream()) {
                    return GoogleCredentials.fromStream(is);
                }
            }
            else {
                // Use a cadeia de credenciais padrão. Útil ao correr dentro de casa GKE
                return GoogleCredentials.getApplicationDefault();
            }
        }
        catch (IOException ioe) {
            throw new IllegalArgumentException("Failed to load service account credentials {}", ioe);
        }
    }

    @Bean
    public FirebaseApp firebaseAppFromCredentials(GoogleCredentials credentials) {
        FirebaseOptions options = FirebaseOptions.builder()
                .setCredentials(credentials)
                .build();

        return FirebaseApp.initializeApp(options, FIREBASE_NAME);
    }

    @Bean
    public FirebaseMessaging firebaseMessaging(FirebaseApp firebaseApp) {
        return FirebaseMessaging.getInstance(firebaseApp);
    }
}
