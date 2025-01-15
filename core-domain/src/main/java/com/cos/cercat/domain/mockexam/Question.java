package com.cos.cercat.domain.mockexam;


import com.cos.cercat.domain.common.Image;
import com.cos.cercat.domain.certificate.Subject;
import java.util.List;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public final class Question {

    private long questionId;
    private MockExam mockExam;
    private Subject subject;
    private Image questionImage;
    private List<QuestionOption> questionOptions;
    private QuestionContent questionContent;

    public void addQuestionImage(Image image) {
        this.questionImage = image;
    }

    public void addOptionImageUrl(int optionSequence, String imageUrl) {
        questionOptions.stream()
                .filter(option -> option.getOptionSequence() == optionSequence)
                .findFirst()
                .ifPresent(option -> option.setImageUrl(imageUrl));
    }


}
