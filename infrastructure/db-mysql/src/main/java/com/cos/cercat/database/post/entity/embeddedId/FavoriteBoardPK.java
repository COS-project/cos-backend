package com.cos.cercat.database.post.entity.embeddedId;

import com.cos.cercat.database.certificate.entity.CertificateEntity;
import com.cos.cercat.database.user.entity.UserEntity;
import jakarta.persistence.Embeddable;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;

import java.io.Serializable;

import static org.hibernate.annotations.OnDeleteAction.*;

@Embeddable
@Getter
@NoArgsConstructor
@AllArgsConstructor(staticName = "of")
@EqualsAndHashCode
public class FavoriteBoardPK implements Serializable {

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity userEntity;

    @ManyToOne
    @JoinColumn(name = "certificate_id")
    @OnDelete(action = CASCADE)
    private CertificateEntity certificateEntity;
}
