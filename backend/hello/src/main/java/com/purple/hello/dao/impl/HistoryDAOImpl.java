package com.purple.hello.dao.impl;

import com.purple.hello.dao.HistoryDAO;
import com.purple.hello.dto.out.ReadQuestionOutDTO;
import com.purple.hello.dto.tool.DeviceWithQuestionDTO;
import com.purple.hello.entity.*;
import com.purple.hello.repo.HistoryRepo;
import com.purple.hello.repo.QuestionRepo;
import com.purple.hello.repo.RoomRepo;
import com.purple.hello.util.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Component
public class HistoryDAOImpl implements HistoryDAO {
    @PersistenceContext
    EntityManager em;
    @Autowired
    private HistoryRepo historyRepo;
    @Autowired
    private RoomRepo roomRepo;
    @Autowired
    private QuestionRepo questionRepo;
    private final QHistory qHistory = QHistory.history;
    private final QQuestion qQuestion = QQuestion.question;
    private final QUser qUser = QUser.user;
    private final QUserRoom qUserRoom = QUserRoom.userRoom;
    private final QRoom qRoom = QRoom.room;
    private final QFeed qFeed = QFeed.feed;

    public HistoryDAOImpl(HistoryRepo historyRepo, RoomRepo roomRepo, QuestionRepo questionRepo) {
        this.historyRepo = historyRepo;
        this.roomRepo = roomRepo;
        this.questionRepo = questionRepo;
    }

    @Override
    public List<DeviceWithQuestionDTO> readDevicesWithDailyQuestionByBeginTime(int beginTime) throws Exception {
        Date currentDateKST = DateUtils.addHours(new Date(), 9);
        String currentDateString = DateUtils.format(currentDateKST, "yyyy-MM-dd");
        String sql = "select distinct r.room_id, ur.room_name, u.device_token, q.content " +
                    "from users u " +
                    "join user_rooms ur " +
                    "on u.user_id = ur.user_id " +
                    "join rooms r " +
                    "on ur.room_id = r.room_id " +
                    "join histories h " +
                    "on r.room_id = h.room_id " +
                    "join questions q " +
                    "on h.question_id = q.question_id " +
                    "where r.begin_time = :beginTime " +
                    "and DATE_FORMAT(ADDDATE(h.create_at, INTERVAL 9 HOUR), \"%Y-%m-%d\") = :createAt " +
                    "and u.device_token is not null " +
                    "and ur.user_room_role != :userRoomRole;";
        Query query = em.createNativeQuery(sql);
        query.setParameter("beginTime", beginTime);
        query.setParameter("createAt", currentDateString);
        query.setParameter("userRoomRole", UserRoomRole.ROLE3.name());

        List resultList = query.getResultList();
        List<DeviceWithQuestionDTO> deviceWithQuestionDTOS = new ArrayList<>();
        for(Object object:resultList) {
            Object[] result = (Object[]) object;

            long roomId = ((Number)result[0]).longValue();
            String roomName = (String) result[1];
            String deviceToken = (String) result[2];
            String content = (String)result[3];

            deviceWithQuestionDTOS.add(DeviceWithQuestionDTO
                    .builder()
                    .roomId(roomId)
                    .roomName(roomName)
                    .deviceToken(deviceToken)
                    .content(content)
                    .build());
        }
        return deviceWithQuestionDTOS;
    }

    @Override
    public List<DeviceWithQuestionDTO> readDevicesNotUploadedByBeginTime(int beginTime) throws Exception {
        Date currentDateKST = DateUtils.addHours(new Date(), 9);
        String currentDateString = DateUtils.format(currentDateKST, "yyyy-MM-dd");

        String sql = "select distinct r.room_id, ur.room_name, u.device_token, q.content " +
                    "from users u " +
                    "join user_rooms ur " +
                    "on u.user_id = ur.user_id " +
                    "join rooms r " +
                    "on ur.room_id = r.room_id " +
                    "join histories h " +
                    "on r.room_id = h.room_id " +
                    "join questions q " +
                    "on h.question_id = q.question_id " +
                    "where r.begin_time = :beginTime " +
                    "and DATE_FORMAT(ADDDATE(h.create_at, INTERVAL 9 HOUR), \"%Y-%m-%d\") = :createAt " +
                    "and u.device_token is not null " +
                    "and ur.user_room_role != :userRoomRole " +
                    "and ur.user_room_id not in " +
                        "(select ur.user_room_id " +
                        "from users u " +
                        "join user_rooms ur " +
                        "on u.user_id = ur.user_id " +
                        "join feeds f " +
                        "on ur.user_room_id = f.user_room_id " +
                        "where DATE_FORMAT(ADDDATE(f.create_at, INTERVAL 9 HOUR), \"%Y-%m-%d\") = :createAt ) " +
                        "and ur.user_room_role != :userRoomRole;";

        Query query = em.createNativeQuery(sql);
        query.setParameter("beginTime", beginTime);
        query.setParameter("createAt", currentDateString);
        query.setParameter("userRoomRole", UserRoomRole.ROLE3.name());
        
        List resultList = query.getResultList();
        List<DeviceWithQuestionDTO> deviceWithQuestionDTOS = new ArrayList<>();
        for(Object object:resultList) {
            Object[] result = (Object[]) object;

            long roomId = ((Number)result[0]).longValue();
            String roomName = (String) result[1];
            String deviceToken = (String) result[2];
            String content = (String)result[3];

            deviceWithQuestionDTOS.add(DeviceWithQuestionDTO
                    .builder()
                    .roomId(roomId)
                    .roomName(roomName)
                    .deviceToken(deviceToken)
                    .content(content)
                    .build());
        }
        return deviceWithQuestionDTOS;
    }

    @Override
    public void createFirstHistory(Long roomId) {
        Room room = roomRepo.findById(roomId).get();
        Question question = questionRepo.findById(1L).get();
        History history = History.builder()
                .room(room)
                .question(question)
                .createAt(new Date())
                .build();
        historyRepo.save(history);
    }

    @Override
    public ReadQuestionOutDTO readQuestionByRoomIdAndDate(long roomId, Date currentDateKST) throws IOException {
        String currentDateString = DateUtils.format(currentDateKST, "yyyy-MM-dd");
        String sql = "select q.question_id, q.content " +
                "from histories h " +
                "join questions q " +
                "on h.question_id = q.question_id " +
                "where h.room_id = :roomId and DATE_FORMAT(ADDDATE(h.create_at, INTERVAL 9 HOUR), \"%Y-%m-%d\") = :createAt " +
                "limit 1;";
        Query query = em.createNativeQuery(sql);
        query.setParameter("roomId", roomId);
        query.setParameter("createAt", currentDateString);

        List resultList = query.getResultList();

        for (Object object:resultList){
            Object [] result = (Object[]) object;

            long questionId = ((Number) result[0]).longValue();
            String content = ((String) result[1]);

            return ReadQuestionOutDTO.builder()
                    .content(content)
                    .questionId(questionId)
                    .build();

        }
        return null;
    }
}
