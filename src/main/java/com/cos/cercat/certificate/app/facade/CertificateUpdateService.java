package com.cos.cercat.certificate.app.facade;

import com.cos.cercat.certificate.app.CertificateService;
import com.cos.cercat.certificate.app.InterestCertificateService;
import com.cos.cercat.certificate.domain.Certificate;
import com.cos.cercat.certificate.domain.InterestCertificate;
import com.cos.cercat.certificate.dto.request.InterestCertificateUpdateRequest;
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
public class CertificateUpdateService {

    private final InterestCertificateService interestCertificateService;
    private final UserService userService;
    private final CertificateService certificateService;
    private final FavoriteBoardService favoriteBoardService;

    @Transactional
    public void updateInterestCertificates(List<InterestCertificateUpdateRequest> requests, Long userId) {
        User user = userService.getUser(userId);
        List<InterestCertificate> interestCertificates = interestCertificateService.getInterestCertificates(user);
        interestCertificates.stream()
                .map(InterestCertificate::getCertificate)
                .forEach(certificate -> {
                    interestCertificateService.delete(user, certificate); //기존 흥미자격증 삭제
                    favoriteBoardService.deleteFavoriteBoard(user, certificate); //
                });

        requests.forEach(request -> {
            Certificate certificate = certificateService.getCertificate(request.certificateId());
            interestCertificateService.createInterestCertificate(request.toEntity(certificate, user)); //다시 생성
        });
        log.info("user - {} 흥미 자격증 수정", user.getEmail());
    }

}
