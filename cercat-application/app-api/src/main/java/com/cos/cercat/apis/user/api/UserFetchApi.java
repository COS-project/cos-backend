package com.cos.cercat.apis.user.api;

import com.cos.cercat.apis.user.dto.response.UserResponse;
import com.cos.cercat.common.domain.Response;
import com.cos.cercat.dto.UserDTO;
import com.cos.cercat.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
@Tag(name = "유저 정보 조회 API")
public class UserFetchApi {

    private final UserService userService;

    @GetMapping("/users/me")
    @Operation(summary = "회원 정보 가져오기")
    public Response<UserResponse> getMemberInfo(@AuthenticationPrincipal UserDTO currentUser) {
        return Response.success(UserResponse.fromDTO(currentUser));
    }
}
