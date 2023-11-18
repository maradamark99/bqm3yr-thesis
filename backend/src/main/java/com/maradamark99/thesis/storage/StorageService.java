package com.maradamark99.thesis.storage;

import io.minio.GetObjectArgs;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.io.ByteArrayInputStream;
import java.io.InputStream;

@Service
@RequiredArgsConstructor
// TODO: handle exceptions better
public class StorageService {

    private final MinioClient minioClient;

    public void putObject(String bucket, String object, byte[] file) {
        var uploadArgs = PutObjectArgs.builder()
                .bucket(bucket)
                .object(object)
                .stream(new ByteArrayInputStream(file), -1, -1)
                .build();
        try {
            minioClient.putObject(uploadArgs);
        }
        catch (Exception e) {

        }
    }

    public byte[] getObject(String bucket, String object) {
        var getObjectArgs = GetObjectArgs.builder()
                .bucket(bucket)
                .object(object)
                .build();
        return readBytesFromStream(getObjectArgs);
    }

    public byte[] getObject(String bucket, String object, long offset, long length) {
        var getObjectArgs = GetObjectArgs.builder()
                .bucket(bucket)
                .object(object)
                .offset(offset)
                .length(length)
                .build();
        return readBytesFromStream(getObjectArgs);
    }

    private byte[] readBytesFromStream(GetObjectArgs getObjectArgs) {
        try (InputStream stream = minioClient.getObject(getObjectArgs)) {
            return stream.readAllBytes();
        }
        catch (Exception e) {
            return null;
        }
    }

}
