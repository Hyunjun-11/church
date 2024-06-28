package com.church.domain.board.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Likes {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(nullable = false)
    private Long likes = 0L;

    @Column(nullable = false)
    private Long hearts = 0L;

    @Column(nullable = false)
    private Long prays = 0L;
    @OneToOne
    @JoinColumn(name = "board_id", nullable = false)
    private Board board;



}
