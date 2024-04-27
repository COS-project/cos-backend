package com.cos.cercat.board;


import com.cos.cercat.certificate.Certificate;
import com.cos.cercat.user.User;

public interface FavoriteBoardRepository {

    boolean isFavorite(User user, Certificate certificate);

    void save(User user, Certificate certificate);

    void remove(User user, Certificate interested);
}
