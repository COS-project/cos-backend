package com.cos.cercat.domain;

import com.cos.cercat.domain.mockexam.Question;
import com.cos.cercat.domain.mockexam.QuestionOption;
import com.cos.cercat.entity.Image;
import com.cos.cercat.domain.embededId.QuestionOptionPK;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@Getter
@AllArgsConstructor
@Builder
@NoArgsConstructor
@Table(indexes = {@Index(columnList = "optionSequence", name = "idx_option_sequence")})
public class QuestionOptionEntity {

    @EmbeddedId
    @Builder.Default
    private QuestionOptionPK questionOptionPK = new QuestionOptionPK();

    @MapsId("questionId")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "question_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private QuestionEntity questionEntity;

    @OneToOne
    @JoinColumn(name = "image_id")
    @Setter
    private Image optionImage;

    private String optionContent;

    public String getImageUrl() {
        return (this.optionImage != null) ? optionImage.getImageUrl() : "";
    }

    public Integer getOptionSequence() {
        return questionOptionPK.getOptionSequence();
    }

    public QuestionOption toDomain() {
        return new QuestionOption(
                questionOptionPK.getOptionSequence(),
                optionContent,
                (optionImage != null) ? optionImage.getImageUrl() : ""
        );
    }


}
