package com.church.domain.board.dto;

import com.church.domain.board.entity.Board;
import com.church.domain.board.entity.Likes;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
public class BoardResponseDto extends BoardDto{
    private Long boardId;
    private LocalDateTime createAt;
    private LocalDateTime updateAt;
    private Long memberId;
    private List<FilesDto> files;
    private LikeDto likes;
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
        Likes boardLikes = board.getLike();
        if (boardLikes == null) {
            this.likes = new LikeDto(0L, 0L, 0L); // LikesDto 기본값 설정
        } else {
            this.likes = new LikeDto(boardLikes.getLikes(), boardLikes.getHearts(), boardLikes.getPrays());
        }

    }
}
