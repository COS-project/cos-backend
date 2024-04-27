package com.cos.cercat.board;

import com.cos.cercat.certificate.Certificate;
import com.cos.cercat.certificate.CertificateReader;
import com.cos.cercat.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class BoardReader {

    private final FavoriteBoardRepository favoriteBoardRepository;
    private final CertificateReader certificateReader;

    public boolean isFavorite(User user, Certificate certificate) {
        return favoriteBoardRepository.isFavorite(user, certificate);
    }

    public List<Board> read() {
        return certificateReader.readAll().stream()
                .map(Board::from)
                .toList();
    }

}
