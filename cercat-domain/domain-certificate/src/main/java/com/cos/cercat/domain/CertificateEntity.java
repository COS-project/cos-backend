package com.cos.cercat.domain;

import com.cos.cercat.domain.certificate.Certificate;
import com.cos.cercat.domain.certificate.Subjects;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Table(name = "certificate")
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

    private CertificateEntity(String certificateName, List<SubjectEntity> subjectEntities) {
        this.certificateName = certificateName;
        addAllSubjects(subjectEntities);
    }

    public static CertificateEntity of(String certificateName, List<SubjectEntity> subjectEntities) {
        return new CertificateEntity(certificateName, subjectEntities);
    }

    private void addAllSubjects(List<SubjectEntity> subjectEntities) {
        subjectEntities.forEach(subject -> subject.setCertificateEntity(this));
    }

    public Certificate toCertificate(List<SubjectEntity> subjectEntities) {
        return new Certificate(
                this.id,
                this.certificateName,
                Subjects.of(subjectEntities.stream()
                        .map(SubjectEntity::toSubject)
                        .toList()
                )
        );
    }
}
