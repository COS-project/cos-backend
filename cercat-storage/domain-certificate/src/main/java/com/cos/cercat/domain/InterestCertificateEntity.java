package com.cos.cercat.domain;

import com.cos.cercat.domain.certificate.InterestCertificate;
import com.cos.cercat.domain.certificate.InterestPriority;
import com.cos.cercat.domain.embededId.InterestCertificatePK;
import com.cos.cercat.entity.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.domain.Persistable;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
@Table(name = "interest_certificate")
public class InterestCertificateEntity extends BaseTimeEntity implements Persistable<InterestCertificatePK> {

    @EmbeddedId
    private InterestCertificatePK interestCertificatePK = new InterestCertificatePK();

    @MapsId("certificateId")
    @ManyToOne
    @JoinColumn(name = "certificate_id")
    private CertificateEntity certificateEntity;

    @MapsId("userId")
    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity userEntity;

    @Enumerated(EnumType.STRING)
    private InterestPriority priority;

    @Builder
    public InterestCertificateEntity(CertificateEntity certificateEntity, UserEntity userEntity, InterestPriority priority) {
        this.certificateEntity = certificateEntity;
        this.userEntity = userEntity;
        this.priority = priority;
    }

    public InterestCertificate toDomain() {
        return new InterestCertificate(userEntity.toDomain(), certificateEntity.toDomain(), priority);
    }


    @Override
    public InterestCertificatePK getId() {
        return this.interestCertificatePK;
    }

    @Override
    public boolean isNew() {
        return super.createdAt == null;
    }
}
