package com.church.domain.bible.dto;


import com.church.domain.bible.entity.Bible;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BibleResponseDto {
    private int book;
    private int chapter;
    private int verse;
    private String content;

    public BibleResponseDto(Bible bible){
        this.book=bible.getBook();
        this.chapter=bible.getChapter();
        this.verse= bible.getVerse();
        this.content=bible.getContent();


    }

}
