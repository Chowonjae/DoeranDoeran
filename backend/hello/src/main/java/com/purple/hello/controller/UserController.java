package com.purple.hello.controller;

import com.purple.hello.dto.in.UpdateUserInfoInDTO;
import com.purple.hello.dto.out.ReadRoomOutDTO;
import com.purple.hello.dto.out.ReadUserInfoOutDTO;
import com.purple.hello.enu.ResultType;
import com.purple.hello.service.*;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private final AlarmService alarmService;
    @Autowired
    private final FeedService feedService;
    @Autowired
    private final QuestionService questionService;
    @Autowired
    private final RoomService roomService;
    @Autowired
    private final UserRoomService userRoomService;
    @Autowired
    private final UserService userService;
    UserController(AlarmService alarmService, FeedService feedService, QuestionService questionService, RoomService roomService,
                   UserRoomService userRoomService, UserService userService){
        this.alarmService = alarmService;
        this.feedService = feedService;
        this.questionService = questionService;
        this.roomService = roomService;
        this.userRoomService = userRoomService;
        this.userService = userService;
    }

    @ApiOperation(
            value = "그룹방 출력 API (v) vv"
            , notes = "그룹 화면에서 사용자에게 맞는 그룹방을 출력해주는 API")
    @GetMapping("/rooms")
    public ResponseEntity<List<ReadRoomOutDTO>> readRoomByUserId(HttpServletRequest request)throws Exception{
        long userId = Long.parseLong(request.getAttribute("userId").toString());
        List<ReadRoomOutDTO> readRoomOutDTOs = roomService.readRoomByUserId(userId);
        return ResponseEntity.status(HttpStatus.OK).body(readRoomOutDTOs);
    }

    @GetMapping("/filter")
    public String filterTest(HttpServletRequest request){
        long userId = (long) request.getAttribute("userId");

        return "User ID: " + userId;
    }

    @ApiOperation(
            value = "유저 정보 수정 API (v) vv"
            , notes = "사용자의 생일과 프로필을 업데이트 해주는 API")
    @PutMapping("/user-info")
    public ResponseEntity<String> updateUserInfo(@RequestBody UpdateUserInfoInDTO updateUserInfoInDTO, HttpServletRequest request) throws Exception{
        long userId = Long.parseLong(request.getAttribute("userId").toString());
        updateUserInfoInDTO.setUserId(userId);
        boolean isUpdated = userService.updateUserInfo(updateUserInfoInDTO);
        if(!isUpdated) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(ResultType.FAILED.name());
        }else {
            return ResponseEntity.status(HttpStatus.OK).body(ResultType.SUCCESS.name());
        }
    }

    @ApiOperation(
            value = "유저 정보 조회 API (v) vv"
            , notes = "사용자의 정보를 출력해주는 API")
    @GetMapping("/user-info")
    public ResponseEntity<ReadUserInfoOutDTO> readUserInfo(HttpServletRequest request) throws Exception{
        long userId = Long.parseLong(request.getAttribute("userId").toString());
        ReadUserInfoOutDTO readUserInfoOutDTO = userService.readUserInfoByUserId(userId);

        if(readUserInfoOutDTO == null)
            throw new IllegalArgumentException();
        
        return ResponseEntity.status(HttpStatus.OK).body(readUserInfoOutDTO);
    }
}
