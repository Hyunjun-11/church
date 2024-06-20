package com.church.util.Image.service;

import com.church.util.gcs.GcsBucketUpload;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RequiredArgsConstructor
@Service
public class ImageService {

    private final GcsBucketUpload gcsBucketUpload;


    //이미지 업로드
    @Transactional
    public String upload(MultipartFile imgFile) throws IOException {
        System.out.println("이미지파일 업로드");
        return gcsBucketUpload.imageUpload(imgFile);
    }

}
