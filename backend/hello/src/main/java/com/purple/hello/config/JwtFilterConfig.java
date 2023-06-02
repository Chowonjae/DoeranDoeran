package com.purple.hello.config;

import com.purple.hello.filters.JwtFilter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JwtFilterConfig {
    @Value("${jwt.secret}")
    private String SECRET_KEY;

    @Value("${jwt.access_expiration}")
    private long ACCESS_TOKEN_EXPIRATION_TIME;
    @Bean
    public FilterRegistrationBean<JwtFilter> jwtFilter(){
        FilterRegistrationBean<JwtFilter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(new JwtFilter(SECRET_KEY, ACCESS_TOKEN_EXPIRATION_TIME));
        registrationBean.addUrlPatterns("/room/*");
        registrationBean.addUrlPatterns("/user/*");
        registrationBean.addUrlPatterns("/option/*");
        return registrationBean;
    }
}
