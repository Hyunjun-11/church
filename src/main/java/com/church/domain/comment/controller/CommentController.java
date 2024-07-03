package com.church.domain.comment.controller;


import com.church.domain.comment.dto.CommentRequestDto;
import com.church.domain.comment.dto.CommentResponseDto;
import com.church.domain.comment.service.CommentService;
import com.church.security.auth.UserDetailsImpl;
import com.church.util.message.Message;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/boards/{boardId}/comments")
public class CommentController {

    private final CommentService commentService;

    @GetMapping
    public ResponseEntity<Message<List<CommentResponseDto>>> readComment(@PathVariable Long boardId) {
        return commentService.readComment(boardId);
    }

    @PostMapping
    public ResponseEntity<Message<CommentResponseDto>> create(
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @RequestBody CommentRequestDto commentRequestDto,
            @PathVariable Long boardId
    ) {
        return commentService.create(userDetails.getMember().getId(), commentRequestDto, boardId);
    }
}