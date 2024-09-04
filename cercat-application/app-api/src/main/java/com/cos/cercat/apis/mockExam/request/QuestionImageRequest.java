package com.cos.cercat.apis.mockExam.request;

import static com.cos.cercat.apis.global.util.FileMapper.toFile;

import com.cos.cercat.common.domain.File;
import com.cos.cercat.domain.mockexam.UploadingOptionImageFile;
import java.util.List;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public final class QuestionImageRequest {

    private MultipartFile questionImageFile;
    private List<QuestionOptionImageRequest> questionOptionImageRequests;

    public File toQuestionImageFile() {
        return toFile(questionImageFile);
    }

    public List<UploadingOptionImageFile> toOptionImages() {
        return questionOptionImageRequests.stream()
                .map(option -> new UploadingOptionImageFile(option.getOptionSequence(),
                        toFile(option.getOptionImageFile())))
                .toList();
    }
}
