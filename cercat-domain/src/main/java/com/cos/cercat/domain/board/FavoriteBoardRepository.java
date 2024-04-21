package com.cos.cercat.domain.board;

import com.cos.cercat.domain.certificate.TargetCertificate;
import com.cos.cercat.domain.user.TargetUser;

public interface FavoriteBoardRepository {

    boolean isFavorite(TargetUser targetUser, TargetCertificate targetCertificate);

    void save(TargetUser targetUser, TargetCertificate targetCertificate);

    void remove(TargetUser targetUser, TargetCertificate targetCertificate);
}
