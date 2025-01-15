package com.cos.cercat.apis.user.api;

import com.cos.cercat.apis.user.request.UserCreateRequest;
import com.cos.cercat.apis.user.response.UserResponse;
import com.cos.cercat.web.Response;
import com.cos.cercat.domain.user.User;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.multipart.MultipartFile;

public interface UserApiDocs {

    @Operation(summary = "회원 정보 가져오기")
    Response<UserResponse> getMemberInfo(User currentUser);

    @Operation(summary = "회원 탈퇴")
    Response<Void> deleteUser(User currentUser);

    @Operation(summary = "회원 정보 추가")
    Response<Void> updateUserInfo(UserCreateRequest request,
                                  MultipartFile file,
                                  User currentUser);

    @Operation(summary = "회원 로그아웃")
    Response<Void> logout(HttpServletRequest request);

}
