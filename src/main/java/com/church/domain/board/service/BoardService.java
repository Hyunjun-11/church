package com.church.domain.board.service;

import com.church.domain.board.dto.BoardRequestDto;
import com.church.domain.board.dto.BoardResponseDto;
import com.church.domain.board.dto.LikeDto;
import com.church.domain.board.entity.Board;
import com.church.domain.board.entity.Category;
import com.church.domain.board.entity.Files;
import com.church.domain.board.entity.Likes;
import com.church.domain.board.repository.BoardRepository;
import com.church.domain.members.entity.Members;
import com.church.domain.members.repository.MemberRepository;
import com.church.security.auth.UserDetailsImpl;
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

    //게시글 전체조회
    @Transactional
    public ResponseEntity<Message<List<BoardResponseDto>>> readAll() {
        List<Board> boardList = boardRepository.findAll();
        List<BoardResponseDto> boards = boardList.stream()
                        .map(BoardResponseDto::new)
                        .collect(Collectors.toList());
        return new ResponseEntity<>(new Message<>("게시글 전체조회 성공",boards), HttpStatus.OK);
    }
    
    //게시글 카테고리별 조회
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

    //게시글 단일 조회
    @Transactional
    public ResponseEntity<Message<BoardResponseDto>> readOne(Long id) {
        Board board = findById(id);
        BoardResponseDto boardInfo = new BoardResponseDto(board);
        return new ResponseEntity<>(new Message<>("게시글 단일 조회 성공",boardInfo),HttpStatus.OK);
    }
    
    //게시글 생성
    @Transactional
    public ResponseEntity<Message<BoardResponseDto>> create(Members member, BoardRequestDto boardRequestDto) {
        Board board = Board.builder()
                .title(boardRequestDto.getTitle())
                .content(boardRequestDto.getContent())
                .category(boardRequestDto.getCategory())
                .author(member.getName())
                .member(member)
                .build();

        if(boardRequestDto.getFiles()!=null){

            List<Files> files = boardRequestDto.getFiles();
            files.forEach(file -> file.setBoard(board));
            board.setFiles(files);
        }

        boardRepository.save(board);
        BoardResponseDto boardResponseDto = new BoardResponseDto(board);
        return new ResponseEntity<>(new Message<>("게시글 등록 성공", boardResponseDto), HttpStatus.OK);
    }

    //게시글 수정
    @Transactional
    public ResponseEntity<Message<BoardResponseDto>> update(Long memberId, Long boardId, BoardRequestDto boardRequestDto) {
        Members member = getMembers(memberId);

        Board board = findById(boardId);
        if (!member.getId().equals(board.getMember().getId())) {
            Message<BoardResponseDto> message = new Message<>("작성자만 수정할 수 있습니다", null);
            return new ResponseEntity<>(message, HttpStatus.FORBIDDEN);
        }

        List<Files> updatedFiles = boardRequestDto.getFiles();
        updatedFiles.forEach(file -> file.setBoard(board));
        board.update(boardRequestDto, updatedFiles);

        boardRepository.save(board);

        BoardResponseDto boardResponseDto = new BoardResponseDto(board);
        return new ResponseEntity<>(new Message<>("게시글 수정 성공", boardResponseDto), HttpStatus.OK);
    }



    //게시글삭제
    @Transactional
    public String delete(Long id) {
        Board board = findById(id);
        boardRepository.deleteById(board.getId());
        return String.format(" ID %d번째 게시글이 삭제되었습니다.", board.getId());
    }
    //좋아요,하트,기도
    @Transactional
    public ResponseEntity<String> like(UserDetailsImpl userDetails, Long boardId, LikeDto likeDto) {
        Board board = findById(boardId);
        Members member = getMembers(userDetails.getMember().getId());

        Likes like = board.getLike();

        if (like == null) {
            like = new Likes();
            like.setBoard(board);
            board.setLike(like); // 새로 생성한 like 객체를 board에 설정
        }

        if (likeDto.getLikes() != null) {
            like.setLikes(like.getLikes() + likeDto.getLikes());
        }
        if (likeDto.getHearts() != null) {
            like.setHearts(like.getHearts() + likeDto.getHearts());
        }
        if (likeDto.getPrays() != null) {
            like.setPrays(like.getPrays() + likeDto.getPrays());
        }

        boardRepository.save(board); // 변경된 board 객체를 저장
        return ResponseEntity.ok("인터랙션이 성공적으로 반영되었습니다.");
    }

    private Members getMembers(Long memberId) {
        return memberRepository.findById(memberId)
                .orElseThrow(() -> new EntityNotFoundException("사용자 정보를 찾을 수 없습니다."));
    }

    private Board findById(Long id) {
        return boardRepository.findById(id).orElseThrow(()->new EntityNotFoundException("해당 게시글을 찾을 수 없습니다."));
    }
}
