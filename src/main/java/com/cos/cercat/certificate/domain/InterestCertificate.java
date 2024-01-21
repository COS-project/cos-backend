package com.cos.cercat.certificate.domain;

import com.cos.cercat.certificate.domain.embededId.InterestCertificatePK;
import com.cos.cercat.global.entity.BaseTimeEntity;
import com.cos.cercat.user.domain.User;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.domain.Persistable;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
public class InterestCertificate extends BaseTimeEntity implements Persistable<InterestCertificatePK> {

    @EmbeddedId
    private InterestCertificatePK interestCertificatePK = new InterestCertificatePK();

    @MapsId("certificateId")
    @ManyToOne
    @JoinColumn(name = "certificate_id")
    private Certificate certificate;

    @MapsId("userId")
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Enumerated(EnumType.STRING)
    private InterestPriority priority;

    public InterestCertificate(Certificate certificate, User user, InterestPriority priority) {
        this.certificate = certificate;
        this.user = user;
        this.priority = priority;
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
