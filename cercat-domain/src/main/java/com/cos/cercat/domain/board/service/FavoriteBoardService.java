package com.cos.cercat.domain.board.service;

import com.cos.cercat.domain.certificate.domain.Certificate;
import com.cos.cercat.domain.board.domain.FavoriteBoard;
import com.cos.cercat.domain.board.domain.embededId.FavoriteBoardPK;
import com.cos.cercat.domain.board.repository.FavoriteBoardRepository;
import com.cos.cercat.domain.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FavoriteBoardService {

    private final FavoriteBoardRepository favoriteBoardRepository;

    public boolean existFavoriteBoard(User user, Certificate certificate) {
        return favoriteBoardRepository.existsFavoriteBoardByFavoriteBoardPK(FavoriteBoardPK.of(user.getId(), certificate.getId()));
    }

    public void createFavoriteBoard(User user, Certificate certificate) {
        favoriteBoardRepository.save(FavoriteBoard.of(user, certificate));
    }

    public void deleteFavoriteBoard(User user, Certificate certificate) {
        favoriteBoardRepository.deleteById(FavoriteBoardPK.of(user.getId(), certificate.getId()));
    }

    public List<FavoriteBoard> getFavoriteBoard(User user) {
        return favoriteBoardRepository.findFavoriteBoardsByUser(user);
    }

}
