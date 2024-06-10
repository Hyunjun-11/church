package com.church.domain.board.dto;

import com.church.domain.board.entity.Board;
import com.church.domain.calendar.dto.CalendarDto;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BoardResponseDto extends BoardDto{
    private Long boardId;

    public BoardResponseDto(Board board) {
        super(
                board.getTitle(),
                board.getContent(),
                board.getAuthor());

        this.boardId = board.getId();
    }
}
