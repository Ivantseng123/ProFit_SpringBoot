package com.ProFit.config;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.FileInputStream;
import java.io.IOException;

@Configuration
public class FirebaseConfig {

    private static final Logger logger = LoggerFactory.getLogger(FirebaseConfig.class);

    @Value("${firebase.config.path}")
    private String firebaseConfigPath;

    @Value("${firebase.bucket.name}")
    private String bucketName;

    @Bean
    public Storage firebaseStorage() throws IOException {
        try (FileInputStream serviceAccount = new FileInputStream(firebaseConfigPath)) {
            logger.info("服務帳戶 JSON 文件已成功讀取。");

            // 建立 GoogleCredentials 物件
            GoogleCredentials credentials = GoogleCredentials.fromStream(serviceAccount)
                    .createScoped("https://www.googleapis.com/auth/cloud-platform");

            // 建立 FirebaseOptions 並初始化 FirebaseApp
            FirebaseOptions options = FirebaseOptions.builder()
                    .setCredentials(credentials)
                    .setStorageBucket(bucketName)
                    .build();

            if (FirebaseApp.getApps().isEmpty()) {
                FirebaseApp.initializeApp(options);
                logger.info("FirebaseApp 已初始化。");
            }

            // 使用正確的憑證來初始化 Storage
            return StorageOptions.newBuilder().setCredentials(credentials).build().getService();
        }
    }
}