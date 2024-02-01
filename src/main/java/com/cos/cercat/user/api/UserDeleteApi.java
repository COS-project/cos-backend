package com.cos.cercat.user.api;

import com.cos.cercat.global.Response;
import com.cos.cercat.user.app.UserService;
import com.cos.cercat.user.dto.UserDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
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

    @DeleteMapping("/logout")
    @Operation(summary = "회원 로그아웃")
    public Response<Void> logout(HttpServletRequest request, @AuthenticationPrincipal UserDTO user) {
        userService.logout(request.getHeader("Access-Token"), user.getEmail());
        return Response.success("로그아웃 성공");
    }

}
