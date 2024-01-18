package com.cos.cercat.mockExam.domain;


import com.cos.cercat.global.entity.Image;
import com.cos.cercat.mockExam.domain.embededId.QuestionOptionPK;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@NoArgsConstructor
public class QuestionOption {

    @EmbeddedId
    private QuestionOptionPK questionOptionPK;

    @OneToOne
    @JoinColumn(name = "image_id")
    @Setter
    private Image optionImage;

    private String optionContent;

    @Builder
    public QuestionOption(QuestionOptionPK questionOptionPK, Image optionImage, String optionContent) {
        this.questionOptionPK = questionOptionPK;
        this.optionImage = optionImage;
        this.optionContent = optionContent;
    }

    public Integer getQuestionSequence() {
        return questionOptionPK.getOptionSequence();
    }

    public String getImageUrl() {
        return (this.optionImage != null) ? optionImage.getImageUrl() : "";
    }

}
