package com.maradamark99.thesis.storage;

import io.minio.MinioClient;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "object-store")
@Getter @Setter
public class StorageConfig {

    private String endpoint;

    private String accessKey;

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
