package com.cos.cercat.domain.board;

import com.cos.cercat.domain.certificate.TargetCertificate;
import com.cos.cercat.domain.user.TargetUser;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
@RequiredArgsConstructor
public class BoardManager {

    private final FavoriteBoardRepository favoriteBoardRepository;

    public void favorite(TargetUser targetUser, TargetCertificate targetCertificate) {
        favoriteBoardRepository.save(targetUser, targetCertificate);
    }

    public void favoriteAll(TargetUser targetUser, List<TargetCertificate> certificates) {
        certificates.forEach(certificate -> favorite(targetUser, certificate));
    }

    public void unfavorite(TargetUser targetUser, TargetCertificate targetCertificate) {
        favoriteBoardRepository.remove(targetUser, targetCertificate);
    }

    public void unfavoriteAll(TargetUser targetUser, List<TargetCertificate> targetCertificates) {
        targetCertificates.forEach(targetCertificate -> unfavorite(targetUser, targetCertificate));
    }
}
