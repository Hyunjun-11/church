//package com.church.util.aws;
//
//import com.amazonaws.services.s3.AmazonS3Client;
//import com.amazonaws.services.s3.model.CannedAccessControlList;
//import com.amazonaws.services.s3.model.ObjectMetadata;
//import com.amazonaws.services.s3.model.PutObjectRequest;
//import lombok.RequiredArgsConstructor;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.stereotype.Service;
//import org.springframework.web.multipart.MultipartFile;
//
//import java.io.IOException;
//import java.util.UUID;
//
//@Service
//@RequiredArgsConstructor
//public class S3Uploader {
//
//    private final AmazonS3Client amazonS3Client;
//
//    @Value("${cloud.aws.s3.bucket}")
//    private String bucket;
//
//    public String upload(MultipartFile multipartFile) throws IOException {
//
//        String fileName = UUID.randomUUID().toString() + "_" + multipartFile.getOriginalFilename();
//
//        ObjectMetadata objMeta = new ObjectMetadata();
//        objMeta.setContentLength(multipartFile.getSize());
//
//        amazonS3Client.putObject(new PutObjectRequest(bucket, fileName, multipartFile.getInputStream(), objMeta)
//                .withCannedAcl(CannedAccessControlList.PublicRead));
//
//        return amazonS3Client.getUrl(bucket, fileName).toString();
//
//    }
//}
