package com.church.domain.comment.service;

import com.church.domain.board.entity.Board;
import com.church.domain.board.repository.BoardRepository;
import com.church.domain.comment.dto.CommentRequestDto;
import com.church.domain.comment.dto.CommentResponseDto;
import com.church.domain.comment.entity.Comment;
import com.church.domain.comment.repository.CommentRepository;
import com.church.domain.members.entity.Members;
import com.church.domain.members.repository.MemberRepository;
import com.church.util.message.Message;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final MemberRepository memberRepository;
    private final BoardRepository boardRepository;


    //댓글 조회
    @Transactional(readOnly = true)
    public ResponseEntity<Message<List<CommentResponseDto>>> readComment(Long boardId) {
        List<Comment> commentList=commentRepository.findByBoardId(boardId);
        List<CommentResponseDto> comments = commentList.stream()
                .map(CommentResponseDto::new)
                .collect(Collectors.toList());

        return new ResponseEntity<>(new Message<>("댓글 조회 성공",comments), HttpStatus.OK);


    }

    //댓글 생성
    @Transactional
    public ResponseEntity<Message<CommentResponseDto>> create(Long memberId,CommentRequestDto requestDto, Long boardId) {

        Members member = memberRepository.findById(memberId)
                .orElseThrow(() -> new EntityNotFoundException("해당 멤버가 존재하지 않습니다."));
        Board board = boardRepository.findById(boardId)
                .orElseThrow(() -> new EntityNotFoundException("해당 게시글이 존재하지 않습니다."));


        Comment comment = Comment.builder()
                .content(requestDto.getContent())
                .member(member)
                .board(board)
                .build();
        commentRepository.save(comment);
        CommentResponseDto responseDto = new CommentResponseDto(comment);
        return new ResponseEntity<>(new Message<>("댓글 등록 성공",responseDto),HttpStatus.OK);

    }

}
