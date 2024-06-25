package com.church.util.gcs;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.storage.BlobId;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class GcsBucketUpload {


    @Value("${spring.cloud.gcp.storage.bucket}")
    private String bucketName;
    @Value("${spring.cloud.gcp.storage.credentials.location}")
    private String location;

    private Storage getStorage() throws IOException {
        InputStream keyFile = ResourceUtils.getURL(location).openStream();
        return StorageOptions.newBuilder()
                .setCredentials(GoogleCredentials.fromStream(keyFile))
                .build()
                .getService();
    }


    public String fileUpload(MultipartFile multipartFile) throws IOException {
        // 서비스 계정 키 파일을 InputStream으로 읽기
        // Storage 객체 생성
        Storage storage = getStorage();
        // 파일 이름 생성
        String fileName = UUID.randomUUID().toString();
        // BlobInfo 객체 생성
        BlobInfo blobInfo = BlobInfo.newBuilder(bucketName, fileName)
                .setContentType(multipartFile.getContentType())
                .build();
        storage.create(blobInfo, multipartFile.getInputStream());
        return "https://storage.googleapis.com/" +  bucketName + "/" + fileName;

    }

    public boolean deleteFile(String fileName) throws IOException {
        Storage storage = getStorage();
        BlobId blobId = BlobId.of(bucketName, fileName);
        return storage.delete(blobId);
    }


}
