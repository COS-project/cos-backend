package com.cos.cercat.mockExam.app;

import com.cos.cercat.mockExam.app.dto.MockExamDto;
import com.cos.cercat.certificate.domain.entity.Certificate;
import com.cos.cercat.mockExam.domain.entity.MockExam;
import com.cos.cercat.mockExam.domain.entity.Question;
import com.cos.cercat.mockExam.domain.repository.MockExamRepostiory;
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
public class QuestionConverter {

    private final ObjectMapper objectMapperService;
    private final QuestionRepository questionRepository;
    private final MockExamRepostiory mockExamRepostiory;

    public static final String CORRECT_ANSWER = "정답";

    public void saveQuestionMap(Certificate certificate, File json) {

        MockExamDto mockExamDto = objectMapperService.jsonToQuestionMap(json);
        MockExam mockExam = mockExamRepostiory.save(MockExam.of(mockExamDto.infoDto(), certificate));

        mockExamDto.questions().forEach((title, content) -> {

            Question question = Question.from(mockExam);
            Matcher matcher = matchingQuestionPattern(title);

            if (matcher.find()) {

                question.setSeqAndTitle(matcher);

                for (String option : content) {
                    question.setOption(option);
                }

                String answer = mockExamDto.getCorrectAnswerViaSeq(question.getQuestion_seq());
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
