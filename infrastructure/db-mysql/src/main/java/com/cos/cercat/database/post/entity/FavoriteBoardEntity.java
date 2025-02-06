package com.cos.cercat.database.post.entity;

import com.cos.cercat.database.certificate.entity.CertificateEntity;
import com.cos.cercat.database.common.entity.BaseTimeEntity;
import com.cos.cercat.database.post.entity.embeddedId.FavoriteBoardPK;
import com.cos.cercat.database.user.entity.UserEntity;
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
