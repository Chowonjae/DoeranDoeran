package com.purple.hello.service;

import com.purple.hello.dao.HistoryDAO;
import com.purple.hello.dao.RoomDAO;
import com.purple.hello.dao.UserDAO;
import com.purple.hello.dao.UserRoomDAO;
import com.purple.hello.dao.impl.*;
import com.purple.hello.dto.in.CreateUserRoomInDTO;
import com.purple.hello.dto.in.DeleteRoomInDTO;
import com.purple.hello.dto.in.UpdateRoomPasswordInDTO;
import com.purple.hello.dto.out.*;
import com.purple.hello.dto.tool.CreateRoomDTO;
import com.purple.hello.dto.tool.MemberDTO;
import com.purple.hello.encoder.PasswordEncoder;
import com.purple.hello.enu.UserRoomRole;
import com.purple.hello.repo.HistoryRepo;
import com.purple.hello.repo.QuestionRepo;
import com.purple.hello.repo.RoomRepo;
import com.purple.hello.service.impl.AwsS3ServiceImpl;
import com.purple.hello.service.impl.RoomServiceImpl;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.python.util.PythonInterpreter;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;

class RoomServiceTest {
    static private RoomServiceImpl roomService;
    static private UserDAOImpl userDAO;
    static private UserRoomDAOImpl userRoomDAO;
    static private RoomDAOImpl roomDAO;
    static private RoomDAOImpl realRoomDAO;
    static private HistoryDAOImpl historyDAO;
    static private QuestionDAOImpl questionDAO;
    static private QuestionRepo questionRepo;
    static private HistoryRepo historyRepo;
    static private RoomRepo roomRepo;
    static private AwsS3ServiceImpl awsS3Service;
    static private PasswordEncoder passwordEncoder;
    static private PythonInterpreter interpreter;
    @BeforeAll
    static void beforeAll(){
    }
    @BeforeEach
    void setUp() {
        roomDAO = Mockito.mock(RoomDAOImpl.class);
        historyDAO = Mockito.mock(HistoryDAOImpl.class);
        questionDAO = Mockito.mock(QuestionDAOImpl.class);
        questionRepo = Mockito.mock(QuestionRepo.class);
        awsS3Service = Mockito.mock(AwsS3ServiceImpl.class);
        passwordEncoder = Mockito.mock(PasswordEncoder.class);
        interpreter = Mockito.mock(PythonInterpreter.class);
        historyRepo = Mockito.mock(HistoryRepo.class);
        roomRepo = Mockito.mock(RoomRepo.class);

        roomService = new RoomServiceImpl(roomDAO, passwordEncoder, historyDAO, questionRepo,
                awsS3Service, userDAO, userRoomDAO, historyRepo, roomRepo);
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void readRoomByUserId() throws Exception{
        List<ReadRoomOutDTO> initReadRoomOutDTOs = new ArrayList<>();

        for (int i = 0; i < 4; i++){
            initReadRoomOutDTOs.add(ReadRoomOutDTO.builder()
                            .userRoomId(1)
                            .roomId(i)
                            .roomName("test_roomName")
                            .members(new ArrayList<>())
                        .build());
        }

        // given
        Mockito.when(roomDAO.readRoomByUserId(any(Long.class)))
                .thenReturn(initReadRoomOutDTOs);

        // when
        List<ReadRoomOutDTO> whenReadRoomOutDTOs = roomService.readRoomByUserId(1);

        // then
        for (int i = 0; i < 4; i++){
            assertEquals(initReadRoomOutDTOs.get(i).getRoomId(), whenReadRoomOutDTOs.get(i).getRoomId());
            assertEquals(initReadRoomOutDTOs.get(i).getUserRoomId(), whenReadRoomOutDTOs.get(i).getUserRoomId());
            assertEquals(initReadRoomOutDTOs.get(i).getRoomName(), whenReadRoomOutDTOs.get(i).getRoomName());
            assertEquals(initReadRoomOutDTOs.get(i).getMembers().size(), whenReadRoomOutDTOs.get(i).getMembers().size());

            int size = initReadRoomOutDTOs.get(i).getMembers().size();

            for (int j = 0; j < size; j++){
                assertEquals(initReadRoomOutDTOs.get(i).getMembers().get(j)
                        , whenReadRoomOutDTOs.get(i).getMembers().get(j));
            }
        }
    }

    @Test
    void readRoomByUserId_InvalidService() throws Exception{
        // given
        Mockito.when(roomDAO.readRoomByUserId(any(Long.class)))
                .thenThrow(new IllegalArgumentException());

        // when - then
        assertThrows(IllegalArgumentException.class,() -> {
            List<ReadRoomOutDTO> whenReadRoomOutDTOs = roomService.readRoomByUserId(1);
        });
    }


    @Test
    void createRoom() throws Exception{
        // init


        CreateRoomDTO initCreateRoomDTO = CreateRoomDTO.builder()
                .roomId(1)
                .build();

        // given
        Mockito.when(roomDAO.createRoom(any(CreateUserRoomInDTO.class)))
                .thenReturn(CreateRoomDTO.builder()
                        .roomId(initCreateRoomDTO.getRoomId())
                        .build());

        // when
        CreateRoomDTO whenCreateRoomDTO = roomService.createRoom(CreateUserRoomInDTO.builder()
                        .roomPassword("test_roomPassword")
                        .roomQuestion("test_roomQuestion")
                        .userId(1)
                        .roomQuestion("test_roomQuestion")
                        .userName("test_userName")
                .build());

        // then
        assertEquals(whenCreateRoomDTO.getRoomId(), initCreateRoomDTO.getRoomId());
    }

    void createRoom_invalidService() throws Exception{
        // given
        Mockito.when(roomDAO.createRoom(any(CreateUserRoomInDTO.class)))
                .thenThrow(new IllegalArgumentException());

        // when-then
        assertThrows(IllegalArgumentException.class, () ->{
            CreateRoomDTO createRoomDTO = roomService.createRoom(CreateUserRoomInDTO.builder()
                    .roomPassword("test_roomPassword")
                    .roomQuestion("test_roomQuestion")
                    .userId(1)
                    .roomQuestion("test_roomQuestion")
                    .userName("test_userName")
                    .build());
        });
    }

    @Test
    void comparePasswordByRoomCode() throws Exception {
        // init
        long initRoomId = 1;
        String initStoredPassword = "test_StoredPassword";
        String encPassword = "test_encPassword";

        // given
        Mockito.when(roomDAO.comparePasswordByRoomCode(initRoomId))
                .thenReturn(initStoredPassword);

        // when
        boolean whenResult = roomService.comparePasswordByRoomCode(initRoomId, encPassword);

        // then
        assertFalse(whenResult);
    }

    @Test
    void comparePasswordByRoomCode_IsWrong() throws Exception {
        // init
        long initRoomId = 1;
        String initStoredPassword = "test_StoredPassword";

        // given
        Mockito.when(roomDAO.comparePasswordByRoomCode(initRoomId))
                .thenReturn(initStoredPassword);

        Mockito.when(passwordEncoder.matches(any(String.class), any(String.class))).thenReturn(false);

        // when
        boolean whenResult = roomService.comparePasswordByRoomCode(initRoomId, initStoredPassword);

        // then
        assertFalse(whenResult);
    }

    @Test
    void comparePasswordByRoomCode_InvalidService() throws Exception {
        // init
        long initRoomId = 1;
        String initStoredPassword = "test_StoredPassword";

        // given
        Mockito.when(roomDAO.comparePasswordByRoomCode(initRoomId))
                        .thenThrow(new IllegalArgumentException());

        // when - then
        assertThrows(IllegalArgumentException.class, () -> {
            roomService.comparePasswordByRoomCode(initRoomId, initStoredPassword);
        });
    }
    @Test
    void readUserRoomJoinByRoomCode() throws Exception {
        // init
        long initRoomId = 1;

        ReadUserRoomJoinOutDTO initReadUserRoomJoinOutDTO = ReadUserRoomJoinOutDTO.builder()
                .roomQuestion("test_roomQuestion")
                .roomName("test_roomName")
                .roomId(1)
                .build();

        // given
        Mockito.when(roomDAO.readUserRoomJoinByRoomId(any(Long.class)))
                .thenReturn(initReadUserRoomJoinOutDTO);

        // when
        ReadUserRoomJoinOutDTO whenReadUserRoomJoinOutDTO = roomService.readUserRoomJoinByRoomId(initRoomId);

        // then
        assertEquals(whenReadUserRoomJoinOutDTO.getRoomId(), initReadUserRoomJoinOutDTO.getRoomId());
        assertEquals(whenReadUserRoomJoinOutDTO.getRoomQuestion(), initReadUserRoomJoinOutDTO.getRoomQuestion());
        assertEquals(whenReadUserRoomJoinOutDTO.getRoomName(), initReadUserRoomJoinOutDTO.getRoomName());
    }

    @Test
    void readUserRoomJoinByRoomCode_InvalidService() throws Exception {
        // init
        long initRoomId = 1;
        // given
        Mockito.when(roomDAO.readUserRoomJoinByRoomId(any(Long.class)))
                .thenThrow(new IllegalArgumentException());

        // when - then
        assertThrows(IllegalArgumentException.class, () -> {
            roomService.readUserRoomJoinByRoomId(initRoomId);
        });
    }

    @Test
    void updateRoomPassword()throws Exception {
        // init

        // given
        Mockito.when(roomDAO.updateRoomPassword(any(UpdateRoomPasswordInDTO.class)))
                .thenReturn(true);

        // when
        boolean whenResult = roomService.updateRoomPassword(UpdateRoomPasswordInDTO.builder()
                        .roomPassword("test_roomPassword")
                        .roomId(1)
                        .roomQuestion("test_roomQuestion")
                        .userId(1)
                .build());

        // then
        assertTrue(whenResult);
    }

    @Test
    void updateRoomPassword_invalidService()throws Exception {
        // init

        // given
        Mockito.when(roomDAO.updateRoomPassword(any(UpdateRoomPasswordInDTO.class)))
                .thenThrow(new IllegalArgumentException());

        // when - then

        assertThrows(IllegalArgumentException.class, () -> {
            boolean whenResult = roomService.updateRoomPassword(UpdateRoomPasswordInDTO.builder()
                    .roomPassword("test_roomPassword")
                    .roomId(1)
                    .roomQuestion("test_roomQuestion")
                    .userId(1)
                    .build());
        });
    }

    @Test
    void readRoomCodeByRoomId()throws Exception {

        // init
        ReadRoomCodeOutDTO initReadRoomCodeOutDTO = new ReadRoomCodeOutDTO("test_url");
        long initRoomId = 1;

        // given
        Mockito.when(roomDAO.readRoomCodeByRoomId(any(Long.class)))
                .thenReturn("test_url");

        // when
        ReadRoomCodeOutDTO whenReadRoomCodeOutDTO = roomService.readRoomCodeByRoomId(initRoomId);

        // then
        assertEquals(initReadRoomCodeOutDTO.getRoomCode(), whenReadRoomCodeOutDTO.getRoomCode());
    }

    @Test
    void deleteRoom() throws Exception {
        // init
        boolean initDeleteRoom = true;

        // given
        Mockito.when(roomDAO.deleteRoom(any(DeleteRoomInDTO.class)))
                .thenReturn(true);

        Mockito.when(awsS3Service.removeDirectory(any(String.class)))
                .thenReturn(true);
        // when
        boolean whenResult = roomService.deleteRoom(DeleteRoomInDTO.builder()
                        .roomId(1)
                        .userId(1)
                        .build());

        // then
        assertTrue(whenResult);
    }

    @Test
    void deleteRoom_InvalidService() throws Exception {
        // init
        boolean initDeleteRoom = true;

        // given
        Mockito.when(roomDAO.deleteRoom(any(DeleteRoomInDTO.class)))
                .thenThrow(new IllegalArgumentException());

        // when - then
        assertThrows(IllegalArgumentException.class, () -> {
            roomService.deleteRoom(DeleteRoomInDTO.builder()
                    .roomId(1)
                    .userId(1)
                    .build());
        });
    }

    @Test
    void deleteRoom_IsDeletedIsTrue_InvalidService() throws Exception {
        // init
        boolean initDeleteRoom = true;

        // given
        Mockito.when(roomDAO.deleteRoom(any(DeleteRoomInDTO.class)))
                        .thenReturn(true);

        Mockito.when(awsS3Service.removeDirectory(any(String.class)))
                .thenThrow(new IllegalArgumentException());

        // when - then
        assertThrows(IllegalArgumentException.class, () -> {
            roomService.deleteRoom(DeleteRoomInDTO.builder()
                    .roomId(1)
                    .userId(1)
                    .build());
        });
    }

    @Test
    void readRoomQuestionByRoomIdAndUserId() throws Exception{
        // init
        ReadRoomQuestionOutDTO initReadRoomQuestionOutDTO = ReadRoomQuestionOutDTO.builder()
                .roomQuestion("test_roomQuestion")
                .roomId(1)
                .build();

        // given
        Mockito.when(roomDAO.readRoomQuestionByRoomIdAndUserId(any(Long.class), any(Long.class)))
                .thenReturn(initReadRoomQuestionOutDTO);

        // when
        ReadRoomQuestionOutDTO whenReadRoomQuestionOutDTO = roomService.readRoomQuestionByRoomIdAndUserId(1, 1);

        // then
        assertEquals(initReadRoomQuestionOutDTO.getRoomQuestion(), whenReadRoomQuestionOutDTO.getRoomQuestion());
        assertEquals(initReadRoomQuestionOutDTO.getRoomId(), whenReadRoomQuestionOutDTO.getRoomId());
    }

    @Test
    void readRoomQuestionByRoomIdAndUserId_InvalidService() throws Exception{
        // init
        ReadRoomQuestionOutDTO initReadRoomQuestionOutDTO = ReadRoomQuestionOutDTO.builder()
                .roomQuestion("test_roomQuestion")
                .roomId(1)
                .build();

        // given
        Mockito.when(roomDAO.readRoomQuestionByRoomIdAndUserId(any(Long.class), any(Long.class)))
                .thenThrow(new IllegalArgumentException());

        // when - then
        assertThrows(IllegalArgumentException.class, () ->{
            roomService.readRoomQuestionByRoomIdAndUserId(1, 1);
        });
    }

    @Test
    void readMemberListByRoomId() throws Exception{
        // init
        List<MemberDTO> MemberDTOs = new ArrayList<>();

        for (int i = 0; i < 4; i++)
            MemberDTOs.add(MemberDTO.builder()
                    .profileUrl("test_profileUrl")
                    .name("test_name")
                    .userRoomRole(UserRoomRole.ROLE1)
                    .userId(1)
                    .build());

        ReadMemberListOutDTO initReadMemberListOutDTO = ReadMemberListOutDTO.builder()
                .members(MemberDTOs)
                .roomId(1)
                .build();

        // given
        Mockito.when(roomDAO.readMemberListByRoomId(any(Long.class), any(Long.class)))
                .thenReturn(MemberDTOs);

        // when
        ReadMemberListOutDTO whenReadMemberListOutDTO = roomService.readMemberListByRoomId(1, 1);

        // then
        assertEquals(initReadMemberListOutDTO.getRoomId(), whenReadMemberListOutDTO.getRoomId());
        assertEquals(initReadMemberListOutDTO.getMembers().size(), whenReadMemberListOutDTO.getMembers().size());

        int size = initReadMemberListOutDTO.getMembers().size();

        for (int i = 0; i < size; i++) {
            assertEquals(initReadMemberListOutDTO.getMembers().get(i).getUserId(), initReadMemberListOutDTO.getMembers().get(i).getUserId());
            assertEquals(initReadMemberListOutDTO.getMembers().get(i).getUserRoomRole(), initReadMemberListOutDTO.getMembers().get(i).getUserRoomRole());
            assertEquals(initReadMemberListOutDTO.getMembers().get(i).getName(), initReadMemberListOutDTO.getMembers().get(i).getName());
            assertEquals(initReadMemberListOutDTO.getMembers().get(i).getProfileUrl(), initReadMemberListOutDTO.getMembers().get(i).getProfileUrl());
        }
    }

    void readMemberListByRoomId_NullInMember() throws Exception{
        // init
                ReadMemberListOutDTO initReadMemberListOutDTO = ReadMemberListOutDTO.builder()
                .members(null)
                .roomId(1)
                .build();

        // given
        Mockito.when(roomDAO.readMemberListByRoomId(any(Long.class), any(Long.class)))
                .thenReturn(null);

        // when - then
        assertThrows(IllegalArgumentException.class, () -> {
            roomService.readMemberListByRoomId(1, 1);
        });
    }
}