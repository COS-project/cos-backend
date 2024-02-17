package com.cos.cercat.certificate.app;

import com.cos.cercat.certificate.domain.Certificate;
import com.cos.cercat.certificate.dto.request.CertificateCreateRequest;
import com.cos.cercat.certificate.dto.request.CertificateExamCreateRequest;
import com.cos.cercat.certificate.dto.request.InterestCertificateCreateRequest;
import com.cos.cercat.favoriteBoard.app.FavoriteBoardService;
import com.cos.cercat.user.app.UserService;
import com.cos.cercat.user.domain.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class CertificateCreateService {

    private final CertificateService certificateService;
    private final UserService userService;
    private final CertificateExamService certificateExamService;
    private final InterestCertificateService interestCertificateService;
    private final FavoriteBoardService favoriteBoardService;


    public void createCertificate(CertificateCreateRequest request) {
        certificateService.createCertificate(request.toEntity());
        log.info("certificate - {} 자격증 생성", request.certificateName());
    }

    public void createCertificateExam(Long certificateId, CertificateExamCreateRequest request) {
        Certificate certificate = certificateService.getCertificate(certificateId);
        certificateExamService.createCertificateExam(request.toEntity(certificate));
        log.info("certificate - {}, examYear - {}, round - {} 자격증 시험 정보 생성",
                certificate.getCertificateName(), request.examYear(), request.round());
    }

    public void createInterestCertificates(List<InterestCertificateCreateRequest> requests, Long userId) {
        User user = userService.getUser(userId);
        requests.forEach(request -> createInterestCertificate(user, request));
        user.updateRole();
        log.info("user - {} 흥미 자격증 설정 완료", user.getEmail());
    }

    private void createInterestCertificate(User user, InterestCertificateCreateRequest request) {
        Certificate certificate = certificateService.getCertificate(request.certificateId());
        interestCertificateService.createInterestCertificate(request.toEntity(certificate, user));
        favoriteBoardService.createFavoriteBoard(user, certificate); //관심 자격증을 게시판 즐겨찾기에 등록
    }

}
