package com.purple.hello.service;

import com.purple.hello.dao.UserRoomDAO;
import com.purple.hello.dao.impl.UserRoomDAOImpl;
import com.purple.hello.dto.in.*;
import com.purple.hello.dto.out.CreateRoomOutDTO;
import com.purple.hello.dto.out.CreateUserRoomJoinOutDTO;
import com.purple.hello.entity.Room;
import com.purple.hello.entity.User;
import com.purple.hello.entity.UserRoom;
import com.purple.hello.enu.BoolAlarm;
import com.purple.hello.enu.UserRoomRole;
import com.purple.hello.service.impl.RoomServiceImpl;
import com.purple.hello.service.impl.UserRoomServiceImpl;
import org.junit.jupiter.api.*;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;

class UserRoomServiceTest {
    static private UserRoomServiceImpl userRoomService;
    static private UserRoomDAOImpl userRoomDAO;
    static private RoomServiceImpl roomService;

    @BeforeAll
    static void beforeAll(){
    }

    @BeforeEach
    void setUp() {
        userRoomDAO = Mockito.mock(UserRoomDAOImpl.class);
        roomService = Mockito.mock(RoomServiceImpl.class);
        userRoomService = new UserRoomServiceImpl(userRoomDAO, roomService);
    }

    @AfterEach
    void tearDown() {
    }
    //
    @Test
    void createUserRoom() throws Exception {
        // init
        long initRoomId = 1;
        CreateRoomOutDTO initCreateRoomOutDTO = CreateRoomOutDTO.builder()
                .roomId(initRoomId)
                .userRoomRole(UserRoomRole.ROLE1)
                .createAt(new Date())
                .userRoomId(1)
                .roomName("test_roomName")
                .userName("test_userName")
                .dayAlarm(BoolAlarm.Y)
                .safeAlarm(BoolAlarm.Y)
                .moveAlarm(BoolAlarm.Y)
                .build();

        // given
        Mockito.when(userRoomDAO.createUserRoom(any(CreateUserRoomInDTO.class), any(Long.class)))
                .thenReturn(initCreateRoomOutDTO);

        // when
        CreateRoomOutDTO whenCreateRoomOutDTO = userRoomService.createUserRoom(CreateUserRoomInDTO.builder()
                .userId(1)
                .userName("test_userName")
                .roomQuestion("test_roomQuestion")
                .roomPassword("test_password")
                .roomName("test_roomName")
                .build(), initRoomId);

        // then
        assertEquals(whenCreateRoomOutDTO.getCreateAt(), initCreateRoomOutDTO.getCreateAt());
        assertEquals(whenCreateRoomOutDTO.getRoomId(), initCreateRoomOutDTO.getRoomId());
        assertEquals(whenCreateRoomOutDTO.getUserRoomId(), initCreateRoomOutDTO.getUserRoomId());
        assertEquals(whenCreateRoomOutDTO.getRoomName(), initCreateRoomOutDTO.getRoomName());
        assertEquals(whenCreateRoomOutDTO.getUserName(), initCreateRoomOutDTO.getUserName());
        assertEquals(whenCreateRoomOutDTO.getUserRoomRole(), initCreateRoomOutDTO.getUserRoomRole());
        assertEquals(whenCreateRoomOutDTO.getDayAlarm(), initCreateRoomOutDTO.getDayAlarm());
        assertEquals(whenCreateRoomOutDTO.getSafeAlarm(), initCreateRoomOutDTO.getSafeAlarm());
        assertEquals(whenCreateRoomOutDTO.getMoveAlarm(), initCreateRoomOutDTO.getMoveAlarm());
    }

    @Test
    void createUserRoom_InvalidService() throws Exception {
        // init
        long initRoomId = 1;

        // given
        Mockito.when(userRoomDAO.createUserRoom(any(CreateUserRoomInDTO.class), any(Long.class)))
                .thenThrow(new IllegalArgumentException());

        // when - then
        assertThrows(IllegalArgumentException.class, () -> {
            userRoomService.createUserRoom(CreateUserRoomInDTO.builder()
                    .userId(1)
                    .userName("test_userName")
                    .roomQuestion("test_roomQuestion")
                    .roomPassword("test_password")
                    .roomName("test_roomName")
                    .build(), initRoomId);
        });
    }
    //
    @Test
    void createUserRoomJoin_UserJoin() throws Exception {
        // init
        CreateUserRoomJoinOutDTO initCreateUserRoomJoinOutDTO =
                CreateUserRoomJoinOutDTO.builder()
                        .userRoomId(1)
                        .userRoomRole(UserRoomRole.ROLE1)
                        .userName("test_userName")
                        .roomName("test_roomName")
                        .safeAlarm(BoolAlarm.Y)
                        .moveAlarm(BoolAlarm.Y)
                        .dayAlarm(BoolAlarm.Y)
                        .createAt(new Date())
                    .build();

        // given
        Mockito.when(userRoomDAO.readUserRoomByUserIdAndRoomId(any(Long.class), any(Long.class)))
                .thenReturn(new UserRoom());

        Mockito.when(userRoomDAO.updateUserRoomRejoin(any(CreateUserRoomJoinInDTO.class)))
                .thenReturn(initCreateUserRoomJoinOutDTO);

        // when
        CreateUserRoomJoinOutDTO whenCreateUserRoomJoinOutDTO
                 = userRoomService.createUserRoomJoin(CreateUserRoomJoinInDTO.builder()
                .roomId(1)
                .userId(1)
                .roomName("test_roomName")
                .userName("test_userName")
                .roomPassword("test_roomPassword")
                .build());

        // then
        assertEquals(initCreateUserRoomJoinOutDTO.getCreateAt(), whenCreateUserRoomJoinOutDTO.getCreateAt());
        assertEquals(initCreateUserRoomJoinOutDTO.getUserRoomId(), whenCreateUserRoomJoinOutDTO.getUserRoomId());
        assertEquals(initCreateUserRoomJoinOutDTO.getUserRoomRole(), whenCreateUserRoomJoinOutDTO.getUserRoomRole());
        assertEquals(initCreateUserRoomJoinOutDTO.getRoomName(), whenCreateUserRoomJoinOutDTO.getRoomName());
        assertEquals(initCreateUserRoomJoinOutDTO.getUserName(), whenCreateUserRoomJoinOutDTO.getUserName());
        assertEquals(initCreateUserRoomJoinOutDTO.getDayAlarm(), whenCreateUserRoomJoinOutDTO.getDayAlarm());
        assertEquals(initCreateUserRoomJoinOutDTO.getMoveAlarm(), whenCreateUserRoomJoinOutDTO.getMoveAlarm());
        assertEquals(initCreateUserRoomJoinOutDTO.getSafeAlarm(), whenCreateUserRoomJoinOutDTO.getSafeAlarm());
    }

    @Test
    void createUserRoomJoin_UserReJoin() throws Exception {
        // init
        CreateUserRoomJoinOutDTO initCreateUserRoomJoinOutDTO =
                CreateUserRoomJoinOutDTO.builder()
                        .userRoomId(1)
                        .userRoomRole(UserRoomRole.ROLE1)
                        .userName("test_userName")
                        .roomName("test_roomName")
                        .safeAlarm(BoolAlarm.Y)
                        .moveAlarm(BoolAlarm.Y)
                        .dayAlarm(BoolAlarm.Y)
                        .createAt(new Date())
                        .build();

        // given
        Mockito.when(userRoomDAO.readUserRoomByUserIdAndRoomId(any(Long.class), any(Long.class)))
                .thenReturn(null);

        Mockito.when(userRoomDAO.createUserRoomJoin(any(CreateUserRoomJoinInDTO.class)))
                .thenReturn(initCreateUserRoomJoinOutDTO);

        // when
        CreateUserRoomJoinOutDTO whenCreateUserRoomJoinOutDTO
                = userRoomService.createUserRoomJoin(CreateUserRoomJoinInDTO.builder()
                .roomId(1)
                .userId(1)
                .roomName("test_roomName")
                .userName("test_userName")
                .roomPassword("test_roomPassword")
                .build());

        // then
        assertEquals(initCreateUserRoomJoinOutDTO.getCreateAt(), whenCreateUserRoomJoinOutDTO.getCreateAt());
        assertEquals(initCreateUserRoomJoinOutDTO.getUserRoomId(), whenCreateUserRoomJoinOutDTO.getUserRoomId());
        assertEquals(initCreateUserRoomJoinOutDTO.getUserRoomRole(), whenCreateUserRoomJoinOutDTO.getUserRoomRole());
        assertEquals(initCreateUserRoomJoinOutDTO.getRoomName(), whenCreateUserRoomJoinOutDTO.getRoomName());
        assertEquals(initCreateUserRoomJoinOutDTO.getUserName(), whenCreateUserRoomJoinOutDTO.getUserName());
        assertEquals(initCreateUserRoomJoinOutDTO.getDayAlarm(), whenCreateUserRoomJoinOutDTO.getDayAlarm());
        assertEquals(initCreateUserRoomJoinOutDTO.getMoveAlarm(), whenCreateUserRoomJoinOutDTO.getMoveAlarm());
        assertEquals(initCreateUserRoomJoinOutDTO.getSafeAlarm(), whenCreateUserRoomJoinOutDTO.getSafeAlarm());
    }

    @Test
    void createUserRoomJoin_UserJoin_InvalidService() throws Exception {
        // given
        Mockito.when(userRoomDAO.readUserRoomByUserIdAndRoomId(any(Long.class), any(Long.class)))
                .thenReturn(new UserRoom());

        Mockito.when(userRoomDAO.updateUserRoomRejoin(any(CreateUserRoomJoinInDTO.class)))
                .thenThrow(new IllegalArgumentException());

        // when - then
        assertThrows(IllegalArgumentException.class, () -> {
            userRoomService.createUserRoomJoin(CreateUserRoomJoinInDTO.builder()
                    .roomId(1)
                    .userId(1)
                    .roomName("test_roomName")
                    .userName("test_userName")
                    .roomPassword("test_roomPassword")
                    .build());
        });
    }

    @Test
    void createUserRoomJoin_UserReJoin_InvalidService() throws Exception {
        // given
        Mockito.when(userRoomDAO.readUserRoomByUserIdAndRoomId(any(Long.class), any(Long.class)))
                .thenReturn(null);

        Mockito.when(userRoomDAO.createUserRoomJoin(any(CreateUserRoomJoinInDTO.class)))
                .thenThrow(new IllegalArgumentException());

        // when - then
        assertThrows(IllegalArgumentException.class, () -> {
            userRoomService.createUserRoomJoin(CreateUserRoomJoinInDTO.builder()
                    .roomId(1)
                    .userId(1)
                    .roomName("test_roomName")
                    .userName("test_userName")
                    .roomPassword("test_roomPassword")
                    .build());
        });
    }

    //
    @Test
    void updateRoomName() throws Exception {
        // init
        UpdateRoomNameInDTO initUpdateRoomNameInDTO = UpdateRoomNameInDTO.builder()
                .userId(1)
                .userRoomId(1)
                .roomName("test_roomName")
                .build();

        // given
        Mockito.when(userRoomDAO.updateRoomName(any(UpdateRoomNameInDTO.class)))
                .thenReturn(true);

        // when
        boolean whenResult = userRoomService.updateRoomName(initUpdateRoomNameInDTO);

        // then
        assertTrue(whenResult);
    }

    @Test
    void updateRoomName_InputIsNull() throws Exception {
        // init
        UpdateRoomNameInDTO initUpdateRoomNameInDTO = UpdateRoomNameInDTO.builder()
                .userId(1)
                .userRoomId(1)
                .roomName(null)
                .build();

        // when - then
        assertThrows(IllegalArgumentException.class, () -> {
            userRoomService.updateRoomName(initUpdateRoomNameInDTO);
        });
    }

    @Test
    void updateRoomName_InvalidService() throws Exception {
        // init
        UpdateRoomNameInDTO initUpdateRoomNameInDTO = UpdateRoomNameInDTO.builder()
                .userId(1)
                .userRoomId(1)
                .roomName("test_roomName")
                .build();

        // given
        Mockito.when(userRoomDAO.updateRoomName(any(UpdateRoomNameInDTO.class)))
                .thenThrow(new IllegalArgumentException());

        // when - then
        assertThrows(IllegalArgumentException.class, () -> {
            userRoomService.updateRoomName(initUpdateRoomNameInDTO);
        });
    }
    //
    @Test
    void updateUserName() throws Exception {
        // init
        UpdateUserNameInDTO initUpdateUserNameInDTO = UpdateUserNameInDTO.builder()
                .userId(1)
                .userRoomId(1)
                .userName("test_userName")
                .build();

        // given
        Mockito.when(userRoomDAO.updateUserName(any(UpdateUserNameInDTO.class)))
                .thenReturn(true);

        // when
        boolean whenResult = userRoomService.updateUserName(initUpdateUserNameInDTO);

        // then
        assertTrue(whenResult);
    }

    @Test
    void updateUserName_InvalidService() throws Exception {
        // init
        UpdateUserNameInDTO initUpdateUserNameInDTO = UpdateUserNameInDTO.builder()
                .userId(1)
                .userRoomId(1)
                .userName("test_userName")
                .build();

        // given
        Mockito.when(userRoomDAO.updateUserName(any(UpdateUserNameInDTO.class)))
                .thenThrow(new IllegalArgumentException());

        // when - then
        assertThrows(IllegalArgumentException.class, () -> {
            userRoomService.updateUserName(initUpdateUserNameInDTO);
        });
    }
    //
    @Test
    void updateMoveAlarm() throws Exception {
        // init
        UpdateMoveAlarmInDTO initUpdateMoveAlarmInDTO = UpdateMoveAlarmInDTO.builder()
                .userId(1)
                .userRoomId(1)
                .moveAlarm(BoolAlarm.Y)
                .build();

        // given
        Mockito.when(userRoomDAO.updateMoveAlarm(any(UpdateMoveAlarmInDTO.class)))
                .thenReturn(true);

        // when
        boolean whenResult = userRoomService.updateMoveAlarm(initUpdateMoveAlarmInDTO);

        // then
        assertTrue(whenResult);
    }

    @Test
    void updateMoveAlarm_InvalidService() throws Exception {
        // init
        UpdateMoveAlarmInDTO initUpdateMoveAlarmInDTO = UpdateMoveAlarmInDTO.builder()
                .userId(1)
                .userRoomId(1)
                .moveAlarm(BoolAlarm.Y)
                .build();

        // given
        Mockito.when(userRoomDAO.updateMoveAlarm(any(UpdateMoveAlarmInDTO.class)))
                .thenThrow(new IllegalArgumentException());

        // when - then
        assertThrows(IllegalArgumentException.class, () -> {
            userRoomService.updateMoveAlarm(initUpdateMoveAlarmInDTO);
        });
    }
    //
    @Test
    void updateSafeAlarm() throws Exception {
        // init
        UpdateSafeAlarmInDTO initUpdateSafeAlarmInDTO = UpdateSafeAlarmInDTO.builder()
                .userId(1)
                .roomId(1)
                .safeAlarm(BoolAlarm.Y)
                .build();

        // given
        Mockito.when(userRoomDAO.updateSafeAlarm(any(UpdateSafeAlarmInDTO.class)))
                .thenReturn(true);

        // when
        boolean whenResult = userRoomService.updateSafeAlarm(initUpdateSafeAlarmInDTO);

        // then
        assertTrue(whenResult);
    }
    @Test
    void updateSafeAlarm_InvalidService() throws Exception {
        // init
        UpdateSafeAlarmInDTO initUpdateSafeAlarmInDTO = UpdateSafeAlarmInDTO.builder()
                .userId(1)
                .roomId(1)
                .safeAlarm(BoolAlarm.Y)
                .build();
        // given
        Mockito.when(userRoomDAO.updateSafeAlarm(any(UpdateSafeAlarmInDTO.class)))
                .thenThrow(new IllegalArgumentException());

        // when - then
        assertThrows(IllegalArgumentException.class, () -> {
            userRoomService.updateSafeAlarm(initUpdateSafeAlarmInDTO);
        });
    }

    //
    @Test
    void deleteUserRoom_InvalidService1() throws Exception {
        DeleteUserRoomInDTO initDeleteUserRoomInDTO = DeleteUserRoomInDTO.builder()
                .userId(1)
                .userRoomId(1)
                .build();

        Mockito.when(userRoomDAO.readUserRoomByUserRoomId(any(Long.class)))
                .thenThrow(new IllegalArgumentException());

        assertThrows(IllegalArgumentException.class, () -> {
            userRoomService.deleteUserRoom(initDeleteUserRoomInDTO);
        });
    }
    @Test
    void deleteUserRoom_IfUserRoleIsRole2() throws Exception {
        // init
        DeleteUserRoomInDTO initDeleteUserRoomInDTO = DeleteUserRoomInDTO.builder()
                .userId(1)
                .userRoomId(1)
                .build();



        // given
        Mockito.when(userRoomDAO.readUserRoomByUserRoomId(any(Long.class)))
                .thenReturn(UserRoom.builder()
                        .userRoomId(1)
                        .userRoomRole(UserRoomRole.ROLE2)
                        .userName("test_userName")
                        .roomName("test_roomName")
                        .safeAlarm(BoolAlarm.Y)
                        .moveAlarm(BoolAlarm.Y)
                        .dayAlarm(BoolAlarm.Y)
                        .createAt(new Date())
                        .build());

        // when
        boolean whenResult = userRoomService.deleteUserRoom(initDeleteUserRoomInDTO);

        // then
        assertTrue(whenResult);
    }
    @Test
    void deleteUserRoom_IfUserRoleIsNotRole2_IsNotNull() throws Exception {
        DeleteUserRoomInDTO initDeleteUserRoomInDTO = DeleteUserRoomInDTO.builder()
                .userId(1)
                .userRoomId(1)
                .build();

        List<UserRoom> initUserRooms = new ArrayList<>();
        initUserRooms.add(UserRoom.builder()
                .userRoomId(1)
                .userRoomRole(UserRoomRole.ROLE1)
                .userName("test_userName")
                .roomName("test_roomName")
                .safeAlarm(BoolAlarm.Y)
                .moveAlarm(BoolAlarm.Y)
                .dayAlarm(BoolAlarm.Y)
                .createAt(new Date())
                .build());

        UserRoom initUserRoom = UserRoom.builder()
                .userRoomId(1)
                .userRoomRole(UserRoomRole.ROLE1)
                .userName("test_userName")
                .roomName("test_roomName")
                .safeAlarm(BoolAlarm.Y)
                .moveAlarm(BoolAlarm.Y)
                .dayAlarm(BoolAlarm.Y)
                .createAt(new Date())
                .room(Room.builder()
                        .roomCode("test_roomCode")
                        .roomId(1)
                        .createAt(new Date())
                        .beginTime(8)
                        .roomPassword("test_roomPassword")
                        .build())
                .user(User.builder()
                        .userId(1)
                        .build())
                .build();


        // when
        Mockito.when(userRoomDAO.readUserRoomsByRoomIdWithoutUserRoomIdUsingLimit(any(Long.class), any(Long.class), any(Integer.class)))
                .thenReturn(initUserRooms);

        Mockito.when(userRoomDAO.readUserRoomByUserRoomId(any(Long.class)))
                .thenReturn(initUserRoom);

        boolean whenResult = userRoomService.deleteUserRoom(initDeleteUserRoomInDTO);

        assertTrue(whenResult);
    }

    @Test
    void deleteUserRoom_IfUserRoleIsNotRole2_IsNull_DeleteRoom() throws Exception {
        // init
        DeleteUserRoomInDTO initDeleteUserRoomInDTO = DeleteUserRoomInDTO.builder()
                .userId(1)
                .userRoomId(1)
                .build();

        UserRoom initUserRoom = UserRoom.builder()
                .userRoomId(1)
                .userRoomRole(UserRoomRole.ROLE1)
                .userName("test_userName")
                .roomName("test_roomName")
                .safeAlarm(BoolAlarm.Y)
                .moveAlarm(BoolAlarm.Y)
                .dayAlarm(BoolAlarm.Y)
                .createAt(new Date())
                .room(Room.builder()
                        .roomCode("test_roomCode")
                        .roomId(1)
                        .createAt(new Date())
                        .beginTime(8)
                        .roomPassword("test_roomPassword")
                        .build())
                .user(User.builder()
                        .userId(1)
                        .build())
                .build();


        List<UserRoom> initUserRooms = new ArrayList<>();

        // given
        Mockito.when(userRoomDAO.readUserRoomsByRoomIdWithoutUserRoomIdUsingLimit(any(Long.class), any(Long.class), any(Integer.class)))
                .thenReturn(initUserRooms);

        Mockito.when(userRoomDAO.readUserRoomByUserRoomId(any(Long.class)))
                .thenReturn(initUserRoom);


        Mockito.when(roomService.deleteRoom(any(DeleteRoomInDTO.class)))
                .thenReturn(true);

        boolean whenResult = userRoomService.deleteUserRoom(initDeleteUserRoomInDTO);

        assertTrue(whenResult);
    }
    @Test
    void deleteUserRoom_IfUserRoleIsNotRole2_IsNull_DoNotDeleteRoom() throws Exception {
        DeleteUserRoomInDTO initDeleteUserRoomInDTO = DeleteUserRoomInDTO.builder()
                .userId(1)
                .userRoomId(1)
                .build();

        List<UserRoom> initUserRooms = new ArrayList<>();

        // when
        Mockito.when(userRoomDAO.readUserRoomByUserRoomId(any(Long.class)))
                .thenReturn(UserRoom.builder()
                        .userRoomId(1)
                        .userRoomRole(UserRoomRole.ROLE1)
                        .userName("test_userName")
                        .roomName("test_roomName")
                        .safeAlarm(BoolAlarm.Y)
                        .moveAlarm(BoolAlarm.Y)
                        .dayAlarm(BoolAlarm.Y)
                        .createAt(new Date())
                        .room(Room.builder()
                                .roomCode("test_roomCode")
                                .roomId(1)
                                .createAt(new Date())
                                .beginTime(8)
                                .roomPassword("test_roomPassword")
                                .build())
                        .user(User.builder()
                                .userId(1)
                                .build())
                        .build());

        Mockito.when(userRoomDAO.readUserRoomsByRoomIdWithoutUserRoomIdUsingLimit(any(Long.class), any(Long.class), any(Integer.class)))
                .thenReturn(new ArrayList<>());

        Mockito.when(roomService.deleteRoom(any(DeleteRoomInDTO.class)))
                .thenReturn(false);

        boolean whenResult = userRoomService.deleteUserRoom(initDeleteUserRoomInDTO);

        assertFalse(whenResult);
    }
}