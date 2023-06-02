package com.purple.hello.repo;

import com.purple.hello.entity.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface QuestionRepo extends JpaRepository<Question, Long> {
    long count();
    Question findByQuestionId(long questionId);
    Optional<Question> findByNo(long no);
}
