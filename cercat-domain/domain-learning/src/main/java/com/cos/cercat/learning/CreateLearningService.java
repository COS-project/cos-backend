package com.cos.cercat.learning;

import com.cos.cercat.certificate.Certificate;
import com.cos.cercat.certificate.CertificateReader;
import com.cos.cercat.certificate.TargetCertificate;
import com.cos.cercat.user.TargetUser;
import com.cos.cercat.user.User;
import com.cos.cercat.user.UserReader;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class CreateLearningService {

    private final UserReader userReader;
    private final CertificateReader certificateReader;
    private final GoalAppender goalAppender;
    private final StudyTimeLogAppender studyTimeLogAppender;

    public void createGoal(TargetUser targetUser,
                           TargetCertificate targetCertificate,
                           NewGoal newGoal) {
        User user = userReader.read(targetUser);
        Certificate certificate = certificateReader.read(targetCertificate);
        goalAppender.append(user, certificate, newGoal);
    }


    public void createStudyTimeLog(TargetGoal targetGoal,
                                   Long studyTime) {
        studyTimeLogAppender.append(targetGoal, studyTime);
    }
}
