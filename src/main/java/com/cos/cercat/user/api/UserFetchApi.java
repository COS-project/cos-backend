package com.cos.cercat.user.api;

import com.cos.cercat.global.Response;
import com.cos.cercat.user.dto.UserDTO;
import com.cos.cercat.user.dto.response.UserResponse;
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

    @GetMapping("/user/me")
    @Operation(summary = "회원 정보 가져오기")
    public Response<UserResponse> getMemberInfo(@AuthenticationPrincipal UserDTO user) {
        return Response.success(UserResponse.fromDTO(user));
    }

}
