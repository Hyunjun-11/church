package com.church.domain.admin.banner.controller;


import com.church.domain.admin.banner.dto.BannerRequestDto;
import com.church.domain.admin.banner.dto.BannerResponseDto;
import com.church.domain.admin.banner.service.BannerService;
import com.church.util.message.Message;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/api/banner")
@RequiredArgsConstructor
public class BannerController {

    private final BannerService bannerService;


    @GetMapping("/{categoryName}")
    public ResponseEntity<Message<BannerResponseDto>> readOne(@PathVariable String categoryName) {

        return bannerService.readOne(categoryName);
    }
    @PostMapping(value = "/", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE}, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Message<BannerResponseDto>> create(@ModelAttribute BannerRequestDto bannerDto) throws IOException {
        return bannerService.create(bannerDto);
    }


    @PutMapping(value = "/{id}", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE}, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Message<BannerResponseDto>> update(@PathVariable Long id, @ModelAttribute BannerRequestDto bannerDto) throws IOException {
        return bannerService.update(id,bannerDto);

    }

}
