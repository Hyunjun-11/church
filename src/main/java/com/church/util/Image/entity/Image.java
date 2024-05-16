package com.church.util.Image.entity;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class Image {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column( nullable = false)
    private String url;

    @Column(nullable = false)
    private String fileName;

    @Column(nullable=false)
    private String filePath;

    private BoardType type;


}
