package com.cos.cercat.apis.certificate.app.usecase;

import com.cos.cercat.common.annotation.UseCase;
import com.cos.cercat.domain.CertificateEntity;
import com.cos.cercat.domain.UserEntity;
import com.cos.cercat.service.*;
import com.cos.cercat.apis.certificate.dto.request.CertificateCreateRequest;
import com.cos.cercat.apis.certificate.dto.request.CertificateExamCreateRequest;
import com.cos.cercat.apis.certificate.dto.request.InterestCertificateCreateRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@UseCase
@RequiredArgsConstructor
@Slf4j
@Transactional
public class CertificateCreateUseCase {

    private final CertificateService certificateService;
    private final UserService userService;
    private final CertificateExamService certificateExamService;
    private final InterestCertificateService interestCertificateService;
    private final FavoriteBoardService favoriteBoardService;


    public void createCertificate(CertificateCreateRequest request) {
        certificateService.createCertificate(request.toEntity());
        log.info("certificateEntity - {} 자격증 생성", request.certificateName());
    }

    public void createCertificateExam(Long certificateId, CertificateExamCreateRequest request) {
        CertificateEntity certificateEntity = certificateService.getCertificate(certificateId);
        certificateExamService.createCertificateExam(request.toEntity(certificateEntity));
        log.info("certificateEntity - {}, examYear - {}, round - {} 자격증 시험 정보 생성",
                certificateEntity.getCertificateName(), request.examYear(), request.round());
    }

    public void createInterestCertificates(List<InterestCertificateCreateRequest> requests, Long userId) {
        UserEntity userEntity = userService.getUser(userId);
        requests.forEach(request -> createInterestCertificate(userEntity, request));
        userEntity.updateRole();
        log.info("userEntity - {} 흥미 자격증 설정 완료", userEntity.getEmail());
    }

    private void createInterestCertificate(UserEntity userEntity, InterestCertificateCreateRequest request) {
        CertificateEntity certificateEntity = certificateService.getCertificate(request.certificateId());
        interestCertificateService.createInterestCertificate(request.toEntity(certificateEntity, userEntity));
        favoriteBoardService.createFavoriteBoard(userEntity, certificateEntity); //관심 자격증을 게시판 즐겨찾기에 등록
    }

}
