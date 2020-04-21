package com.cloud.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

import java.util.Date;

/**
 * @author zhuz
 * @Description: jwt加密
 * @date 2019/2/28 18:36
 */
public class JwtUtil {

    /**
     * @param user    用户信息
     * @param secret  秘钥
     * @param timeOut 超时时间（单位s）
     * @Description: 生成一个jwt字符串
     * @author zhu
     * @date 2020/3/4 17:26
     */
    public static String encode(String user, String secret, long timeOut) {
        Algorithm algorithm = Algorithm.HMAC256(secret);
        String token = JWT.create()
                //过期时间
                .withExpiresAt(new Date(System.currentTimeMillis() + timeOut))
                //负载
                .withClaim("user", user)
                .withClaim("id", secret)
                //签名
                .sign(algorithm);
        return token;
    }


}
