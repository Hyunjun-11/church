package com.church.domain.bible.controller;


import com.church.domain.bible.dto.BibleRequestDto;
import com.church.domain.bible.dto.BibleResponseDto;
import com.church.domain.bible.service.BibleService;
import com.church.util.message.Message;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/bible")
@RequiredArgsConstructor
public class BibleController {

    private final BibleService bibleService;


    @PostMapping("/")
    public ResponseEntity<Message<List<BibleResponseDto>>> getBibles(@RequestBody BibleRequestDto bibleDto) {
        System.out.println(bibleDto.getBook());
        System.out.println(bibleDto.getChapter());
        System.out.println(bibleDto.getVerseEnd());
        System.out.println(bibleDto.getVerseStart());

        return bibleService.getBibleVerses(bibleDto);
    }

}
