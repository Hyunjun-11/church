package com.church.domain.bible.controller;


import com.church.domain.bible.dto.BibleRequestDto;
import com.church.domain.bible.dto.BibleResponseDto;
import com.church.domain.bible.service.BibleService;
import com.church.util.message.Message;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/bible")
@RequiredArgsConstructor
public class BibleController {

    private final BibleService bibleService;


    @GetMapping("/")
    public ResponseEntity<Message<List<BibleResponseDto>>> getBibles(@RequestBody BibleRequestDto bibleDto) {

        return bibleService.getBibleVerses(bibleDto);
    }

}
