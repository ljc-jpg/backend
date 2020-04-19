package com.cloud.service;

import com.cloud.spring.RedisConfig;
import com.cloud.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

@Service
public class LoginService {

    private static int expireTime;

    @Value("${jwt-expire-time}")
    public void setExpireTime(int expireTime) {
        LoginService.expireTime = expireTime * 60 * 1000;
    }

    public String login(HttpServletResponse response, HttpServletRequest request, String userName, String pwd) {

        //随机字符串
        String secret = UUID.randomUUID().toString().replaceAll("-", "");
        String token = JwtUtil.encode("1", secret, expireTime);
        RedisConfig.set(token, secret, expireTime);

        return token;
    }


}
