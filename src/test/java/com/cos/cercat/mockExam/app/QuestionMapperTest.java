package com.cos.cercat.mockExam.app;

import com.cos.cercat.certificate.domain.Certificate;
import com.cos.cercat.certificate.repository.CertificateRepository;
import com.cos.cercat.question.app.QuestionConvertService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.File;

@SpringBootTest
class QuestionMapperTest {

    @Autowired
    QuestionConvertService questionMapper;
    @Autowired
    CertificateRepository certificateRepository;

    @Test
    public void given_when_then() throws Exception {

        //Given
        String path = "/Users/kangjiwon/Desktop/Projects/Core/컴퓨터활용능력1급";
        File folder = new File(path);
        String certificateName = folder.getName().trim();
        Certificate certificate = certificateRepository.findById(1L).get();
        File[] listedFiles = folder.listFiles();
        //When

        assert listedFiles != null;


        for (File listedFile : listedFiles) {
            if (!listedFile.isHidden())
                questionMapper.saveQuestionMap(certificate, listedFile);
        }

        //Then
    }

}