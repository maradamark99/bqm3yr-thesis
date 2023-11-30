package com.maradamark99.thesis.storage;

import io.minio.*;
import io.minio.errors.*;
import org.apache.tomcat.util.http.fileupload.FileUploadException;
import org.springframework.stereotype.Component;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

@Component
// TODO: handle exceptions better
public class MinioStorageClient implements StorageClient {

    private final MinioClient minioClient;

    public MinioStorageClient(StorageConfig storageConfig) {
        this.minioClient = MinioClient
                .builder()
                .endpoint(storageConfig.getEndpoint())
                .credentials(storageConfig.getAccessKey(), storageConfig.getSecretKey())
                .build();
    }

    @Override
    public void putObject(String bucket, String object, byte[] file) throws FileUploadException {
        var uploadArgs = PutObjectArgs.builder()
                .bucket(bucket)
                .object(object)
                .stream(new ByteArrayInputStream(file), file.length, -1)
                .build();
        try {
            minioClient.putObject(uploadArgs);
        } catch (Exception e) {
            throw new FileUploadException();
        }
    }

    @Override
    public byte[] getObject(String bucket, String object) {
        var getObjectArgs = GetObjectArgs.builder()
                .bucket(bucket)
                .object(object)
                .build();
        return readBytesFromStream(getObjectArgs);
    }

    @Override
    public byte[] getObject(String bucket, String object, long offset, long length) {
        var getObjectArgs = GetObjectArgs.builder()
                .bucket(bucket)
                .object(object)
                .offset(offset)
                .length(length)
                .build();
        return readBytesFromStream(getObjectArgs);
    }

    @Override
    public void makeBucket(String bucket) {
        try {
            var makeBucketArgs = MakeBucketArgs.builder()
                    .bucket(bucket)
                    .build();
            minioClient.makeBucket(makeBucketArgs);
        }
        catch (Exception e) {

        }
    }

    @Override
    public boolean bucketExists(String bucket) {
        var bucketExistsArgs = BucketExistsArgs.builder()
                .bucket(bucket)
                .build();
        try {
            return minioClient.bucketExists(bucketExistsArgs);
        } catch (Exception e) {
            return false;
        }
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
