package com.cos.cercat.examReview.app;

import com.cos.cercat.certificate.app.CertificateExamService;
import com.cos.cercat.certificate.app.CertificateService;
import com.cos.cercat.certificate.app.InterestCertificateService;
import com.cos.cercat.certificate.domain.Certificate;
import com.cos.cercat.certificate.domain.CertificateExam;
import com.cos.cercat.certificate.domain.InterestCertificate;
import com.cos.cercat.examReview.dto.request.ExamReviewSearchCond;
import com.cos.cercat.examReview.dto.response.ExamReviewResponse;
import com.cos.cercat.user.app.UserService;
import com.cos.cercat.user.domain.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ExamReviewFetchService {

    private final ExamReviewService examReviewService;
    private final CertificateService certificateService;
    private final CertificateExamService certificateExamService;
    private final UserService userService;
    private final InterestCertificateService interestCertificateService;

    /***
     * 최근 자격증시험의 따끈 후기를 조회합니다.
     * @param certificateId 자격증 ID
     * @param cond 검색 필터
     * @param pageable 페이징 정보
     * @return 슬라이스 형태의 따끈후기 Response DTO
     */
    public Slice<ExamReviewResponse> getExamReviews(Long certificateId, ExamReviewSearchCond cond, Pageable pageable) {
        Certificate certificate = certificateService.getCertificate(certificateId);
        CertificateExam recentCertificateExam = certificateExamService.getRecentCertificateExam(certificate);

        log.info("certificate - {}, examYear - {}, round - {} 따끈 후기 조회",
                certificate.getCertificateName(), recentCertificateExam.getExamYear(), recentCertificateExam.getRound());
        return examReviewService.getExamReviews(recentCertificateExam, cond, pageable).map(ExamReviewResponse::from);
    }

    /***
     * 해당 자격증의 가장 최근 시험의 따끈후기 작성대상자인지 조회
     * @param certificateId 자격증 ID
     * @param userId 유저 ID
     * @return 아직 따끈후기를 작성하지않았다면 true를 반환, 이미 작성했다면 false를반환한다.
     */
    public boolean checkReviewDateAfterExamDate(Long certificateId, Long userId) {
        Certificate certificate = certificateService.getCertificate(certificateId);
        User user = userService.getUser(userId);
        List<Certificate> certificates = interestCertificateService.getInterestCertificates(user).stream()
                .map(InterestCertificate::getCertificate)
                .toList();
        boolean interest = certificates.contains(certificate);

        CertificateExam recentCertificateExam = certificateExamService.getRecentCertificateExam(certificate);

        boolean examDatePassed = certificateExamService.isExamDatePassed(certificate);
        boolean existsExamReview = examReviewService.existsExamReview(user, recentCertificateExam);

        log.info("user - {}, certificate - {} 따끈후기 작성 여부 조회", user.getEmail(), certificate.getCertificateName());
        return examDatePassed && !existsExamReview && interest;
    }

}
