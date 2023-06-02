package com.purple.hello.test;

import com.purple.hello.entity.UserRoom;
import com.purple.hello.enu.BoolAlarm;
import com.purple.hello.enu.UserRoomRole;
import com.purple.hello.repo.UserRoomRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

import java.util.Date;

@TestConfiguration
public class TestConfig {
    @Autowired
    private UserRoomRepo userRoomRepo;

    @Bean
    public CommandLineRunner testDataLoader(){
        return new CommandLineRunner() {
            @Override
            public void run(String... args) throws Exception {
                UserRoom userRoom = UserRoom.builder()
                        .userRoomId(1)
                        .userRoomRole(UserRoomRole.ROLE1)
                        .userName("test_userName")
                        .roomName("test_roomName")
                        .safeAlarm(BoolAlarm.Y)
                        .moveAlarm(BoolAlarm.Y)
                        .dayAlarm(BoolAlarm.Y)
                        .createAt(new Date())
                        .user(null)
                        .room(null)
                        .build();

                userRoomRepo.save(userRoom);
            }
        };
    }
}
