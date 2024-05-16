package com.church.util.Image.service;

import com.church.util.Image.dto.ImageRequestDto;
import com.church.util.Image.repository.ImageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RequiredArgsConstructor
@Service
public class ImageService {

    private final ImageRepository imageRepository;


    //이미지 업로드
    @Transactional
    public String upload(ImageRequestDto requestDto) {
        MultipartFile imgFile = requestDto.getImage();
        String imgName = imgFile.getOriginalFilename();


        return null;
    }

}
