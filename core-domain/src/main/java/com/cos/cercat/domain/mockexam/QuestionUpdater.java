package com.cos.cercat.domain.mockexam;

import com.cos.cercat.domain.common.File;
import com.cos.cercat.domain.common.FileUploader;
import com.cos.cercat.domain.common.Image;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class QuestionUpdater {

    private final FileUploader fileUploader;
    private final MockExamRepository mockExamRepository;
    private final MockExamCache mockExamCache;

    public void updateImage(Question question, File questionImageFile,
            List<UploadingOptionImageFile> optionImageFiles) {
        Image questionImage = fileUploader.upload(questionImageFile);
        question.addQuestionImage(questionImage);

        for (UploadingOptionImageFile optionImageFile : optionImageFiles) {
            Image optionImage = fileUploader.upload(optionImageFile.optionImageFile());
            question.addOptionImageUrl(optionImageFile.optionSequence(), optionImage.getImageUrl());
        }
        mockExamRepository.updateQuestion(question);
        mockExamCache.delete(MockExamId.from(question.getMockExam().id()));
    }
}
