//package com.cos.cercat.apis;
//
//import com.cos.cercat.domain.CertificateEntity;
//import com.cos.cercat.domain.CertificateExamEntity;
//import com.cos.cercat.domain.ExamInfoEntity;
//import com.cos.cercat.domain.SubjectEntity;
//import com.cos.cercat.repository.CertificateExamJpaRepository;
//import com.cos.cercat.repository.CertificateJpaRepository;
//import com.cos.cercat.repository.SubjectJpaRepository;
//import com.cos.cercat.service.QuestionConvertService;
//import jakarta.annotation.PostConstruct;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.stereotype.Component;
//
//import java.io.File;
//import java.time.LocalDateTime;
//
//@Slf4j
//@Component
//@RequiredArgsConstructor
//public class TestInit {
//
//    private final SubjectJpaRepository subjectRepository;
//    private final CertificateJpaRepository certificateRepository;
//    private final QuestionConvertService questionMapper;
//    private final CertificateExamJpaRepository certificateExamRepository;
//
//    @PostConstruct
//    public void init() {
//        CertificateEntity testCertificate = certificateRepository.save(new CertificateEntity(1L, "컴퓨터활용능력1급"));
////        ExamInfoEntity testExamInfo = new ExamInfoEntity(
////                2022, // examYear
////                1, // round
////                LocalDateTime.of(2022, 1, 1, 0, 0), // applicationStartDateTime
////                LocalDateTime.of(2022, 1, 31, 23, 59), // applicationDeadlineDateTime
////                LocalDateTime.of(2022, 2, 15, 12, 0), // resultAnnouncementDateTime
////                LocalDateTime.of(2022, 3, 1, 9, 0), // examDateTime
////                100000, // writtenExamFee
////                50000, // practicalExamFee
////                120L, // writtenExamTimeLimit
////                60L, // practicalExamTimeLimit
////                60, // subjectPassingCriteria
////                70, // totalAvgCriteria
////                70, // practicalPassingCriteria
////                "Subject 1, Subject 2, Subject 3", // subjectsInfo
////                "This is a dummy exam.", // description
////                "Written and Practical", // examFormat
////                "Anyone can apply" // examEligibility
////        );
////        certificateExamRepository.save(new CertificateExamEntity(1L, testCertificate, testExamInfo));
////
////        SubjectEntity subject1 = subjectRepository.save(new SubjectEntity(1L, testCertificate, "컴퓨터 일반", 20, 100));
////        SubjectEntity subject2 = subjectRepository.save(new SubjectEntity(2L, testCertificate, "스프레드시트", 20, 100));
////        SubjectEntity subject3 = subjectRepository.save(new SubjectEntity(3L, testCertificate, "데이터 베이스", 20, 100));
////
////        log.info("TestCertificate = {}", testCertificate);
////        log.info("TestSubject 1 - {}", subject1);
////        log.info("TestSubject 2 - {}", subject2);
////        log.info("TestSubject 3 - {}", subject3);
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
//}
