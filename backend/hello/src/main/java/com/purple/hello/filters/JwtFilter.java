package com.purple.hello.filters;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;

public class JwtFilter implements Filter {
    private static final String AUTHORIZATION_HEADER = "Authorization";
    private static final String BEARER_PREFIX = "Bearer ";
    private final String secret;
    private final long expirationMillis;

    public JwtFilter(String secret, long expirationMillis) {
        this.secret = secret;
        this.expirationMillis = expirationMillis;
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        // JWT 토큰에서 userId를 추출하는 작업 수행
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        String authorizationHeader = httpRequest.getHeader(AUTHORIZATION_HEADER);


        // AUTHORIZATION_HEADER 가 정상적으로 들어왔을 경우 JWT 를 추출하여 추가적인 작업을 하는 로직
        if(authorizationHeader != null && authorizationHeader.startsWith(BEARER_PREFIX)) {
            String jwt = authorizationHeader.substring(BEARER_PREFIX.length());
            // 토큰 유효성 검사
            try{
                Claims claims = Jwts.parser().setSigningKey(secret).parseClaimsJws(jwt).getBody();
                // 토큰 만료일 검사
                Date expiration = claims.getExpiration();
                if(expiration.before(new Date(System.currentTimeMillis()))){
                    throw new JwtException("Access Token Expired");
                }

                // 유효한 토큰인 경우, request 에 userId 저장
                long userId = Long.parseLong(claims.getSubject());
                httpRequest.setAttribute("userId", userId);
            } catch (JwtException e){
                ((HttpServletResponse) response).sendError(HttpServletResponse.SC_UNAUTHORIZED, e.getMessage());
                return;
            }
        }else if(authorizationHeader == null || !authorizationHeader.startsWith(BEARER_PREFIX)){
            String message = "Authorization Expired";
            ((HttpServletResponse) response).sendError(HttpServletResponse.SC_UNAUTHORIZED, message);
            return;
        }

        chain.doFilter(request, response);
    }
}
