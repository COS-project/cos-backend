package com.cos.cercat.domain.user;

import com.cos.cercat.common.domain.File;
import com.cos.cercat.common.domain.FileUploader;
import com.cos.cercat.common.domain.Image;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

  private final UserReader userReader;
  private final UserRemover userRemover;
  private final UserUpdater userUpdater;
  private final FileUploader fileUploader;
  private final TokenManager tokenManager;


  public void updateUser(TargetUser targetUser,
      File file,
      String nickname) {
    Image image = fileUploader.upload(file);
    User user = userReader.read(targetUser);
    user.update(nickname, image);
    userUpdater.update(user);
  }

  public void logout(String accessToken) {
    tokenManager.ban(accessToken);
  }

  public void deleteUser(TargetUser targetUser) {
    User user = userReader.read(targetUser);
    userRemover.remove(user);
  }
}
