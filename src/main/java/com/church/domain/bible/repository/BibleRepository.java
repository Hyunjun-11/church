package com.church.domain.bible.repository;

import com.church.domain.bible.entity.Bible;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BibleRepository extends JpaRepository<Bible,Long> {

    List<Bible> findByBookAndChapterAndVerseBetween(String book, String chapter, String verseStart, String verseEnd);
}
