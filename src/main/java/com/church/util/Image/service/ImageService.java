package com.church.util.Image.service;

import com.church.domain.guide.dto.ResponseDto;
import com.church.util.Image.dto.ImageRequestDto;
import com.church.util.Image.dto.ImageResponseDto;
import com.church.util.Image.entity.Image;
import com.church.util.Image.repository.ImageRepository;
import com.church.util.gcs.GcsBucketUpload;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RequiredArgsConstructor
@Service
public class ImageService {

    private final ImageRepository imageRepository;
    private final GcsBucketUpload gcsBucketUpload;


    //이미지 업로드
    @Transactional
    public ResponseEntity<ImageResponseDto> upload(ImageRequestDto requestDto) throws IOException {
        MultipartFile imgFile = requestDto.getImage();
        String imgUrl=gcsBucketUpload.imageUpload(imgFile);

       Image image=Image.builder()
               .url(imgUrl)
               .imageName(requestDto.getImageName())
               .boardType(requestDto.getBoardType())
               .build();

       imageRepository.save(image);

       ImageResponseDto responseDto=new ImageResponseDto(image);



        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

}
