package com.cloud.service;

import com.cloud.dao.UserMapper;
import com.cloud.model.User;
import com.cloud.util.CookieUtils;
import com.cloud.util.JwtUtil;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import static com.cloud.util.CookieUtils.COOKIE_TOKEN;
import static com.cloud.util.CookieUtils.addCookie;

/**
 * @author zhuz
 * @date 2020/8/3
 */
@Service
public class LoginService {

    private static final Logger logger = LoggerFactory.getLogger(LoginService.class);

    private static long expireTime;

    @Resource
    private UserMapper userMapper;

    @Autowired
    public StringRedisTemplate redisTemplate;

    @Value("${jwt-expire-time}")
    public void setExpireTime(long expireTime) {
        LoginService.expireTime = expireTime * 60 * 1000;
    }

    @Value("${domain-name}")
    private String domainName;

    public User login(HttpServletRequest request, HttpServletResponse response, String loginName, String psd) {
        User u = userMapper.selectByLoginName(loginName, psd);
        if (null == u || null == u.getUserId()) {
            throw new RuntimeException("账号或者密码错误");
        }
        //生成密钥   用户信息  随机字符串  时间戳
        String secret = UUID.randomUUID().toString().replaceAll("-", "");
        String token = JwtUtil.encode(u.toString(), secret, expireTime);
        u.setPsw(token);
        //存入token 密钥 过期时间
        redisTemplate.opsForValue().set(token, secret);
        redisTemplate.expire(token, expireTime, TimeUnit.MILLISECONDS);
        addCookie(request, response, COOKIE_TOKEN, token, domainName);
        return u;
    }

    public void loginOut(HttpServletRequest request, HttpServletResponse response) {
        String token = CookieUtils.getCookie(request, COOKIE_TOKEN);
        if (StringUtils.isEmpty(token)) {
            throw new RuntimeException("token为空");
        }
        //token删除
        String sign = redisTemplate.opsForValue().get(token);
        if (!StringUtils.isEmpty(sign)) {
            redisTemplate.delete(token);
        }
        addCookie(request, response, "token", null, domainName);
    }


}
