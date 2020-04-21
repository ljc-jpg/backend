package com.cloud.utils;

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
 * @Description: jwt解密
 * @date 2019/2/28 18:36
 */
public class JwtUtil {


    /**
     * @param secret token
     * @Description: 解密jwt
     * @author zhuz
     * @date 2019/3/4 18:14
     */
    public static Map<String, Claim> decode(String token, String secret) {
        if (token == null || token.length() == 0) {
            return new HashMap<>();
        }
        Algorithm algorithm = Algorithm.HMAC256(secret);
        JWTVerifier jwtVerifier = JWT.require(algorithm).build();
        DecodedJWT decodedJWT = jwtVerifier.verify(token);
        return decodedJWT.getClaims();
    }

}
