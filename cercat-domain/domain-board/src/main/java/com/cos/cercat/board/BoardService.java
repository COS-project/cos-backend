package com.cos.cercat.board;

import com.cos.cercat.certificate.*;
import com.cos.cercat.user.TargetUser;
import com.cos.cercat.user.User;
import com.cos.cercat.user.UserReader;
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

    public void flipFavorite(TargetUser targetUser, TargetCertificate targetCertificate) {
        User user = userReader.read(targetUser);
        Certificate certificate = certificateReader.read(targetCertificate);


        if (boardReader.isFavorite(user, certificate)) {
            boardManager.unfavorite(user, certificate);
            return;
        }
        boardManager.favorite(user, certificate);
    }

    public List<Board> read() {
        return boardReader.read();

    }

    public boolean isFavorite(TargetUser targetUser, TargetCertificate targetCertificate) {
        User user = userReader.read(targetUser);
        Certificate certificate = certificateReader.read(targetCertificate);
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
