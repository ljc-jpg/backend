package com.cloud.utils.wechat;

import com.alibaba.fastjson.JSONObject;
import com.cloud.util.ActiveEnum;

import java.util.Map;

/**
 * @author zhuz
 * @date 2020/8/3
 */
public class Material {

    /**
     * 上传图文素材
     *
     * @author zhuz
     * @date 2020/8/3
     */
    private static final String UPLOAD_NEWS = "https://api.weixin.qq.com/cgi-bin/material/add_news?access_token=";

    /**
     * 获得素材列表
     *
     * @author zhuz
     * @date 2020/8/3
     */
    private static final String GET_LIST = "https://api.weixin.qq.com/cgi-bin/material/batchget_material?access_token=";

    /**
     * 获得单个素材
     *
     * @author zhuz
     * @date 2020/8/3
     */
    private static final String GET_MATERIAL = "https://api.weixin.qq.com/cgi-bin/material/get_material?access_token=";

    /**
     * 上传永久素材
     *
     * @author zhuz
     * @date 2020/8/3
     */
    private static final String UPLOAD_MATERIAL = "https://api.weixin.qq.com/cgi-bin/material/add_material?access_token=";

    /**
     * 删除永久素材
     *
     * @author zhuz
     * @date 2020/8/3
     */
    private static final String DEL_MATERIAL = "https://api.weixin.qq.com/cgi-bin/material/del_material?access_token=";

    /**
     * 修改永久素材
     *
     * @author zhuz
     * @date 2020/8/3
     */
    private static final String UPDATE_NEWS = "https://api.weixin.qq.com/cgi-bin/material/update_news?access_token=";

    /**
     * 上传图片获得URL
     *
     * @author zhuz
     * @date 2020/8/3
     */
    private static final String UPLOAD_IMG = "https://api.weixin.qq.com/cgi-bin/media/uploadimg?access_token=";

    /**
     * 获得素材总数
     *
     * @author zhuz
     * @date 2020/8/3
     */
    private static final String MATERIAL_COUNT = "https://api.weixin.qq.com/cgi-bin/material/get_materialcount?access_token=";

    /**
     * 预览
     *
     * @author zhuz
     * @date 2020/8/3
     */
    private static final String PREVIEW_NEWS = "https://api.weixin.qq.com/cgi-bin/message/mass/preview?access_token=";

    /**
     * 群发地址
     *
     * @author zhuz
     * @date 2020/8/3
     */
    private static final String SEND_ALL = "https://api.weixin.qq.com/cgi-bin/message/mass/sendall?access_token=";

    /**
     * 撤销群发
     *
     * @author zhuz
     * @date 2020/8/3
     */
    private static final String REVERT_MATERIAL = "https://api.weixin.qq.com/cgi-bin/message/mass/delete?access_token=";

    private static final String ERR_KEY = "errcode";
    private static final String ERR_MSG = "errmsg";

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
                if (!ActiveEnum.ZERO_EVENT.getValue().equals(jsonObject.getString(ERR_KEY))) {
                    resultMap.put("returnCode", jsonObject.getString(ERR_KEY));
                    resultMap.put("msg", WeChatReturnCode.getMsg(Integer.parseInt(jsonObject.getString(ERR_KEY)),
                            jsonObject.getString(ERR_MSG)));
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
