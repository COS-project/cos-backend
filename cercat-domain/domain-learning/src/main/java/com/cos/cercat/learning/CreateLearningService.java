package com.cos.cercat.learning;

import com.cos.cercat.certificate.TargetCertificate;
import com.cos.cercat.user.TargetUser;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class CreateLearningService {

    private final GoalAppender goalAppender;
    private final StudyTimeLogAppender studyTimeLogAppender;

    public void createGoal(TargetUser targetUser,
                           TargetCertificate targetCertificate,
                           NewGoal newGoal) {
        goalAppender.append(targetUser, targetCertificate, newGoal);
    }


    public void createStudyTimeLog(TargetGoal targetGoal,
                                   Long studyTime) {
        studyTimeLogAppender.append(targetGoal, studyTime);
    }
}
