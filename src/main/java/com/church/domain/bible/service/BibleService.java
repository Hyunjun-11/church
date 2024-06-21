package com.church.domain.bible.service;


import com.church.domain.bible.dto.BibleRequestDto;
import com.church.domain.bible.dto.BibleResponseDto;
import com.church.domain.bible.entity.Bible;
import com.church.domain.bible.repository.BibleRepository;
import com.church.util.message.Message;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BibleService {

    private final BibleRepository bibleRepository;


    public ResponseEntity<Message<List<BibleResponseDto>>> getBibleVerses(BibleRequestDto bibleDto) {

        List<Bible> bibleList= bibleRepository.findByBookAndChapterAndVerseBetween(bibleDto.getBook(),bibleDto.getChapter(),bibleDto.getVerseStart(),bibleDto.getVerseEnd());

        List<BibleResponseDto> list= bibleList.stream()
                .map(BibleResponseDto :: new)
                .toList();
        return new ResponseEntity<>(new Message<>("성경 조회 성공",list), HttpStatus.OK);
    }
}
