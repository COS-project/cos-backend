package com.cos.cercat.domain;

import com.cos.cercat.domain.certificate.Certificate;
import jakarta.persistence.*;
import lombok.*;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Table(name = "certificate")
@Builder
public class CertificateEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "certificate_id")
    private Long id;

    private String certificateName;

    public CertificateEntity(Long id, String certificateName) {
        this.id = id;
        this.certificateName = certificateName;
    }

    public CertificateEntity(String certificateName) {
        this.certificateName = certificateName;
    }

    public static CertificateEntity from(String certificateName) {
        return new CertificateEntity(certificateName);
    }

    public static CertificateEntity from(Certificate certificate) {
        return new CertificateEntity(certificate.id(), certificate.certificateName());
    }

    public Certificate toDomain() {
        return new Certificate(id, certificateName);
    }
}
