package com.cos.cercat.mockExam.app.util;


import com.cos.cercat.certificate.domain.entity.Certificate;
import com.cos.cercat.common.exception.CustomException;
import com.cos.cercat.common.exception.ErrorCode;
import com.cos.cercat.mockExam.app.dto.CertificateDTO;
import com.cos.cercat.mockExam.app.dto.MockExamInfoDTO;
import com.cos.cercat.mockExam.app.dto.MockExamDTO;
import com.fasterxml.jackson.core.type.TypeReference;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


@Slf4j
@RequiredArgsConstructor
@Service
public class ObjectMapper {

    private final com.fasterxml.jackson.databind.ObjectMapper objectMapper;

    public MockExamInfoDTO jsonToQuestionMap(Certificate certificate, File json) {
        try {
            return MockExamInfoDTO.of(
                    extractInformation(certificate, json.getName().split("\\.")[0]),
                    objectMapper.readValue(json, new TypeReference<>() {}));
        } catch (IOException e) {
            log.error("Exception [Err_Location] : {}", e.getStackTrace()[0]);
            throw new RuntimeException(e);
        }
    }

    private MockExamDTO extractInformation(Certificate certificate, String fileName) {
        // 정규 표현식 패턴 설정
        String pattern = "(.+?) +(\\d{4}) +(\\d+)회";
        Pattern regex = Pattern.compile(pattern);

        // 정규 표현식과 매칭되는 부분 추출
        Matcher matcher = regex.matcher(fileName);
        if (matcher.find()) {
            // 그룹 1: 이름, 그룹 2: 년도, 그룹 3: 회차
            String name = matcher.group(1);
            String year = matcher.group(2);
            String round = matcher.group(3);

            return new MockExamDTO(name, Integer.parseInt(year), Integer.parseInt(round), CertificateDTO.from(certificate));
        }

        throw new CustomException(ErrorCode.INCORRECT_FILE_FORMAT);// 매칭되는 부분이 없을 경우
    }

}
