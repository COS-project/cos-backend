package com.cos.cercat.domain.learning;

import com.cos.cercat.domain.certificate.Certificate;
import com.cos.cercat.domain.certificate.CertificateReader;
import com.cos.cercat.domain.certificate.CertificateId;
import com.cos.cercat.domain.user.UserId;
import com.cos.cercat.domain.user.User;
import com.cos.cercat.domain.user.UserReader;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class CreateLearningService {

    private final UserReader userReader;
    private final CertificateReader certificateReader;
    private final GoalAppender goalAppender;
    private final StudyTimeLogAppender studyTimeLogAppender;

    public void createGoal(UserId userId,
                           CertificateId certificateId,
                           NewGoal newGoal) {
        User user = userReader.read(userId);
        Certificate certificate = certificateReader.read(certificateId);
        goalAppender.append(user, certificate, newGoal);
    }


    public void createStudyTimeLog(GoalId goalId,
                                   Long studyTime) {
        studyTimeLogAppender.append(goalId, studyTime);
    }
}
