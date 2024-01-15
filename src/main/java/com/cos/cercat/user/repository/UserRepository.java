package com.cos.cercat.user.repository;

import com.cos.cercat.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
