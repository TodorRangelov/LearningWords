package com.LearningEnglish.repository;

import com.LearningEnglish.model.Word;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WordRepository extends JpaRepository<Word, Integer> {

    List<Word> findByToEnglish(String toEnglish);
    List<Word> findByToBulgarian(String toBulgarian);

    @Query(value = "SELECT *\n" +
                    "FROM words\n" +
                    "ORDER BY id desc\n" +
                    "LIMIT :limit OFFSET :offset", nativeQuery = true)
    List<Word> findWordsOffsetAndLimit(@Param("offset") int offset, @Param("limit") int limit);


}
