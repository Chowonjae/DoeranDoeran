package com.purple.hello.service;

import com.purple.hello.dao.impl.UserDAOImpl;
import com.purple.hello.dto.in.OauthUserInputDTO;
import com.purple.hello.dto.in.UpdateUserInfoInDTO;
import com.purple.hello.entity.User;
import com.purple.hello.service.impl.UserServiceImpl;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;

class UserServiceTest {
    static private UserServiceImpl userService;
    static private UserDAOImpl userDAO;
    @BeforeEach
    void setUp() {
        userDAO = Mockito.mock(UserDAOImpl.class);
        userService = new UserServiceImpl(userDAO);
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void readUserByOauthId() throws Exception {
        // init
        String initOauthId = "test_oauthId";

        User initUser = User.builder()
                .userId(1)
                .oauthId("test_oauthId")
                .email("test_email")
                .name("test_name")
                .userProfileUrl("test_userProfileUrl")
                .createAt(new Date())
                .birth(new Date())
                .accessToken("test_accessToken")
                .refreshToken("test_refreshToken")
                .build();

        // given
        Mockito.when(userDAO.readUserByOauthId(any(String.class)))
                .thenReturn(initUser);

        // when
        User whenResult = userService.readUserByOauthId(initOauthId);

        // then
        assertEquals(initUser.getUserId(), whenResult.getUserId());
        assertEquals(initUser.getOauthId(), whenResult.getOauthId());
        assertEquals(initUser.getEmail(), whenResult.getEmail());
        assertEquals(initUser.getName(), whenResult.getName());
        assertEquals(initUser.getUserProfileUrl(), whenResult.getUserProfileUrl());
        assertEquals(initUser.getCreateAt(), whenResult.getCreateAt());
        assertEquals(initUser.getBirth(), whenResult.getBirth());
        assertEquals(initUser.getAccessToken(), whenResult.getAccessToken());
        assertEquals(initUser.getRefreshToken(), whenResult.getRefreshToken());
    }

    @Test
    void readUserByOauthId_InvalidService() throws Exception {
        // init
        String oauthId = "test_oauthId";

        // given
        Mockito.when(userDAO.readUserByOauthId(any(String.class)))
                .thenThrow(new IllegalArgumentException());

        // when - then
        assertThrows(IllegalArgumentException.class, () -> {
            userService.readUserByOauthId(oauthId);
        });
    }

    @Test
    void insertUser() throws Exception {
        // init

        User initUser = User.builder()
                .userId(1)
                .oauthId("test_oauthId")
                .email("test_email")
                .name("test_name")
                .userProfileUrl("test_userProfileUrl")
                .createAt(new Date())
                .birth(new Date())
                .accessToken("test_accessToken")
                .refreshToken("test_refreshToken")
                .build();

        // given
        Mockito.when(userDAO.insertUser(any(User.class)))
                .thenReturn(initUser);

        // when
        User whenResult = userService.insertUser(new OauthUserInputDTO("test_oauthId"));

        // then
        assertEquals(initUser.getUserId(), whenResult.getUserId());
        assertEquals(initUser.getOauthId(), whenResult.getOauthId());
        assertEquals(initUser.getEmail(), whenResult.getEmail());
        assertEquals(initUser.getName(), whenResult.getName());
        assertEquals(initUser.getUserProfileUrl(), whenResult.getUserProfileUrl());
        assertEquals(initUser.getCreateAt(), whenResult.getCreateAt());
        assertEquals(initUser.getBirth(), whenResult.getBirth());
        assertEquals(initUser.getAccessToken(), whenResult.getAccessToken());
        assertEquals(initUser.getRefreshToken(), whenResult.getRefreshToken());
    }

    @Test
    void isValidRefreshToken() throws Exception {
        // init
        String refreshToken = "test_refreshToken";

        User initUser = User.builder()
                .userId(1)
                .oauthId("test_oauthId")
                .email("test_email")
                .name("test_name")
                .userProfileUrl("test_userProfileUrl")
                .createAt(new Date())
                .birth(new Date())
                .accessToken("test_accessToken")
                .refreshToken("test_refreshToken")
                .build();

        // given
        Mockito.when(userDAO.isValidRefreshToken(any(String.class)))
                .thenReturn(initUser);

        // when
        User whenResult = userService.isValidRefreshToken(refreshToken);

        // then
        assertEquals(initUser.getUserId(), whenResult.getUserId());
        assertEquals(initUser.getOauthId(), whenResult.getOauthId());
        assertEquals(initUser.getEmail(), whenResult.getEmail());
        assertEquals(initUser.getName(), whenResult.getName());
        assertEquals(initUser.getUserProfileUrl(), whenResult.getUserProfileUrl());
        assertEquals(initUser.getCreateAt(), whenResult.getCreateAt());
        assertEquals(initUser.getBirth(), whenResult.getBirth());
        assertEquals(initUser.getAccessToken(), whenResult.getAccessToken());
        assertEquals(initUser.getRefreshToken(), whenResult.getRefreshToken());

    }

    @Test
    void isValidRefreshToken_InvalidService() throws Exception {
        String refreshToken = "test_refreshToken";

        // given
        Mockito.when(userDAO.isValidRefreshToken(any(String.class)))
                .thenThrow(new IllegalArgumentException());

        // when - then
        assertThrows(IllegalArgumentException.class, () -> {
            userService.isValidRefreshToken(refreshToken);
        });
    }

    @Test
    void updateUserInfo() throws Exception {
        // init
        UpdateUserInfoInDTO initUpdateUserInfoInDTO = UpdateUserInfoInDTO.builder()
                .userId(1)
                .profileURL("test_userProfileUrl")
                .birth(new Date())
                .build();
        
        // given
        Mockito.when(userDAO.updateUserInfo(any(UpdateUserInfoInDTO.class)))
                .thenReturn(true);
        
        // when
        boolean whenResult = userService.updateUserInfo(initUpdateUserInfoInDTO);
        
        // then
        assertTrue(whenResult);
    }

    @Test
    void updateUserInfo_InvalidService() throws Exception {
        // init
        UpdateUserInfoInDTO initUpdateUserInfoInDTO = UpdateUserInfoInDTO.builder()
                .userId(1)
                .profileURL("test_userProfileUrl")
                .birth(new Date())
                .build();

        // given
        Mockito.when(userDAO.updateUserInfo(any(UpdateUserInfoInDTO.class)))
                .thenThrow(new IllegalArgumentException());
        
        // when - then
        assertThrows(IllegalArgumentException.class, () -> {
            userService.updateUserInfo(initUpdateUserInfoInDTO);
        });
    }
}