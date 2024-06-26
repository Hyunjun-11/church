package com.church.util.gcs;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.storage.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.concurrent.TimeUnit;

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
        Storage storage = getStorage();
        String fileName =  multipartFile.getOriginalFilename(); // 파일 이름 생성
        BlobInfo blobInfo = BlobInfo.newBuilder(bucketName, fileName)
                .setContentType(multipartFile.getContentType())
                .build();
        storage.create(blobInfo, multipartFile.getInputStream());
        return fileName; // 전체 URL 대신 파일 이름 반환
    }



    public boolean deleteFile(String fileName) throws IOException {
        Storage storage = getStorage();
        BlobId blobId = BlobId.of(bucketName, fileName);
        return storage.delete(blobId);
    }


    public String generateSignedUrl(String fileName) throws IOException {
        Storage storage = getStorage();
        BlobId blobId = BlobId.of(bucketName, fileName);
        URL url = storage.signUrl(BlobInfo.newBuilder(blobId).build(), 15, TimeUnit.MINUTES);
        System.out.println(url);
        return url.toString();
    }

    public InputStream downloadFile(String fileName) throws IOException {
        Storage storage = getStorage();
        Blob blob = storage.get(BlobId.of(bucketName, fileName));
        return new ByteArrayInputStream(blob.getContent()); // byte[]를 InputStream으로 변환
    }
}
