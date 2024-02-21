package com.cos.cercat.user.app;

import com.cos.cercat.global.common.Image;
import com.cos.cercat.global.util.FileUploader;
import com.cos.cercat.user.dto.request.UserCreateRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserUpdateService {

    private final UserService userService;
    private final FileUploader fileUploader;

    @Transactional
    public void updateUserInfo(UserCreateRequest request,
                               MultipartFile file,
                               Long userId) {
        List<Image> images = fileUploader.uploadFileInStorage(List.of(file));
        Image userImage = images.isEmpty() ? null : images.get(0);

        userService.updateUser(userId, request.nickname(), userImage);
    }

    @Transactional
    public void logout(String accessToken, String email) {
        userService.logout(accessToken, email);
        log.info("user - {} 로그아웃", email);
    }

}
