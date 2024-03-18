package com.cos.cercat;

import com.cos.cercat.certificate.domain.*;
import com.cos.cercat.certificate.repository.CertificateExamRepository;
import com.cos.cercat.certificate.repository.CertificateRepository;
import com.cos.cercat.mockExam.app.QuestionConvertService;
import com.cos.cercat.certificate.repository.SubjectRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import java.io.File;
import java.time.LocalDateTime;

@Slf4j
@Component
@RequiredArgsConstructor
public class TestInit {

    private final SubjectRepository subjectRepository;
    private final CertificateRepository certificateRepository;
    private final QuestionConvertService questionMapper;
    private final CertificateExamRepository certificateExamRepository;

    @PostConstruct
    public void init() {
        Certificate testCertificate = certificateRepository.save(new Certificate(1L, "컴퓨터활용능력1급"));
        ExamInfo testExamInfo = ExamInfo.of(
                new ExamSchedule(LocalDateTime.now().plusDays(1), LocalDateTime.now(), LocalDateTime.now(), LocalDateTime.now()),
                new ExamFee(20000, 30000),
                new ExamTimeLimit(1000000L, 2000000L),
                new PassingCriteria(40, 60, 70),
                "컴퓨터 일반, 스프레드시트, 데이터 베이스",
                "사무자동화의 필수 프로그램인 스프레드 시트, 데이터베이스 활용능력을 평가하는 국가기술 자격시험입니다.",
                "대면시험",
                "응시자격에 제한은 없지만 실기 시험은 필기합격 훟 2년 이내 실기 시험응시 가능"
        );
        certificateExamRepository.save(CertificateExam.of(testCertificate, testExamInfo, 2023, 3));

        Subject subject1 = subjectRepository.save(new Subject(1L, testCertificate, "컴퓨터 일반", 20, 100));
        Subject subject2 = subjectRepository.save(new Subject(2L, testCertificate, "스프레드시트", 20, 100));
        Subject subject3 = subjectRepository.save(new Subject(3L, testCertificate, "데이터 베이스", 20, 100));

        log.info("TestCertificate = {}", testCertificate);
        log.info("TestSubject 1 - {}", subject1);
        log.info("TestSubject 2 - {}", subject2);
        log.info("TestSubject 3 - {}", subject3);

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
