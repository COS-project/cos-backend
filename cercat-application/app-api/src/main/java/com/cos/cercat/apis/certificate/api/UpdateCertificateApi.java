package com.cos.cercat.apis.certificate.api;

import com.cos.cercat.apis.certificate.dto.request.InterestCertificateUpdateRequest;
import com.cos.cercat.common.domain.Response;
import com.cos.cercat.domain.certificate.UpdateCertificateService;
import com.cos.cercat.domain.user.TargetUser;
import com.cos.cercat.dto.UserDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
@Tag(name = "자격증 수정 API")
public class UpdateCertificateApi {

    private final UpdateCertificateService updateCertificateService;

    @PutMapping("/interest-certificates")
    @Operation(summary = "관심 자격증 수정")
    public Response<Void> updateInterestCertificates(@RequestBody InterestCertificateUpdateRequest request,
                                                     @AuthenticationPrincipal UserDTO currentUser) {
        updateCertificateService.updateCertificateExam(TargetUser.from(currentUser.getId()), request.toInterestTargets());
        return Response.success("관심 자격증 수정 성공");
    }

}
