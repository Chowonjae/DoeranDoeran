package com.purple.hello.controller;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken.Payload;
import com.google.api.client.util.NullValue;
import com.purple.hello.dto.in.OauthUserInputDTO;
import com.purple.hello.entity.User;
import com.purple.hello.jwt.JwtTokenProvider;
import com.purple.hello.service.UserService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.json.JSONObject;

import org.springframework.web.bind.annotation.*;

import javax.annotation.Nullable;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Date;

@RestController
@RequestMapping(value = "/account", produces = "application/json; charset=utf-8")
public class AccountController {
    @Autowired
    private JwtTokenProvider jwtTokenProvider;
    @Autowired
    private final UserService userService;
    @Value("${jwt.secret}")
    private String secret;

    AccountController(UserService userService){
        this.userService = userService;
    }

    @GetMapping("/login/kakao")
    public ResponseEntity<String> loginKakao(@RequestHeader("id-token") String token, @RequestHeader(value = "device-token", required = false) String deviceToken, HttpServletResponse httpServletResponse)throws Exception{
        ResponseEntity<String> response = userService.getKakaoUserInfoWithAccessToken(token);
        JSONObject jsonObject = new JSONObject(response.getBody());
        String oauthId = jsonObject.get("id").toString();

        String[] tokens = loginLogic(oauthId, deviceToken);
        httpServletResponse.addHeader("Access-Token", tokens[0]);
        httpServletResponse.addHeader("Refresh-Token", tokens[1]);

        return ResponseEntity.status(HttpStatus.OK).body("LOGIN SUCCESS");
    }

    @GetMapping("/login/google")
    public ResponseEntity<String> loginGoogle(@RequestHeader("id-token") String token, @RequestHeader(value = "device-token", required = false) String deviceToken, HttpServletResponse httpServletResponse) throws Exception {
        Payload payload = userService.googleIdTokenVerify(token);
        if(payload != null){
            String oauthId = payload.getSubject();

            String[] tokens = loginLogic(oauthId, deviceToken);
            httpServletResponse.addHeader("Access-Token", tokens[0]);
            httpServletResponse.addHeader("Refresh-Token", tokens[1]);

            return ResponseEntity.status(HttpStatus.OK).body("LOGIN SUCCESS");
        }
        return ResponseEntity.status(HttpStatus.OK).body("LOGIN FAIL");
    }

    @GetMapping("/reissue")
    public ResponseEntity<?> reissue(@RequestHeader("Access-Token") String accessToken,
                                     @RequestHeader("Refresh-Token") String refreshToken,
                                     HttpServletResponse response){
        if(accessToken == null){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Access Token Expired");
        }

        User user = userService.isValidRefreshToken(refreshToken);

        if(user != null){
            try{
                Claims claims = Jwts.parser().setSigningKey(secret).parseClaimsJws(refreshToken).getBody();
                Date expiration = claims.getExpiration();
                if(expiration.before(new Date(System.currentTimeMillis()))){
                    throw new JwtException("Expired JWT token");
                }
                accessToken = jwtTokenProvider.createAccessToken(String.valueOf(user.getUserId()));
                refreshToken = jwtTokenProvider.createRefreshToken(String.valueOf(user.getUserId()));

                userService.updateRefreshToken(user.getUserId(), refreshToken);

                response.addHeader("Access-Token", accessToken);
                response.addHeader("Refresh-Token", refreshToken);
                return ResponseEntity.status(HttpStatus.OK).body("REISSUE SUCCESS");
            } catch (JwtException e){
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Refresh Token Expired");
            }
        }else{
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Refresh Token Expired");
        }
    }

    @GetMapping("/logout")
    public ResponseEntity<String> logout(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse){
        long userId = (long) httpServletRequest.getAttribute("userId");

        httpServletResponse.addHeader("Access-Token", null);
        httpServletResponse.addHeader("Refresh-Token", null);

        return ResponseEntity.status(HttpStatus.OK).body("LOGOUT SUCCESS");
    }

    private String[] loginLogic(String oauthId, String deviceToken)throws Exception{
        String[] answer = new String[2];
        User user = userService.readUserByOauthId(oauthId);

        if(user == null){
            OauthUserInputDTO oauthUserInputDTO = new OauthUserInputDTO(oauthId);
            user = userService.insertUser(oauthUserInputDTO);
        }
        user.setDeviceToken(deviceToken);
        userService.updateDeviceToken(user);

        final String accessToken = jwtTokenProvider.createAccessToken(String.valueOf(user.getUserId()));
        final String refreshToken = jwtTokenProvider.createRefreshToken(String.valueOf(user.getUserId()));

        userService.updateRefreshToken(user.getUserId(), refreshToken);

        answer[0] = accessToken;
        answer[1] = refreshToken;

        return answer;
    }
}
