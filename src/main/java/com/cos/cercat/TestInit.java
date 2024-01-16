//package com.cos.cercat;
//
//import com.cos.cercat.certificate.domain.Certificate;
//import com.cos.cercat.certificate.repository.CertificateRepository;
//import com.cos.cercat.question.app.QuestionConvertService;
//import com.cos.cercat.certificate.domain.Subject;
//import com.cos.cercat.certificate.repository.SubjectRepository;
//import com.cos.cercat.user.domain.User;
//import com.cos.cercat.user.repository.UserRepository;
//import jakarta.annotation.PostConstruct;
//import jakarta.annotation.PreDestroy;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.stereotype.Component;
//
//import java.io.File;
//
//@Slf4j
//@Component
//@RequiredArgsConstructor
//public class TestInit {
//
//    private final UserRepository userRepository;
//    private final SubjectRepository subjectRepository;
//    private final CertificateRepository certificateRepository;
//    private final QuestionConvertService questionMapper;
//
//    @PostConstruct
//    private void init() {
//        Certificate testCertificate = certificateRepository.save(new Certificate(1L, "컴퓨터활용능력1급"));
//        Subject subject1 = subjectRepository.save(new Subject(1L, testCertificate, "컴퓨터", 20, 100));
//        Subject subject2 = subjectRepository.save(new Subject(2L, testCertificate, "스프레드 시트", 20, 100));
//        Subject subject3 = subjectRepository.save(new Subject(3L, testCertificate, "데이터 베이스", 20, 100));
//        User testUser = userRepository.save(new User(1L,"강지원"));
//        log.info("TestCertificate = {}", testCertificate);
//        log.info("TestSubject 1 - {}", subject1);
//        log.info("TestSubject 2 - {}", subject2);
//        log.info("TestSubject 3 - {}", subject3);
//        log.info("TestUser = {}", testUser);
//
//        String path = "/Users/kangjiwon/Desktop/Projects/Core/컴퓨터활용능력1급";
//        File folder = new File(path);
//        String certificateName = folder.getName().trim();
//        File[] listedFiles = folder.listFiles();
//        //When
//
//        assert listedFiles != null;
//        for (File listedFile : listedFiles) {
//            if (!listedFile.isHidden())
//                questionMapper.saveQuestionMap(testCertificate, listedFile);
//        }
//    }
//
//    @PreDestroy
//    private void delete() {
//        certificateRepository.deleteById(1L);
//        userRepository.deleteById(1L);
//    }
//}
