package com.maradamark99.thesis.storage;

import io.minio.MinioClient;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@Getter
public class StorageConfig {

    @Value("${object-store.endpoint}")
    private String endpoint;

    @Value("${object-store.access-key")
    private String accessKey;

    @Value("${object-store.secret-key")
    private String secretKey;

    @Bean
    public MinioClient minioClient() {
        return MinioClient
                .builder()
                .endpoint(endpoint)
                .credentials(accessKey, secretKey)
                .build();
    }

}
