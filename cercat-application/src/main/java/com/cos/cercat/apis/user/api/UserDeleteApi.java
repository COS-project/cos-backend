package com.cos.cercat.apis.user.api;

import com.cos.cercat.common.domain.Response;
import com.cos.cercat.dto.UserDTO;;
import com.cos.cercat.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
@Tag(name = "유저 정보 삭제 API")
public class UserDeleteApi {

    private final UserService userService;

    @DeleteMapping("/users/me")
    @Operation(summary = "회원 탈퇴")
    public Response<Void> deleteUser(@AuthenticationPrincipal UserDTO user) {
        userService.deleteUser(user.getId());
        return Response.success("회원탈퇴 성공");
    }

}
