package com.cos.cercat.board;

import com.cos.cercat.certificate.Certificate;
import com.cos.cercat.certificate.FavoriteBoardManager;
import com.cos.cercat.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;


@Component
@RequiredArgsConstructor
public class BoardManager implements FavoriteBoardManager {

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
