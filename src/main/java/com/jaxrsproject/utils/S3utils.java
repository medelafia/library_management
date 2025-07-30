package com.jaxrsproject.utils;

import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;

public class S3utils {
    private static S3Client s3Client ;

    static {
        s3Client = S3Client.builder()
                .region(Region.EU_NORTH_1)
                .credentialsProvider(
                        StaticCredentialsProvider
                                .create(AwsBasicCredentials
                                        .create(System.getenv("S3_ACCESS_KEY"), System.getenv("S3_SECRET_KEY"))))

                .build() ;
    }
    public static S3Client getS3Client() {
        return s3Client;
    }
}
