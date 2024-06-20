package com.church.util.Image.controller;


import com.church.util.Image.service.ImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/upload")
public class ImageController {

    private final ImageService imageService;
    //이미지 업로드
    @PostMapping(value = "/image", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE}, produces = MediaType.APPLICATION_JSON_VALUE)
    public String image(@ModelAttribute MultipartFile imgFile) throws IOException {
        System.out.println(imgFile.getOriginalFilename());
        return imageService.upload(imgFile);
    }

}
