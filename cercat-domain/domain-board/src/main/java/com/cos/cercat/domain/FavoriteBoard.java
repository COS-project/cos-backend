package com.cos.cercat.domain;

import com.cos.cercat.domain.embededId.FavoriteBoardPK;
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
