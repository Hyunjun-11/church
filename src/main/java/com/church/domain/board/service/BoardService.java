package com.church.domain.board.service;

import com.church.domain.board.dto.BoardRequestDto;
import com.church.domain.board.dto.BoardResponseDto;
import com.church.domain.board.entity.Board;
import com.church.domain.board.repository.BoardRepository;
import com.church.util.message.Message;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BoardService {

    private BoardRepository boardRepository;

    @Transactional
    public ResponseEntity<Message<List<BoardResponseDto>>> readAll() {
        List<Board> boardList = boardRepository.findAll();
        List<BoardResponseDto> boards =new ArrayList<>();
        for (Board board : boardList) {
            boards.add(new BoardResponseDto(board));
        }
        return new ResponseEntity<>(new Message<>("게시글 전체조회 성공",boards), HttpStatus.OK);
    }
    public ResponseEntity<Message<BoardResponseDto>> readOne(Long id) {
        Board board = findById(id);
        BoardResponseDto boardInfo = new BoardResponseDto(board);
        return new ResponseEntity<>(new Message<>("게시글 단일 조회 성공",boardInfo),HttpStatus.OK);
    }

    @Transactional
    public ResponseEntity<Message<BoardResponseDto>> create(BoardRequestDto boardRequestDto) {
        Board board = Board.builder()
                .title(boardRequestDto.getTitle())
                .content(boardRequestDto.getContent())
                .author(boardRequestDto.getAuthor())
                .build();
        boardRepository.save(board);
        BoardResponseDto boardResponseDto = new BoardResponseDto(board);
        return new ResponseEntity<>(new Message<>("게시글 등록 성공",boardResponseDto),HttpStatus.OK);
    }

    @Transactional
    public ResponseEntity<Message<BoardResponseDto>> update(Long id, BoardRequestDto boardRequestDto) {

        Board board = findById(id);
        board.update(boardRequestDto);

        BoardResponseDto boardResponseDto = new BoardResponseDto(board);
        return new ResponseEntity<>(new Message<>("게시글 수정 성공", boardResponseDto), HttpStatus.OK);
    }

    @Transactional
    public String delete(Long id) {
        Board board = findById(id);
        boardRepository.deleteById(board.getId());
        return String.format(" ID %d번째 게시글이 삭제되었습니다.", board.getId());
    }

    private Board findById(Long id) {
        return boardRepository.findById(id).orElseThrow(()->new EntityNotFoundException("해당 게시글을 찾을 수 없습니다."));
    }


}