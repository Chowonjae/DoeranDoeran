package com.purple.hello.service.impl;

import com.google.api.client.json.JsonFactory;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.purple.hello.dao.UserDAO;
import com.purple.hello.dto.in.OauthUserInputDTO;
import com.purple.hello.dto.in.UpdateUserInfoInDTO;
import com.purple.hello.dto.out.ReadUserInfoOutDTO;
import com.purple.hello.entity.User;
import com.purple.hello.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken.Payload;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Collections;

@Service
public class UserServiceImpl implements UserService {
    private final UserDAO userDAO;
    @Value("${google.CLIENT_ID}")
    private String CLIENT_ID;

    @Autowired
    public UserServiceImpl(UserDAO userDAO){
        this.userDAO = userDAO;
    }
    /**
     * 응답 받은 카카오 서버용 액세스토큰 요청한 뒤 유저 정보를 응답 받음
     * */
    @Override
    public ResponseEntity<String> getKakaoUserInfoWithAccessToken(String token) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + token);
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(params, headers);

        String url = "https://kapi.kakao.com/v2/user/me";

        RestTemplate restTemplate = new RestTemplateBuilder().build();
        return restTemplate.postForEntity(url, request, String.class);
    }

    @Override
    public Payload googleIdTokenVerify(String idTokenString) throws GeneralSecurityException, IOException {
        HttpTransport transport = new NetHttpTransport();
        JsonFactory jsonFactory = new JacksonFactory();
        GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(transport, jsonFactory)
                .setAudience(Collections.singletonList(CLIENT_ID))
                .build();
        GoogleIdToken idToken = verifier.verify(idTokenString);
        if(idToken != null){
            return idToken.getPayload();
        }
        return null;
    }

    @Override
    public User readUserByOauthId(String oauthId) throws Exception{
        return userDAO.readUserByOauthId(oauthId);
    }

    @Override
    public User insertUser(OauthUserInputDTO oauthUserInputDTO) {
        User user = new User();
        user.setOauthId(oauthUserInputDTO.getOauthId());
        return userDAO.insertUser(user);
    }

    @Override
    public void updateRefreshToken(long userId, String refreshToken) {
        userDAO.updateRefreshToken(userId, refreshToken);
    }

    @Override
    public User isValidRefreshToken(String refreshToken) {
        return userDAO.isValidRefreshToken(refreshToken);
    }

    @Override
    @Transactional
    public boolean updateUserInfo(UpdateUserInfoInDTO updateUserInfoInDTO) throws Exception {
        return userDAO.updateUserInfo(updateUserInfoInDTO);
    }

    @Override
    public ReadUserInfoOutDTO readUserInfoByUserId(long userId) {
        return userDAO.readUserInfoByUserId(userId);
    }

    @Override
    @Transactional
    public void updateDeviceToken(User user) {
        userDAO.updateDeviceToken(user);
    }
}
