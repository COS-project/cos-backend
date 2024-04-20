package com.cos.cercat.domain;

import com.cos.cercat.domain.embededId.FavoriteBoardPK;
import com.cos.cercat.entity.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Persistable;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Table(name = "favorite_board")
public class FavoriteBoardEntity extends BaseTimeEntity implements Persistable<FavoriteBoardPK> {

    @EmbeddedId
    private FavoriteBoardPK favoriteBoardPK = new FavoriteBoardPK();

    @MapsId("userId")
    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity userEntity;

    @MapsId("certificateId")
    @ManyToOne
    @JoinColumn(name = "certifiate_id")
    private CertificateEntity certificateEntity;

    private FavoriteBoardEntity(UserEntity userEntity, CertificateEntity certificateEntity) {
        this.userEntity = userEntity;
        this.certificateEntity = certificateEntity;
    }

    public static FavoriteBoardEntity of(UserEntity userEntity, CertificateEntity certificateEntity) {
        return new FavoriteBoardEntity(
                userEntity,
                certificateEntity
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
