package com.church.domain.bible.repository;

import com.church.domain.bible.entity.Bible;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BibleRepository extends JpaRepository<Bible,Long> {

//    List<Bible> findByBookAndChapterAndVerseBetween(int book, int chapter, int verseStart, int verseEnd);
    @Query("SELECT b FROM Bible b WHERE b.book = :book AND b.chapter = :chapter AND b.verse BETWEEN :verseStart AND :verseEnd")
    List<Bible> findVerses(
            @Param("book") int book,
            @Param("chapter") int chapter,
            @Param("verseStart") int verseStart,
            @Param("verseEnd") int verseEnd
    );

}
