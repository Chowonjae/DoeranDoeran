package com.purple.hello.dao.impl;

import com.purple.hello.dao.UserRoomDAO;
import com.purple.hello.dto.in.*;
import com.purple.hello.dto.out.CreateRoomOutDTO;
import com.purple.hello.dto.out.CreateUserRoomJoinOutDTO;
import com.purple.hello.dto.tool.HistoryTypeDTO;
import com.purple.hello.entity.*;
import com.purple.hello.enu.BoolAlarm;
import com.purple.hello.enu.UserRoomRole;
import com.purple.hello.repo.RoomRepo;
import com.purple.hello.repo.UserRepo;
import com.purple.hello.repo.UserRoomRepo;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAUpdateClause;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.*;

@Component
public class UserRoomDAOImpl implements UserRoomDAO {

    @PersistenceContext
    private EntityManager em;
    private final QUserRoom qUserRoom = QUserRoom.userRoom;
    @Autowired
    private final UserRoomRepo userRoomRepo;
    @Autowired
    private final RoomRepo roomRepo;
    @Autowired
    private final UserRepo userRepo;
    public UserRoomDAOImpl(UserRoomRepo userRoomRepo, RoomRepo roomRepo, UserRepo userRepo){
        this.userRoomRepo = userRoomRepo;
        this.roomRepo = roomRepo;
        this.userRepo = userRepo;
    }
    @Override
    public CreateRoomOutDTO createUserRoom(CreateUserRoomInDTO createUserRoomInDTO, long roomId) {
        Optional<Room> room = this.roomRepo.findById(roomId);
        Optional<User> user = this.userRepo.findById(createUserRoomInDTO.getUserId());

        if (room.isEmpty() || user.isEmpty())
            throw new IllegalArgumentException();

        UserRoom userRoom = UserRoom.builder()
                .userRoomRole(UserRoomRole.ROLE1)
                .userName(createUserRoomInDTO.getUserName())
                .roomName(createUserRoomInDTO.getRoomName())
                .safeAlarm(BoolAlarm.Y)
                .moveAlarm(BoolAlarm.Y)
                .dayAlarm(BoolAlarm.Y)
                .createAt(new Date())
                .room(room.get())
                .user(user.get())
                .build();

        room.get().getUserRoom().add(userRoom);
        user.get().getUserRoom().add(userRoom);

        UserRoom rUserRoom = this.userRoomRepo.save(userRoom);

        CreateRoomOutDTO createRoomOutDTO = CreateRoomOutDTO.builder()
                .userRoomId(rUserRoom.getUserRoomId())
                .roomId(room.get().getRoomId())
                .userRoomRole(rUserRoom.getUserRoomRole())
                .userName(rUserRoom.getUserName())
                .roomName(rUserRoom.getRoomName())
                .safeAlarm(rUserRoom.getSafeAlarm())
                .moveAlarm(rUserRoom.getSafeAlarm())
                .dayAlarm(rUserRoom.getDayAlarm())
                .createAt(rUserRoom.getCreateAt())
                .build();

        return createRoomOutDTO;
    }

    @Override
    public CreateUserRoomJoinOutDTO createUserRoomJoin(CreateUserRoomJoinInDTO createUserRoomJoinInDTO) throws Exception{
        Optional<Room> room = this.roomRepo.findById(createUserRoomJoinInDTO.getRoomId());
        Optional<User> user = this.userRepo.findById(createUserRoomJoinInDTO.getUserId());

        if (room.isEmpty() || user.isEmpty())
            throw new IllegalArgumentException();

        UserRoom userRoom = UserRoom.builder()
                .createAt(new Date())
                .dayAlarm(BoolAlarm.Y)
                .moveAlarm(BoolAlarm.Y)
                .safeAlarm(BoolAlarm.Y)
                .roomName(createUserRoomJoinInDTO.getRoomName())
                .userName(createUserRoomJoinInDTO.getUserName())
                .userRoomRole(UserRoomRole.ROLE2)
                .user(user.get())
                .room(room.get())
                .build();

        room.get().getUserRoom().add(userRoom);
        user.get().getUserRoom().add(userRoom);

        UserRoom rUserRoom = this.userRoomRepo.save(userRoom);

        CreateUserRoomJoinOutDTO createUserRoomJoinOutDTO = CreateUserRoomJoinOutDTO
                .builder()
                .createAt(rUserRoom.getCreateAt())
                .userRoomRole(rUserRoom.getUserRoomRole())
                .userRoomId(rUserRoom.getUserRoomId())
                .userName(rUserRoom.getUserName())
                .roomName(rUserRoom.getRoomName())
                .dayAlarm(rUserRoom.getDayAlarm())
                .moveAlarm(rUserRoom.getMoveAlarm())
                .safeAlarm(rUserRoom.getSafeAlarm())
                .build();

        return createUserRoomJoinOutDTO;
    }

    @Override
    public boolean updateRoomName(UpdateRoomNameInDTO updateRoomNameInDTO) throws Exception{
        JPAUpdateClause jpaUpdateClause = new JPAUpdateClause(em, qUserRoom);
        long updatedRowCount = jpaUpdateClause.set(qUserRoom.roomName, updateRoomNameInDTO.getRoomName())
                .where(qUserRoom.userRoomId.eq(updateRoomNameInDTO.getUserRoomId())
                        .and(qUserRoom.user.userId.eq(updateRoomNameInDTO.getUserId())))
                .execute();



        if(updatedRowCount == 0)
            throw new IllegalAccessException();

        else
            return true;
    }

    @Override
    public boolean updateUserName(UpdateUserNameInDTO updateUserNameInDTO) throws Exception{
        JPAUpdateClause jpaUpdateClause = new JPAUpdateClause(em, qUserRoom);
        long updatedRowCount = jpaUpdateClause.set(qUserRoom.userName, updateUserNameInDTO.getUserName())
                .where(qUserRoom.userRoomId.eq(updateUserNameInDTO.getUserRoomId())
                        .and(qUserRoom.user.userId.eq(updateUserNameInDTO.getUserId())))
                .execute();

        if(updatedRowCount == 0)
            throw new IllegalArgumentException();

        return true;
    }

    @Override
    public boolean updateMoveAlarm(UpdateMoveAlarmInDTO updateMoveAlarmInDTO) throws Exception{
        JPAUpdateClause jpaUpdateClause = new JPAUpdateClause(em, qUserRoom);
        long updatedRowCount = jpaUpdateClause.set(qUserRoom.moveAlarm, updateMoveAlarmInDTO.getMoveAlarm())
                .where(qUserRoom.userRoomId.eq(updateMoveAlarmInDTO.getUserRoomId())
                        .and(qUserRoom.user.userId.eq(updateMoveAlarmInDTO.getUserId())))
                .execute();

        if(updatedRowCount == 0)
            throw new IllegalArgumentException();

        return true;
    }

    @Override
    public boolean updateSafeAlarm(UpdateSafeAlarmInDTO updateSafeAlarmInDTO) throws Exception{
        JPAUpdateClause jpaUpdateClause = new JPAUpdateClause(em, qUserRoom);
        long updatedRowCount = jpaUpdateClause.set(qUserRoom.safeAlarm, updateSafeAlarmInDTO.getSafeAlarm())
                .where(qUserRoom.userRoomId.eq(updateSafeAlarmInDTO.getRoomId())
                        .and(qUserRoom.user.userId.eq(updateSafeAlarmInDTO.getUserId())))
                .execute();
        if(updatedRowCount == 0)
            throw new IllegalArgumentException();

        return true;
    }

    @Override
    public void deleteUserRoom(DeleteUserRoomInDTO deleteUserRoomInDTO) {
        JPAUpdateClause jpaUpdateClause = new JPAUpdateClause(em, qUserRoom);
        jpaUpdateClause.set(qUserRoom.userRoomRole, UserRoomRole.ROLE3)
                .where(qUserRoom.userRoomId.eq(deleteUserRoomInDTO.getUserRoomId())
                        .and(qUserRoom.user.userId.eq(deleteUserRoomInDTO.getUserId())))
                .execute();
    }

    @Override
    public UserRoom readUserRoomByUserIdAndRoomId(long userId, long roomId) {
        UserRoom userRoom = new JPAQuery<>(em)
                .select(qUserRoom)
                .from(qUserRoom)
                .where(qUserRoom.room.roomId.eq(roomId).and(qUserRoom.user.userId.eq(userId)))
                .fetchOne();
        return userRoom;
    }

    @Override
    public CreateUserRoomJoinOutDTO updateUserRoomRejoin(CreateUserRoomJoinInDTO createUserRoomJoinInDTO) throws Exception{
        UserRoom userRoom = readUserRoomByUserIdAndRoomId(createUserRoomJoinInDTO.getUserId(), createUserRoomJoinInDTO.getRoomId());
        JPAUpdateClause jpaUpdateClause = new JPAUpdateClause(em, qUserRoom);
        long updatedRowCount = jpaUpdateClause.set(qUserRoom.userRoomRole, UserRoomRole.ROLE2)
                .set(qUserRoom.userName, createUserRoomJoinInDTO.getUserName())
                .set(qUserRoom.roomName, createUserRoomJoinInDTO.getRoomName())
                .where(qUserRoom.userRoomId.eq(userRoom.getUserRoomId()))
                .where(qUserRoom.userRoomRole.eq(UserRoomRole.ROLE3))
                .execute();
        if(updatedRowCount == 0) {
            throw new IllegalArgumentException();
        }else {
            if(em.contains(userRoom)){
                em.refresh(userRoom);
            }
            UserRoom rUserRoom = userRoomRepo.findById(userRoom.getUserRoomId()).get();
            CreateUserRoomJoinOutDTO createUserRoomJoinOutDTO = CreateUserRoomJoinOutDTO
                    .builder()
                    .createAt(rUserRoom.getCreateAt())
                    .userRoomRole(rUserRoom.getUserRoomRole())
                    .userRoomId(rUserRoom.getUserRoomId())
                    .userName(rUserRoom.getUserName())
                    .roomName(rUserRoom.getRoomName())
                    .dayAlarm(rUserRoom.getDayAlarm())
                    .moveAlarm(rUserRoom.getMoveAlarm())
                    .safeAlarm(rUserRoom.getSafeAlarm())
                    .build();
            return createUserRoomJoinOutDTO;
        }
    }

    @Override
    public void updateUserRoomRoleByUserRoomId(long userRoomId, UserRoomRole userRoomRole) {
        JPAUpdateClause jpaUpdateClause = new JPAUpdateClause(em, qUserRoom);
        jpaUpdateClause.set(qUserRoom.userRoomRole, userRoomRole)
                .where(qUserRoom.userRoomId.eq(userRoomId))
                .execute();
    }

    @Override
    public UserRoom readUserRoomByUserRoomId(long userRoomId) throws Exception {
        UserRoom userRoom = new JPAQuery<>(em)
                .select(qUserRoom.userRoom)
                .from(qUserRoom)
                .where(qUserRoom.userRoomId.eq(userRoomId))
                .fetchOne();

        if (userRoom == null)
            throw new IllegalArgumentException();

        return userRoom;
    }

    @Override
    public List<UserRoom> readUserRoomsByRoomIdWithoutUserRoomIdUsingLimit(long roomId, long userRoomId, int limit) {
        List<UserRoom> userRooms = new JPAQuery<>(em)
                .select(qUserRoom)
                .from(qUserRoom)
                .where(qUserRoom.room.roomId.eq(roomId)
                        .and(qUserRoom.userRoomId.ne(userRoomId))
                        .and(qUserRoom.userRoomRole.ne(UserRoomRole.ROLE3)))
                .limit(limit)
                .fetch();
        return userRooms;
    }

    @Override
    public Map<Long, Integer> getMemberCount(List<Long> roomList) {
        String jpql = "SELECT count(user_id) as memberCount, room_id " +
                "FROM user_rooms " +
                "WHERE room_id in (:roomList) " +
                "group by room_id";
        Query query = em.createNativeQuery(jpql);
        query.setParameter("roomList", roomList);
        List result = query.getResultList();

        if(result.size() == 0) return null;

        Map<Long, Integer> map = new HashMap<>();

        for(Object o : result){
            Object[] results = (Object[]) o;

            map.put(Long.parseLong(results[0].toString()), Integer.parseInt(results[1].toString()));
        }
        return map;
    }

    @Override
    public List<UserRoom> readUserRoomsByUserId(long userId) {
        return new JPAQuery<>(em)
                .select(qUserRoom)
                .from(qUserRoom)
                .where(qUserRoom.user.userId.eq(userId))
                .where(qUserRoom.userRoomRole.ne(UserRoomRole.ROLE3))
                .fetch();
    }
}
