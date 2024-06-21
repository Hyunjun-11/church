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
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BibleService {

    private final BibleRepository bibleRepository;


    @Transactional
    public ResponseEntity<Message<List<BibleResponseDto>>> getBibleVerses(BibleRequestDto bibleDto) {
        System.out.println("bibleDto.getBook()"+bibleDto.getBook());
        System.out.println("bibleDto.getChapter()"+bibleDto.getChapter());
        System.out.println("bibleDto.getVerseStart()"+bibleDto.getVerseStart());
        System.out.println("bibleDto.getVerseEnd()"+bibleDto.getVerseEnd());

        List<Bible> bibleList= bibleRepository.findVerses(bibleDto.getBook(),bibleDto.getChapter(),bibleDto.getVerseStart(),bibleDto.getVerseEnd());
        List<Bible> lists=bibleRepository.findVerses(43,3,2,4);
        System.out.println(lists.toString());
        System.out.println(bibleList.toString());



        List<BibleResponseDto> list= bibleList.stream()
                .map(BibleResponseDto :: new)
                .toList();
        return new ResponseEntity<>(new Message<>("성경 조회 성공",list), HttpStatus.OK);
    }
}
