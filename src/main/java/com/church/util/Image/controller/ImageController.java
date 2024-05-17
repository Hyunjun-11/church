package com.church.util.Image.controller;


import com.church.util.Image.dto.ImageRequestDto;
import com.church.util.Image.service.ImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
public class ImageController {

    private final ImageService imageService;


    @PostMapping(value = "/image",consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<String> image(@ModelAttribute ImageRequestDto requestDto) throws IOException {

        return imageService.upload(requestDto);



    }
}
