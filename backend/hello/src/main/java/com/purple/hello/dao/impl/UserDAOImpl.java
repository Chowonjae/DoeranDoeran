package com.purple.hello.dao.impl;

import com.purple.hello.dao.UserDAO;
import com.purple.hello.dto.in.UpdateUserInfoInDTO;
import com.purple.hello.dto.out.ReadUserInfoOutDTO;
import com.purple.hello.dto.tool.ReadUserRoomDeviceDTO;
import com.purple.hello.entity.QRoom;
import com.purple.hello.entity.QUser;
import com.purple.hello.entity.QUserRoom;
import com.purple.hello.entity.User;
import com.purple.hello.enu.UserRoomRole;
import com.purple.hello.repo.UserRepo;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAUpdateClause;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;

@Component
public class UserDAOImpl implements UserDAO {
    @PersistenceContext
    private EntityManager em;
    private final UserRepo userRepo;
    private final QUser qUser = QUser.user;
    private final QUserRoom qUserRoom = QUserRoom.userRoom;
    private final QRoom qRoom = QRoom.room;
    @Autowired
    public UserDAOImpl(UserRepo userRepo) {
        this.userRepo = userRepo;
    }
    @Override
    public User readUserByOauthId(String oauthId) throws Exception{
        Optional<User> isUser = userRepo.findByOauthId(oauthId);
        return isUser.orElse(null);
    }

    @Override
    public User insertUser(User user) {
        return userRepo.save(user);
    }

    @Override
    @Transactional
    public void updateRefreshToken(long userId, String refreshToken) {
        User user = em.find(User.class, userId);
        user.setRefreshToken(refreshToken);
        em.merge(user);
    }

    @Override
    public User isValidRefreshToken(String refreshToken) {
        return userRepo.findByRefreshToken(refreshToken);
    }

    @Override
    public boolean updateUserInfo(UpdateUserInfoInDTO updateUserInfoInDTO) throws Exception {
        JPAUpdateClause jpaUpdateClause = new JPAUpdateClause(em, qUser);
        jpaUpdateClause.set(qUser.birth, updateUserInfoInDTO.getBirth())
                .set(qUser.userProfileUrl, updateUserInfoInDTO.getProfileURL())
                .where(qUser.userId.eq(updateUserInfoInDTO.getUserId()))
                .execute();
        return true;
    }

    @Override
    public ReadUserInfoOutDTO readUserInfoByUserId(long userId) {
        ReadUserInfoOutDTO readUserInfoOutDTO = new JPAQuery<>(em)
                .select(Projections.constructor(ReadUserInfoOutDTO.class, qUser.userId, qUser.birth, qUser.userProfileUrl))
                .from(qUser)
                .where(qUser.userId.eq(userId))
                .fetchOne();
        return readUserInfoOutDTO;
    }

    @Override
    public List<ReadUserRoomDeviceDTO> readOtherDevicesByRoomId(long roomId, long userId) {
        return new JPAQuery<>(em)
                .select(Projections.constructor(ReadUserRoomDeviceDTO.class, qUserRoom.roomName, qUser.deviceToken))
                .from(qUser)
                .join(qUserRoom)
                .on(qUser.userId.eq(qUserRoom.user.userId))
                .join(qRoom)
                .on(qUserRoom.room.roomId.eq(qRoom.roomId))
                .where(qRoom.roomId.eq(roomId))
                .where(qUser.userId.ne(userId))
                .where(qUserRoom.roomName.isNotNull())
                .where(qUser.deviceToken.isNotNull())
                .where(qUserRoom.userRoomRole.ne(UserRoomRole.ROLE3))
                .fetch();
    }

    public void updateDeviceToken(User user) {
        JPAUpdateClause jpaUpdateClause = new JPAUpdateClause(em, qUser);
        jpaUpdateClause.set(qUser.deviceToken, user.getDeviceToken())
                .where(qUser.userId.eq(user.getUserId()))
                .execute();
    }
}
