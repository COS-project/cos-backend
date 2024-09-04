package com.cos.cercat.domain.mockexam;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class QuestionFinder {

    private final MockExamRepository mockExamRepository;


}
