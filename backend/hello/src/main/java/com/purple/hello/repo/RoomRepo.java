package com.purple.hello.repo;

import com.purple.hello.entity.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoomRepo extends JpaRepository<Room, Long> {
    List<Room> findAllByRoomCode(String roomCode);
    Room findRoomByRoomId(Long roomId);
    List<Room> findAll();
}
