package com.cos.cercat.mockExam.domain.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Certificate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "certificate_id")
    private Long id;

    private String certificateName;

    private Certificate(String certificate_name) {
        this.certificateName = certificate_name;
    }

    public static Certificate from(String certificateName) {
        return new Certificate(certificateName);
    }
}
