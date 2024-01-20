package com.cos.cercat.certificate.domain;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Embeddable;
import jakarta.persistence.OneToMany;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Embeddable
public class Subjects {

    @OneToMany(mappedBy = "examInfo", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Subject> subjects = new ArrayList<>();

    public List<Subject> getAll() {
        return this.subjects;
    }
}
