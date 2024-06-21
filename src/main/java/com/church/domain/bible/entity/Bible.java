package com.church.domain.bible.entity;

import jakarta.persistence.*;
import lombok.*;

@Setter
@Getter
@Entity
@Table(name="bible")
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Bible {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "book", length = 2)
    private int book;

    @Column(name = "chapter", length = 3)
    private int chapter;

    @Column(name = "verse", length = 3)
    private int verse;

    @Column(name = "content")
    private String content;

}
