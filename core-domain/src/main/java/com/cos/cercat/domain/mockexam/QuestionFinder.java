package com.cos.cercat.domain.mockexam;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class QuestionFinder {

    private final MockExamRepository mockExamRepository;


}
