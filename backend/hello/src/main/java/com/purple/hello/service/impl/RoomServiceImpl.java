package com.purple.hello.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.purple.hello.dao.*;
import com.purple.hello.dto.in.*;
import com.purple.hello.dto.out.*;
import com.purple.hello.dto.tool.*;
import com.purple.hello.encoder.PasswordEncoder;
import com.purple.hello.entity.History;
import com.purple.hello.entity.Question;
import com.purple.hello.entity.Room;
import com.purple.hello.entity.UserRoom;
import com.purple.hello.generator.RoomCode;
import com.purple.hello.repo.HistoryRepo;
import com.purple.hello.repo.QuestionRepo;
import com.purple.hello.repo.RoomRepo;
import com.purple.hello.service.AwsS3Service;
import com.purple.hello.service.RoomService;
import com.purple.hello.util.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;

@Service
public class RoomServiceImpl implements RoomService {
    @Autowired
    private RoomCode roomCode;
    @Autowired
    private final RoomDAO roomDAO;
    @Autowired
    private final HistoryDAO historyDAO;
    @Autowired
    private final UserRoomDAO userRoomDAO;
    @Autowired
    private final QuestionRepo questionRepo;
    @Autowired
    private final HistoryRepo historyRepo;
    @Autowired
    private final RoomRepo roomRepo;
    @Autowired
    private final PasswordEncoder passwordEncoder;
    @Value("${server_addr}")
    private String url;
    private final AwsS3Service awsS3Service;
    private final UserDAO userDAO;

    public RoomServiceImpl(RoomDAO roomDAO, PasswordEncoder passwordEncoder, HistoryDAO historyDAO, QuestionRepo questionRepo, AwsS3Service awsS3Service, UserDAO userDAO, UserRoomDAO userRoomDAO, HistoryRepo historyRepo, RoomRepo roomRepo){
        this.roomDAO = roomDAO;
        this.passwordEncoder = passwordEncoder;
        this.historyDAO = historyDAO;
        this.questionRepo = questionRepo;
        this.awsS3Service = awsS3Service;
        this.userDAO = userDAO;
        this.userRoomDAO = userRoomDAO;
        this.historyRepo = historyRepo;
        this.roomRepo = roomRepo;
    }

    @Override
    public List<ReadRoomOutDTO> readRoomByUserId(long userId) throws Exception {
        return this.roomDAO.readRoomByUserId(userId);
    }

    @Override
    public CreateRoomDTO createRoom(CreateUserRoomInDTO createUserRoomInDTO) throws Exception {
        createUserRoomInDTO.setRoomPassword(passwordEncoder.encode(createUserRoomInDTO.getRoomPassword()));
        return this.roomDAO.createRoom(createUserRoomInDTO);
    }

    @Override
    public boolean comparePasswordByRoomCode(long roomId, String password) throws Exception {
        String storedPassword = this.roomDAO.comparePasswordByRoomCode(roomId);

        return passwordEncoder.matches(password, storedPassword);
    }

    @Override
    public ReadUserRoomJoinOutDTO readUserRoomJoinByRoomId(long roomId) throws Exception {
        return this.roomDAO.readUserRoomJoinByRoomId(roomId);
    }

    @Override
    @Transactional
    public boolean updateRoomPassword(UpdateRoomPasswordInDTO updateRoomPasswordInDTO) throws Exception {
        updateRoomPasswordInDTO.setRoomPassword(passwordEncoder.encode(updateRoomPasswordInDTO.getRoomPassword()));
        return this.roomDAO.updateRoomPassword(updateRoomPasswordInDTO);
    }

    public ReadRoomCodeOutDTO readRoomCodeByRoomId(long roomId) throws JsonProcessingException {
        String url = roomDAO.readRoomCodeByRoomId(roomId);

        ReadRoomCodeOutDTO readRoomCodeOutDTO = new ReadRoomCodeOutDTO();
        if (url == null) {
            String newUrl = saveAndResult(roomId);
            readRoomCodeOutDTO.setRoomCode(newUrl);
            return readRoomCodeOutDTO;
        }
        readRoomCodeOutDTO.setRoomCode(url);
        return readRoomCodeOutDTO;
    }

    @Override
    public void updateRoomCodeByRoomId(UpdateRoomCodeInDTO updateRoomCodeInDTO) {
        roomDAO.updateRoomCodeByRoomId(updateRoomCodeInDTO);
    }

    private String saveAndResult(long roomId) throws JsonProcessingException {
        String resultString = roomCode.makeUrl(roomId);
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.readTree(resultString);
        String newUrl = String.valueOf(jsonNode.get("shortLink")).replaceAll("\"", "");
        UpdateRoomCodeInDTO updateRoomCodeInDTO = new UpdateRoomCodeInDTO();
        updateRoomCodeInDTO.setRoomId(roomId);
        updateRoomCodeInDTO.setRoomCode(newUrl);
        updateRoomCodeByRoomId(updateRoomCodeInDTO);
        return newUrl;
    }

    public boolean deleteRoom(DeleteRoomInDTO deleteRoomInDTO) throws Exception {
        boolean isDeleted = this.roomDAO.deleteRoom(deleteRoomInDTO);
        if (isDeleted) {
            String dirName = "feed/" + deleteRoomInDTO.getRoomId();
            awsS3Service.removeDirectory(dirName);
        }
        return isDeleted;
    }

    public void createQuestion() throws Exception {
        List<Room> roomList = roomDAO.getRoom();
        try {
            if (roomList.size() == 0)
                throw new NullPointerException("Don't Created Room");

            LocalDateTime currentDate = LocalDateTime.now().plusHours(9);
            List<Long> roomListIdxUpper = new ArrayList<>();
            List<QuestionIdRoomIdDTO> roomListIdxLower = new ArrayList<>();

            for (Room room : roomList) {
                LocalDate createdAt = DateUtils.addHours(room.getCreateAt(), 9).toInstant()
                        .atZone(ZoneId.systemDefault())
                        .toLocalDate();
                long result = currentDate.getDayOfYear() - createdAt.getDayOfYear();

                if (result < 9) {    // 방이 생성된지 10일 이전
                    long totalQuestion = questionRepo.count() - 1;
                    long questionId = (result % totalQuestion) + 1;

                    QuestionIdRoomIdDTO questionIdRoomIdDTO = QuestionIdRoomIdDTO.builder()
                            .questionID(questionId)
                            .roomID(room.getRoomId())
                            .build();
                    roomListIdxLower.add(questionIdRoomIdDTO);
                } else {              // 방이 생선된지 10일 이후
                    roomListIdxUpper.add(room.getRoomId());
                }
            }
            // 10일 이전 이면
            if(roomListIdxLower.size() != 0){
                List<History> history = makeData(roomListIdxLower, true);
                historyRepo.saveAll(history);
            }
            // 10일 이후 이면
            if (roomListIdxUpper.size() != 0) {
                // 각 방마다 daily, game, know 타입이 몇 개 있는지
                Map<Long, List<HistoryTypeDTO>> resultMapType = roomDAO.getHistoryTypeCount(roomListIdxUpper);
                // 각 방마다 daily, game, know 타입에 대해서 포스트가 몇 개 있는지 추출
                Map<Long, List<HistoryTypeDTO>> resultMapFeed = roomDAO.getHistoryTypeFeedCount(roomListIdxUpper);
                // 각 방마다 타입에 현재 인덱스를 출력한다.
                Map<Long, List<HistoryCurrent>> resultCurrent = roomDAO.getHistoryCurrent(roomListIdxUpper);
                // 타입마다 최소인덱스, 최대인덱스를 출력한다.
                Map<String, HistoryMinMaxDTO> resultMapMinMax = roomDAO.getHistoryMinMax();
                // 각 방의 멤버 수를 불러온다.
                Map<Long, Integer> memberCount = userRoomDAO.getMemberCount(roomListIdxUpper);

                Map<Long, HistoryTypePythonDTO> pyMap = new HashMap<>();
                for (Long l : roomListIdxUpper) {
                    HistoryTypePythonDTO historyTypePythonDTO = HistoryTypePythonDTO.builder()
                            .memberCount(memberCount.get(l))
                            .types(resultMapType.get(l))
                            .feeds(resultMapFeed.get(l))
                            .current(resultCurrent.get(l))
                            .build();
                    pyMap.put(l, historyTypePythonDTO);
                }
                ObjectMapper mapper = new ObjectMapper();
                String json = mapper.writeValueAsString(pyMap);
                String minMaxJson = mapper.writeValueAsString(resultMapMinMax);
                String result = "[" + minMaxJson + ", " + json + "]";

                // Python Flask API
                HttpHeaders headers = new HttpHeaders();
                headers.setContentType(MediaType.APPLICATION_JSON);

                HttpEntity<String> requestEntity = new HttpEntity<>(result, headers);

                RestTemplate restTemplate = new RestTemplateBuilder().build();
                ResponseEntity<String> data = restTemplate.postForEntity(url, requestEntity, String.class);

                ObjectMapper objectMapper = new ObjectMapper();

                List<QuestionIdRoomIdDTO> pythonList = objectMapper.readValue(data.getBody(), new TypeReference<List<QuestionIdRoomIdDTO>>() {});

                List<History> history = makeData(pythonList, false);

                historyRepo.saveAll(history);
            }

        } catch (NullPointerException e) {
            e.printStackTrace();
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public ReadRoomQuestionOutDTO readRoomQuestionByRoomIdAndUserId(long roomId, long userId) throws Exception {
        ReadRoomQuestionOutDTO readRoomQuestionOutDTO = roomDAO.readRoomQuestionByRoomIdAndUserId(roomId, userId);
        if(readRoomQuestionOutDTO == null)
            throw new IllegalArgumentException();
        return readRoomQuestionOutDTO;
    }

    @Override
    public ReadMemberListOutDTO readMemberListByRoomId(long roomId, long userId) throws Exception {
        List<MemberDTO> memberDTOS = roomDAO.readMemberListByRoomId(roomId, userId);
        if (memberDTOS == null) {
            throw new IllegalArgumentException();
        } else {
            ReadMemberListOutDTO readMemberListOutDTO = new ReadMemberListOutDTO(roomId, memberDTOS);
            return readMemberListOutDTO;
        }
    }

    @Override
    public List<NotificationDTO> makeNotificationForOtherDevicesByRoomId(long userId) throws Exception{
        List<UserRoom> userRooms = userRoomDAO.readUserRoomsByUserId(userId);
        if(userRooms.size() == 0) {
            throw new IllegalArgumentException();
        }
        List<NotificationDTO> notificationDTOS = new ArrayList<>();
        for(UserRoom userRoom:userRooms) {
            List<ReadUserRoomDeviceDTO> readUserRoomDeviceDTOS = userDAO.readOtherDevicesByRoomId(userRoom.getRoom().getRoomId(), userId);
            if(readUserRoomDeviceDTOS.size() == 0) {
                continue;
            }
            for(ReadUserRoomDeviceDTO readUserRoomDeviceDTO : readUserRoomDeviceDTOS) {
                String title = "무동작 감지";
                String content = readUserRoomDeviceDTO.getRoomName() + "의 " + userRoom.getUserName() + "(이)가\n24시간 동안 활동하지 않았습니다.";
                notificationDTOS.add(NotificationDTO
                        .builder()
                        .deviceToken(readUserRoomDeviceDTO.getDeviceToken())
                        .title(title)
                        .content(content)
                        .build());
            }
        }

        return notificationDTOS;
    }
    private List<History> makeData(List<QuestionIdRoomIdDTO> data, boolean type){
        List<History> result = new ArrayList<>();
        for(QuestionIdRoomIdDTO questionIdRoomIdDTO : data){
            Room room = roomRepo.findById(questionIdRoomIdDTO.getRoomID()).get();
            Question question;
            if(type) question = questionRepo.findById(questionIdRoomIdDTO.getQuestionID()).get();
            else question = questionRepo.findByNo(questionIdRoomIdDTO.getQuestionID()).get();

            History history = History.builder()
                            .room(room)
                            .question(question)
                            .createAt(new Date())
                            .build();
            result.add(history);
        }
        return result;
    }
}
