package com.cos.cercat.mockExam.app;

import com.cos.cercat.certificate.domain.Certificate;
import com.cos.cercat.certificate.domain.Subject;
import com.cos.cercat.certificate.repository.CertificateRepository;
import com.cos.cercat.certificate.repository.SubjectRepository;
import com.cos.cercat.user.domain.User;
import com.cos.cercat.user.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;

import java.io.File;

@SpringBootTest
class QuestionMapperTest {

    @Autowired
    QuestionConvertService questionMapper;
    @Autowired
    CertificateRepository certificateRepository;
    @Autowired
    SubjectRepository subjectRepository;
    @Autowired
    UserRepository userRepository;

    @Test
    public void given_when_then() throws Exception {


    }

}