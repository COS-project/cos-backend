package com.cos.cercat.board;

import com.cos.cercat.certificate.CreateInterestCertificateEvent;
import com.cos.cercat.certificate.RemoveInterestCertificateEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class BoardEventListener {

    private final BoardManager boardManager;

    @Async
    @EventListener
    public void favoriteAll(CreateInterestCertificateEvent createInterestCertificateEvent) {
        boardManager.favoriteAll(createInterestCertificateEvent.user(), createInterestCertificateEvent.certificateList());
    }

    @Async
    @EventListener
    public void unfavoriteAll(RemoveInterestCertificateEvent removeInterestCertificateEvent) {
        boardManager.unfavoriteAll(removeInterestCertificateEvent.user(), removeInterestCertificateEvent.certificateList());
    }
}
