package org.example.backendrh.config;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

@Configuration
public class FirebaseConfig {
    @Bean
    public FirebaseApp firebaseApp() throws Exception {
        // Recupera o conteúdo do JSON do Firebase de uma variável de ambiente
        String firebaseConfig = System.getenv("FIREBASE_CONFIG");
        if (firebaseConfig == null) {
            throw new IllegalStateException("FIREBASE_CONFIG environment variable not set!");
        }
        InputStream serviceAccount = new ByteArrayInputStream(firebaseConfig.getBytes(StandardCharsets.UTF_8));
        FirebaseOptions options = FirebaseOptions.builder()
                .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                // substitua o valor abaixo pelo URL real do seu projeto
                .setDatabaseUrl("https://sistemarh-601cf.firebaseio.com/")
                .build();
        return FirebaseApp.initializeApp(options);
    }
}
