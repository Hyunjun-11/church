package com.church.domain.board.service;

import com.church.domain.board.dto.BoardRequestDto;
import com.church.domain.board.dto.BoardResponseDto;
import com.church.domain.board.entity.Board;
import com.church.domain.board.entity.Category;
import com.church.domain.board.entity.Files;
import com.church.domain.board.repository.BoardRepository;
import com.church.domain.likes.dto.LikesDto;
import com.church.domain.likes.entity.Hearts;
import com.church.domain.likes.entity.Likes;
import com.church.domain.likes.entity.Prays;
import com.church.domain.likes.repository.HeartsRepository;
import com.church.domain.likes.repository.LikesRepository;
import com.church.domain.likes.repository.PraysRepository;
import com.church.domain.members.entity.Members;
import com.church.domain.members.repository.MemberRepository;
import com.church.util.annotation.LogMethodName;
import com.church.util.message.Message;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class BoardService {

    private final BoardRepository boardRepository;
    private final MemberRepository memberRepository;
    private final HeartsRepository heartsRepository;
    private final PraysRepository praysRepository;
    private final LikesRepository likesRepository;

    //게시글 전체조회
    @LogMethodName
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
        Long hearts = heartsRepository.countByBoardAndIsLikeTrue(board);
        Long likes = likesRepository.countByBoardAndIsLikeTrue(board);
        Long prays = praysRepository.countByBoardAndIsLikeTrue(board);
        LikesDto likesDto = new LikesDto(likes, hearts, prays);
        BoardResponseDto boardInfo = new BoardResponseDto(board,likesDto);


        return new ResponseEntity<>(new Message<>("게시글 단일 조회 성공",boardInfo),HttpStatus.OK);
    }
    
    //게시글 생성
    @LogMethodName
    @Transactional
    public ResponseEntity<Message<BoardResponseDto>> create(Members member, BoardRequestDto boardRequestDto) {
        Board board = Board.builder()
                .title(boardRequestDto.getTitle())
                .content(boardRequestDto.getContent())
                .category(boardRequestDto.getCategory())
                .author(member.getName())
                .member(member)
                .build();

        if(!boardRequestDto.getFiles().isEmpty()) {
            List<Files> files = boardRequestDto.getFiles();
            files.forEach(file -> file.setBoard(board));
            board.getFiles().addAll(files);
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

        List<Files> updatedFiles = null;
        if (boardRequestDto.getFiles() != null) {
            updatedFiles = boardRequestDto.getFiles();
            updatedFiles.forEach(file -> file.setBoard(board));
        }

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
    //하트
    @Transactional
    public ResponseEntity<String> heart(Long memberId, Long id) {
        Members member=getMembers(memberId);
        Board board=findById(id);
        Optional<Hearts> optionalHearts = heartsRepository.findByBoardAndMember(board, member);
        Hearts hearts;

        if(optionalHearts.isEmpty()){
            hearts = new Hearts();
            hearts.setBoard(board);
            hearts.setMember(member);
            hearts.setIsLike(true);
        }else{
            hearts = optionalHearts.get();
            hearts.setIsLike(!hearts.getIsLike());
        }

        heartsRepository.save(hearts);
        return ResponseEntity.ok("하트");
    }
    //기도
    @Transactional
    public ResponseEntity<String> prays(Long memberId, Long id) {
        Members member=getMembers(memberId);
        Board board=findById(id);
        Optional<Prays> optionalHearts = praysRepository.findByBoardAndMember(board, member);
        Prays prays;

        if(optionalHearts.isEmpty()){
            prays = new Prays();
            prays.setBoard(board);
            prays.setMember(member);
            prays.setIsLike(true);
        }else{
            prays = optionalHearts.get();
            prays.setIsLike(!prays.getIsLike());
        }

        praysRepository.save(prays);
        return ResponseEntity.ok("기도");
    }
    //좋아요
    @Transactional
    public ResponseEntity<String> likes(Long memberId, Long id) {
        Members member=getMembers(memberId);
        Board board=findById(id);
        Optional<Likes> optionalLikes = likesRepository.findByBoardAndMember(board, member);
        Likes likes;

        if(optionalLikes.isEmpty()){
            likes = new Likes();
            likes.setBoard(board);
            likes.setMember(member);
            likes.setIsLike(true);
        }else{
            likes = optionalLikes.get();
            likes.setIsLike(!likes.getIsLike());
        }

        likesRepository.save(likes);
        return ResponseEntity.ok("좋아요 ");
    }

    private Members getMembers(Long memberId) {
        return memberRepository.findById(memberId)
                .orElseThrow(() -> new EntityNotFoundException("사용자 정보를 찾을 수 없습니다."));
    }

    private Board findById(Long id) {
        return boardRepository.findById(id).orElseThrow(()->new EntityNotFoundException("해당 게시글을 찾을 수 없습니다."));
    }


}
