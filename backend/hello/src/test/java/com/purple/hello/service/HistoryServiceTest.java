package com.purple.hello.service;

import com.purple.hello.dao.HistoryDAO;
import com.purple.hello.dao.RoomDAO;
import com.purple.hello.dao.impl.HistoryDAOImpl;
import com.purple.hello.dao.impl.RoomDAOImpl;
import com.purple.hello.dto.out.ReadQuestionOutDTO;
import com.purple.hello.service.impl.HistoryServiceImpl;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.util.Calendar;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;

class HistoryServiceTest {
    static private HistoryServiceImpl historyService;
    static private RoomDAO roomDAO;
    static private HistoryDAO historyDAO;
    @BeforeAll
    static void beforeAll(){
    }
    @BeforeEach
    void setUp() {
        roomDAO = Mockito.mock(RoomDAOImpl.class);
        historyDAO = Mockito.mock(HistoryDAOImpl.class);

        historyService = new HistoryServiceImpl(roomDAO, historyDAO);
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void readQuestionByRoomIdAndDate() throws Exception {
        // init
        ReadQuestionOutDTO initReadQuestionOutDTO = ReadQuestionOutDTO.builder()
                .questionId(1)
                .content("test_content")
                .build();

        // given
        Mockito.when(roomDAO.readBeginTimeByRoomId(any(Long.class)))
                .thenReturn(-1);

        Mockito.when(historyDAO.readQuestionByRoomIdAndDate(any(Long.class), any(Date.class)))
                .thenReturn(initReadQuestionOutDTO);

        // when
        ReadQuestionOutDTO whenReadQuestionOutDTO =
                historyService.readQuestionByRoomIdAndDate(1, new Date());

        // then
        assertEquals(initReadQuestionOutDTO.getQuestionId(), whenReadQuestionOutDTO.getQuestionId());
        assertEquals(initReadQuestionOutDTO.getContent(), whenReadQuestionOutDTO.getContent());
    }

    @Test
    void readQuestionByRoomIdAndDate_WrongInput() throws Exception {
        // when - then
        assertThrows(IllegalArgumentException.class, () -> {
            historyService.readQuestionByRoomIdAndDate(1, null);
        });
    }

    @Test
    void readQuestionByRoomIdAndDate_InsertEarlyDate() throws Exception {
        // init
        ReadQuestionOutDTO initReadQuestionOutDTO = ReadQuestionOutDTO.builder()
                .questionId(1)
                .content("test_content")
                .build();

        // given
        Mockito.when(roomDAO.readBeginTimeByRoomId(any(Long.class)))
                .thenReturn(1);

        Mockito.when(historyDAO.readQuestionByRoomIdAndDate(any(Long.class), any(Date.class)))
                .thenThrow(new IllegalArgumentException());

        // when
        assertThrows(IllegalArgumentException.class, () -> {
            historyService.readQuestionByRoomIdAndDate(1, new Date());
        });
    }

    @Test
    void readQuestionByRoomIdAndDate_BeforeBeginTime() throws Exception {
        // init
        ReadQuestionOutDTO initReadQuestionOutDTO = ReadQuestionOutDTO.builder()
                .questionId(1)
                .content("test_content")
                .build();

        // given
        Mockito.when(roomDAO.readBeginTimeByRoomId(any(Long.class)))
                .thenReturn(25);

        Mockito.when(historyDAO.readQuestionByRoomIdAndDate(any(Long.class), any(Date.class)))
                .thenReturn(initReadQuestionOutDTO);

        // when
        assertThrows(IllegalArgumentException.class, () -> {
            historyService.readQuestionByRoomIdAndDate(1, new Date());
        });
    }

}