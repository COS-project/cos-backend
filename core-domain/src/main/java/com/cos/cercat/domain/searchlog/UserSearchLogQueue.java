package com.cos.cercat.domain.searchlog;


import com.cos.cercat.domain.certificate.Certificate;
import com.cos.cercat.domain.user.User;

import java.util.List;

public interface UserSearchLogQueue {

    void push(User user, SearchLog searchLog);

    List<String> getAll(User user, Certificate certificate);

    void pop(User user, SearchLog searchLog);

    void popAll(User user, Certificate certificate);

    boolean exists(User user, SearchLog searchLog);

}
