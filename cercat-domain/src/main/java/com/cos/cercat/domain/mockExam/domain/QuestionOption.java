package com.cos.cercat.domain.mockExam.domain;

import com.cos.cercat.domain.common.domain.Image;
import com.cos.cercat.domain.mockExam.domain.embededId.QuestionOptionPK;
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
public class QuestionOption {

    @EmbeddedId
    @Builder.Default
    private QuestionOptionPK questionOptionPK = new QuestionOptionPK();

    @MapsId("questionId")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "question_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Question question;

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


}
