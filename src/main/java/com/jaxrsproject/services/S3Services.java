package com.jaxrsproject.services;


import com.jaxrsproject.entities.Book;
import com.jaxrsproject.utils.S3utils;
import software.amazon.awssdk.core.ResponseInputStream;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.model.*;

import java.io.IOException;
import java.io.InputStream;

public class S3Services {

    private String bucketName = System.getenv("S3_BUCKET_NAME") ; ;
    private BookServices bookServices = new BookServices();
    public String uploadImage(String isbn , InputStream inputStream , String originalFileName) throws IOException {
        if(!this.bucketCreated()) {
            CreateBucketRequest createBucketRequest = CreateBucketRequest.builder()
                    .bucket(bucketName)
                    .build();
            S3utils.getS3Client().createBucket(createBucketRequest) ;
        }
        String fileName = new StringBuilder().append(isbn).append('_').append(originalFileName).toString() ;
        S3utils
                .getS3Client()
                .putObject(
                        PutObjectRequest.builder().bucket(this.bucketName).key(fileName).acl(ObjectCannedACL.PUBLIC_READ).build(),
                        RequestBody.fromBytes(inputStream.readAllBytes())
                ) ;

        String coverUrl =  String.format("http://%s:%s/api/cover/%s" ,System.getenv("IP_ADDRESS") , System.getenv("PORT"), fileName) ;
        Book book = this.bookServices.getBookById(isbn) ;
        book.setCoverUrl(coverUrl);
        this.bookServices.updateBook(book) ;

        return coverUrl ;
    }
    public ResponseInputStream<GetObjectResponse> getImage(String key) {
        return  S3utils.getS3Client().getObject(builder -> builder.bucket(this.bucketName).key(key).build()) ;
    }
    public boolean bucketCreated() {
        try {
            S3utils.getS3Client().headBucket(req -> req.bucket(this.bucketName)) ;
            return true ;
        }catch (NoSuchBucketException noSuchBucketException) {
            return false ;
        }
    }
}
