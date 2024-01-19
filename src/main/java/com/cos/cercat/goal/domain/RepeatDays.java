package com.cos.cercat.goal.domain;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Embeddable;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;

import java.util.ArrayList;
import java.util.List;

@Embeddable
public class RepeatDays {

    @OneToMany(mappedBy = "goal", fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<RepeatDay> repeatDays = new ArrayList<>();

    public void addAll(List<RepeatDay> repeatDays) {
        this.repeatDays.addAll(repeatDays);
    }

    public List<RepeatDay> getAll() {
        return this.repeatDays;
    }

    public List<RepeatDay> getStudyRepeatDays() {
        return this.repeatDays.stream()
                .filter(repeatDay -> repeatDay.getRepeatType() == RepeatType.STUDY)
                .toList();
    }

    public List<RepeatDay> getMockExamRepeatDays() {
        return this.repeatDays.stream()
                .filter(repeatDay -> repeatDay.getRepeatType() == RepeatType.MOCK_EXAM)
                .toList();
    }
}
