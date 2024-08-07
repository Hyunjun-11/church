package com.church.domain.board.dto;

import com.church.domain.board.entity.Board;
import com.church.domain.likes.dto.LikesDto;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@ToString
public class BoardResponseDto extends BoardDto{
    private Long boardId;
    private LocalDateTime createAt;
    private LocalDateTime updateAt;
    private Long memberId;
    private List<FilesDto> files;
    private Long likes;
    private Long hearts;
    private Long prays;
    public BoardResponseDto(Board board) {
        super(
                board.getTitle(),
                board.getContent(),
                board.getAuthor(),
                board.getCategory()
                 // files 필드 추가
        );


        this.boardId = board.getId();
        this.createAt = board.getCreateAt();
        this.updateAt = board.getModifiedDate();
        this.memberId = board.getMember().getId();
        this.files = (board.getFiles() != null) ?
                board.getFiles().stream()
                        .map(FilesDto::new)
                        .collect(Collectors.toList()) :
                Collections.emptyList();


    }
    public BoardResponseDto(Board board, LikesDto likesDto) {
        super(
                board.getTitle(),
                board.getContent(),
                board.getAuthor(),
                board.getCategory()

        );


        this.boardId = board.getId();
        this.createAt = board.getCreateAt();
        this.updateAt = board.getModifiedDate();
        this.memberId = board.getMember().getId();
        this.files = (board.getFiles() != null) ?
                board.getFiles().stream()
                        .map(FilesDto::new)
                        .collect(Collectors.toList()) :
                Collections.emptyList();
        this.likes = likesDto.getLikes();
        this.hearts = likesDto.getHearts();
        this.prays = likesDto.getPrays();


    }
}
