package com.cos.cercat.domain.embededId;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Embeddable
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class QuestionOptionPK implements Serializable {

    private Long questionId;

    private Integer optionSequence;

    public QuestionOptionPK(Integer optionSequence) {
        this.optionSequence = optionSequence;
    }

    public static QuestionOptionPK from(Integer optionSequence) {
        return new QuestionOptionPK(
                optionSequence
        );
    }
}
