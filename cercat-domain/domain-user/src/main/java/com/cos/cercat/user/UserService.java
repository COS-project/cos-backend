package com.cos.cercat.user;

import com.cos.cercat.common.domain.File;
import com.cos.cercat.common.domain.Image;
import com.cos.cercat.global.FileUploader;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserReader userReader;
    private final UserRemover userRemover;
    private final UserUpdater userUpdater;
    private final FileUploader fileUploader;
    private final TokenCacheManager tokenCacheManager;


    public void updateUser(TargetUser targetUser,
                           File file,
                           String nickname) {
        Image image = fileUploader.upload(file);
        User user = userReader.read(targetUser);
        user.update(nickname, image);
        userUpdater.update(user);
    }

    public void logout(String accessToken) {
        tokenCacheManager.ban(accessToken);
    }

    public void deleteUser(TargetUser targetUser) {
        User user = userReader.read(targetUser);
        userRemover.remove(user);
    }
}
