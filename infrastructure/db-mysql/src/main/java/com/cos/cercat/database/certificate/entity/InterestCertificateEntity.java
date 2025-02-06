package com.cos.cercat.database.certificate.entity;

import com.cos.cercat.database.common.entity.BaseTimeEntity;
import com.cos.cercat.database.user.entity.UserEntity;
import com.cos.cercat.domain.certificate.InterestCertificate;
import com.cos.cercat.domain.certificate.InterestPriority;
import com.cos.cercat.database.certificate.entity.embededId.InterestCertificatePK;
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

    @Enumerated(EnumType.STRING)
    private InterestPriority priority;

    @Builder
    public InterestCertificateEntity(CertificateEntity certificateEntity, UserEntity userEntity, InterestPriority priority) {
        this.interestCertificatePK = InterestCertificatePK.of(certificateEntity, userEntity);
        this.priority = priority;
    }

    public InterestCertificate toDomain() {
        return new InterestCertificate(
                interestCertificatePK.getUserEntity().toDomain(),
                interestCertificatePK.getCertificateEntity().toDomain(),
                priority
        );
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
