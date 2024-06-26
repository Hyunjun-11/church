package com.church.domain.admin.banner.service;

import com.church.domain.admin.banner.dto.BannerRequestDto;
import com.church.domain.admin.banner.dto.BannerResponseDto;
import com.church.domain.admin.banner.entity.Banner;
import com.church.domain.admin.banner.repository.BannerRepository;
import com.church.util.gcs.GcsBucketUpload;
import com.church.util.message.Message;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class BannerService {

    private final BannerRepository bannerRepository;
    private final GcsBucketUpload gcsBucketUpload;


    @Transactional
    public ResponseEntity<Message<BannerResponseDto>> readOne(String name) {
//        System.out.println(name);
        Banner banner =findByCategoryName(name);
        BannerResponseDto responseDto=new BannerResponseDto(banner);
        return new ResponseEntity<>(new Message<>("배너조회 성공",responseDto), HttpStatus.OK);


    }

    public ResponseEntity<Message<BannerResponseDto>> create(BannerRequestDto bannerDto) throws IOException {
        String url = upload(bannerDto);
        Banner banner = Banner.builder()
                .url(url)
                .categoryName(bannerDto.getCategoryName())
                .build();
        bannerRepository.save(banner);
        BannerResponseDto responseDto = new BannerResponseDto(banner);
        return new ResponseEntity<>(new Message<>("배너 등록 성공", responseDto), HttpStatus.OK);
    }

    

    //배너 수정
    @Transactional
    public ResponseEntity<Message<BannerResponseDto>> update(Long id, BannerRequestDto bannerDto) throws IOException {
        Banner banner = findById(id);
        String url = upload(bannerDto);
        banner.setUrl(url);

        bannerRepository.save(banner);
        BannerResponseDto responseDto = new BannerResponseDto(banner);
        return new ResponseEntity<>(new Message<>("배너 수정 성공",responseDto), HttpStatus.OK);
    }

    //배너 아이디찾기
    private Banner findById(Long id) {
        return bannerRepository.findById(id).orElse(null);
    }
    //배너 이름찾기
    private Banner findByCategoryName(String name) {
        return bannerRepository.findByCategoryName(name).orElseThrow(()-> new EntityNotFoundException("해당 배너를 찾을 수 없습니다."));
    }
    //이미지 등록
    private String upload(BannerRequestDto bannerDto) throws IOException {
        return gcsBucketUpload.fileUpload(bannerDto.getImageFile());
    }

}
