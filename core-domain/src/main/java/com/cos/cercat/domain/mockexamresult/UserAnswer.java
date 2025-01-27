package com.cos.cercat.domain.mockexamresult;

import com.cos.cercat.domain.mockexam.Question;
import com.cos.cercat.domain.user.Ownable;
import com.cos.cercat.domain.user.User;
import lombok.Getter;

@Getter
public class UserAnswer implements Ownable {

    private final Long id;
    private final User user;
    private final Long subjectResultId;
    private final Question question;
    private final int selectOptionSeq;
    private final long takenTime;
    private final boolean isCorrect;
    private boolean isReviewed;

    public UserAnswer(Long id,
                      User user,
                      Long subjectResultId,
                      Question question,
                      int selectOptionSeq,
                      long takenTime,
                      boolean isCorrect,
                      boolean isReviewed) {
        this.id = id;
        this.user = user;
        this.subjectResultId = subjectResultId;
        this.question = question;
        this.selectOptionSeq = selectOptionSeq;
        this.takenTime = takenTime;
        this.isCorrect = isCorrect;
        this.isReviewed = isReviewed;
    }

    public void review() {
        this.isReviewed = true;
    }

    @Override
    public User getOwner() {
        return this.user;
    }

    @Override
    public boolean isOwner(User user) {
        return this.user.getId().equals(user.getId());
    }
}
