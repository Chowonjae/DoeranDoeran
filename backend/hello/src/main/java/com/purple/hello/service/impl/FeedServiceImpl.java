package com.purple.hello.service.impl;

import com.purple.hello.dao.FeedDAO;
import com.purple.hello.dao.RoomDAO;
import com.purple.hello.dto.in.CreateFeedInDTO;
import com.purple.hello.dto.out.CompareFeedByRoomIdOutDTO;
import com.purple.hello.dto.out.CreateFeedOutDTO;
import com.purple.hello.dto.out.ReadFeedOutDTO;
import com.purple.hello.dto.tool.AwsS3DTO;
import com.purple.hello.entity.Room;
import com.purple.hello.service.AwsS3Service;
import com.purple.hello.service.FeedService;
import com.purple.hello.service.FileService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Date;
import java.util.List;

@Service
public class FeedServiceImpl implements FeedService {
    private final AwsS3Service awsS3Service;
    private final FileService fileService;
    private final FeedDAO feedDAO;
    private final RoomDAO roomDAO;
    public FeedServiceImpl(AwsS3Service awsS3Service, FileService fileService, FeedDAO feedDAO, RoomDAO roomDAO){
        this.awsS3Service = awsS3Service;
        this.fileService = fileService;
        this.feedDAO = feedDAO;
        this.roomDAO = roomDAO;
    }

    @Override
    public List<CompareFeedByRoomIdOutDTO> compareFeedByRoomIdAndDate(long roomId, Date date) throws Exception{
        return this.feedDAO.compareFeedByRoomIdAndDate(roomId, date);
    }

    @Override
    public CreateFeedOutDTO createFeed(CreateFeedInDTO createFeedInDTO) throws Exception {
        MultipartFile multipartFile = createFeedInDTO.getFeedFile();

        if(multipartFile == null)
            throw new IllegalArgumentException();

        String fileName = multipartFile.getOriginalFilename();
        String fileExtension = fileService.getExtension(fileName);
        String[] imageExtensions = {"jpg", "jpeg", "png", "bmp", "webp"};
        if(!(fileService.compareExtensions(fileExtension, imageExtensions)))
            throw new IllegalArgumentException();

        Room room = roomDAO.readRoomByUserRoomId(createFeedInDTO.getUserRoomId());
        String dirname = "feed" + "/" + room.getRoomId();
        AwsS3DTO awsS3DTO = awsS3Service.upload(multipartFile, dirname);

        if(awsS3DTO == null)
            throw new IllegalArgumentException();

        else {
            createFeedInDTO.setFeedUrl(awsS3DTO.getPath());
            return this.feedDAO.createFeed(createFeedInDTO);
        }
    }

    @Override
    public List<ReadFeedOutDTO> readFeedByRoomIdAndDate(long roomId, Date date) throws Exception{
        return this.feedDAO.readFeedByRoomIdAndDate(roomId, date);
    }
}
