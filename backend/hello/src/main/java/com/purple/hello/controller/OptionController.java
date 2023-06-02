package com.purple.hello.controller;

import com.purple.hello.dto.in.*;
import com.purple.hello.enu.ResultType;
import com.purple.hello.service.*;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.PersistenceException;
import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;

@RestController("/option")
@ControllerAdvice
public class OptionController {
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
    OptionController(AlarmService alarmService, FeedService feedService, QuestionService questionService, RoomService roomService,
                      UserRoomService userRoomService, UserService userService){
        this.alarmService = alarmService;
        this.feedService = feedService;
        this.questionService = questionService;
        this.roomService = roomService;
        this.userRoomService = userRoomService;
        this.userService = userService;
    }

    @ApiOperation(
            value = "그룹 이름 변경 API (^)"
            , notes = "해당 그룹의 이름을 변경해주는 API. 모든 이용자가 변경 가능하다.")
    @PutMapping("/room/name")
    public ResponseEntity<String> updateRoomName(@RequestBody UpdateRoomNameInDTO updateRoomNameInDTO, HttpServletRequest request) throws Exception{
        long userId = Long.parseLong(request.getAttribute("userId").toString());
        updateRoomNameInDTO.setUserId(userId);
        boolean isUpdated = userRoomService.updateRoomName(updateRoomNameInDTO);
        if(!isUpdated) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(ResultType.FAILED.name());
        }else {
            return ResponseEntity.status(HttpStatus.OK).body(ResultType.SUCCESS.name());
        }

    }

    @ApiOperation(
            value = "나의 이름 변경 API "
            , notes = "해당 그룹에서 사용할 이용자의 이름을 변경해주는 API. 그룹방 별로 모든 이용자가 사용할 수 있다.")
    @PutMapping("/room/user")
    public ResponseEntity<String> updateUserName(@RequestBody UpdateUserNameInDTO updateUserNameInDTO, HttpServletRequest request) throws Exception{
        long userId = Long.parseLong(request.getAttribute("userId").toString());
        updateUserNameInDTO.setUserId(userId);
        boolean isUpdated = userRoomService.updateUserName(updateUserNameInDTO);
        if(!isUpdated) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(ResultType.FAILED.name());
        }else {
            return ResponseEntity.status(HttpStatus.OK).body(ResultType.SUCCESS.name());
        }
    }

    @ApiOperation(
            value = "그룹 비밀번호 변경 API vv"
            , notes = "해당 그룹방 비밀번호를 변경해주는 API. 관리자만 사용 가능하다.")
    @PutMapping("/room/password")
    public ResponseEntity<String> updateRoomPassword(@RequestBody UpdateRoomPasswordInDTO updateRoomPasswordInDTO, HttpServletRequest request) throws Exception {
        long userId = Long.parseLong(request.getAttribute("userId").toString());

        updateRoomPasswordInDTO.setUserId(userId);
        boolean isUpdated = roomService.updateRoomPassword(updateRoomPasswordInDTO);
        if (!isUpdated) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(ResultType.FAILED.name());
        }else {
            return ResponseEntity.status(HttpStatus.OK).body(ResultType.SUCCESS.name());
        }
    }

    @ApiOperation(
            value = "무동작 감지 알람 변경 API (x)"
            , notes = "그룹 내 이용자의 무동작이 감지된 경우 이외 사용자에게 알람을 전하는 API")
    @PutMapping("/room/move-alarm")
    public ResponseEntity<String> updateMoveAlarm(UpdateMoveAlarmInDTO updateMoveAlarmInDTO, HttpServletRequest request) throws Exception{
        long userId = Long.parseLong(request.getAttribute("userId").toString());
        updateMoveAlarmInDTO.setUserId(userId);
        boolean isUpdated = userRoomService.updateMoveAlarm(updateMoveAlarmInDTO);
        if (!isUpdated) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(ResultType.FAILED.name());
        }else {
            return ResponseEntity.status(HttpStatus.OK).body(ResultType.SUCCESS.name());
        }
    }
    @ApiOperation(
            value = "안전 감지 알람 변경 API ",
            notes = "그룹 내 이용자의 위험이 감지된 경우 이외 사용자에게 알람을 전하는 API")
    @PutMapping("/room/safe-alarm")
    public ResponseEntity<String> updateSafeAlarm(@RequestBody UpdateSafeAlarmInDTO updateSafeAlarmInDTO, HttpServletRequest request) throws Exception{
        long userId = Long.parseLong(request.getAttribute("userId").toString());
        updateSafeAlarmInDTO.setUserId(userId);

        boolean isUpdated = userRoomService.updateSafeAlarm(updateSafeAlarmInDTO);
        if (!isUpdated) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(ResultType.FAILED.name());
        }else {
            return ResponseEntity.status(HttpStatus.OK).body(ResultType.SUCCESS.name());
        }
    }

    @ApiOperation(
            value = "그룹 폭파 API(v) vv",
            notes = "방 생성자가 그룹방을 삭제하는 API. 관리자만 사용 가능하다.")
    @DeleteMapping("/room")
    public ResponseEntity<String> deleteRoom(@RequestBody DeleteRoomInDTO deleteRoomInDTO, HttpServletRequest request) throws Exception{
        long userId = Long.parseLong(request.getAttribute("userId").toString());
        deleteRoomInDTO.setUserId(userId);
        boolean isDeleted = this.roomService.deleteRoom(deleteRoomInDTO);
        if (!isDeleted) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(ResultType.FAILED.name());
        }else {
            return ResponseEntity.status(HttpStatus.OK).body(ResultType.SUCCESS.name());
        }
    }

    @ApiOperation(
            value = "그룹 탈퇴 API "
            , notes = "일반 사용자가 그룹에서 나가게 하는 API. 모든 이용자가 사용 가능하다.")
    @PutMapping("/room/exit")
    public ResponseEntity<String> deleteUserRoom(DeleteUserRoomInDTO deleteUserRoomInDTO, HttpServletRequest request) throws Exception{
        long userId = Long.parseLong(request.getAttribute("userId").toString());
        deleteUserRoomInDTO.setUserId(userId);
        boolean isUpdated = userRoomService.deleteUserRoom(deleteUserRoomInDTO);
        if(!isUpdated){
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(ResultType.FAILED.name());
        }else {
            return ResponseEntity.status(HttpStatus.OK).body(ResultType.SUCCESS.name());
        }
    }
}
