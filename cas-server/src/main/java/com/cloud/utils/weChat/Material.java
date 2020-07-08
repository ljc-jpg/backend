package com.cloud.utils.weChat;

import com.alibaba.fastjson.JSONObject;
import com.cloud.util.HttpUtils;
import org.apache.commons.lang.StringUtils;

import java.io.File;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

public class Material {

    //上传图文素材
    private static final String UPLOAD_NEWS = "https://api.weixin.qq.com/cgi-bin/material/add_news?access_token=";
    //获得素材列表
    private static final String GET_LIST = "https://api.weixin.qq.com/cgi-bin/material/batchget_material?access_token=";
    //获得单个素材
    private static final String GET_MATERIAL = "https://api.weixin.qq.com/cgi-bin/material/get_material?access_token=";
    //上传永久素材
    private static final String UPLOAD_MATERIAL = "https://api.weixin.qq.com/cgi-bin/material/add_material?access_token=";
    //删除永久素材
    private static final String DEL_MATERIAL = "https://api.weixin.qq.com/cgi-bin/material/del_material?access_token=";
    //修改永久素材
    private static final String UPDATE_NEWS = "https://api.weixin.qq.com/cgi-bin/material/update_news?access_token=";
    //上传图片获得URL
    private static final String UPLOAD_IMG = "https://api.weixin.qq.com/cgi-bin/media/uploadimg?access_token=";
    //获得素材总数
    private static final String MATERIAL_COUNT = "https://api.weixin.qq.com/cgi-bin/material/get_materialcount?access_token=";
    //预览
    private static final String PREVIEW_NEWS = "https://api.weixin.qq.com/cgi-bin/message/mass/preview?access_token=";
    //群发
    private static final String SEND_ALL = "https://api.weixin.qq.com/cgi-bin/message/mass/sendall?access_token=";
    //撤销群发
    private static final String REVERT_MATERIAL = "https://api.weixin.qq.com/cgi-bin/message/mass/delete?access_token=";

    private static final String ERR_KEY = "errcode";
    private static final String ERR_MSG = "errmsg";

    /**
     * 推送文章
     *
     * @param accessToken
     * @param params
     * @return
     */
    public static Map<String, Object> sendNews(String accessToken, Map<String, Object> params) {
        Map<String, Object> resultMap = new HashMap<String ,Object>();
        if (StringUtils.isEmpty(accessToken) || null == params) {
            resultMap.put("returnCode", "2");
            resultMap.put("msg", "参数错误");
            return resultMap;
        }
        String url = UPLOAD_NEWS + accessToken;
        String json = JSONObject.toJSON(params).toString();
        JSONObject jsonObject = HttpUtils.httpsRequest(url, "POST", json);//获得返回数据
        parseMap(jsonObject, resultMap);
        if ("1".equals(resultMap.get("returnCode").toString())) {
            resultMap.put("media_id", jsonObject.getString("media_id"));
        }
        return resultMap;
    }

    /**
     * 修改图文消息
     *
     * @param accessToken
     * @param params
     * @return
     */
    public static Map<String, Object> updateNews(String accessToken, Map<String, Object> params) {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        String url = UPDATE_NEWS + accessToken;
        String json = JSONObject.toJSON(params).toString();
        JSONObject jsonObject = HttpUtils.httpsRequest(url, "POST", json);//获得返回数据
        parseMap(jsonObject, resultMap);
        return resultMap;
    }

    public static Map<String, Object> updateNews(String accessToken, String json) {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        String url = UPDATE_NEWS + accessToken;
        JSONObject jsonObject = HttpUtils.httpsRequest(url, "POST", json);//获得返回数据
        parseMap(jsonObject, resultMap);
        return resultMap;
    }

    /**
     * 群发图文消息
     *
     * @param mediaId
     * @return
     */
    public static Map<String, Object> sendNewsToAll(String accessToken, String mediaId) {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        if (StringUtils.isEmpty(accessToken) || StringUtils.isEmpty(mediaId)) {
            resultMap.put("returnCode", "2");
            resultMap.put("msg", "参数错误");
            return resultMap;
        }
        Map<String, Object> params = parseNewsMap(mediaId);
        String url = SEND_ALL + accessToken;
        String json = JSONObject.toJSON(params).toString();
        JSONObject jsonObject = HttpUtils.httpsRequest(url, "POST", json);//获得返回数据
        parseMap(jsonObject, resultMap);
        if ("1".equals(resultMap.get("returnCode").toString())) {//群发成功
            resultMap.put("msg_id", jsonObject.getString("msg_id"));
            resultMap.put("msg_data_id", jsonObject.getString("msg_data_id"));
        }
        return resultMap;
    }

    /**
     * 发送文字
     *
     * @param accessToken
     * @param content
     * @return
     */
    public static Map<String, Object> sendText(String accessToken, String content) {
        Map<String, Object> params = new HashMap<String, Object>();
        Map<String, Object> filter = new HashMap<String, Object>();
        filter.put("is_to_all", true);
        params.put("filter", filter);
        Map<String, Object> text = new HashMap<String, Object>();
        text.put("content", content);
        params.put("text", text);
        params.put("msgtype", "text");
        String json = JSONObject.toJSON(params).toString();
        return sendMessage(accessToken, json);
    }

    /**
     * 群发图片
     *
     * @param accessToken
     * @param mediaId
     * @return
     */
    public static Map<String, Object> sendImg(String accessToken, String mediaId) {
        if (StringUtils.isEmpty(mediaId)) {
            System.out.println("没有数据");
            return new HashMap<String, Object>();
        }
        Map<String, Object> params = new HashMap<String, Object>();
        Map<String, Object> filter = new HashMap<String, Object>();
        filter.put("is_to_all", true);
        params.put("filter", filter);
        Map<String, Object> text = new HashMap<String, Object>();
        text.put("media_id", mediaId);
        params.put("image", text);
        params.put("msgtype", "image");
        String json = JSONObject.toJSON(params).toString();
        return sendMessage(accessToken, json);
    }

    /**
     * 预览文本消息
     *
     * @param accessToken
     * @param content
     * @param wxName
     * @return
     */
    public static Map<String, Object> previewText(String accessToken, String content, String wxName) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("towxname", wxName);
        Map<String, Object> text = new HashMap<String, Object>();
        text.put("content", content);
        params.put("text", text);
        params.put("msgtype", "text");
        String json = JSONObject.toJSON(params).toString();
        return previewMessage(accessToken, json);
    }

    /**
     * @param accessToken
     * @param json
     * @return
     */
    private static Map<String, Object> sendMessage(String accessToken, String json) {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        if (StringUtils.isEmpty(accessToken) || StringUtils.isEmpty(json)) {
            resultMap.put("returnCode", "2");
            resultMap.put("msg", "参数错误");
            return resultMap;
        }
        String url = SEND_ALL + accessToken;
        JSONObject jsonObject = HttpUtils.httpsRequest(url, "POST", json);//获得返回数据
        parseMap(jsonObject, resultMap);
        if ("1".equals(resultMap.get("returnCode").toString())) {//群发成功
            resultMap.put("msg_id", jsonObject.getString("msg_id"));
            resultMap.put("msg_data_id", jsonObject.getString("msg_data_id"));
        }
        return resultMap;
    }

    /**
     * 预览
     *
     * @param accessToken
     * @param json
     * @return
     */
    private static Map<String, Object> previewMessage(String accessToken, String json) {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        String url = PREVIEW_NEWS + accessToken;
        JSONObject jsonObject = HttpUtils.httpsRequest(url, "POST", json);//获得返回数据
        parseMap(jsonObject, resultMap);
        if ("1".equals(resultMap.get("returnCode").toString())) {//群发成功
            resultMap.put("msg_id", jsonObject.getString("msg_id"));
        }
        return resultMap;
    }

    /**
     * 图文的Map封装
     *
     * @param mediaId
     * @return
     */
    private static Map<String, Object> parseNewsMap(String mediaId) {
        Map<String, Object> params = new HashMap<String, Object>();
        Map<String, Object> filterMap = new HashMap<String, Object>();
        filterMap.put("is_to_all", true);
        Map<String, Object> mediaMap = new HashMap<String, Object>();
        mediaMap.put("media_id", mediaId);
        params.put("filter", filterMap);
        params.put("mpnews", mediaMap);
        params.put("msgtype", "mpnews");
        return params;
    }

    /**
     * 预览 图文消息
     *
     * @param accessToken
     * @param wxName
     * @param mediaId
     * @return
     */
    public static Map<String, Object> previewNews(String accessToken, String wxName, String mediaId) {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        if (StringUtils.isEmpty(accessToken) || StringUtils.isEmpty(wxName) || StringUtils.isEmpty(mediaId)) {
            resultMap.put("returnCode", "0");
            resultMap.put("msg", "参数错误");
            return resultMap;
        }
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("towxname", wxName);
        Map<String, Object> paramsSub = new HashMap<String, Object>();
        paramsSub.put("media_id", mediaId);
        params.put("mpnews", paramsSub);
        params.put("msgtype", "mpnews");
        String json = JSONObject.toJSON(params).toString();
        String url = PREVIEW_NEWS + accessToken;
        JSONObject jsonObject = HttpUtils.httpsRequest(url, "POST", json);//获得返回数据
        parseMap(jsonObject, resultMap);
        return resultMap;
    }

    /**
     * 删除永久素材
     *
     * @param accessToken
     * @param mediaId
     * @return
     */
    public static Map<String, Object> deleteMaterial(String accessToken, String mediaId) {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        if (StringUtils.isEmpty(accessToken) || StringUtils.isEmpty(mediaId)) {
            resultMap.put("returnCode", "2");
            resultMap.put("msg", "参数错误");
            return resultMap;
        }
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("media_id", mediaId);
        String url = DEL_MATERIAL + accessToken;
        String json = JSONObject.toJSON(params).toString();
        JSONObject jsonObject = HttpUtils.httpsRequest(url, "POST", json);//获得返回数据
        parseMap(jsonObject, resultMap);
        return resultMap;
    }

    /**
     * 撤销群发消息
     *
     * @param accessToken
     * @param msgId
     * @return
     */
    public static Map<String, Object> revert(String accessToken, String msgId) {
        String url = REVERT_MATERIAL + accessToken;
        Map<String, Object> params = new HashMap<String, Object>();
        Map<String, Object> resultMap = new HashMap<String, Object>();
        params.put("msg_id", msgId);
        String json = JSONObject.toJSON(params).toString();
        JSONObject jsonObject = HttpUtils.httpsRequest(url, "POST", json);//获得返回数据
        parseMap(jsonObject, resultMap);
        return resultMap;
    }

    /**
     * 文件上传
     * success: {"type":"TYPE","media_id":"MEDIA_ID","created_at":123456789}
     * error:   {"errcode":40004,"errmsg":"invalid media type"}
     * returnCode:1 成功
     * msg
     * type
     * media_id
     *
     * @return
     */
    public static Map<String, Object> uploadFile(String accessToken, String type, InputStream stream, String fileName, long fileSize) throws Exception {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        String url = UPLOAD_MATERIAL + accessToken + "&type=" + type;
        String result = HttpUtils.postFile(url, stream, fileName, fileSize);
        JSONObject jsonObject = JSONObject.parseObject(result);
        parseMap(jsonObject, resultMap);
        return resultMap;
    }

    public static Map<String, Object> uploadImg(String accessToken, File file) throws Exception {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        String url = UPLOAD_IMG + accessToken;
        String result = HttpUtils.postFile(url, "media", file);
        JSONObject jsonObject = JSONObject.parseObject(result);
        parseMap(jsonObject, resultMap);
        resultMap.put("imgUrl", jsonObject.getString("url"));
        resultMap.put("media_id", jsonObject.getString("media_id"));
        return resultMap;
    }

    public static Map<String, Object> uploadImg(String accessToken, InputStream stream, String fileName, long fileSize) throws Exception {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        String url = UPLOAD_IMG + accessToken;
        String result = HttpUtils.postFile(url, stream, fileName, fileSize);
        JSONObject jsonObject = JSONObject.parseObject(result);
        parseMap(jsonObject, resultMap);
        resultMap.put("imgUrl", jsonObject.getString("url"));
        return resultMap;
    }

  /*  public static void main(String[] args) throws Exception {
        String accesstoken = "EhF5h57lKWGZRwomjzsu7_afrazyH_x8km_Z_0fd2YX6h_ez4BqhhfXjkj6UWChZIs3rX1vMCMFk5yj7qKwbMIMXo2DWumseUbJkci60GSFmZXA89VCRPSXvjcyiCZ9MQAJhAFAUEN";
        String path = "F:\\0505194922.jpg";
        File file = new File(path);
        InputStream in = new FileInputStream(file);
        Map<String, Object> params = uploadImg(accesstoken, in, file.getName(), 123l);
        System.out.println(params);
    }*/

    /**
     * 获得单个素材
     *
     * @param accessToken
     * @param mediaId
     * @return
     */
    public static JSONObject getNews(String accessToken, String mediaId) {
        String url = GET_MATERIAL + accessToken;
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("media_id", mediaId);
        String json = JSONObject.toJSON(params).toString();
        JSONObject jsonObject = HttpUtils.httpsRequest(url, "POST", json);//获得返回数据
        return jsonObject;
    }


    public static Map<String, Object> getMaterialCounts(String accessToken) {
        String url = MATERIAL_COUNT + accessToken;
        Map<String, Object> resultMap = new HashMap<String, Object>();
        JSONObject jsonObject = HttpUtils.httpsRequest(url, "POST", null);//获得返回数据
        parseMap(jsonObject, resultMap);
        resultMap.put("voice_count", jsonObject.getString("voice_count"));
        resultMap.put("video_count", jsonObject.getString("video_count"));
        resultMap.put("image_count", jsonObject.getString("image_count"));
        resultMap.put("news_count", jsonObject.getString("news_count"));
        return resultMap;
    }

    private static JSONObject getList(String accessToken, String type, int start, int pageSize) {
        String url = GET_LIST + accessToken;
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("type", type);
        params.put("offset", start);
        params.put("count", pageSize);
        String json = JSONObject.toJSON(params).toString();
        JSONObject jsonObject = HttpUtils.httpsRequest(url, "POST", json);//获得返回数据
        return jsonObject;
    }

    /**
     * @param accessToken
     * @param start
     * @param pageSize
     * @return
     */
    public static JSONObject getNewsList(String accessToken, int start, int pageSize) {
        return getList(accessToken, "news", start, pageSize);
    }

    /**
     * 获得素材列表
     *
     * @param accessToken
     * @param type
     * @param start
     * @param pageSize
     * @return
     */
    public static JSONObject getMaterialList(String accessToken, String type, int start, int pageSize) {
        return getList(accessToken, type, start, pageSize);
    }

    /**
     * 返回消息
     *
     * @param jsonObject
     * @param resultMap
     * @return
     */
    public static Map<String, Object> parseMap(JSONObject jsonObject, Map<String, Object> resultMap) {
        if (null != jsonObject) {
            if (jsonObject.containsKey(ERR_KEY)) {
                if (!"0".equals(jsonObject.getString(ERR_KEY))) {
                    resultMap.put("returnCode", jsonObject.getString(ERR_KEY));
                    resultMap.put("msg", WeChatReturnCode.getMsg(Integer.parseInt(jsonObject.getString(ERR_KEY)), jsonObject.getString(ERR_MSG)));
                    return resultMap;
                }
            }
        } else {
            resultMap.put("returnCode", "0");
            resultMap.put("msg", "数据为空");
            return resultMap;
        }
        resultMap.put("type", jsonObject.getString("type"));
        resultMap.put("media_id", jsonObject.getString("media_id"));
        resultMap.put("returnCode", "1");
        resultMap.put("msg", "成功");
        return resultMap;
    }

}
