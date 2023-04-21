package com.purple.hello.service.impl;

import com.purple.hello.dao.RoomDAO;
import com.purple.hello.dto.in.CreateUserRoomInDTO;
import com.purple.hello.dto.in.UpdateRoomPasswordInDTO;
import com.purple.hello.dto.in.UpdateRoomCodeInDTO;
import com.purple.hello.dto.in.DeleteRoomInDTO;
import com.purple.hello.dto.tool.CreateRoomDTO;
import com.purple.hello.dto.out.ReadRoomCodeOutDTO;
import com.purple.hello.dto.out.ReadRoomOutDTO;
import com.purple.hello.dto.out.ReadUserRoomJoinOutDTO;
import com.purple.hello.encoder.PasswordEncoder;
import com.purple.hello.generator.RoomCode;
import com.purple.hello.service.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.time.Instant;
import java.util.List;

@Service
public class RoomServiceImpl implements RoomService {
    @Autowired
    private RoomCode roomCode;
    @Autowired
    private final RoomDAO roomDAO;
    @Autowired
    private final PasswordEncoder passwordEncoder;
    RoomServiceImpl(RoomDAO roomDAO, PasswordEncoder passwordEncoder){
        this.roomDAO = roomDAO;
        this.passwordEncoder = passwordEncoder;
    }
    @Override
    public List<ReadRoomOutDTO> readRoomByUserId(long userId) {
        return this.roomDAO.readRoomByUserId(userId);
    }

    @Override
    public CreateRoomDTO createRoom(CreateUserRoomInDTO createUserRoomInDTO) {
        createUserRoomInDTO.setRoomPassword(passwordEncoder.encode(createUserRoomInDTO.getRoomPassword()));
        return this.roomDAO.createRoom(createUserRoomInDTO);
    }

    @Override
    public boolean comparePasswordByRoomCode(long roomId, String password) {
        String storedPassword = this.roomDAO.comparePasswordByRoomCode(roomId);
        return passwordEncoder.matches(password, storedPassword);
    }

    @Override
    public ReadUserRoomJoinOutDTO readUserRoomJoinByRoomCode(String roomCode) {
        return this.roomDAO.readUserRoomJoinByRoomCode(roomCode);
    }

    @Override
    @Transactional
    public void updateRoomPassword(UpdateRoomPasswordInDTO updateRoomPasswordInDTO) {
        updateRoomPasswordInDTO.setRoomPassword(passwordEncoder.encode(updateRoomPasswordInDTO.getRoomPassword()));
        this.roomDAO.updateRoomPassword(updateRoomPasswordInDTO);
    }
    public ReadRoomCodeOutDTO readRoomCodeByRoomId(long roomId) {
        String url = roomDAO.readRoomCodeByRoomId(roomId);

        if (url == null)
            return null;

        Instant currentTime = Instant.now();
        ReadRoomCodeOutDTO readRoomCodeOutDTO = new ReadRoomCodeOutDTO();
        if(url != null && url.length() > 0){
            String createdTimeString = roomCode.getTime(url);
            Instant createdTime = Instant.parse(createdTimeString);
            Duration duration = Duration.between(createdTime, currentTime);
            if(duration.compareTo(Duration.ofSeconds(30)) > 0){
                String newUrl = saveAndResult(roomId, currentTime);
                readRoomCodeOutDTO.setRoomCode(newUrl);
            }else{
                readRoomCodeOutDTO.setRoomCode(url);
            }
            return readRoomCodeOutDTO;
        }
        String newUrl = saveAndResult(roomId, currentTime);
        readRoomCodeOutDTO.setRoomCode(newUrl);
        return readRoomCodeOutDTO;
    }

    @Override
    public void updateRoomCodeByRoomId(UpdateRoomCodeInDTO updateRoomCodeInDTO) {
        roomDAO.updateRoomCodeByRoomId(updateRoomCodeInDTO);
    }
    private String saveAndResult(long roomId, Instant time) {
        String newUrl = roomCode.makeUrl(roomId, time);
        UpdateRoomCodeInDTO updateRoomCodeInDTO = new UpdateRoomCodeInDTO();
        updateRoomCodeInDTO.setRoomId(roomId);
        updateRoomCodeInDTO.setRoomCode(newUrl);

        updateRoomCodeByRoomId(updateRoomCodeInDTO);
        return newUrl;
    }
    public boolean deleteRoom(DeleteRoomInDTO deleteRoomInDTO) {
        return this.roomDAO.deleteRoom(deleteRoomInDTO);
    }
}
