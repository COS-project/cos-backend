package com.cos.cercat.domain.certificate.domain;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Certificate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "certificate_id")
    private Long id;

    private String certificateName;

    @Embedded
    private Subjects subjects = new Subjects();

    public Certificate(Long id, String certificateName) {
        this.id = id;
        this.certificateName = certificateName;
    }

    public Certificate(String certificateName, List<Subject> subjects) {
        this.certificateName = certificateName;
        addAllSubjects(subjects);
    }

    private void addAllSubjects(List<Subject> subjects) {
        subjects.forEach(subject -> subject.setCertificate(this));
        this.subjects.addAll(subjects);
    }
}
