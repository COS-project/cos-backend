package com.cos.cercat.certificate.api;

import com.cos.cercat.certificate.app.CertificateUpdateService;
import com.cos.cercat.certificate.dto.request.InterestCertificateUpdateRequest;
import com.cos.cercat.global.Response;
import com.cos.cercat.user.dto.UserDTO;
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

    private final CertificateUpdateService certificateUpdateService;

    @PutMapping("/interest-certificates")
    public Response<Void> updateInterestCertificates(@RequestBody List<InterestCertificateUpdateRequest> requests,
                                                     @AuthenticationPrincipal UserDTO user) {
        certificateUpdateService.updateInterestCertificates(requests, user.getId());
        return Response.success("흥미 자격증 수정 성공");
    }

}
