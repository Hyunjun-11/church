package com.church.domain.likes.repository;

import com.church.domain.board.entity.Board;
import com.church.domain.likes.entity.Prays;
import com.church.domain.members.entity.Members;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PraysRepository extends JpaRepository<Prays, Long> {
    Optional<Prays> findByBoardAndMember(Board board, Members member);
    Long countByBoardAndIsLikeTrue(Board board);

}
