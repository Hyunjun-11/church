package com.church.domain.board.service;

import com.church.domain.board.dto.BoardRequestDto;
import com.church.domain.board.dto.BoardResponseDto;
import com.church.domain.board.entity.Board;
import com.church.domain.board.entity.Category;
import com.church.domain.board.entity.Files;
import com.church.domain.board.repository.BoardRepository;
import com.church.domain.members.entity.Members;
import com.church.domain.members.repository.MemberRepository;
import com.church.util.message.Message;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BoardService {

    private final BoardRepository boardRepository;
    private final MemberRepository memberRepository;

    @Transactional
    public ResponseEntity<Message<List<BoardResponseDto>>> readAll() {
        List<Board> boardList = boardRepository.findAll();
        List<BoardResponseDto> boards = boardList.stream()
                        .map(BoardResponseDto::new)
                        .collect(Collectors.toList());
        return new ResponseEntity<>(new Message<>("게시글 전체조회 성공",boards), HttpStatus.OK);
    }
    @Transactional
    public ResponseEntity<Message<List<BoardResponseDto>>> readByCategory(String category) {

        try {
        Category categoryEnum = Category.valueOf(category.toUpperCase());
        List<Board> boardList = boardRepository.findByCategory(categoryEnum);
        List<BoardResponseDto> boards = boardList.stream()
                .map(BoardResponseDto::new)
                .collect(Collectors.toList());
        return new ResponseEntity<>(new Message<>("게시글 카테고리 조회 성공",boards), HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid category: " + category, e);
        }

    }
    @Transactional
    public ResponseEntity<Message<BoardResponseDto>> readOne(Long id) {
        Board board = findById(id);
        BoardResponseDto boardInfo = new BoardResponseDto(board);
        return new ResponseEntity<>(new Message<>("게시글 단일 조회 성공",boardInfo),HttpStatus.OK);
    }

    @Transactional
    public ResponseEntity<Message<BoardResponseDto>> create(Members member, BoardRequestDto boardRequestDto) {
        Board board = Board.builder()
                .title(boardRequestDto.getTitle())
                .content(boardRequestDto.getContent())
                .category(boardRequestDto.getCategory())
                .author(member.getName())
                .member(member)
                .build();

        List<Files> files = boardRequestDto.getFiles();
        files.forEach(file -> file.setBoard(board));
        board.setFiles(files);

        boardRepository.save(board);
        BoardResponseDto boardResponseDto = new BoardResponseDto(board);
        return new ResponseEntity<>(new Message<>("게시글 등록 성공", boardResponseDto), HttpStatus.OK);
    }

    @Transactional
    public ResponseEntity<Message<BoardResponseDto>> update(Long memberId, Long boardId, BoardRequestDto boardRequestDto) {

        Members member = memberRepository.findById(memberId)
                .orElseThrow(() -> new EntityNotFoundException("사용자 정보를 찾을 수 없습니다."));

        Board board = findById(boardId);
        if (!member.getId().equals(board.getMember().getId())) {
            Message<BoardResponseDto> message = new Message<>("작성자만 수정할 수 있습니다", null);
            return new ResponseEntity<>(message, HttpStatus.FORBIDDEN);
        }

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
