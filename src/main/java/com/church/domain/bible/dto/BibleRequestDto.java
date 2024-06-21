package com.church.domain.bible.dto;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BibleRequestDto {
    private String book;
    private String chapter;
    private String verseStart;
    private String verseEnd;
    private String content;
}
