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

        Certificate testCertificate = certificateRepository.save(new Certificate(1L, "컴퓨터활용능력1급"));
        Subject subject1 = subjectRepository.save(new Subject(1L, testCertificate, "컴퓨터", 20, 100));
        Subject subject2 = subjectRepository.save(new Subject(2L, testCertificate, "스프레드 시트", 20, 100));
        Subject subject3 = subjectRepository.save(new Subject(3L, testCertificate, "데이터 베이스", 20, 100));
        User testUser = userRepository.save(new User(1L,"강지원"));

        String path = "/Users/kangjiwon/Desktop/Projects/Core/컴퓨터활용능력1급";
        File folder = new File(path);
        String certificateName = folder.getName().trim();
        File[] listedFiles = folder.listFiles();
        //When

        assert listedFiles != null;
        for (File listedFile : listedFiles) {
            if (!listedFile.isHidden())
                questionMapper.saveQuestionMap(testCertificate, listedFile);
        }
    }

}