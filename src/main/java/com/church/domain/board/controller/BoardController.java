package com.church.domain.board.controller;


import com.church.domain.board.dto.BoardRequestDto;
import com.church.domain.board.dto.BoardResponseDto;
import com.church.domain.board.service.BoardService;
import com.church.security.auth.UserDetailsImpl;
import com.church.util.message.Message;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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
    //아이디 상세조회
    @GetMapping("/{id}")
    public ResponseEntity<Message<BoardResponseDto>> readOne(@PathVariable Long id) {
        return boardService.readOne(id);
    }

    //카테고리별 조회
    @GetMapping("/category")
    public ResponseEntity<Message<List<BoardResponseDto>>> readByCategory(@RequestParam String category) {
        return boardService.readByCategory(category);
    }
    //게시글 등록
    @PostMapping("/")
    public ResponseEntity<Message<BoardResponseDto>> create(@AuthenticationPrincipal UserDetailsImpl userDetails,@RequestBody  BoardRequestDto boardRequestDto) {
        System.out.println(boardRequestDto.toString());

        return boardService.create(userDetails.getMember(),boardRequestDto);
    }

    //게시글 수정
    @PutMapping("/{id}")
    public ResponseEntity<Message<BoardResponseDto>> update(@AuthenticationPrincipal UserDetailsImpl userDetails,  @PathVariable Long id, @RequestBody  BoardRequestDto boardRequestDto) {
        return boardService.update(userDetails.getMember().getId(),id,boardRequestDto);
    }
    @DeleteMapping("/{id}")
    public String delete(@PathVariable Long id) {
        return boardService.delete(id);
    }

}
