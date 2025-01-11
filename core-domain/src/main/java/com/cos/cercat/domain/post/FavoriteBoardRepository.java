package com.cos.cercat.domain.post;


import com.cos.cercat.domain.certificate.Certificate;
import com.cos.cercat.domain.user.User;

public interface FavoriteBoardRepository {

    boolean isFavorite(User user, Certificate certificate);

    void save(User user, Certificate certificate);

    void remove(User user, Certificate interested);
}
