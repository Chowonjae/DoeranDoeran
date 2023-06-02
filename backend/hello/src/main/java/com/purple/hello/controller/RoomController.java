package com.purple.hello.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.purple.hello.dto.in.CreateFeedInDTO;
import com.purple.hello.dto.in.CreateUserRoomInDTO;
import com.purple.hello.dto.in.CreateUserRoomJoinInDTO;
import com.purple.hello.dto.out.*;
import com.purple.hello.dto.tool.CreateRoomDTO;
import com.purple.hello.dto.tool.NotificationDTO;
import com.purple.hello.service.*;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/room")
@ControllerAdvice
public class RoomController {
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
    @Autowired
    private final HistoryService historyService;
    private final NotificationService notificationService;
    RoomController(AlarmService alarmService, FeedService feedService, QuestionService questionService, RoomService roomService,
                   UserRoomService userRoomService, UserService userService, HistoryService historyService, NotificationService notificationService){
        this.alarmService = alarmService;
        this.feedService = feedService;
        this.questionService = questionService;
        this.roomService = roomService;
        this.userRoomService = userRoomService;
        this.userService = userService;
        this.historyService = historyService;
        this.notificationService = notificationService;
    }

    @ApiOperation(
            value = "그룹방 생성 API (v) vv"
            , notes = "관리자가 그룹방을 생성할 경우 그룹방에 맞는 정보를 저장해주는 API")
    @PostMapping("/create")
    public ResponseEntity<CreateRoomOutDTO> createRoom(@RequestBody CreateUserRoomInDTO createUserRoomInDTO, HttpServletRequest request)throws Exception{
        long userId = Long.parseLong(request.getAttribute("userId").toString());
        createUserRoomInDTO.setUserId(userId);
        CreateRoomDTO createRoomDTO = this.roomService.createRoom(createUserRoomInDTO);

        if(createRoomDTO != null)
            historyService.createFirstHistory(createRoomDTO.getRoomId());


        // userRoom 객체 생성
        CreateRoomOutDTO createRoomOutDTO = this.userRoomService.createUserRoom(createUserRoomInDTO, createRoomDTO.getRoomId());

        return ResponseEntity.status(HttpStatus.OK).body(createRoomOutDTO);
    }
    @ApiOperation(
            value = "그룹방 입장 정보 출력 API (v) vv",
            notes = "그룹방 코드를 입력할 경우 다이얼로그에 사용할 정보를 출력해주는 API")
    @GetMapping("/join-info")
    public ResponseEntity<ReadUserRoomJoinOutDTO> readUserRoomJoinByRoomId(@RequestParam long roomId)throws Exception{
        ReadUserRoomJoinOutDTO readUserRoomJoinOutDTO = this.roomService.readUserRoomJoinByRoomId(roomId);

        if (readUserRoomJoinOutDTO == null)
            throw new IllegalArgumentException();

        return ResponseEntity.status(HttpStatus.OK).body(readUserRoomJoinOutDTO);
    }

    @ApiOperation(
            value = "그룹방 정보 추가 API vv",
            notes = "이용자가 그룹방에 입장할 경우 그룹방 정보를 수정 및 추가해주는 API")
    @PostMapping("/join")
    public ResponseEntity<CreateUserRoomJoinOutDTO> createUserRoomJoin(@RequestBody CreateUserRoomJoinInDTO createUserRoomJoinInDTO,
                                                                       HttpServletRequest request)throws Exception{
        long userId = Long.parseLong(request.getAttribute("userId").toString());
        createUserRoomJoinInDTO.setUserId(userId);

        boolean isCorrectPassword = this.roomService.comparePasswordByRoomCode(createUserRoomJoinInDTO.getRoomId(),
                createUserRoomJoinInDTO.getRoomPassword());

        if (!isCorrectPassword)
            throw new IllegalArgumentException();

        CreateUserRoomJoinOutDTO createUserRoomJoinOutDTO = this.userRoomService.createUserRoomJoin(createUserRoomJoinInDTO);

        return ResponseEntity.status(HttpStatus.OK).body(createUserRoomJoinOutDTO);
    }

    @ApiOperation(value = "사진 제출 여부 확인 API (v) vv",
            notes = "이용자별 사진 제출 여부를 확인해주는 API")
    @GetMapping("/feed-status")
    public ResponseEntity<List<CompareFeedByRoomIdOutDTO>> compareFeedByUserIdAndRoomId(
            @RequestParam("roomId") long roomId, @DateTimeFormat(pattern="yyyy-MM-dd") Date date) throws Exception{
        List<CompareFeedByRoomIdOutDTO> compareFeedByRoomIdOutDTOs = this.feedService.compareFeedByRoomIdAndDate(roomId, date);

        return ResponseEntity.status(HttpStatus.OK).body(compareFeedByRoomIdOutDTOs);
    }
    @ApiOperation(value = "피드 생성 API (v) vv",
                  notes = "이용자가 피드를 올릴 경우 피드 정보를 저장해주는 API")
    @PostMapping(value ="/feed", consumes = "multipart/form-data")
    public ResponseEntity<CreateFeedOutDTO> createFeed(CreateFeedInDTO createFeedInDTO) throws Exception{
        try{
            CreateFeedOutDTO result = this.feedService.createFeed(createFeedInDTO);
            if (result == null)
                throw new IllegalArgumentException();

            return ResponseEntity.status(HttpStatus.OK).body(result);
        }catch (IOException e){
            throw new IllegalArgumentException();
        }
    }
    @ApiOperation(value = "초대 링크 생성 API (v) vv",
                    notes = "해당 그룹방 초대 링크를 출력")
    @GetMapping("/code")
    public ResponseEntity<ReadRoomCodeOutDTO> readRoomCodeByRoomId(@RequestParam long roomId) throws JsonProcessingException {
        ReadRoomCodeOutDTO result = roomService.readRoomCodeByRoomId(roomId);

        if (result == null)
            throw new IllegalArgumentException();

        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    @ApiOperation(value = "날짜 기반 피드 출력 API (o)",
            notes = "해당 날짜에 기재한 피드 전체를 출력해주는 API / timezone 차이로 날짜 인식을 못하는 오류")
    @GetMapping("/date-feed")
    public ResponseEntity<List<ReadFeedOutDTO>> readFeedByRoomIdAndDate(@RequestParam("roomId") long roomId, @DateTimeFormat(pattern="yyyy-MM-dd") Date date) throws Exception{
        List<ReadFeedOutDTO> readFeedOutDTOs = this.feedService.readFeedByRoomIdAndDate(roomId, date);

        return ResponseEntity.status(HttpStatus.OK).body(readFeedOutDTOs);
    }

    @ApiOperation(value = "날짜 기반 질문 출력 API (o)",
            notes = "해당 날짜에 해당하는 질문을 출력해주는 API")
    @GetMapping("/date-question")
    public ResponseEntity<ReadQuestionOutDTO> readQuestionByRoomIdAndDate(@RequestParam("roomId") long roomId, @DateTimeFormat(pattern="yyyy-MM-dd") Date date) {
        try{
            ReadQuestionOutDTO readQuestionOutDTO = this.historyService.readQuestionByRoomIdAndDate(roomId, date);
            if(readQuestionOutDTO == null){
                return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
            }
            return ResponseEntity.status(HttpStatus.OK).body(readQuestionOutDTO);
        }catch (NullPointerException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }catch (IOException e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @ApiOperation(value = "질문 출력 API",
            notes = "해당 날짜에 사용할 질문을 랜덤으로 출력해주는 API")
    @GetMapping("/question")
    public ResponseEntity<ReadQuestionOutDTO> readQuestionByQuestionId(@RequestParam("questionId") long questionId) throws Exception{
        try{
            roomService.createQuestion();
        }catch (NullPointerException e){
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
        }
        return ResponseEntity.status(HttpStatus.OK).body(null);
    }

    @ApiOperation(
            value = "비밀번호 질문 출력 API "
            , notes = "그룹 비밀번호 질문을 출력하는 API.")
    @GetMapping("/room-question")
    public ResponseEntity<ReadRoomQuestionOutDTO> readRoomQuestion(@RequestParam("roomId") long roomId, HttpServletRequest request) throws Exception{
        long userId = Long.parseLong(request.getAttribute("userId").toString());
        ReadRoomQuestionOutDTO readRoomQuestionOutDTO = roomService.readRoomQuestionByRoomIdAndUserId(roomId, userId);
        if(readRoomQuestionOutDTO == null){
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
        }else {
            return ResponseEntity.status(HttpStatus.OK).body(readRoomQuestionOutDTO);
        }
    }

    @ApiOperation(value = "그룹 유저 리스트 출력 API (v) vv",
            notes = "해당 그룹방 유저리스트를 출력")
    @GetMapping("/user-list")
    public ResponseEntity<ReadMemberListOutDTO> readMemberListByRoomId(@RequestParam long roomId, HttpServletRequest request) throws Exception{
        long userId = Long.parseLong(request.getAttribute("userId").toString());
        ReadMemberListOutDTO result = roomService.readMemberListByRoomId(roomId, userId);

        if (result == null) {
            throw new IllegalArgumentException();
        }else {
            return ResponseEntity.status(HttpStatus.OK).body(result);
        }
    }

    @ApiOperation(value = "무응답 알림 전송 API (v) vv",
            notes = "해당 그룹방의 나머지 유저에게 알림 전송")
    @GetMapping("/no-response-notification")
    public ResponseEntity<ReadUserRoomNotificationOutDTO> sendNoResponseNotificationByRoomId(HttpServletRequest request) throws Exception{
        long userId = Long.parseLong(request.getAttribute("userId").toString());
        List<NotificationDTO> notificationDTOS = roomService.makeNotificationForOtherDevicesByRoomId(userId);
        int notificationCount = notificationService.sendNotifications(notificationDTOS);
        if(notificationCount == 0) {
            throw new IllegalArgumentException();
        }else {
            return ResponseEntity.status(HttpStatus.OK).body(ReadUserRoomNotificationOutDTO.builder().notificationCount(notificationCount).build());
        }
    }
}
