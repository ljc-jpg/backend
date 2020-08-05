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
 * jwt加密
 *
 * @author zhuz
 * @date 2020/8/5
 */
public class JwtUtil {

    private JwtUtil() {
    }

    /**
     * 生成一个jwt字符串
     *
     * @param user    用户信息
     * @param secret  秘钥
     * @param timeOut 超时时间（单位s）
     * @return {@link java.lang.String}
     * @author zhuz
     * @date 2020/8/5
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
     * 解密jwt
     *
     * @param token
     * @param secret
     * @return {@link Map< String, Claim>}
     * @author zhuz
     * @date 2020/8/5
     */
    public static Map<String, Claim> decode(String token, String secret) {
        if (token == null || token.length() == 0) {
            return new HashMap<>(ActiveEnum.ONE_EVENT.getKey());
        }
        Algorithm algorithm = Algorithm.HMAC256(secret);
        JWTVerifier jwtVerifier = JWT.require(algorithm).build();
        DecodedJWT jwt = jwtVerifier.verify(token);
        return jwt.getClaims();
    }

}
