package com.cloud.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import com.cloud.dao.EducationMapper;
import com.cloud.model.Education;

import com.cloud.util.ActiveEnum;
import com.cloud.util.HttpUtils;
import com.cloud.utils.wechat.AccessToken;
import com.cloud.utils.wechat.WxTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @author zhuz
 * @date 2020/8/3
 */
@Service
public class WeChatService {

    private static final Logger logger = LoggerFactory.getLogger(WeChatService.class);

    public static String SEND_MSG_URL = "https://api.weixin.qq.com/cgi-bin/message/template/send?access_token=";

    private static String COLOR_GRAY = "#040404";

    @Value("${weChat.auth.redirectUri}")
    private String redirectUri;

    @Resource
    private EducationMapper educationMapper;

    @Autowired
    public StringRedisTemplate redisTemplate;

    private static Map setValue(String value, String color) {
        Map<String, String> resultMap = new HashMap<>(ActiveEnum.TWO_EVENT.getKey());
        resultMap.put("value", value);
        resultMap.put("color", color);
        return resultMap;
    }

    /**
     * 获取微信accessToken
     *
     * @param educationId
     * @return {@link String}
     * @author zhuz
     * @date 2020/8/3
     */
    public String getAccessToken(String educationId) {
        String token;
        Education education = educationMapper.selectByPrimaryKey(educationId);
        if (null == education|| StringUtils.isEmpty(education.getWechatAppId())) {
            throw new RuntimeException("未找到学校");
        }
        String appId = education.getWechatAppId();
        String appSecret = education.getAppSecret();

        AccessToken accessToken = new AccessToken();
        String tokenStr = redisTemplate.opsForValue().get("send_template_message_token_" + appId);
        logger.info("redis 获取 token  == >      " + tokenStr);
        if (!StringUtils.isEmpty(tokenStr)) {
            token = tokenStr;
        } else {
            token = accessToken.request(appId, appSecret);
            String key = "send_template_message_token_" + appId;
            logger.info("自动生成key" + key + "  token:" + token);
            redisTemplate.opsForValue().set(key, token);
            redisTemplate.expire(key, 3600, TimeUnit.SECONDS);
        }
        return token;
    }



    /**
     * 发送模板消息
     *
     * @param token
     * @param json
     * @return {@link Map< String, Object>}
     * @author zhuz
     * @date 2020/8/3
     */
    public static Map<String, Object> sendTemplate(String token, String json) {
        //获得返回数据
        JSONObject jsonObject = HttpUtils.httpsRequest(SEND_MSG_URL + token, "POST", json);
        return jsonObject;
    }

    /**
     * 组装微信模板消息参数 url
     *
     * @param wxTemplate
     * @return {@link String}
     * @author zhuz
     * @date 2020/8/3
     */
    public static String globalTemplate(WxTemplate wxTemplate) {
        String id = StringUtils.isEmpty(wxTemplate.getTemplateId()) ? "7K2P0iMnHPdzZiMITs4GBLuiMWRyd5AHPvOtvBV2b_0" : wxTemplate.getTemplateId();
        Map<String, Object> params = new HashMap<>(ActiveEnum.FOUR_EVENT.getKey());
        params.put("touser", wxTemplate.getOpenId());
        params.put("template_id", id);
        params.put("url", wxTemplate.getUrl());

        Map<String, Map> data = new HashMap<>(6);
        data.put("first", setValue(wxTemplate.getFirst(), COLOR_GRAY));
        data.put("keyword1", setValue(wxTemplate.getSchoolName(), COLOR_GRAY));
        data.put("keyword2", setValue(wxTemplate.getUser(), COLOR_GRAY));
        data.put("keyword3", setValue(wxTemplate.getTime(), COLOR_GRAY));
        data.put("keyword4", setValue(wxTemplate.getContent(), COLOR_GRAY));
        data.put("remark", setValue(wxTemplate.getRemark(), COLOR_GRAY));

        params.put("data", data);
        String json = JSON.toJSON(params).toString();
        logger.info("微信公众号通用模板 json:{}", json);
        return json;
    }



    /**
     * @Author zhuz
     * @Description 发送微信模板消息
     * @Date 10:35 2020/7/8
     * @Param [openIds, wxTemplate]
     **/
    public void sendGlobalTemplate(WxTemplate wxTemplate) {
        String tokenStr = getAccessToken(wxTemplate.getSchId());
        logger.info("sendGlobalTemplate tokenStr:" + tokenStr);
        String[] openId = wxTemplate.getOpenId().split(",");
        for (String id : openId) {
            wxTemplate.setOpenId(id);
            Map res = sendTemplate(tokenStr, globalTemplate(wxTemplate));
            logger.info("sendGlobalTemplate res:", res);
        }
    }

    /**
     * @Author zhuz
     * @Description 绑定H5微信授权
     * @Date 10:32 2020/7/8
     * @Param [appId, paramStr]
     **/
    public StringBuffer bindH5WeChatServer(String appId, String paramStr) throws Exception {
        String authorize = "https://open.weixin.qq.com/connect/oauth2/authorize";
        logger.info("授权参数 paramStr: {}", paramStr);
        String[] paramsArray = paramStr.split("--");
        Map pa = new HashMap();
        for (String s : paramsArray) {
            if (s.length() > 0) {
                String valStr = URLEncoder.encode(s.substring(s.indexOf(":") + 1), "utf-8");
                pa.put(s.substring(0, s.indexOf(":")), valStr);
            }
        }

        String redirect = redirectUri;
        redirect = URLEncoder.encode(redirect, "utf-8");
        String state = JSONObject.toJSONString(pa);
        StringBuffer url = new StringBuffer(authorize + "?appid=")
                .append(appId).append("&redirect_uri=").append(redirect)
                .append("&response_type=code&scope=snsapi_userinfo&state=" + state)
                .append("#wechat_redirect");

        logger.info("授权 url：" + url);
        logger.info("授权 pa: {}" + pa);
        return url;
    }

}
