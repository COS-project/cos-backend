package com.cos.cercat.domain.certificate.domain;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Embeddable;
import jakarta.persistence.OneToMany;

import java.util.ArrayList;
import java.util.List;

@Embeddable
public class Subjects {

    @OneToMany(mappedBy = "certificate", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Subject> subjects = new ArrayList<>();

    public void addAll(List<Subject> subjects) {
        this.subjects.addAll(subjects);
    }

    public List<Subject> getAll() {
        return this.subjects;
    }
}
