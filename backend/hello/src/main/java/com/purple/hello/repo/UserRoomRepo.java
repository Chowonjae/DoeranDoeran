package com.purple.hello.repo;

import com.purple.hello.entity.UserRoom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRoomRepo extends JpaRepository<UserRoom, Long> {
}
