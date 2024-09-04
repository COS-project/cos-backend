package com.cos.cercat.apis.mockExam.request;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public final class QuestionOptionImageRequest {

    private Integer optionSequence;
    private MultipartFile optionImageFile;
}
