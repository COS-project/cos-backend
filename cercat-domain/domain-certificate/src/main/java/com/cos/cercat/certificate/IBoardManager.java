package com.cos.cercat.certificate;


import com.cos.cercat.user.User;

import java.util.List;

public interface IBoardManager {
    void favoriteAll(User user, List<Certificate> certificates);
    void unfavoriteAll(User user, List<Certificate> certificates);
}
