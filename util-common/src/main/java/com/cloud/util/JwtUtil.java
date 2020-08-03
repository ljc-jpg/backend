package com.cloud.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author zhuz
 * @Description: jwt加密
 * @date 2019/2/28 18:36
 */
public class JwtUtil {

    private JwtUtil() {
    }

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


    /**
     * @param secret token
     * @Description: 解密jwt
     * @author zhuz
     * @date 2019/3/4 18:14
     */
    public static Map<String, Claim> decode(String token, String secret) {
        if (token == null || token.length() == 0) {
            return new HashMap<>(ActiveEnum.ONE_EVENT.getKey());
        }
        Algorithm algorithm = Algorithm.HMAC256(secret);
        JWTVerifier jwtVerifier = JWT.require(algorithm).build();
        DecodedJWT decodedJWT = jwtVerifier.verify(token);
        return decodedJWT.getClaims();
    }

}
