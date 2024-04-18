package com.cos.cercat.apis.certificate.app.usecase;

import com.cos.cercat.common.annotation.UseCase;
import com.cos.cercat.domain.CertificateEntity;
import com.cos.cercat.domain.UserEntity;
import com.cos.cercat.service.CertificateService;
import com.cos.cercat.service.FavoriteBoardService;
import com.cos.cercat.service.InterestCertificateService;
import com.cos.cercat.domain.InterestCertificate;
import com.cos.cercat.apis.certificate.dto.request.InterestCertificateUpdateRequest;
import com.cos.cercat.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@UseCase
@RequiredArgsConstructor
@Slf4j
public class CertificateUpdateUseCase {

    private final InterestCertificateService interestCertificateService;
    private final UserService userService;
    private final CertificateService certificateService;
    private final FavoriteBoardService favoriteBoardService;

    @Transactional
    public void updateInterestCertificates(List<InterestCertificateUpdateRequest> requests, Long userId) {
        UserEntity userEntity = userService.getUser(userId);
        List<InterestCertificate> interestCertificates = interestCertificateService.getInterestCertificates(userEntity);
        interestCertificates.stream()
                .map(InterestCertificate::getCertificateEntity)
                .forEach(certificate -> {
                    interestCertificateService.delete(userEntity, certificate); //기존 흥미자격증 삭제
                    favoriteBoardService.deleteFavoriteBoard(userEntity, certificate); //
                });

        requests.forEach(request -> {
            CertificateEntity certificateEntity = certificateService.getCertificate(request.certificateId());
            interestCertificateService.createInterestCertificate(request.toEntity(certificateEntity, userEntity)); //다시 생성
        });
        log.info("userEntity - {} 흥미 자격증 수정", userEntity.getEmail());
    }

}
