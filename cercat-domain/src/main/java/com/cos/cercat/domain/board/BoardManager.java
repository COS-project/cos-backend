package com.cos.cercat.domain.board;

import com.cos.cercat.domain.certificate.Certificate;
import com.cos.cercat.domain.certificate.TargetCertificate;
import com.cos.cercat.domain.user.TargetUser;
import com.cos.cercat.domain.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
@RequiredArgsConstructor
public class BoardManager {

    private final FavoriteBoardRepository favoriteBoardRepository;

    public void favorite(User user, Certificate interestingCertificate) {
        favoriteBoardRepository.save(user, interestingCertificate);
    }

    public void favoriteAll(User user, List<Certificate> interestingCertificates) {
        interestingCertificates.forEach(certificate -> favorite(user, certificate));
    }

    public void unfavorite(User user, Certificate interested) {
        favoriteBoardRepository.remove(user, interested);
    }

    public void unfavoriteAll(User user, List<Certificate> interested) {
        interested.forEach(interestedCertificate -> unfavorite(user, interestedCertificate));
    }
}
