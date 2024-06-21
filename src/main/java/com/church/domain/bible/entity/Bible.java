package com.church.domain.bible.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
public class Bible {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "book", length = 2)
    private String book;

    @Column(name = "chapter", length = 3)
    private String chapter;

    @Column(name = "verse", length = 3)
    private String verse;

    @Column(name = "content")
    private String content;

}
