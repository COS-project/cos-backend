package com.cos.cercat.mockExam.app.service;

import com.cos.cercat.mockExam.app.dto.MockExamInfoDTO;
import com.cos.cercat.certificate.domain.entity.Certificate;
import com.cos.cercat.mockExam.app.util.ObjectMapper;
import com.cos.cercat.mockExam.domain.entity.MockExam;
import com.cos.cercat.mockExam.domain.entity.Question;
import com.cos.cercat.mockExam.domain.repository.MockExamRepository;
import com.cos.cercat.mockExam.domain.repository.QuestionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
@Slf4j
public class QuestionConvertService {

    private final ObjectMapper objectMapperService;
    private final QuestionRepository questionRepository;
    private final MockExamRepository mockExamRepostiory;

    public static final String CORRECT_ANSWER = "정답";

    public void saveQuestionMap(Certificate certificate, File json) {

        MockExamInfoDTO mockExamInfoDTO = objectMapperService.jsonToQuestionMap(certificate, json);
        MockExam mockExam = mockExamRepostiory.save(MockExam.of(mockExamInfoDTO.mockExamDTO()));

        mockExamInfoDTO.questions().forEach((title, content) -> {

            Question question = Question.from(mockExam);
            Matcher matcher = matchingQuestionPattern(title);

            if (matcher.find()) {

                question.setSeqAndTitle(matcher);

                for (String option : content) {
                    question.setOption(option);
                }

                String answer = mockExamInfoDTO.getCorrectAnswerViaSeq(question.getQuestion_seq());
                question.setCorrectOption(answer);
            }

            questionRepository.save(question);
        });
    }

    private Matcher matchingQuestionPattern(String questionTitle) {
        Pattern pattern = Pattern.compile("(\\d+)\\.(.+)");

        return pattern.matcher(questionTitle);
    }
}
