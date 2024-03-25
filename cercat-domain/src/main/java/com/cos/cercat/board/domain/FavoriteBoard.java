package com.cos.cercat.board.domain;

import com.cos.cercat.board.domain.embededId.FavoriteBoardPK;
import com.cos.cercat.certificate.domain.Certificate;
import com.cos.cercat.user.domain.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class FavoriteBoard {

    @EmbeddedId
    private FavoriteBoardPK favoriteBoardPK = new FavoriteBoardPK();

    @MapsId("userId")
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @MapsId("certificateId")
    @ManyToOne
    @JoinColumn(name = "certifiate_id")
    private Certificate certificate;

    private FavoriteBoard(User user, Certificate certificate) {
        this.user = user;
        this.certificate = certificate;
    }

    public static FavoriteBoard of(User user, Certificate certificate) {
        return new FavoriteBoard(
                user,
                certificate
        );
    }
}
