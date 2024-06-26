package com.church.domain.board.dto;

import com.church.domain.board.entity.Board;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class BoardResponseDto extends BoardDto{
    private Long boardId;
    private LocalDateTime createAt;
    private LocalDateTime updateAt;
    private Long memberId;

    public BoardResponseDto(Board board) {
        super(
                board.getTitle(),
                board.getContent(),
                board.getAuthor(),
                board.getCategory(),
                board.getFiles());


        this.boardId = board.getId();
        this.createAt = board.getCreateAt();
        this.updateAt = board.getModifiedDate();
        this.memberId = board.getMember().getId();

    }
}
