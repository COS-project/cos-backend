package com.cos.cercat.domain.postsearch;


import com.cos.cercat.domain.user.User;

import java.util.List;

public interface SearchLogQueue {

    void push(User user, SearchLog searchLog);

    List<SearchLog> getAll(User user);

    void pop(User user, SearchLog searchLog);

    void popAll(User user);

    boolean exists(User user, SearchLog searchLog);

}
