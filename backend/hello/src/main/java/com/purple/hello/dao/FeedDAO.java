package com.purple.hello.dao;

import com.purple.hello.dto.in.CreateFeedInDTO;
import com.purple.hello.dto.out.CompareFeedByRoomIdOutDTO;
import com.purple.hello.dto.out.CreateFeedOutDTO;
import com.purple.hello.dto.out.ReadFeedOutDTO;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

public interface FeedDAO {
    List<CompareFeedByRoomIdOutDTO> compareFeedByRoomIdAndDate(long roomId, Date date)throws Exception;
    CreateFeedOutDTO createFeed(CreateFeedInDTO createFeedInDTO) throws Exception;
    List<ReadFeedOutDTO> readFeedByRoomIdAndDate(long roomId, Date date)throws Exception;
}
