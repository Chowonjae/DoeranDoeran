package com.purple.hello.repo;

import com.purple.hello.entity.History;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface HistoryRepo extends JpaRepository<History, Long> {
    Optional<History> findByRoom_RoomId(long roomId);
}
