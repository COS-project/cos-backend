package com.cos.cercat.apis.certificate.api;

import com.cos.cercat.apis.certificate.request.InterestCertificateUpdateRequest;
import com.cos.cercat.web.Response;
import com.cos.cercat.domain.user.User;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.RequestBody;

@Tag(name = "자격증 수정 API")
public interface UpdateCertificateApiDocs {

    @Operation(summary = "관심 자격증 수정")
    Response<Void> updateInterestCertificates(@RequestBody InterestCertificateUpdateRequest request,
                                              @AuthenticationPrincipal User currentUser);

}
