package com.cloud.service;

import com.cloud.dao.UserMapper;
import com.cloud.model.User;
import com.cloud.util.CookieUtils;
import com.cloud.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Service
public class LoginService {

    private static long expireTime;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    public StringRedisTemplate redisTemplate;

    @Value("${jwt-expire-time}")
    public void setExpireTime(long expireTime) {
        LoginService.expireTime = expireTime * 60 * 1000;
    }

    @Value("${domain-name}")
    private String domainName;

    public void login(HttpServletRequest request, HttpServletResponse response, String loginName, String pwd) {
        User user = new User();
        user.setLoginName(loginName);
        user.setPsw(pwd);
        User u = userMapper.selectOne(user);
        if (null == u || null == u.getUserId()) throw new RuntimeException("账号或者密码错误");
        //生成密钥   用户信息  随机字符串  时间戳
        String secret = UUID.randomUUID().toString().replaceAll("-", "");
        String token = JwtUtil.encode(u.toString(), secret, expireTime);
        //存入token 密钥 过期时间
        redisTemplate.opsForValue().set(token, secret);
        redisTemplate.expire(token, expireTime, TimeUnit.MILLISECONDS);
        CookieUtils.addCookie(request, response, CookieUtils.COOKIE_TOKEN, token, domainName);
    }

}
