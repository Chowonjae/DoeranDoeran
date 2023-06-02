package com.purple.hello.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.purple.hello.dto.in.CreateRoomInDTO;
import com.purple.hello.dto.in.CreateUserRoomInDTO;
import com.purple.hello.dto.in.CreateUserRoomJoinInDTO;
import com.purple.hello.dto.out.*;
import com.purple.hello.dto.tool.CreateRoomDTO;
import com.purple.hello.enu.BoolAlarm;
import com.purple.hello.enu.UserRoomRole;
import com.purple.hello.repo.UserRoomRepo;
import com.purple.hello.service.impl.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.web.HttpRequestMethodNotSupportedException;

import java.util.ArrayList;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(RoomController.class)
class RoomControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    ObjectMapper objectMapper;
    @MockBean
    AlarmServiceImpl alarmService;
    @MockBean
    FeedServiceImpl feedService;
    @MockBean
    QuestionServiceImpl questionService;
    @MockBean
    RoomServiceImpl roomService;
    @MockBean
    UserRoomServiceImpl userRoomService;
    @MockBean
    UserServiceImpl userService;
    @MockBean
    HistoryServiceImpl historyService;
    @MockBean
    UserRoomRepo userRoomRepo;
    @MockBean
    NotificationServiceImpl notificationService;
    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    //
    @Test
    void createRoom() throws Exception {
        // init
        CreateRoomInDTO createRoomInDTO = CreateRoomInDTO.builder()
                .roomQuestion("test_roomQuestion")
                .roomPassword("test_roomPassword")
                .roomName("test_roomName")
                .userName("test_userName")
                .build();

        String content = objectMapper.writeValueAsString(createRoomInDTO);

        // given
        given(roomService.createRoom(any()))
                .willReturn(CreateRoomDTO.builder()
                        .roomId(1)
                        .build());

        given(userRoomService.createUserRoom(any(), eq(1L)))
                .willReturn(CreateRoomOutDTO.builder()
                        .userRoomId(1)
                        .roomId(1)
                        .userRoomRole(UserRoomRole.ROLE1)
                        .userName("test_userName")
                        .roomName("test_roomName")
                        .safeAlarm(BoolAlarm.Y)
                        .moveAlarm(BoolAlarm.Y)
                        .dayAlarm(BoolAlarm.Y)
                        .createAt(new Date())
                        .build());

        // when - then
        mockMvc.perform(post("/room/create", 1)
                        .requestAttr("userId", 1)
                        .characterEncoding("UTF-8")
                        .content(content)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
    }

    @Test
    void createRoom_InValidController() throws Exception {
        // init
        CreateRoomInDTO createRoomInDTO = CreateRoomInDTO.builder()
                .roomQuestion("test_roomQuestion")
                .roomPassword("test_roomPassword")
                .roomName("test_roomName")
                .userName("test_userName")
                .build();

        String content = objectMapper.writeValueAsString(createRoomInDTO);

        // given
        given(roomService.createRoom(any()))
                .willReturn(CreateRoomDTO.builder()
                        .roomId(1)
                        .build());

        given(userRoomService.createUserRoom(any(CreateUserRoomInDTO.class), any(Long.class)))
                .willThrow(new IllegalArgumentException());

        // when - then
        mockMvc.perform(post("/room/create")
                        .characterEncoding("UTF-8")
                        .content(content)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .requestAttr("userId", 1L))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(result -> Assertions.assertTrue(result.getResolvedException() instanceof IllegalArgumentException))
                .andReturn();
    }

    @Test
    void createRoom_WrongHttpMethod() throws Exception {
        // init
        CreateRoomInDTO createRoomInDTO = CreateRoomInDTO.builder()
                .roomQuestion("test_roomQuestion")
                .roomPassword("test_roomPassword")
                .roomName("test_roomName")
                .userName("test_userName")
                .build();

        // given
        given(roomService.createRoom(any()))
                .willReturn(CreateRoomDTO.builder()
                        .roomId(1)
                        .build());

        given(userRoomService.createUserRoom(any(), eq(1L)))
                .willReturn(CreateRoomOutDTO.builder()
                        .userRoomId(1)
                        .roomId(1)
                        .userRoomRole(UserRoomRole.ROLE1)
                        .userName("test_userName")
                        .roomName("test_roomName")
                        .safeAlarm(BoolAlarm.Y)
                        .moveAlarm(BoolAlarm.Y)
                        .dayAlarm(BoolAlarm.Y)
                        .createAt(new Date())
                        .build());

        // when - then
        mockMvc.perform(get("/room/create")
                        .requestAttr("userId", 1L))
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof HttpRequestMethodNotSupportedException))
                .andReturn();
    }

    @Test
    void createRoom_WrongHttpMethod_InValidController() throws Exception {
        // init
        CreateRoomInDTO createRoomInDTO = CreateRoomInDTO.builder()
                .roomQuestion("test_roomQuestion")
                .roomPassword("test_roomPassword")
                .roomName("test_roomName")
                .userName("test_userName")
                .build();

        // given
        given(roomService.createRoom(any()))
                .willReturn(CreateRoomDTO.builder()
                        .roomId(1)
                        .build());

        given(userRoomService.createUserRoom(any(), eq(1L)))
                .willThrow(new IllegalArgumentException());

        // when - then
        mockMvc.perform(get("/room/create?userId={userId}", 1))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof HttpRequestMethodNotSupportedException))
                .andReturn();
    }
    @Test
    void readUserRoomJoinByRoomCode() throws Exception {
        // init
        long initRoomId = 1;

        String content = objectMapper.writeValueAsString(initRoomId);

        // given
        given(roomService.readUserRoomJoinByRoomId(any(Long.class)))
                .willReturn(ReadUserRoomJoinOutDTO.builder()
                        .roomId(1)
                        .roomName("test_roomName")
                        .roomQuestion("test_roomQuestion").build());

        // when - then
        mockMvc.perform(get("/room/join-info?roomId=1"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andReturn();
    }

    @Test
    void readUserRoomJoinByRoomCode_InValidController() throws Exception {
        // init
        String roomCode = "test_roomCode";

        String content = objectMapper.writeValueAsString(roomCode);

        // given
        given(roomService.readUserRoomJoinByRoomId(any(Long.class)))
                .willThrow(new IllegalArgumentException());

        // when - then
        mockMvc.perform(get("/room/join-info?roomId=1")
                        .requestAttr("userId", 1L))
                .andExpect(result -> Assertions.assertTrue(result.getResolvedException() instanceof IllegalArgumentException))
                .andReturn();
    }

    @Test
    void readUserRoomJoinByRoomCode_WrongHttpMethod() throws Exception {
        // init
        String roomCode = "test_roomCode";

        String content = objectMapper.writeValueAsString(roomCode);

        // given
        given(roomService.readUserRoomJoinByRoomId(any(Long.class)))
                .willThrow(new IllegalArgumentException());

        // when - then
        mockMvc.perform(post("/room/join-info")
                        .characterEncoding("UTF-8")
                        .content(content)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .requestAttr("userId", 1L))
                .andExpect(result -> Assertions.assertTrue(result.getResolvedException() instanceof HttpRequestMethodNotSupportedException))
                .andReturn();
    }

    @Test
    void readUserRoomJoinByRoomCode_WrongHttpMethod_InValidController() throws Exception {
        // init
        String roomCode = "test_roomCode";

        String content = objectMapper.writeValueAsString(roomCode);

        // given
        given(roomService.readUserRoomJoinByRoomId(any(Long.class)))
                .willThrow(new IllegalArgumentException());

        // when - then
        mockMvc.perform(post("/room/join-info")
                        .characterEncoding("UTF-8")
                        .content(content)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .requestAttr("userId", 1L))
                .andExpect(result -> Assertions.assertTrue(result.getResolvedException() instanceof HttpRequestMethodNotSupportedException))
                .andReturn();
    }
    //
    @Test
    void createUserRoomJoin() throws Exception {
        // init
        CreateUserRoomJoinInDTO createUserRoomJoinInDTO = CreateUserRoomJoinInDTO.builder()
                .roomId(1)
                .userId(1)
                .roomName("test_roomName")
                .roomPassword("test_roomPassword")
                .userName("test_userName")
                .build();

        String content = objectMapper.writeValueAsString(createUserRoomJoinInDTO);

        // given
        given(userRoomService.createUserRoomJoin(any()))
                .willReturn(CreateUserRoomJoinOutDTO.builder()
                        .userRoomId(1)
                        .userRoomRole(UserRoomRole.ROLE1)
                        .userName("test_userName")
                        .roomName("test_roomName")
                        .safeAlarm(BoolAlarm.Y)
                        .moveAlarm(BoolAlarm.Y)
                        .dayAlarm(BoolAlarm.Y)
                        .createAt(new Date())
                        .build());

        given(roomService.comparePasswordByRoomCode(any(Long.class), any(String.class)))
                .willReturn(true);

        // when - then
        mockMvc.perform(post("/room/join")
                        .characterEncoding("UTF-8")
                        .content(content)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .requestAttr("userId", 1L))
                .andExpect(status().isOk())
                .andReturn();
    }

    @Test
    void createUserRoomJoin_InValidController() throws Exception {
        // init
        CreateUserRoomJoinInDTO createUserRoomJoinInDTO = CreateUserRoomJoinInDTO.builder()
                .roomId(1)
                .userId(1)
                .roomName("test_roomName")
                .roomPassword("test_roomPassword")
                .userName("test_userName")
                .build();

        String content = objectMapper.writeValueAsString(createUserRoomJoinInDTO);

        // given
        given(userRoomService.createUserRoomJoin(any()))
                .willThrow(new IllegalArgumentException());

        // when - then
        mockMvc.perform(post("/room/join")
                        .characterEncoding("UTF-8")
                        .content(content)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .requestAttr("userId", 1L))
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof IllegalArgumentException))
                .andReturn();
    }

    @Test
    void createUserRoomJoin_WrongHttpMethod() throws Exception {
        // init
        CreateUserRoomJoinInDTO createUserRoomJoinInDTO = CreateUserRoomJoinInDTO.builder()
                .roomId(1)
                .userId(1)
                .roomName("test_roomName")
                .roomPassword("test_roomPassword")
                .userName("test_userName")
                .build();

        String content = objectMapper.writeValueAsString(createUserRoomJoinInDTO);

        // given
        given(userRoomService.createUserRoomJoin(any()))
                .willReturn(CreateUserRoomJoinOutDTO.builder()
                        .userRoomId(1)
                        .userRoomRole(UserRoomRole.ROLE1)
                        .userName("test_userName")
                        .roomName("test_roomName")
                        .safeAlarm(BoolAlarm.Y)
                        .moveAlarm(BoolAlarm.Y)
                        .dayAlarm(BoolAlarm.Y)
                        .createAt(new Date())
                        .build());

        // when - then
        mockMvc.perform(put("/room/join")
                        .characterEncoding("UTF-8")
                        .content(content)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .requestAttr("userId", 1L))
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof HttpRequestMethodNotSupportedException))
                .andReturn();
    }

    @Test
    void createUserRoomJoin_WrongHttpMethod_InValidController() throws Exception {
        // init
        CreateUserRoomJoinInDTO createUserRoomJoinInDTO = CreateUserRoomJoinInDTO.builder()
                .roomId(1)
                .userId(1)
                .roomName("test_roomName")
                .roomPassword("test_roomPassword")
                .userName("test_userName")
                .build();

        String content = objectMapper.writeValueAsString(createUserRoomJoinInDTO);

        // given
        given(userRoomService.createUserRoomJoin(any()))
                .willThrow(new IllegalArgumentException());

        // when - then
        mockMvc.perform(put("/room/join")
                        .characterEncoding("UTF-8")
                        .content(content)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .requestAttr("userId", 1L))
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof HttpRequestMethodNotSupportedException))
                .andReturn();
    }

    @Test
    void compareFeedByUserIdAndRoomId() throws Exception {
        // init
        CompareFeedByRoomIdOutDTO compareFeedByRoomIdOutDTO = CompareFeedByRoomIdOutDTO.builder()
                .userId(1)
                .isSubmit(true)
                .build();

        String content = objectMapper.writeValueAsString(compareFeedByRoomIdOutDTO);

        // given
        given(feedService.compareFeedByRoomIdAndDate(any(Long.class), any(Date.class)))
                .willReturn(new ArrayList<>());

        // when - then
        mockMvc.perform(get("/room/feed-status?roomId=1&date=2023-05-08")
                        .requestAttr("userId", 1L))
                .andExpect(status().isOk())
                .andReturn();
    }

    @Test
    void compareFeedByUserIdAndRoomId_InValidController() throws Exception{
        // init
        CompareFeedByRoomIdOutDTO compareFeedByRoomIdOutDTO = CompareFeedByRoomIdOutDTO.builder()
                .userId(1)
                .isSubmit(true)
                .build();

        String content = objectMapper.writeValueAsString(compareFeedByRoomIdOutDTO);

        // given
        given(feedService.compareFeedByRoomIdAndDate(any(Long.class), any(Date.class)))
                .willThrow(new IllegalArgumentException());

        // when - then
        mockMvc.perform(get("/room/feed-status?roomId=1&date=2023-05-08")
                        .requestAttr("userId", 1L))
                .andExpect(result -> Assertions.assertTrue(result.getResolvedException() instanceof IllegalArgumentException));
    }

    @Test
    void compareFeedByUserIdAndRoomId_WrongHttpMethod() throws Exception{
        // init
        CompareFeedByRoomIdOutDTO compareFeedByRoomIdOutDTO = CompareFeedByRoomIdOutDTO.builder()
                .userId(1)
                .isSubmit(true)
                .build();

        String content = objectMapper.writeValueAsString(compareFeedByRoomIdOutDTO);

        // given
        given(feedService.compareFeedByRoomIdAndDate(any(Long.class), any(Date.class)))
                .willReturn(new ArrayList<>());

        // when - then
        mockMvc.perform(post("/room/feed-status")
                        .characterEncoding("UTF-8")
                        .content(content)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .requestAttr("userId", 1L))
                .andExpect(result -> Assertions.assertTrue(result.getResolvedException() instanceof HttpRequestMethodNotSupportedException))
                .andReturn();
    }

    @Test
    void compareFeedByUserIdAndRoomId_WrongHttpMethod_InValidController()throws Exception{
        // init
        CompareFeedByRoomIdOutDTO compareFeedByRoomIdOutDTO = CompareFeedByRoomIdOutDTO.builder()
                .userId(1)
                .isSubmit(true)
                .build();

        String content = objectMapper.writeValueAsString(compareFeedByRoomIdOutDTO);

        // given
        given(feedService.compareFeedByRoomIdAndDate(any(Long.class), any(Date.class)))
                .willThrow(new IllegalArgumentException());

        // when - then
        mockMvc.perform(post("/room/feed-status")
                        .characterEncoding("UTF-8")
                        .content(content)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .requestAttr("userId", 1L))
                .andExpect(result -> Assertions.assertTrue(result.getResolvedException() instanceof HttpRequestMethodNotSupportedException))
                .andReturn();
    }

    @Test
    void createFeed() throws Exception{
        /*
        // init
        CreateFeedInDTO createFeedInDTO = CreateFeedInDTO.builder()
                .feedUrl("test_feedUrl")
                .feedType(FeedType.FEED1)
                .feedFile(null)
                .content("test_content")
                .userRoomId(1)
                .build();

        String content = objectMapper.writeValueAsString(createFeedInDTO);

        // given
        given(feedService.createFeed(any()))
                .willReturn(CreateFeedOutDTO.builder().build());

        // when - then
        mockMvc.perform(post("/room/feed")
                        .characterEncoding("UTF-8")
                        .content(content)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .requestAttr("userId", 1L))
                .andExpect(status().isOk())
                .andReturn();

         */
    }

    @Test
    void createFeed_InValidController() throws Exception{
        /*
        // init
        CreateFeedInDTO createFeedInDTO = CreateFeedInDTO.builder()
                .feedUrl("test_feedUrl")
                .feedType(FeedType.FEED1)
                .feedFile(null)
                .content("test_content")
                .userRoomId(1)
                .build();

        String content = objectMapper.writeValueAsString(createFeedInDTO);

        // given
        given(feedService.createFeed(any()))
                .willThrow(new IllegalArgumentException());

        // when - then
        mockMvc.perform(post("/room/feed")
                        .characterEncoding("UTF-8")
                        .content(content)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .requestAttr("userId", 1L))
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof IllegalArgumentException))
                .andReturn();
         */
    }

    @Test
    void createFeed_WrongHttpMethod() throws Exception{
        /*
        // init
        CreateFeedInDTO createFeedInDTO = CreateFeedInDTO.builder()
                .feedUrl("test_feedUrl")
                .feedType(FeedType.FEED1)
                .feedFile(null)
                .content("test_content")
                .userRoomId(1)
                .build();

        String content = objectMapper.writeValueAsString(createFeedInDTO);

        // given
        given(feedService.createFeed(any()))
                .willReturn(CreateFeedOutDTO.builder().build());

        // when - then
        mockMvc.perform(post("/room/feed")
                        .characterEncoding("UTF-8")
                        .content(content)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .requestAttr("userId", 1L))
                .andExpect(result -> Assertions.assertTrue(result.getResolvedException() instanceof HttpRequestMethodNotSupportedException))
                .andReturn();

         */
    }

    @Test
    void createFeed_WrongHttpMethod_InValidController() throws Exception{
        /*
        // init

        CreateFeedInDTO createFeedInDTO = CreateFeedInDTO.builder()
                .feedUrl("test_feedUrl")
                .feedType(FeedType.FEED1)
                .feedFile(null)
                .content("test_content")
                .userRoomId(1)
                .build();

        String content = objectMapper.writeValueAsString(createFeedInDTO);

        // given
        given(feedService.createFeed(any()))
                .willThrow(new IllegalArgumentException());

        // when - then
        mockMvc.perform(post("/room/feed")
                        .characterEncoding("UTF-8")
                        .content(content)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .requestAttr("userId", 1L))
                .andExpect(result -> Assertions.assertTrue(result.getResolvedException() instanceof HttpRequestMethodNotSupportedException))
                .andReturn();

         */
    }

    @Test
    void readRoomCodeByRoomId() throws Exception{
        // init
        long roomId = 1;

        String content = objectMapper.writeValueAsString(roomId);

        // given
        given(roomService.readRoomCodeByRoomId(any(Long.class)))
                .willReturn(new ReadRoomCodeOutDTO("roomCode"));

        // when - then
        mockMvc.perform(get("/room/code?roomId=1")
                        .requestAttr("userId", 1L))
                .andExpect(status().isOk())
                .andReturn();
    }

    @Test
    void readRoomCodeByRoomId_InValidController() throws Exception{
        // init
        long roomId = 1;

        String content = objectMapper.writeValueAsString(roomId);

        // given
        given(roomService.readRoomCodeByRoomId(any(Long.class)))
                .willThrow(new IllegalArgumentException());

        // when - then
        mockMvc.perform(get("/room/code?roomId=1")
                        .requestAttr("userId", 1L))
                .andExpect(result -> Assertions.assertTrue(result.getResolvedException() instanceof IllegalArgumentException))
                .andReturn();
    }

    @Test
    void readRoomCodeByRoomId_WrongHttpMethod() throws Exception{
        // init
        long roomId = 1;

        String content = objectMapper.writeValueAsString(roomId);

        // given
        given(roomService.readRoomCodeByRoomId(any(Long.class)))
                .willReturn(new ReadRoomCodeOutDTO("roomCode"));

        // when - then
        mockMvc.perform(put("/room/code")
                        .characterEncoding("UTF-8")
                        .content(content)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .requestAttr("userId", 1L))
                .andExpect(result -> Assertions.assertTrue(result.getResolvedException() instanceof HttpRequestMethodNotSupportedException))
                .andReturn();
    }

    @Test
    void readRoomCodeByRoomId_WrongHttpMethod_InValidController() throws Exception{
        // init
        long roomId = 1;

        String content = objectMapper.writeValueAsString(roomId);

        // given
        given(roomService.readRoomCodeByRoomId(any(Long.class)))
                .willThrow(new IllegalArgumentException());

        // when - then
        mockMvc.perform(put("/room/code")
                        .characterEncoding("UTF-8")
                        .content(content)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .requestAttr("userId", 1L))
                .andExpect(result -> Assertions.assertTrue(result.getResolvedException() instanceof HttpRequestMethodNotSupportedException))
                .andReturn();
    }

    @Test
    void readFeedByRoomIdAndDate() throws Exception{
        // init
        long roomId = 1;


        String content = objectMapper.writeValueAsString(roomId);

        // given
        given(feedService.readFeedByRoomIdAndDate(any(Long.class), any(Date.class)))
                .willReturn(new ArrayList<>());

        // when - then
        mockMvc.perform(get("/room/date-feed?roomId=1&date=2023-05-08")
                        .requestAttr("userId", 1L))
                .andExpect(status().isOk())
                .andReturn();
    }

    @Test
    void readFeedByRoomIdAndDate_InValidController() throws Exception{
        // init
        long roomId = 1;


        // given
        given(feedService.readFeedByRoomIdAndDate(any(Long.class), any(Date.class)))
                .willThrow(new IllegalArgumentException());

        // when - then
        mockMvc.perform(get("/room/date-feed?roomId=1&date=2023-05-08"))
                .andExpect(result -> Assertions.assertTrue(result.getResolvedException() instanceof IllegalArgumentException))
                .andReturn();
    }

    @Test
    void readFeedByRoomIdAndDate_WrongHttpMethod() throws Exception{
        // init
        long roomId = 1;

        String content = objectMapper.writeValueAsString(roomId);

        // given
        given(feedService.readFeedByRoomIdAndDate(any(Long.class), any(Date.class)))
                .willReturn(new ArrayList<>());

        // when - then
        mockMvc.perform(post("/room/date-question")
                        .characterEncoding("UTF-8")
                        .content(content)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .requestAttr("userId", 1L))
                .andExpect(result -> Assertions.assertTrue(result.getResolvedException() instanceof HttpRequestMethodNotSupportedException))
                .andReturn();
    }

    @Test
    void readFeedByRoomIdAndDate_WrongHttpMethod_InValidController() throws Exception{
        // init
        long roomId = 1;

        String content = objectMapper.writeValueAsString(roomId);

        // given
        given(feedService.readFeedByRoomIdAndDate(any(Long.class), any(Date.class)))
                .willThrow(new IllegalArgumentException());

        // when - then
        mockMvc.perform(post("/room/date-question")
                        .characterEncoding("UTF-8")
                        .content(content)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .requestAttr("userId", 1L))
                .andExpect(result -> Assertions.assertTrue(result.getResolvedException() instanceof HttpRequestMethodNotSupportedException))
                .andReturn();
    }

    @Test
    void readQuestionByQuestionId() throws Exception{
        // init
        long questionIdId = 1;

        String content = objectMapper.writeValueAsString(questionIdId);

        // given
        given(roomService.readRoomQuestionByRoomIdAndUserId(any(Long.class), any(Long.class)))
                .willReturn(new ReadRoomQuestionOutDTO(1L, "content"));

        // when - then
        mockMvc.perform(get("/room/question?questionId=1"))
                .andExpect(status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
    }

    @Test
    void readQuestionByQuestionId_InValidController() throws Exception{
        // init
        long questionIdId = 1;

        String content = objectMapper.writeValueAsString(questionIdId);

        // given
        given(roomService.readRoomQuestionByRoomIdAndUserId(any(Long.class), any(Long.class)))
                .willThrow(new IllegalArgumentException());

        // when - then
        mockMvc.perform(get("/room/room-question?roomId={roomId}", 1)
                        .requestAttr("userId", 1L))
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof IllegalArgumentException))
                .andReturn();
    }

    @Test
    void readQuestionByQuestionId_WrongHttpMethod() throws Exception{
        // init
        long questionIdId = 1;

        String content = objectMapper.writeValueAsString(questionIdId);

        // given
        given(roomService.readRoomQuestionByRoomIdAndUserId(any(Long.class), any(Long.class)))
                .willReturn(new ReadRoomQuestionOutDTO(1L, "content"));

        // when - then
        mockMvc.perform(post("/room/question")
                        .characterEncoding("UTF-8")
                        .content(content)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .requestAttr("userId", 1L))
                .andExpect(result -> Assertions.assertTrue(result.getResolvedException() instanceof HttpRequestMethodNotSupportedException))
                .andReturn();
    }

    @Test
    void readQuestionByQuestionId_WrongHttpMethod_InValidController() throws Exception{
        // init
        long questionIdId = 1;

        String content = objectMapper.writeValueAsString(questionIdId);

        // given
        given(roomService.readRoomQuestionByRoomIdAndUserId(any(Long.class), any(Long.class)))
                .willThrow(new IllegalArgumentException());

        // when - then
        mockMvc.perform(post("/room/question")
                        .characterEncoding("UTF-8")
                        .content(content)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .requestAttr("userId", 1L))
                .andExpect(result -> Assertions.assertTrue(result.getResolvedException() instanceof HttpRequestMethodNotSupportedException))
                .andReturn();
    }

    @Test
    void readRoomQuestion() throws Exception{
        // init
        long roomId = 1;

        String content = objectMapper.writeValueAsString(roomId);

        // given
        given(roomService.readRoomQuestionByRoomIdAndUserId(any(Long.class), any(Long.class)))
                .willReturn(ReadRoomQuestionOutDTO.builder()
                        .roomId(1)
                        .roomQuestion("test_roomQuestion")
                        .build());

        // when - then
        mockMvc.perform(get("/room/room-question?roomId=1")
                        .requestAttr("userId", 1L))
                .andExpect(status().isOk())
                .andReturn();
    }

    @Test
    void readRoomQuestion_InValidController() throws Exception {
        // init
        long roomId = 1;

        String content = objectMapper.writeValueAsString(roomId);

        // given
        given(roomService.readRoomQuestionByRoomIdAndUserId(any(Long.class), any(Long.class)))
                .willThrow(new IllegalArgumentException());

        // when - then
        mockMvc.perform(get("/room/room-question?roomId=1")
                        .requestAttr("userId", 1L))
                .andExpect(result -> Assertions.assertTrue(result.getResolvedException() instanceof IllegalArgumentException))
                .andReturn();
    }

    @Test
    void readRoomQuestion_WrongHttpMethod()throws Exception {
        // init
        long roomId = 1;

        String content = objectMapper.writeValueAsString(roomId);

        // given
        given(roomService.readRoomQuestionByRoomIdAndUserId(any(Long.class), any(Long.class)))
                .willReturn(ReadRoomQuestionOutDTO.builder()
                        .roomId(1)
                        .roomQuestion("test_roomQuestion")
                        .build());

        // when - then
        mockMvc.perform(post("/room/room-question")
                        .characterEncoding("UTF-8")
                        .content(content)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .requestAttr("userId", 1L))
                .andExpect(result -> Assertions.assertTrue(result.getResolvedException() instanceof HttpRequestMethodNotSupportedException))
                .andReturn();
    }

    @Test
    void readRoomQuestion_WrongHttpMethod_InValidController() throws Exception{
        // init
        long roomId = 1;

        String content = objectMapper.writeValueAsString(roomId);

        // given
        given(roomService.readRoomQuestionByRoomIdAndUserId(any(Long.class), any(Long.class)))
                .willThrow(new IllegalArgumentException());

        // when - then
        mockMvc.perform(post("/room/room-question")
                        .characterEncoding("UTF-8")
                        .content(content)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .requestAttr("userId", 1L))
                .andExpect(result -> Assertions.assertTrue(result.getResolvedException() instanceof HttpRequestMethodNotSupportedException))
                .andReturn();
    }

    @Test
    void readMemberListByRoomId() throws Exception{
        // init
        long roomId = 1;

        String content = objectMapper.writeValueAsString(roomId);

        // given
        given(roomService.readMemberListByRoomId(any(Long.class), any(Long.class)))
                .willReturn(ReadMemberListOutDTO.builder()
                        .roomId(1)
                        .members(new ArrayList<>())
                        .build());

        // when - then
        mockMvc.perform(get("/room/user-list?roomId=1")
                        .requestAttr("userId", 1L))
                .andExpect(status().isOk())
                .andReturn();
    }

    @Test
    void readMemberListByRoomId_InValidController() throws Exception{
        // init
        long roomId = 1;

        String content = objectMapper.writeValueAsString(roomId);

        // given
        given(roomService.readMemberListByRoomId(any(Long.class), any(Long.class)))
                .willThrow(new IllegalArgumentException());

        // when - then
        mockMvc.perform(get("/room/user-list?roomId=1")
                        .requestAttr("userId", 1L))
                .andExpect(result -> Assertions.assertTrue(result.getResolvedException() instanceof IllegalArgumentException))
                .andReturn();
    }

    @Test
    void readMemberListByRoomId_WrongHttpMethod() throws Exception{
        // init
        long roomId = 1;

        String content = objectMapper.writeValueAsString(roomId);

        // given
        given(roomService.readMemberListByRoomId(any(Long.class), any(Long.class)))
                .willReturn(ReadMemberListOutDTO.builder()
                        .roomId(1)
                        .members(new ArrayList<>())
                        .build());

        // when - then
        mockMvc.perform(post("/room/user-list")
                        .characterEncoding("UTF-8")
                        .content(content)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .requestAttr("userId", 1L))
                .andExpect(result -> Assertions.assertTrue(result.getResolvedException() instanceof HttpRequestMethodNotSupportedException))
                .andReturn();
    }

    @Test
    void readMemberListByRoomId_WrongHttpMethod_InValidController() throws Exception{
        // init
        long roomId = 1;

        String content = objectMapper.writeValueAsString(roomId);

        // given
        given(roomService.readMemberListByRoomId(any(Long.class), any(Long.class)))
                .willThrow(new IllegalArgumentException());

        // when - then
        mockMvc.perform(post("/room/user-list")
                        .characterEncoding("UTF-8")
                        .content(content)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .requestAttr("userId", 1L))
                .andExpect(result -> Assertions.assertTrue(result.getResolvedException() instanceof HttpRequestMethodNotSupportedException))
                .andReturn();
    }
}