package com.cos.cercat.domain.postsearch;


import com.cos.cercat.domain.user.User;

import java.util.List;

public interface SearchLogCache {

    void cache(User user, SearchLog searchLog);

    List<SearchLog> get(User user);

    void delete(User user, SearchLog searchLog);

    void deleteAll(User user);

}
