package com.cos.cercat.service;

import com.cos.cercat.domain.CertificateEntity;
import com.cos.cercat.domain.SubjectEntity;
import com.cos.cercat.dto.MockExamInfoDTO;
import com.cos.cercat.domain.MockExam;
import com.cos.cercat.domain.Question;
import com.cos.cercat.repository.MockExamRepository;
import com.cos.cercat.repository.QuestionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
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

    private final QuestionMapper questionMapperService;
    private final QuestionRepository questionRepository;
    private final MockExamRepository mockExamRepository;
    private final SubjectService subjectService;

    public static final String CORRECT_ANSWER = "정답";

    @Transactional
    public void saveQuestionMap(CertificateEntity certificateEntity, File json) {

        List<Question> questions = new ArrayList<>();

        MockExamInfoDTO mockExamInfoDTO = questionMapperService.jsonToQuestionMap(certificateEntity, json);
        MockExam mockExam = mockExamRepository.save(MockExam.of(mockExamInfoDTO.mockExamDTO(), 1000000L));
        List<SubjectEntity> subjectEntityList = subjectService.getSubjectList(certificateEntity);

        mockExamInfoDTO.questions().forEach((title, content) -> {

            Question question = Question.from(mockExam);
            Matcher matcher = matchingQuestionPattern(title);

            if (matcher.find()) {

                question.setSeqAndTitle(matcher);

                if (question.getQuestionSeq() <= 20) {
                    question.setSubjectEntity(subjectEntityList.get(0));
                } else if (question.getQuestionSeq() <= 40) {
                    question.setSubjectEntity(subjectEntityList.get(1));
                } else {
                    question.setSubjectEntity(subjectEntityList.get(2));
                }

                question.setContent(content);

                String answer = mockExamInfoDTO.getCorrectAnswerViaSeq(question.getQuestionSeq());
                question.setCorrectOption(answer);
                questions.add(question);
            }
        });

        questionRepository.batchInsert(questions);
    }



    private Matcher matchingQuestionPattern(String questionTitle) {
        Pattern pattern = Pattern.compile("(\\d+)\\.(.+)");

        return pattern.matcher(questionTitle);
    }
}
