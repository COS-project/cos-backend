package com.cos.cercat.service;

import com.cos.cercat.domain.CertificateEntity;
import com.cos.cercat.domain.MockExamEntity;
import com.cos.cercat.domain.SubjectEntity;
import com.cos.cercat.dto.MockExamInfoDTO;
import com.cos.cercat.domain.QuestionEntity;
import com.cos.cercat.repository.MockExamJpaRepository;
import com.cos.cercat.repository.QuestionJpaRepository;
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
    private final QuestionJpaRepository questionJpaRepository;
    private final MockExamJpaRepository mockExamJpaRepository;
    private final SubjectService subjectService;

    public static final String CORRECT_ANSWER = "정답";

    @Transactional
    public void saveQuestionMap(CertificateEntity certificateEntity, File json) {

        List<QuestionEntity> questionEntities = new ArrayList<>();

        MockExamInfoDTO mockExamInfoDTO = questionMapperService.jsonToQuestionMap(certificateEntity, json);
        MockExamEntity mockExamEntity = mockExamJpaRepository.save(MockExamEntity.of(mockExamInfoDTO.mockExamDTO(), 1000000L));
        List<SubjectEntity> subjectEntityList = subjectService.getSubjectList(certificateEntity);

        mockExamInfoDTO.questions().forEach((title, content) -> {

            QuestionEntity questionEntity = QuestionEntity.from(mockExamEntity);
            Matcher matcher = matchingQuestionPattern(title);

            if (matcher.find()) {

                questionEntity.setSeqAndTitle(matcher);

                if (questionEntity.getQuestionSeq() <= 20) {
                    questionEntity.setSubjectEntity(subjectEntityList.get(0));
                } else if (questionEntity.getQuestionSeq() <= 40) {
                    questionEntity.setSubjectEntity(subjectEntityList.get(1));
                } else {
                    questionEntity.setSubjectEntity(subjectEntityList.get(2));
                }

                questionEntity.setContent(content);

                String answer = mockExamInfoDTO.getCorrectAnswerViaSeq(questionEntity.getQuestionSeq());
                questionEntity.setCorrectOption(answer);
                questionEntities.add(questionEntity);
            }
        });

        questionJpaRepository.batchInsert(questionEntities);
    }



    private Matcher matchingQuestionPattern(String questionTitle) {
        Pattern pattern = Pattern.compile("(\\d+)\\.(.+)");

        return pattern.matcher(questionTitle);
    }
}
