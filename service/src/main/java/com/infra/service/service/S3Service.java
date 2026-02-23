package com.infra.service.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;
import software.amazon.awssdk.services.s3.presigner.model.PresignedPutObjectRequest;
import software.amazon.awssdk.services.s3.presigner.model.PutObjectPresignRequest;

import java.time.Duration;
import java.util.Map;
import java.util.UUID;

@Service
public class S3Service {
    @Value("${aws.bucketName}")
    private String bucketName;

    private final S3Presigner s3Presigner;

    public S3Service(S3Presigner s3Presigner){
        this.s3Presigner = s3Presigner;
    }

    public Map<String,String> generateUploadURL(String fileName){
        String key = UUID.randomUUID() + "-" + fileName;

        PutObjectRequest objectRequest = PutObjectRequest.builder()
                .bucket(bucketName)
                .key(key)
                .contentType("image/jpeg")
                .build();

        PutObjectPresignRequest presignRequest = PutObjectPresignRequest.builder()
                .signatureDuration(Duration.ofMinutes(10))
                .putObjectRequest(objectRequest)
                .build();

        PresignedPutObjectRequest presigned =
                s3Presigner.presignPutObject(presignRequest);

        return Map.of(
                "uploadUrl", presigned.url().toString(),
                "key",key
        );
    }



}
