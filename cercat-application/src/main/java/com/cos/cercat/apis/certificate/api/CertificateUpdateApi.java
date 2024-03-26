package com.cos.cercat.apis.certificate.api;

import com.cos.cercat.apis.certificate.app.usecase.CertificateUpdateUseCase;
import com.cos.cercat.apis.certificate.dto.request.InterestCertificateUpdateRequest;
import com.cos.cercat.common.dto.Response;
import com.cos.cercat.domain.user.dto.UserDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
@Tag(name = "자격증 수정 API")
public class CertificateUpdateApi {

    private final CertificateUpdateUseCase certificateUpdateUseCase;

    @PutMapping("/interest-certificates")
    @Operation(summary = "관심 자격증 수정")
    public Response<Void> updateInterestCertificates(@RequestBody List<InterestCertificateUpdateRequest> requests,
                                                     @AuthenticationPrincipal UserDTO user) {
        certificateUpdateUseCase.updateInterestCertificates(requests, user.getId());
        return Response.success("관심 자격증 수정 성공");
    }

}
