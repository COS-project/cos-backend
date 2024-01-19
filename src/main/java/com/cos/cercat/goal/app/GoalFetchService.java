package com.cos.cercat.goal.app;

import com.cos.cercat.certificate.app.CertificateService;
import com.cos.cercat.certificate.domain.Certificate;
import com.cos.cercat.goal.domain.Goal;
import com.cos.cercat.goal.dto.response.GoalResponse;
import com.cos.cercat.user.app.UserService;
import com.cos.cercat.user.domain.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class GoalFetchService {

    private final GoalService goalService;
    private final CertificateService certificateService;
    private final UserService userService;

    public GoalResponse getGoal(Long certificateId, Long userId) {
        Certificate certificate = certificateService.getCertificate(certificateId);
        User user = userService.getUser(userId);
        Goal goal = goalService.getGoal(user, certificate);

        return GoalResponse.from(goal);
    }
}
