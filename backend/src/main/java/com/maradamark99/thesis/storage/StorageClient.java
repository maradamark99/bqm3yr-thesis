package com.maradamark99.thesis.storage;

import io.minio.errors.*;
import org.apache.tomcat.util.http.fileupload.FileUploadException;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

public interface StorageClient {

    byte[] getObject(String bucket, String object);

    byte[] getObject(String bucket, String object, long offset, long length);

    void makeBucket(String bucket);

    boolean bucketExists(String bucket);

    void putObject(String bucket, String object, byte[] file) throws FileUploadException;

}
