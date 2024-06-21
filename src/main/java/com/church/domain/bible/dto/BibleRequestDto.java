package com.church.domain.bible.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BibleRequestDto {
    private int book;
    private int chapter;
    private int verseStart;
    private int verseEnd;
    private String content;
}
