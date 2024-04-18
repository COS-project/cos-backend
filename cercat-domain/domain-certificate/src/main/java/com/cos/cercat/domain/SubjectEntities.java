package com.cos.cercat.domain;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Embeddable;
import jakarta.persistence.OneToMany;

import java.util.ArrayList;
import java.util.List;

@Embeddable
public class SubjectEntities {

    @OneToMany(mappedBy = "certificate", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<SubjectEntity> subjectEntities = new ArrayList<>();

    public void addAll(List<SubjectEntity> subjectEntities) {
        this.subjectEntities.addAll(subjectEntities);
    }

    public List<SubjectEntity> getAll() {
        return this.subjectEntities;
    }
}
