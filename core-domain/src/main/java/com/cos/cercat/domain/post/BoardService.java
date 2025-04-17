package com.cos.cercat.domain.post;

import com.cos.cercat.domain.certificate.*;
import com.cos.cercat.domain.certificate.event.internal.InterestCertificateCreatedEvent;
import com.cos.cercat.domain.certificate.event.internal.InterestCertificateRemovedEvent;
import com.cos.cercat.domain.user.UserId;
import com.cos.cercat.domain.user.User;
import com.cos.cercat.domain.user.UserReader;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BoardService {
    private final UserReader userReader;
    private final CertificateReader certificateReader;
    private final BoardReader boardReader;
    private final BoardManager boardManager;

    public void flipFavorite(UserId userId, CertificateId certificateId) {
        User user = userReader.read(userId);
        Certificate certificate = certificateReader.read(certificateId);


        if (boardReader.isFavorite(user, certificate)) {
            boardManager.unfavorite(user, certificate);
            return;
        }
        boardManager.favorite(user, certificate);
    }

    public List<Board> read() {
        return boardReader.read();
    }

    public boolean isFavorite(UserId userId, CertificateId certificateId) {
        User user = userReader.read(userId);
        Certificate certificate = certificateReader.read(certificateId);
        return boardReader.isFavorite(user, certificate);
    }

    @Async
    @EventListener
    public void favoriteAll(InterestCertificateCreatedEvent interestCertificateCreatedEvent) {
        boardManager.favoriteAll(interestCertificateCreatedEvent.user(), interestCertificateCreatedEvent.certificateList());
    }

    @Async
    @EventListener
    public void unfavoriteAll(InterestCertificateRemovedEvent interestCertificateRemovedEvent) {
        boardManager.unfavoriteAll(interestCertificateRemovedEvent.user(), interestCertificateRemovedEvent.certificateList());
    }

}
