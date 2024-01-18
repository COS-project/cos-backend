package com.cos.cercat.mockExam.domain;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Embeddable;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Embeddable
public class QuestionOptions {

    @OneToMany(mappedBy = "questionOptionPK.question", fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<QuestionOption> questionOptions = new ArrayList<>();

    public void addAll(List<QuestionOption> options) {
        this.questionOptions.addAll(options);
    }

    public void add(QuestionOption option) {
        this.questionOptions.add(option);
    }

    public List<QuestionOption> getAll() {
        return this.questionOptions;
    }
}
