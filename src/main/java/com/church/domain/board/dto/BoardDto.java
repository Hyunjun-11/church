package com.church.domain.board.dto;


import com.church.domain.board.entity.Category;
import com.church.domain.board.entity.Files;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BoardDto {
    private String title;
    private String content;
    private String author;
    private Category category;
}
