package com.cloud.utils.weChat;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;

/**
 * @author zhuz
 * @Description Access token实体模型
 * @date 2014年12月12日
 */
public class AccessToken extends Token {

    private static Logger logger = LoggerFactory.getLogger(AccessToken.class);

    private static String ACCESS_TOKEN_URL = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential";

    @Value("${weChat.appId}")
    private String appId;

    @Value("${weChat.appsecret}")
    private String appSecret;

    @Override
    protected String tokenName() {
        return "access_token";
    }

    @Override
    protected String expiresInName() {
        return "expires_in";
    }

    /**
     * @Author zhuz
     * @Description  获得默认accessToken
     * @Date 17:27 2020/7/8
     * @Param [appId, appSecret]
     **/
    @Override
    protected String accessTokenUrl() {
        String url = ACCESS_TOKEN_URL + "&appid=" + appId + "&secret=" + appSecret;
        logger.info("创建获取access_token :" , url);
        return url;
    }

    /**
     * @Author zhuz
     * @Description  通过参数获得accessToken
     * @Date 17:27 2020/7/8
     * @Param [appId, appSecret]
     **/
    @Override
    protected String accessTokenUrl(String appId, String appSecret) {
        String url = ACCESS_TOKEN_URL + "&appid=" + appId + "&secret=" + appSecret;
        logger.info("accessTokenUrl :" , url);
        return url;
    }
}
