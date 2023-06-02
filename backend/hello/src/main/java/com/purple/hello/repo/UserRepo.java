package com.purple.hello.repo;

import com.purple.hello.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepo extends JpaRepository<User, Long> {
    Optional<User> findByOauthId(String oauthId);
    User findByRefreshToken(String refreshToken);
}
