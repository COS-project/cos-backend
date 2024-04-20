package com.cos.cercat.domain.board;

import com.cos.cercat.domain.certificate.TargetCertificate;
import com.cos.cercat.domain.user.TargetUser;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BoardService {
    private final BoardReader boardReader;
    private final BoardManager boardManager;

    public void flipFavorite(TargetUser targetUser, TargetCertificate targetCertificate) {

        if (boardReader.isFavorite(targetUser, targetCertificate)) {
            boardManager.unfavorite(targetUser, targetCertificate);
            return;
        }
        boardManager.favorite(targetUser, targetCertificate);
    }

    public List<Board> read() {
        return boardReader.read();

    }

    public boolean isFavorite(TargetUser targetUser, TargetCertificate targetCertificate) {
        return boardReader.isFavorite(targetUser, targetCertificate);
    }


}
