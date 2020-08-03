package com.cloud.utils.wechat;

import com.alibaba.fastjson.JSONObject;
import com.cloud.util.ActiveEnum;
import com.cloud.util.HttpUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author zhuz
 * @date 2015年1月29日
 */
public abstract class AbstractToken {
    private static Logger logger = LoggerFactory.getLogger(AbstractToken.class);

    /**
     * token
     *
     * @author zhuz
     * @date 2020/8/3
     */
    private String token;
    /**
     * token有效时间
     *
     * @author zhuz
     * @date 2020/8/3
     */
    private long expires;

    /**
     * token产生时间
     *
     * @author zhuz
     * @date 2020/8/3
     */
    private long tokenTime;

    /**
     * 冗余时间，提前10秒就去请求新的token
     *
     * @author zhuz
     * @date 2020/8/3
     */
    private int redundance = 10 * 1000;

    /**
     * 得到access token
     *
     * @return {@link String}
     * @author zhuz
     * @date 2020/8/3
     */
    public String getToken() {
        return this.token;
    }

    /**
     * 得到有效时间
     *
     * @author zhuz
     * @date 2020/8/3
     */
    public long getExpires() {
        return expires;
    }

    /**
     * 请求信息的access_token
     * http请求方式: GET
     * url https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=APPID&secret=APPSECRET
     * 返回结果{"access_token":"ACCESS_TOKEN","expires_in":7200}
     * 错误返回 {"errcode":40013,"errmsg":"invalid appid"}
     *
     * @param
     * @return {@link boolean}
     * @author zhuz
     * @date 2020/8/3
     */
    public boolean request() {
        String url = accessTokenUrl();
        String result = HttpUtils.get(url);
        if (StringUtils.isBlank(result)) {
            return false;
        }
        if (!parseData(result)) {
            return false;
        }
        logger.info("token获取成功");
        return true;
    }

    /**
     * 发获取accessToken请求 并返回
     *
     * @param appId
     * @param appSecret
     * @return {@link Map< String, Object>}
     * @author zhuz
     * @date 2020/8/3
     */
    public Map<String, Object> request(String appId, String appSecret) {
        Map<String, Object> resultMap = new HashMap<>(ActiveEnum.TWO_EVENT.getKey());
        String url = accessTokenUrl(appId, appSecret);
        String result = HttpUtils.get(url);
        JSONObject jsonObject = JSONObject.parseObject(result);
        Material.parseMap(jsonObject, resultMap);
        if (null != jsonObject) {
            resultMap.put("accessToken", jsonObject.getString("access_token"));
        } else {
            resultMap.put("accessToken", "");
        }
        logger.info("result:" + result + "===accessToken:" + resultMap);
        return resultMap;
    }

    /**
     * 解析token数据
     *
     * @param data
     * @return {@link boolean}
     * @author zhuz
     * @date 2020/8/3
     */
    private boolean parseData(String data) {
        JSONObject jsonObject = JSONObject.parseObject(data);
        String tokenName = tokenName();
        String expiresInName = expiresInName();
        try {
            String token = jsonObject.get(tokenName).toString();
            if (StringUtils.isBlank(token)) {
                logger.error("token获取失败:", data);
                return false;
            }
            this.token = token;
            this.tokenTime = (new Date()).getTime();
            String expiresIn = jsonObject.get(expiresInName).toString();
            if (StringUtils.isBlank(expiresIn)) {
                logger.error("token获取失败,获取结果", expiresIn);
                return false;
            } else {
                this.expires = Long.valueOf(expiresIn);
            }
        } catch (Exception e) {
            logger.error("token 结果解析失败，token参数名称: " + tokenName
                    + "有效期参数名称:" + expiresInName
                    + "token请求结果:" + data);
            return false;
        }
        return true;
    }

    /**
     * 判断accessToken 是否有效
     *
     * @param
     * @return {@link boolean}
     * @author zhuz
     * @date 2020/8/3
     */
    public boolean isValid() {
        //黑名单判定法
        if (StringUtils.isBlank(this.token)) {
            return false;
        }
        if (this.expires <= 0) {
            return false;
        }
        //过期
        if (isExpire()) {
            return false;
        }
        return true;
    }

    /**
     * token的参数名称
     *
     * @param
     * @return {@link String}
     * @author zhuz
     * @date 2020/8/3
     */
    protected abstract String tokenName();

    /**
     * expireIn的参数名称
     *
     * @param
     * @return {@link String}
     * @author zhuz
     * @date 2020/8/3
     */
    protected abstract String expiresInName();

    /**
     * accesstoken的请求utl
     *
     * @param
     * @return {@link String}
     * @author zhuz
     * @date 2020/8/3
     */
    protected abstract String accessTokenUrl();

    /**
     * 组织accessToken的请求Url
     *
     * @param appId
     * @param appSecret
     * @return {@link String}
     * @author zhuz
     * @date 2020/8/3
     */
    protected abstract String accessTokenUrl(String appId, String appSecret);

    /**
     * 判断accessToken是否过期
     *
     * @param
     * @return {@link boolean}
     * @author zhuz
     * @date 2020/8/3
     */
    private boolean isExpire() {
        Date currentDate = new Date();
        long currentTime = currentDate.getTime();
        long expiresTime = expires * 1000 - redundance;
        //判断是否过期
        if ((tokenTime + expiresTime) > currentTime) {
            return false;
        }
        return true;
    }

}
