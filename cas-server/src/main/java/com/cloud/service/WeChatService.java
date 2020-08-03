package com.cloud.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.cloud.dao.Zxxx0101Mapper;
import com.cloud.model.Zxxx0101;
import com.cloud.util.HttpUtils;
import com.cloud.utils.weChat.AccessToken;
import com.cloud.utils.weChat.WXTemplate;
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


@Service
public class WeChatService {

    private static final Logger logger = LoggerFactory.getLogger(WeChatService.class);

    public static String SEND_MSG_URL = "https://api.weixin.qq.com/cgi-bin/message/template/send?access_token=";

    private static String COLOR_GRAY = "#040404";

    @Value("${weChat.auth.redirectUri}")
    private String redirectUri;

    @Resource
    private Zxxx0101Mapper zxxx0101Mapper;

    @Autowired
    private StringRedisTemplate redisTemplate;

    private static Map setValue(String value, String color) {
        Map<String, String> resultMap = new HashMap<>();
        resultMap.put("value", value);
        resultMap.put("color", color);
        return resultMap;
    }

    //发送模板消息
    public static Map<String, Object> sendTemplate(String token, String json) {
        JSONObject jsonObject = HttpUtils.httpsRequest(SEND_MSG_URL + token, "POST", json);//获得返回数据
        return jsonObject;
    }

    //组装微信模板消息参数 url
    public static String globalTemplate(WXTemplate wxTemplate) {
        String id = StringUtils.isEmpty(wxTemplate.getTemplateId()) ? "7K2P0iMnHPdzZiMITs4GBLuiMWRyd5AHPvOtvBV2b_0" : wxTemplate.getTemplateId();
        Map<String, Object> params = new HashMap<>();
        params.put("touser", wxTemplate.getOpenId());
        params.put("template_id", id);
        params.put("url", wxTemplate.getUrl());

        Map<String, Map> data = new HashMap<>();
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

    //获取微信accessToken
    public String getAccessToken(String schId) {
        String token = null;
        try {
            Zxxx0101 zxxx0101 = zxxx0101Mapper.selectBySchId(schId);
            logger.info("appId:" + zxxx0101.getWeChatAppID() + "appSecret:" + zxxx0101.getAppSecret());
            if (null == zxxx0101) {
                logger.error("未找到学校");
                throw new RuntimeException("getAccessToken未找到学校");
            }
            AccessToken accessToken = new AccessToken();
            String tokenStr = redisTemplate.opsForValue().get("send_template_message_token_" + zxxx0101.getWeChatAppID());
            logger.info("redis 获取 token  == >      " + tokenStr);
            if (!StringUtils.isEmpty(tokenStr)) {
                token = tokenStr;
            } else {
                token = (String) accessToken.request(zxxx0101.getWeChatAppID(), zxxx0101.getAppSecret()).get("accessToken");
                logger.info("自动生成token:" + token);
                redisTemplate.opsForValue().set("send_template_message_token_" + zxxx0101.getWeChatAppID(), token, 3600);
            }
        } catch (Exception e) {
            logger.error("getAccessToken" + e);
        }
        return token;
    }

    /**
     * @Author zhuz
     * @Description 发送微信模板消息
     * @Date 10:35 2020/7/8
     * @Param [openIds, wxTemplate]
     **/
    public void sendGlobalTemplate(String openIds, WXTemplate wxTemplate) {
        String tokenStr = getAccessToken(wxTemplate.getSchId());
        logger.info("sendGlobalTemplate tokenStr:" + tokenStr);
        String[] openId = openIds.split(",");
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
