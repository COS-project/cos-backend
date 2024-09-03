package com.cos.cercat.infra.entity;

import com.cos.cercat.infra.entity.embededId.FavoriteBoardPK;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.domain.Persistable;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "favorite_board")
public class FavoriteBoardEntity extends BaseTimeEntity implements Persistable<FavoriteBoardPK> {

    @EmbeddedId
    private FavoriteBoardPK favoriteBoardPK = new FavoriteBoardPK();

    public FavoriteBoardEntity(FavoriteBoardPK favoriteBoardPK) {
        this.favoriteBoardPK = favoriteBoardPK;
    }

    public static FavoriteBoardEntity of(UserEntity userEntity, CertificateEntity certificateEntity) {
        return new FavoriteBoardEntity(
                FavoriteBoardPK.of(userEntity, certificateEntity)
        );
    }

    @Override
    public FavoriteBoardPK getId() {
        return favoriteBoardPK;
    }

    @Override
    public boolean isNew() {
        return createdAt == null;
    }
}
