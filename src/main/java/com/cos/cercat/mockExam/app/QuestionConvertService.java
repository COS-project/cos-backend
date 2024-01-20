package com.cos.cercat.mockExam.app;

import com.cos.cercat.certificate.app.SubjectService;
import com.cos.cercat.certificate.domain.Subject;
import com.cos.cercat.mockExam.dto.MockExamInfoDTO;
import com.cos.cercat.certificate.domain.Certificate;
import com.cos.cercat.mockExam.util.ObjectMapper;
import com.cos.cercat.mockExam.domain.MockExam;
import com.cos.cercat.mockExam.domain.Question;
import com.cos.cercat.mockExam.repository.MockExamRepository;
import com.cos.cercat.mockExam.repository.QuestionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
@Slf4j
public class QuestionConvertService {

    private final ObjectMapper objectMapperService;
    private final QuestionRepository questionRepository;
    private final MockExamRepository mockExamRepository;
    private final SubjectService subjectService;

    public static final String CORRECT_ANSWER = "정답";

    @Transactional
    public void saveQuestionMap(Certificate certificate, File json) {

        List<Question> questions = new ArrayList<>();

        MockExamInfoDTO mockExamInfoDTO = objectMapperService.jsonToQuestionMap(certificate, json);
        MockExam mockExam = mockExamRepository.save(MockExam.of(mockExamInfoDTO.mockExamDTO(), 1000000L));
        List<Subject> subjectList = subjectService.getSubjectList(certificate);

        mockExamInfoDTO.questions().forEach((title, content) -> {

            Question question = Question.from(mockExam);
            Matcher matcher = matchingQuestionPattern(title);

            if (matcher.find()) {

                question.setSeqAndTitle(matcher);

                if (question.getQuestionSeq() <= 20) {
                    question.setSubject(subjectList.get(0));
                } else if (question.getQuestionSeq() <= 40) {
                    question.setSubject(subjectList.get(1));
                } else {
                    question.setSubject(subjectList.get(2));
                }

                question.setContent(content);

                String answer = mockExamInfoDTO.getCorrectAnswerViaSeq(question.getQuestionSeq());
                question.setCorrectOption(answer);
                questionRepository.save(question);
            }
        });
    }

    private Matcher matchingQuestionPattern(String questionTitle) {
        Pattern pattern = Pattern.compile("(\\d+)\\.(.+)");

        return pattern.matcher(questionTitle);
    }
}
