package com.cos.cercat.mockExam.app;


import com.cos.cercat.mockExam.app.dto.MockExamDto;
import com.fasterxml.jackson.core.type.TypeReference;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;


@Slf4j
@RequiredArgsConstructor
@Service
public class ObjectMapper {

    private final com.fasterxml.jackson.databind.ObjectMapper objectMapper;

    public MockExamDto jsonToQuestionMap(File json) {
        try {
            return MockExamDto.of(json.getName().split("\\.")[0], objectMapper.readValue(json, new TypeReference<>() {}));
        } catch (IOException e) {
            log.error("Exception [Err_Location] : {}", e.getStackTrace()[0]);
            throw new RuntimeException(e);
        }
    }

}
