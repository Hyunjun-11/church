package com.church.domain.board.dto;


import com.church.domain.board.entity.Category;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class BoardDto {
    private String title;
    private String content;
    private String author;
    private Category category;
}
