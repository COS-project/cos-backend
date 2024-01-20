package com.cos.cercat.certificate.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
public class Certificate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "certificate_id")
    private Long id;

    private String certificateName;

    @OneToOne(mappedBy = "certificate")
    private ExamInfo examInfo;

    public Certificate(Long id, String certificateName) {
        this.id = id;
        this.certificateName = certificateName;
    }
}
