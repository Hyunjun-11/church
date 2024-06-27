package com.church.domain.board.dto;

import com.church.domain.board.entity.Files;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@ToString
@Getter
@Setter
public class BoardRequestDto extends BoardDto {
    private List<Files> files;
}
