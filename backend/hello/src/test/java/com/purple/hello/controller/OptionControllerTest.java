package com.purple.hello.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.purple.hello.dto.in.*;
import com.purple.hello.enu.BoolAlarm;
import com.purple.hello.repo.UserRoomRepo;
import com.purple.hello.service.impl.*;
import org.junit.jupiter.api.*;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.HttpRequestMethodNotSupportedException;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(OptionController.class)
class OptionControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    RoomServiceImpl roomService;
    @MockBean
    UserRoomServiceImpl userRoomService;
    @MockBean
    AlarmServiceImpl alarmService;
    @MockBean
    FeedServiceImpl feedService;
    @MockBean
    QuestionServiceImpl questionService;
    @MockBean
    UserServiceImpl userService;
    @MockBean
    HistoryServiceImpl historyService;
    @MockBean
    NotificationServiceImpl notificationService;

    @BeforeEach
    void setUp() {

    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void updateRoomName() throws Exception {
        // init
        UpdateRoomNameInDTO updateRoomNameInDTO = UpdateRoomNameInDTO.builder()
                .userId(1)
                .userRoomId(1)
                .roomName("roomName")
                .build();

        String content = objectMapper.writeValueAsString(updateRoomNameInDTO);

        // given
        given(userRoomService.updateRoomName(any()))
                .willReturn(true);

        // when - then
        mockMvc.perform(put("/room/name")
                        .characterEncoding("UTF-8")
                        .content(content)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .requestAttr("userId", updateRoomNameInDTO.getUserId()))
                .andExpect(status().isOk())
                .andReturn();
    }

    @Test
    void updateRoomName_WrongHttpMethod() throws Exception {
        // init
        UpdateRoomNameInDTO updateRoomNameInDTO = UpdateRoomNameInDTO.builder()
                .userId(1)
                .userRoomId(1)
                .roomName("roomName")
                .build();

        String content = objectMapper.writeValueAsString(updateRoomNameInDTO);

        // given
        given(userRoomService.updateRoomName(any()))
                .willReturn(true);

        // when - then
        mockMvc.perform(get("/room/name")
                        .requestAttr("userId", updateRoomNameInDTO.getUserId()))
                .andExpect(result -> Assertions.assertTrue(result.getResolvedException() instanceof HttpRequestMethodNotSupportedException))
                .andReturn();
    }

    @Test
    void updateRoomName_InValidController() throws Exception {
        // init
        UpdateRoomNameInDTO updateRoomNameInDTO = UpdateRoomNameInDTO.builder()
                .userId(1)
                .userRoomId(1)
                .roomName("roomName")
                .build();

        String content = objectMapper.writeValueAsString(updateRoomNameInDTO);

        // given
        given(userRoomService.updateRoomName(any()))
                .willThrow(new IllegalArgumentException());

        // when - then
        mockMvc.perform(put("/room/name")
                        .characterEncoding("UTF-8")
                        .content(content)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .requestAttr("userId", updateRoomNameInDTO.getUserId()))
                .andExpect(result -> Assertions.assertTrue(result.getResolvedException() instanceof IllegalArgumentException))
                .andReturn();
    }

    @Test
    void updateRoomName_WrongHttpMethod_InValidController() throws Exception {
        // init
        UpdateRoomNameInDTO updateRoomNameInDTO = UpdateRoomNameInDTO.builder()
                .userId(1)
                .userRoomId(1)
                .roomName("roomName")
                .build();

        String content = objectMapper.writeValueAsString(updateRoomNameInDTO);

        // given
        given(userRoomService.updateRoomName(any()))
                .willThrow(new IllegalArgumentException());

        // when - then
        mockMvc.perform(get("/room/name")
                        .requestAttr("userId", updateRoomNameInDTO.getUserId()))
                .andExpect(result -> Assertions.assertTrue(result.getResolvedException() instanceof HttpRequestMethodNotSupportedException))
                .andReturn();
    }

    @Test
    void updateRoomPassword() throws Exception {
        // init
        UpdateRoomPasswordInDTO updateRoomPasswordInDTO = UpdateRoomPasswordInDTO.builder()
                .userId(1)
                .roomId(1)
                .roomQuestion("test_roomQuestion")
                .roomPassword("test_roomPassword")
                .build();

        String content = objectMapper.writeValueAsString(updateRoomPasswordInDTO);

        // given
        given(roomService.updateRoomPassword(any()))
                .willReturn(true);

        // when - then
        mockMvc.perform(put("/room/password")
                        .characterEncoding("UTF-8")
                        .content(content)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .requestAttr("userId", updateRoomPasswordInDTO.getUserId()))
                .andExpect(status().isOk())
                .andReturn();
    }

    @Test
    void updateRoomPassword_WrongHttpMethod() throws Exception {
        // init
        UpdateRoomPasswordInDTO updateRoomPasswordInDTO = UpdateRoomPasswordInDTO.builder()
                .userId(1)
                .roomId(1)
                .roomQuestion("test_roomQuestion")
                .roomPassword("test_roomPassword")
                .build();

        String content = objectMapper.writeValueAsString(updateRoomPasswordInDTO);

        // given
        given(roomService.updateRoomPassword(any()))
                .willReturn(true);

        // when - then
        mockMvc.perform(get("/room/password")
                        .requestAttr("userId", updateRoomPasswordInDTO.getUserId()))
                .andExpect(result -> Assertions.assertTrue(result.getResolvedException() instanceof HttpRequestMethodNotSupportedException))
                .andReturn();
    }

    @Test
    void updateRoomPassword_InValidController() throws Exception {
        // init
        UpdateRoomPasswordInDTO updateRoomPasswordInDTO = UpdateRoomPasswordInDTO.builder()
                .userId(1)
                .roomId(1)
                .roomQuestion("test_roomQuestion")
                .roomPassword("test_roomPassword")
                .build();

        String content = objectMapper.writeValueAsString(updateRoomPasswordInDTO);

        // given
        given(roomService.updateRoomPassword(any()))
                .willThrow(new IllegalArgumentException());

        // when - then
        mockMvc.perform(put("/room/password")
                        .characterEncoding("UTF-8")
                        .content(content)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .requestAttr("userId", updateRoomPasswordInDTO.getUserId()))
                .andExpect(result -> Assertions.assertTrue(result.getResolvedException() instanceof IllegalArgumentException))
                .andReturn();
    }

    @Test
    void updateRoomPassword_WrongHttpMethod_InValidController() throws Exception {
        // init
        UpdateRoomPasswordInDTO updateRoomPasswordInDTO = UpdateRoomPasswordInDTO.builder()
                .userId(1)
                .roomId(1)
                .roomQuestion("test_roomQuestion")
                .roomPassword("test_roomPassword")
                .build();

        String content = objectMapper.writeValueAsString(updateRoomPasswordInDTO);

        // given
        given(roomService.updateRoomPassword(any()))
                .willThrow(new IllegalArgumentException());

        // when - then
        mockMvc.perform(get("/room/password")
                        .requestAttr("userId", updateRoomPasswordInDTO.getUserId()))
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof HttpRequestMethodNotSupportedException))
                .andReturn();
    }

    @Test
    void updateMoveAlarm() throws Exception {
        // init
        UpdateMoveAlarmInDTO updateMoveAlarmInDTO = UpdateMoveAlarmInDTO.builder()
                .userId(1)
                .userRoomId(1)
                .moveAlarm(BoolAlarm.Y)
                .build();

        String content = objectMapper.writeValueAsString(updateMoveAlarmInDTO);

        // given
        given(userRoomService.updateMoveAlarm(any()))
                .willReturn(true);

        // when - then
        mockMvc.perform(put("/room/move-alarm")
                        .characterEncoding("UTF-8")
                        .content(content)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .requestAttr("userId", updateMoveAlarmInDTO.getUserId()))
                .andExpect(status().isOk())
                .andReturn();
    }

    @Test
    void updateMoveAlarm_WrongHttpMethod() throws Exception {
        // init
        UpdateMoveAlarmInDTO updateMoveAlarmInDTO = UpdateMoveAlarmInDTO.builder()
                .userId(1)
                .userRoomId(1)
                .moveAlarm(BoolAlarm.Y)
                .build();

        String content = objectMapper.writeValueAsString(updateMoveAlarmInDTO);

        // given
        given(userRoomService.updateMoveAlarm(any()))
                .willReturn(true);

        // when - then
        mockMvc.perform(get("/room/move-alarm")
                        .requestAttr("userId", updateMoveAlarmInDTO.getUserId()))
                .andExpect(result -> Assertions.assertTrue(result.getResolvedException() instanceof HttpRequestMethodNotSupportedException))
                .andReturn();
    }

    @Test
    void updateMoveAlarm_InValidController() throws Exception {
        // init
        UpdateMoveAlarmInDTO updateMoveAlarmInDTO = UpdateMoveAlarmInDTO.builder()
                .userId(1)
                .userRoomId(1)
                .moveAlarm(BoolAlarm.Y)
                .build();

        String content = objectMapper.writeValueAsString(updateMoveAlarmInDTO);

        // given
        given(userRoomService.updateMoveAlarm(any()))
                .willThrow(new IllegalArgumentException());

        // when - then
        mockMvc.perform(put("/room/move-alarm")
                        .characterEncoding("UTF-8")
                        .content(content)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .requestAttr("userId", updateMoveAlarmInDTO.getUserId()))
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof IllegalArgumentException))
                .andReturn();
    }

    @Test
    void updateMoveAlarm_WrongHttpMethod_InValidController() throws Exception {
        // init
        UpdateMoveAlarmInDTO updateMoveAlarmInDTO = UpdateMoveAlarmInDTO.builder()
                .userId(1)
                .userRoomId(1)
                .moveAlarm(BoolAlarm.Y)
                .build();

        String content = objectMapper.writeValueAsString(updateMoveAlarmInDTO);

        // given
        given(userRoomService.updateMoveAlarm(any()))
                .willThrow(new IllegalArgumentException());

        // when - then
        mockMvc.perform(get("/room/move-alarm")
                        .requestAttr("userId", updateMoveAlarmInDTO.getUserId()))
                .andExpect(result -> Assertions.assertTrue(result.getResolvedException() instanceof HttpRequestMethodNotSupportedException))
                .andReturn();
    }

    //
    @Test
    void updateSafeAlarm() throws Exception {
        // init
        UpdateSafeAlarmInDTO updateSafeAlarmInDTO = UpdateSafeAlarmInDTO.builder()
                .userId(1)
                .roomId(1)
                .safeAlarm(BoolAlarm.Y)
                .build();

        String content = objectMapper.writeValueAsString(updateSafeAlarmInDTO);

        // given
        given(userRoomService.updateSafeAlarm(any()))
                .willReturn(true);

        // when - then
        mockMvc.perform(put("/room/safe-alarm")
                        .characterEncoding("UTF-8")
                        .content(content)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .requestAttr("userId", updateSafeAlarmInDTO.getUserId()))
                .andExpect(status().isOk())
                .andReturn();
    }

    @Test
    void updateSafeAlarm_WrongHttpMethod() throws Exception {
        // init
        UpdateSafeAlarmInDTO updateSafeAlarmInDTO = UpdateSafeAlarmInDTO.builder()
                .userId(1)
                .roomId(1)
                .safeAlarm(BoolAlarm.Y)
                .build();

        String content = objectMapper.writeValueAsString(updateSafeAlarmInDTO);

        // given
        given(userRoomService.updateSafeAlarm(any()))
                .willReturn(true);

        // when - then
        mockMvc.perform(get("/room/safe-alarm")
                        .requestAttr("userId", updateSafeAlarmInDTO.getUserId()))
                .andExpect(result -> Assertions.assertTrue(result.getResolvedException() instanceof HttpRequestMethodNotSupportedException))
                .andReturn();
    }

    @Test
    void updateSafeAlarm_InValidController() throws Exception {
        // init
        UpdateSafeAlarmInDTO updateSafeAlarmInDTO = UpdateSafeAlarmInDTO.builder()
                .userId(1)
                .roomId(1)
                .safeAlarm(BoolAlarm.Y)
                .build();

        String content = objectMapper.writeValueAsString(updateSafeAlarmInDTO);

        // given
        given(userRoomService.updateSafeAlarm(any()))
                .willThrow(new IllegalArgumentException());

        // when - then
        mockMvc.perform(put("/room/safe-alarm")
                        .characterEncoding("UTF-8")
                        .content(content)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .requestAttr("userId", updateSafeAlarmInDTO.getUserId()))
                .andExpect(result -> Assertions.assertTrue(result.getResolvedException() instanceof IllegalArgumentException))
                .andReturn();
    }

    @Test
    void updateSafeAlarm_WrongHttpMethod_InValidController() throws Exception {
        // init
        UpdateSafeAlarmInDTO updateSafeAlarmInDTO = UpdateSafeAlarmInDTO.builder()
                .userId(1)
                .roomId(1)
                .safeAlarm(BoolAlarm.Y)
                .build();

        String content = objectMapper.writeValueAsString(updateSafeAlarmInDTO);

        // given
        given(userRoomService.updateSafeAlarm(any()))
                .willThrow(new IllegalArgumentException());

        // when - then
        mockMvc.perform(get("/room/safe-alarm")
                        .requestAttr("userId", updateSafeAlarmInDTO.getUserId()))
                .andExpect(result -> Assertions.assertTrue(result.getResolvedException() instanceof HttpRequestMethodNotSupportedException))
                .andReturn();
    }
    //
    @Test
    void deleteRoom() throws Exception {
        // init
        DeleteRoomInDTO deleteRoomInDTO = DeleteRoomInDTO.builder()
                .roomId(1)
                .userId(1)
                .build();

        String content = objectMapper.writeValueAsString(deleteRoomInDTO);

        // given
        given(roomService.deleteRoom(any()))
                .willReturn(true);

        // when - then
        mockMvc.perform(delete("/room")
                        .characterEncoding("UTF-8")
                        .content(content)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .requestAttr("userId", deleteRoomInDTO.getUserId()))
                .andExpect(status().isOk())
                .andReturn();
    }

    @Test
    void deleteRoom_WrongHttpMethod() throws Exception {
        // init
        DeleteRoomInDTO deleteRoomInDTO = DeleteRoomInDTO.builder()
                .roomId(1)
                .userId(1)
                .build();

        String content = objectMapper.writeValueAsString(deleteRoomInDTO);

        // given
        given(roomService.deleteRoom(any()))
                .willReturn(true);

        // when - then
        mockMvc.perform(get("/room")
                        .requestAttr("userId", deleteRoomInDTO.getUserId()))
                .andExpect(result -> Assertions.assertTrue(result.getResolvedException() instanceof HttpRequestMethodNotSupportedException))
                .andReturn();
    }

    @Test
    void deleteRoom_InValidController() throws Exception {
        // init
        DeleteRoomInDTO deleteRoomInDTO = DeleteRoomInDTO.builder()
                .roomId(1)
                .userId(1)
                .build();

        String content = objectMapper.writeValueAsString(deleteRoomInDTO);

        // given
        given(roomService.deleteRoom(any()))
                .willThrow(new IllegalArgumentException());

        // when - then
        mockMvc.perform(delete("/room")
                        .characterEncoding("UTF-8")
                        .content(content)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .requestAttr("userId", deleteRoomInDTO.getUserId()))
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof IllegalArgumentException))
                .andReturn();
    }

    @Test
    void deleteRoom_WrongHttpMethod_InValidController() throws Exception {
        // init
        DeleteRoomInDTO deleteRoomInDTO = DeleteRoomInDTO.builder()
                .roomId(1)
                .userId(1)
                .build();

        String content = objectMapper.writeValueAsString(deleteRoomInDTO);

        // given
        given(roomService.deleteRoom(any()))
                .willThrow(new IllegalArgumentException());

        // when - then
        mockMvc.perform(get("/room")
                        .requestAttr("userId", deleteRoomInDTO.getUserId()))
                .andExpect(result -> Assertions.assertTrue(result.getResolvedException() instanceof HttpRequestMethodNotSupportedException))
                .andReturn();
    }
    //
    @Test
    void deleteUserRoom() throws Exception {
        // init
        DeleteUserRoomInDTO deleteUserRoomInDTO = DeleteUserRoomInDTO.builder()
                .userId(1)
                .userRoomId(1)
                .build();

        String content = objectMapper.writeValueAsString(deleteUserRoomInDTO);

        // given
        given(userRoomService.deleteUserRoom(any()))
                .willReturn(true);

        // when - then
        mockMvc.perform(put("/room/exit")
                        .characterEncoding("UTF-8")
                        .content(content)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .requestAttr("userId", deleteUserRoomInDTO.getUserId()))
                .andExpect(status().isOk())
                .andReturn();
    }

    @Test
    void deleteUserRoom_WrongHttpMethod() throws Exception {
        // init
        DeleteUserRoomInDTO deleteUserRoomInDTO = DeleteUserRoomInDTO.builder()
                .userId(1)
                .userRoomId(1)
                .build();

        String content = objectMapper.writeValueAsString(deleteUserRoomInDTO);

        // given
        given(userRoomService.deleteUserRoom(any()))
                .willReturn(true);

        // when - then
        mockMvc.perform(get("/room/exit")
                        .requestAttr("userId", deleteUserRoomInDTO.getUserId()))
                .andExpect(result -> Assertions.assertTrue(result.getResolvedException() instanceof HttpRequestMethodNotSupportedException))
                .andReturn();
    }

    @Test
    void deleteUserRoom_InValidController() throws Exception {
        // init
        DeleteUserRoomInDTO deleteUserRoomInDTO = DeleteUserRoomInDTO.builder()
                .userId(1)
                .userRoomId(1)
                .build();

        String content = objectMapper.writeValueAsString(deleteUserRoomInDTO);

        // given
        given(userRoomService.deleteUserRoom(any()))
                .willThrow(new IllegalArgumentException());

        // when - then
        mockMvc.perform(put("/room/exit")
                        .characterEncoding("UTF-8")
                        .content(content)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .requestAttr("userId", deleteUserRoomInDTO.getUserId()))
                .andExpect(result -> Assertions.assertTrue(result.getResolvedException() instanceof IllegalArgumentException))
                .andReturn();
    }

    @Test
    void deleteUserRoom_WrongHttpMethod_InValidController() throws Exception {
        // init
        DeleteUserRoomInDTO deleteUserRoomInDTO = DeleteUserRoomInDTO.builder()
                .userId(1)
                .userRoomId(1)
                .build();

        String content = objectMapper.writeValueAsString(deleteUserRoomInDTO);

        // given
        given(userRoomService.deleteUserRoom(any()))
                .willThrow(new IllegalArgumentException());

        // when - then
        mockMvc.perform(get("/room/exit")
                        .requestAttr("userId", deleteUserRoomInDTO.getUserId()))
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof HttpRequestMethodNotSupportedException))
                .andReturn();
    }
}