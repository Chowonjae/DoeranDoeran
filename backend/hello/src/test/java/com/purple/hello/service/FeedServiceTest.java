package com.purple.hello.service;

import com.purple.hello.dao.RoomDAO;
import com.purple.hello.dao.impl.FeedDAOImpl;
import com.purple.hello.dao.impl.RoomDAOImpl;
import com.purple.hello.dto.in.CreateFeedInDTO;
import com.purple.hello.dto.out.CompareFeedByRoomIdOutDTO;
import com.purple.hello.dto.out.CreateFeedOutDTO;
import com.purple.hello.dto.out.ReadFeedOutDTO;
import com.purple.hello.dto.tool.AwsS3DTO;
import com.purple.hello.entity.Room;
import com.purple.hello.enu.FeedType;
import com.purple.hello.service.impl.AwsS3ServiceImpl;
import com.purple.hello.service.impl.FeedServiceImpl;
import com.purple.hello.service.impl.FileServiceImpl;
import org.junit.jupiter.api.*;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;

class FeedServiceTest {
    static private FeedServiceImpl feedService;
    static private AwsS3ServiceImpl awsS3Service;
    static private FileServiceImpl fileService;
    static private FeedDAOImpl feedDAO;
    static private RoomDAOImpl roomDAO;

    @BeforeAll
    static void beforeAll() {
    }

    @BeforeEach
    void setUp() {
        awsS3Service = Mockito.mock(AwsS3ServiceImpl.class);
        fileService = Mockito.mock(FileServiceImpl.class);
        feedDAO = Mockito.mock(FeedDAOImpl.class);
        roomDAO = Mockito.mock(RoomDAOImpl.class);

        feedService = new FeedServiceImpl(awsS3Service, fileService, feedDAO, roomDAO);
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void compareFeedByRoomIdAndDate() throws Exception{
        // given
        List<CompareFeedByRoomIdOutDTO> compareFeedByRoomIdOutDTOs = new ArrayList<>();

        for (int i = 1; i <=4; i++) {
            CompareFeedByRoomIdOutDTO compareFeedByRoomIdOutDTO
                    = CompareFeedByRoomIdOutDTO.builder()
                    .userId(i)
                    .isSubmit(true)
                    .build();

            compareFeedByRoomIdOutDTOs.add(compareFeedByRoomIdOutDTO);
        }

        Mockito.when(feedDAO.compareFeedByRoomIdAndDate(any(Long.class), any(Date.class)))
                .thenReturn(compareFeedByRoomIdOutDTOs);

        // when
        List<CompareFeedByRoomIdOutDTO> testCompareFeedByRoomIdOutDTOs
                = feedService.compareFeedByRoomIdAndDate(1, new Date());

        // then
        for (int i = 0; i < 4; i++) {
            assertEquals(testCompareFeedByRoomIdOutDTOs.get(0).getUserId(), compareFeedByRoomIdOutDTOs.get(0).getUserId());
            assertEquals(testCompareFeedByRoomIdOutDTOs.get(0).isSubmit(), compareFeedByRoomIdOutDTOs.get(0).isSubmit());
        }
    }

    @Test
    void compareFeedByRoomIdAndDate_InValidService() throws Exception{
        // given
        List<CompareFeedByRoomIdOutDTO> compareFeedByRoomIdOutDTOs = new ArrayList<>();

        for (int i = 1; i <=4; i++) {
            CompareFeedByRoomIdOutDTO compareFeedByRoomIdOutDTO
                    = CompareFeedByRoomIdOutDTO.builder()
                    .userId(i)
                    .isSubmit(true)
                    .build();

            compareFeedByRoomIdOutDTOs.add(compareFeedByRoomIdOutDTO);
        }

        Mockito.when(feedDAO.compareFeedByRoomIdAndDate(any(Long.class), any(Date.class)))
                .thenThrow(new IllegalArgumentException());

        // when - then
        assertThrows(IllegalArgumentException.class, () -> {
            List<CompareFeedByRoomIdOutDTO> testCompareFeedByRoomIdOutDTOs
                    = feedService.compareFeedByRoomIdAndDate(1, new Date());
        });
    }

    @Test
    void createFeed() throws Exception{
        // given
        CreateFeedOutDTO createFeedOutDTO = CreateFeedOutDTO.builder()
                .createAt(new Date())
                .feedId(1)
                .feedUrl("test_feedUrl")
                .feedType(FeedType.FEED1)
                .userRoomId(1)
                .content("test_content")
                .build();

        Mockito.when(fileService.compareExtensions(any(), any()))
                .thenReturn(true);

        Mockito.when(awsS3Service.upload(any(MultipartFile.class), any(String.class)))
                .thenReturn(new AwsS3DTO());

        Mockito.when(feedDAO.createFeed(any(CreateFeedInDTO.class)))
                .thenReturn(createFeedOutDTO);

        Mockito.when(roomDAO.readRoomByUserRoomId(any(Long.class)))
                .thenReturn(Room.builder()
                        .beginTime(8)
                        .createAt(new Date())
                        .roomQuestion("test_roomQuestion")
                        .roomPassword("test_roomPassword")
                        .build());

        // when
        CreateFeedOutDTO testCreateFeedOutDTO
                = feedService.createFeed(CreateFeedInDTO.builder()
                        .feedFile(new MockMultipartFile("filename", "original_filename", "content_type", "content".getBytes()))
                        .userRoomId(1)
                        .feedUrl("test_feedUrl")
                        .feedType(FeedType.FEED1)
                        .content("test_content")
                        .build());

        // then
        assertEquals(createFeedOutDTO.getCreateAt(),testCreateFeedOutDTO.getCreateAt());
        assertEquals(createFeedOutDTO.getFeedId(), testCreateFeedOutDTO.getFeedId());
        assertEquals(createFeedOutDTO.getFeedType(), testCreateFeedOutDTO.getFeedType());
        assertEquals(createFeedOutDTO.getFeedUrl(), testCreateFeedOutDTO.getFeedUrl());
        assertEquals(createFeedOutDTO.getUserRoomId(), testCreateFeedOutDTO.getUserRoomId());
        assertEquals(createFeedOutDTO.getContent(), testCreateFeedOutDTO.getContent());
    }

    @Test
    void createFeed_InvalidService_InsertNullInParameter() throws Exception{
        // given

        Mockito.when(feedDAO.createFeed(any(CreateFeedInDTO.class)))
                .thenThrow(new IllegalArgumentException());

        Mockito.when(fileService.compareExtensions(any(String.class), any(String[].class)))
                .thenReturn(false);

        Mockito.when(awsS3Service.upload(any(MultipartFile.class), any(String.class)))
                .thenReturn(null);

        // when - then
        assertThrows(NullPointerException.class, () -> {
            CreateFeedOutDTO testCreateFeedOutDTO = feedService.createFeed(null);
        });
    }

    @Test
    void createFeed_InvalidService_InvalidService1() throws Exception{
        // given
        Mockito.when(fileService.compareExtensions(any(String.class), any(String[].class)))
                .thenReturn(false);

        Mockito.when(awsS3Service.upload(any(MultipartFile.class), any(String.class)))
                .thenReturn(null);

        Mockito.when(feedDAO.createFeed(any(CreateFeedInDTO.class)))
                .thenThrow(new IllegalArgumentException());

        // when - then
        assertThrows(IllegalArgumentException.class, () -> {
            feedService.createFeed(CreateFeedInDTO.builder()
                            .userRoomId(1)
                            .feedFile(null)
                            .feedUrl("test_feedUrl")
                            .feedType(FeedType.FEED1)
                            .content("test_content")
                            .build());
        });
    }

    @Test
    void createFeed_InvalidService_InvalidService2() throws Exception{
        // given
        Mockito.when(awsS3Service.upload(any(MultipartFile.class), any(String.class)))
                .thenReturn(null);

        Mockito.when(feedDAO.createFeed(any(CreateFeedInDTO.class)))
                .thenThrow(new IllegalArgumentException());

        // when - then
        assertThrows(IllegalArgumentException.class, () -> {
            feedService.createFeed(CreateFeedInDTO.builder()
                    .userRoomId(1)
                    .feedFile(null)
                    .feedUrl("test_feedUrl")
                    .feedType(FeedType.FEED1)
                    .content("test_content")
                    .build());
        });
    }

    @Test
    void createFeed_InvalidService_InvalidService3() throws Exception{
        // given
        Mockito.when(feedDAO.createFeed(any(CreateFeedInDTO.class)))
                .thenThrow(new IllegalArgumentException());

        // when - then
        assertThrows(IllegalArgumentException.class, () -> {
            feedService.createFeed(CreateFeedInDTO.builder()
                    .userRoomId(1)
                    .feedFile(null)
                    .feedUrl("test_feedUrl")
                    .feedType(FeedType.FEED1)
                    .content("test_content")
                    .build());
        });
    }

    @Test
    void readFeedByRoomIdAndDate()throws Exception {
        // given
        List<ReadFeedOutDTO> testReadFeedOutDTOs = new ArrayList<>();

        for (int i = 0; i < 4; i++){
            testReadFeedOutDTOs.add(ReadFeedOutDTO.builder()
                    .content("test_content")
                    .userId(i)
                    .feedId(i)
                    .createAt(new Date())
                    .feedUrl("test_feedUrl")
                    .build());
        }

        Mockito.when(feedDAO.readFeedByRoomIdAndDate(any(Long.class), any(Date.class)))
                .thenReturn(testReadFeedOutDTOs);

        // when
        List<ReadFeedOutDTO> compareReadFeedOutDTOs = feedService.readFeedByRoomIdAndDate(1, new Date());

        // then
        for (int i = 0; i < 4; i++){
            assertEquals(testReadFeedOutDTOs.get(i).getFeedId(), compareReadFeedOutDTOs.get(i).getFeedId());
            assertEquals(testReadFeedOutDTOs.get(i).getFeedUrl(), compareReadFeedOutDTOs.get(i).getFeedUrl());
            assertEquals(testReadFeedOutDTOs.get(i).getUserId(), compareReadFeedOutDTOs.get(i).getUserId());
            assertEquals(testReadFeedOutDTOs.get(i).getContent(), compareReadFeedOutDTOs.get(i).getContent());
            assertEquals(testReadFeedOutDTOs.get(i).getCreateAt(), compareReadFeedOutDTOs.get(i).getCreateAt());
        }
    }

    @Test
    void readFeedByRoomIdAndDate_InvalidService() throws Exception{
        // given
        List<ReadFeedOutDTO> readFeedOutDTOs = new ArrayList<>();

        Mockito.when(feedDAO.readFeedByRoomIdAndDate(any(Long.class), any(Date.class)))
                .thenThrow(new IllegalArgumentException());

        // when - then
        assertThrows(IllegalArgumentException.class, () -> {
            List<ReadFeedOutDTO> testReadFeedOutDTOs
                    = feedService.readFeedByRoomIdAndDate(1, new Date());
        });

    }
}