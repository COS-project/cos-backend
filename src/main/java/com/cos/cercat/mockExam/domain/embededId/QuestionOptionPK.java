package com.cos.cercat.mockExam.domain.embededId;

import com.cos.cercat.mockExam.domain.Question;
import jakarta.persistence.Embeddable;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.io.Serializable;
import java.util.Objects;

@Embeddable
@Getter
@NoArgsConstructor
@AllArgsConstructor(staticName = "of")
public class QuestionOptionPK implements Serializable {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "question_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Question question;

    private Integer optionSequence;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof QuestionOptionPK that)) return false;
        return Objects.equals(getQuestion().getId(), that.getQuestion().getId()) && Objects.equals(getOptionSequence(), that.getOptionSequence());
    }
}
