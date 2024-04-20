package com.cos.cercat.domain.board;

import com.cos.cercat.domain.certificate.CertificateReader;
import com.cos.cercat.domain.certificate.TargetCertificate;
import com.cos.cercat.domain.user.TargetUser;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class BoardReader {

    private final FavoriteBoardRepository favoriteBoardRepository;
    private final CertificateReader certificateReader;

    public boolean isFavorite(TargetUser targetUser, TargetCertificate targetCertificate) {
        return favoriteBoardRepository.isFavorite(targetUser, targetCertificate);
    }

    public List<Board> read() {
        return certificateReader.readAll().stream()
                .map(Board::from)
                .toList();
    }

}
