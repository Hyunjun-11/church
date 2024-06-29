package com.church.domain.likes.repository;

import com.church.domain.board.entity.Board;
import com.church.domain.likes.entity.Hearts;
import com.church.domain.members.entity.Members;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface HeartsRepository extends JpaRepository<Hearts, Long> {

    Optional<Hearts> findByBoardAndMember(Board board, Members member);
    Long countByBoardAndIsLikeTrue(Board board);
}
