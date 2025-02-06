package com.cos.cercat.database.certificate.entity.embededId;

import com.cos.cercat.database.certificate.entity.CertificateEntity;
import com.cos.cercat.database.user.entity.UserEntity;
import jakarta.persistence.Embeddable;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.*;
import org.hibernate.annotations.OnDelete;

import java.io.Serializable;

import static org.hibernate.annotations.OnDeleteAction.*;

@Embeddable
@NoArgsConstructor
@AllArgsConstructor(staticName = "of")
@EqualsAndHashCode
@Getter
public class InterestCertificatePK implements Serializable {

    @JoinColumn(name = "certificate_id")
    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = CASCADE)
    private CertificateEntity certificateEntity;

    @JoinColumn(name = "user_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private UserEntity userEntity;
}
