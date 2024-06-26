package com.church.domain.board.repository;

import com.church.domain.board.entity.Board;
import com.church.domain.board.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BoardRepository extends JpaRepository<Board, Long> {
    List<Board> findByCategory(Category category);
}
