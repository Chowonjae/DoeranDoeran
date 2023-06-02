package com.purple.hello.dao.impl;

import com.purple.hello.dao.QuestionDAO;
import com.purple.hello.repo.QuestionRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class QuestionDAOImpl implements QuestionDAO {
    @Autowired
    private QuestionRepo questionRepo;
    public QuestionDAOImpl(QuestionRepo questionRepo){
        this.questionRepo = questionRepo;
    }
}
