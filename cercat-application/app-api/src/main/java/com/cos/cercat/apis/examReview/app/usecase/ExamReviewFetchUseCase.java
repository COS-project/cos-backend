package com.cos.cercat.apis.examReview.app.usecase;

import com.cos.cercat.apis.examReview.dto.response.ExamReviewResponse;
import com.cos.cercat.common.annotation.UseCase;
import com.cos.cercat.domain.CertificateEntity;
import com.cos.cercat.domain.CertificateExamEntity;
import com.cos.cercat.domain.InterestCertificateEntity;
import com.cos.cercat.service.CertificateExamService;
import com.cos.cercat.service.CertificateService;
import com.cos.cercat.service.InterestCertificateService;
import com.cos.cercat.dto.ExamReviewSearchCond;
import com.cos.cercat.service.ExamReviewService;
import com.cos.cercat.service.UserService;
import com.cos.cercat.domain.UserEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@UseCase
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class ExamReviewFetchUseCase {

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
        CertificateEntity certificateEntity = certificateService.getCertificate(certificateId);
        CertificateExamEntity recentCertificateExamEntity = certificateExamService.getRecentCertificateExam(certificateEntity);

        log.info("certificateEntity - {}, examYear - {}, round - {} 따끈 후기 조회",
                certificateEntity.getCertificateName(), recentCertificateExamEntity.getExamInfoEntity().getExamYear(), recentCertificateExamEntity.getExamInfoEntity().getRound());
        return examReviewService.getExamReviews(recentCertificateExamEntity, cond, pageable).map(ExamReviewResponse::from);
    }

    /***
     * 해당 자격증의 가장 최근 시험의 따끈후기 작성대상자인지 조회
     * @param certificateId 자격증 ID
     * @param userId 유저 ID
     * @return 아직 따끈후기를 작성하지않았다면 true를 반환, 이미 작성했다면 false를반환한다.
     */
    public boolean checkReviewDateAfterExamDate(Long certificateId, Long userId) {
        CertificateEntity certificateEntity = certificateService.getCertificate(certificateId);
        UserEntity userEntity = userService.getUser(userId);
        List<CertificateEntity> certificateEntities = interestCertificateService.getInterestCertificates(userEntity).stream()
                .map(InterestCertificateEntity::getCertificateEntity)
                .toList();
        boolean interest = certificateEntities.contains(certificateEntity);

        CertificateExamEntity recentCertificateExamEntity = certificateExamService.getRecentCertificateExam(certificateEntity);

        boolean examDatePassed = certificateExamService.isExamDatePassed(certificateEntity);
        boolean existsExamReview = examReviewService.existsExamReview(userEntity, recentCertificateExamEntity);

        log.info("userEntity - {}, certificateEntity - {} 따끈후기 작성 여부 조회", userEntity.getEmail(), certificateEntity.getCertificateName());
        return examDatePassed && !existsExamReview && interest;
    }

}
