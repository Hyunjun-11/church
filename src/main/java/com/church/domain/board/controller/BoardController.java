package com.church.domain.board.controller;


import com.church.domain.board.dto.BoardRequestDto;
import com.church.domain.board.dto.BoardResponseDto;
import com.church.domain.board.service.BoardService;
import com.church.util.message.Message;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/board")
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;

    //게시글 전체조회
    @GetMapping("/")
    public ResponseEntity<Message<List<BoardResponseDto>>> readAll() {
        return boardService.readAll();
    }
    @GetMapping("/{id}")
    public ResponseEntity<Message<BoardResponseDto>> readOne(@PathVariable Long id) {
        return boardService.readOne(id);
    }
    //게시글 등록
    @PostMapping("/")
    public ResponseEntity<Message<BoardResponseDto>> create(@RequestBody  BoardRequestDto boardRequestDto) {
        return boardService.create(boardRequestDto);
    }
    //게시글 수정
    @PutMapping("/{id}")
    public ResponseEntity<Message<BoardResponseDto>> update(@PathVariable Long id,@RequestBody  BoardRequestDto boardRequestDto) {
        return boardService.update(id,boardRequestDto);
    }
    @DeleteMapping("/{id}")
    public String delete(@PathVariable Long id) {
        return boardService.delete(id);
    }

}
