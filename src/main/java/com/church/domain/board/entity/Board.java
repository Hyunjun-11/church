package com.church.domain.board.entity;

import com.church.domain.board.dto.BoardRequestDto;
import com.church.domain.comment.entity.Comment;
import com.church.domain.members.entity.Members;
import com.church.util.Timestamped;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class Board extends Timestamped {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String content;

    @Column(nullable = false)
    private String author;

    @Column(nullable = false)
    private Category category;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Members member;

    @OneToMany(mappedBy = "board", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Files> files = new ArrayList<>();

    @OneToMany(mappedBy = "board", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Comment> comments = new ArrayList<>();

    public void update(BoardRequestDto requestDto, List<Files> updatedFiles){
        this.title = requestDto.getTitle();
        this.content = requestDto.getContent();
        this.author = requestDto.getAuthor();
        this.category = requestDto.getCategory();
        if (updatedFiles != null) {
            updateFiles(updatedFiles);
        }
    }

    private void updateFiles(List<Files> updatedFiles) {
        this.files.clear();
        for (Files file : updatedFiles) {
            addFile(file);
        }
    }

    public void addFile(Files file) {
        files.add(file);
        file.setBoard(this);
    }

    public void removeFile(Files file) {
        files.remove(file);
        file.setBoard(null);
    }

}
