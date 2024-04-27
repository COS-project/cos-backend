package com.cos.cercat.apis.certificate.api;

import com.cos.cercat.apis.certificate.request.InterestCertificateUpdateRequest;
import com.cos.cercat.certificate.UpdateCertificateService;
import com.cos.cercat.common.domain.Response;
import com.cos.cercat.user.TargetUser;
import com.cos.cercat.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v2")
public class UpdateCertificateApi implements UpdateCertificateApiDocs{

    private final UpdateCertificateService updateCertificateService;

    @PutMapping("/interest-certificates")
    public Response<Void> updateInterestCertificates(@RequestBody InterestCertificateUpdateRequest request,
                                                     @AuthenticationPrincipal User currentUser) {
        updateCertificateService.updateCertificateExam(TargetUser.from(currentUser.getId()), request.toInterestTargets());
        return Response.success("관심 자격증 수정 성공");
    }

}
