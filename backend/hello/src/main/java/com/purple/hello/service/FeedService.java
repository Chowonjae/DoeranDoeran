package com.purple.hello.service;

import com.purple.hello.dto.in.CreateFeedInDTO;
import com.purple.hello.dto.out.CompareFeedByRoomIdOutDTO;
import com.purple.hello.dto.out.CreateFeedOutDTO;
import com.purple.hello.dto.out.ReadFeedOutDTO;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Date;
import java.util.List;

@Service
public interface FeedService  {
    List<CompareFeedByRoomIdOutDTO> compareFeedByRoomIdAndDate(long roomId, Date date)throws Exception;
    CreateFeedOutDTO createFeed(CreateFeedInDTO createFeedInDTO) throws Exception;
    List<ReadFeedOutDTO> readFeedByRoomIdAndDate(long roomId, Date date)throws Exception;
}
