package com.purple.hello.dao.impl;

import com.purple.hello.dao.FeedDAO;
import com.purple.hello.dto.in.CreateFeedInDTO;
import com.purple.hello.dto.out.CompareFeedByRoomIdOutDTO;
import com.purple.hello.dto.out.CreateFeedOutDTO;
import com.purple.hello.dto.out.ReadFeedOutDTO;
import com.purple.hello.dto.tool.DeviceWithQuestionDTO;
import com.purple.hello.dto.tool.UserIdDTO;
import com.purple.hello.dto.tool.UserIdDateDTO;
import com.purple.hello.entity.*;
import com.purple.hello.repo.FeedRepo;
import com.purple.hello.repo.UserRoomRepo;
import com.purple.hello.util.DateUtils;
import com.querydsl.core.types.ConstantImpl;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.*;
import com.querydsl.jpa.impl.JPAQuery;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Component
public class FeedDAOImpl implements FeedDAO {
    @PersistenceContext
    EntityManager em;
    private final QFeed qFeed = QFeed.feed;
    private final QUser qUser = QUser.user;
    private final QUserRoom qUserRoom = QUserRoom.userRoom;
    private final QRoom qRoom = QRoom.room;
    private final FeedRepo feedRepo;
    private final UserRoomRepo userRoomRepo;
    public FeedDAOImpl(FeedRepo feedRepo, UserRoomRepo userRoomRepo){
        this.feedRepo = feedRepo;
        this.userRoomRepo = userRoomRepo;
    }

    @Override
    public List<CompareFeedByRoomIdOutDTO> compareFeedByRoomIdAndDate(long roomId, Date date) throws Exception{
        // 특정 그룹방에서 특정 날짜에 피드를 올린 유저의 ID를 반환
        Date dateKST = DateUtils.addHours(date, 9);
        String dateString = DateUtils.format(dateKST, "yyyy-MM-dd");
        String sql = "select u.user_id, f.create_at " +
                    "from rooms r " +
                    "join user_rooms ur " +
                    "on r.room_id = ur.room_id " +
                    "join users u " +
                    "on u.user_id = ur.user_id " +
                    "join feeds f " +
                    "on f.user_room_id = ur.user_room_id " +
                    "where r.room_id = :roomId and DATE_FORMAT(ADDDATE(f.create_at, INTERVAL 9 HOUR), \"%Y-%m-%d\") = :createAt ;";
        Query query = em.createNativeQuery(sql);
        query.setParameter("roomId", roomId);
        query.setParameter("createAt", dateString);
        List resultList = query.getResultList();
        List<UserIdDateDTO> userIdDateDTOs = new ArrayList<>();
        for(Object object:resultList) {
            Object[] result = (Object[]) object;

            long userId = ((Number)result[0]).longValue();
            Date createAt = (Date)result[1];

            userIdDateDTOs.add(UserIdDateDTO.builder()
                    .userId(userId)
                    .date(DateUtils.addHours(createAt, 9))
                    .build());
        }

        // 특정 그룹방의 모든 회원을 반환
        List<UserIdDTO> allUserIdDTOs = new JPAQuery<>(em)
                .select(Projections.constructor(UserIdDTO.class, qUser.userId))
                .distinct()
                .from(qUser)
                .join(qUserRoom)
                .on(qUser.userId.eq(qUserRoom.user.userId))
                .join(qRoom)
                .on(qRoom.roomId.eq(qUserRoom.room.roomId))
                .fetch();

        List<CompareFeedByRoomIdOutDTO> compareFeedByRoomIdOutDTOs = new ArrayList<>();

        if (userIdDateDTOs.isEmpty()) {
            for (UserIdDTO allUserIdDTO : allUserIdDTOs){
                compareFeedByRoomIdOutDTOs.add(new CompareFeedByRoomIdOutDTO(allUserIdDTO.getUserId(), false));
            }
            return compareFeedByRoomIdOutDTOs;
        }

        List<UserIdDTO> userIdDTOs = new ArrayList<>();

        for (UserIdDateDTO userIdDateDTO : userIdDateDTOs){
            userIdDTOs.add(new UserIdDTO(userIdDateDTO.getUserId()));
        }

        // 특정 그룹방 모든 인원과 피드를 올린 인원을 비교하여 DTO에 값 부여
        lp : for (UserIdDTO allUserIdDTO : allUserIdDTOs){
            for (UserIdDTO userIdDTO:userIdDTOs){
                if (userIdDTO.getUserId() == allUserIdDTO.getUserId()){
                    compareFeedByRoomIdOutDTOs.add(new CompareFeedByRoomIdOutDTO(userIdDTO.getUserId(), true));
                        continue lp;
                }
            }
            compareFeedByRoomIdOutDTOs.add(new CompareFeedByRoomIdOutDTO(allUserIdDTO.getUserId(), false));
        }

        return compareFeedByRoomIdOutDTOs;
    }

    @Override
    public CreateFeedOutDTO createFeed(CreateFeedInDTO createFeedInDTO) throws Exception{
        if (this.userRoomRepo.findById(createFeedInDTO.getUserRoomId()).isEmpty())
            throw new IllegalArgumentException();

        // feed 데이터 저장
        Feed feed = Feed.builder()
                .feedUrl(createFeedInDTO.getFeedUrl())
                .content(createFeedInDTO.getContent())
                .feedType(createFeedInDTO.getFeedType())
                .createAt(new Date())
                .build();

        // 연관관계 설정
        UserRoom userRoom = this.userRoomRepo.getById(createFeedInDTO.getUserRoomId());

        userRoom.getFeed().add(feed);
        feed.setUserRoom(userRoom);

        // DB(영속성 콘텍스트) 저장
        Feed rFeed = this.feedRepo.save(feed);

        // 반환 객체 초기화
        CreateFeedOutDTO createFeedOutDTO = CreateFeedOutDTO
                .builder()
                .feedId(rFeed.getFeedId())
                .content(rFeed.getContent())
                .feedType(rFeed.getFeedType())
                .feedUrl(rFeed.getFeedUrl())
                .createAt(DateUtils.addHours(rFeed.getCreateAt(), 9))
                .userRoomId(userRoom.getUserRoomId())
                .build();

        // 반환
        return createFeedOutDTO;
    }

    @Override
    public List<ReadFeedOutDTO> readFeedByRoomIdAndDate(long roomId, Date date) throws Exception{
        Date dateKST = DateUtils.addHours(date, 9);
        String dateString = DateUtils.format(dateKST, "yyyy-MM-dd");
        String sql = "select f.feed_id, f.feed_url, f.content, u.user_id, f.create_at " +
                "from users u " +
                "join user_rooms ur " +
                "on u.user_id = ur.user_id " +
                "join rooms r " +
                "on r.room_id = ur.room_id " +
                "join feeds f " +
                "on f.user_room_id = ur.user_room_id " +
                "where r.room_id = :roomId " +
                "and DATE_FORMAT(ADDDATE(f.create_at, INTERVAL 9 HOUR), \"%Y-%m-%d\") = :createAt ;";
        Query query = em.createNativeQuery(sql);
        query.setParameter("roomId", roomId);
        query.setParameter("createAt", dateString);
        List resultList = query.getResultList();
        List<ReadFeedOutDTO> readFeedOutDTOS = new ArrayList<>();
        for(Object object:resultList) {
            Object[] result = (Object[]) object;

            long feedId = ((Number)result[0]).longValue();
            String feedUrl = (String) result[1];
            String content = (String) result[2];
            long userId = ((Number)result[3]).longValue();
            Date createAt = (Date) result[4];

            readFeedOutDTOS.add(ReadFeedOutDTO
                    .builder()
                    .feedId(feedId)
                    .feedUrl(feedUrl)
                    .content(content)
                    .userId(userId)
                    .createAt(DateUtils.addHours(createAt, 9))
                    .build());
        }
        return readFeedOutDTOS;
    }
}
