package com.church.domain.user.worship.bulletin.entity;


import com.church.util.Image.entity.BoardType;
import com.church.util.Image.entity.Image;
import com.church.util.Timestamped;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Bulletin extends Timestamped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String content;

    @Column(nullable = false)
    private BoardType boardType;

    @Column
    private String imageUrl;

}
