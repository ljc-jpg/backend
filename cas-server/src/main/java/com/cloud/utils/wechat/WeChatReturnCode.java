package com.cloud.utils.wechat;

import org.apache.commons.lang.StringUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * 微信全局返回码
 *
 * @author Zhuz
 * @date 2015-7-8
 */
public class WeChatReturnCode {

    /**
     * 全局返回码说明
     */
    private final static Map<Integer, String> RETURN_CODE_MAP = new HashMap<>();

    static {
        RETURN_CODE_MAP.put(-1, "系统繁忙，此时请开发者稍候再试");
        RETURN_CODE_MAP.put(0, "请求成功");
        RETURN_CODE_MAP.put(40001, "获取access_token时AppSecret错误，或者access_token无效。请管理员认真比对AppSecret的正确性，或查看是否正在为恰当的公众号调用接口");
        RETURN_CODE_MAP.put(40002, "不合法的凭证类型");
        RETURN_CODE_MAP.put(40003, "不合法的OpenID，请开发者确认OpenID（该用户）是否已关注公众号，或是否是其他公众号的OpenID");
        RETURN_CODE_MAP.put(40004, "不合法的媒体文件类型");
        RETURN_CODE_MAP.put(40005, "不合法的文件类型");
        RETURN_CODE_MAP.put(40006, "不合法的文件大小");
        RETURN_CODE_MAP.put(40007, "不合法的媒体文件id");
        RETURN_CODE_MAP.put(40008, "不合法的消息类型");
        RETURN_CODE_MAP.put(40009, "不合法的图片文件大小");
        RETURN_CODE_MAP.put(40010, "不合法的语音文件大小");
        RETURN_CODE_MAP.put(40011, "不合法的视频文件大小");
        RETURN_CODE_MAP.put(40012, "不合法的缩略图文件大小");
        RETURN_CODE_MAP.put(40013, "不合法的AppID，请开发者检查AppID的正确性，避免异常字符，注意大小写");
        RETURN_CODE_MAP.put(40014, "不合法的access_token，请开发者认真比对access_token的有效性（如是否过期），或查看是否正在为恰当的公众号调用接口");
        RETURN_CODE_MAP.put(40015, "不合法的菜单类型");
        RETURN_CODE_MAP.put(40016, "不合法的按钮个数");
        RETURN_CODE_MAP.put(40017, "不合法的按钮个数");
        RETURN_CODE_MAP.put(40018, "不合法的按钮名字长度");
        RETURN_CODE_MAP.put(40019, "不合法的按钮KEY长度");
        RETURN_CODE_MAP.put(40020, "不合法的按钮URL长度");
        RETURN_CODE_MAP.put(40021, "不合法的菜单版本号");
        RETURN_CODE_MAP.put(40022, "不合法的子菜单级数");
        RETURN_CODE_MAP.put(40023, "不合法的子菜单按钮个数");
        RETURN_CODE_MAP.put(40024, "不合法的子菜单按钮类型");
        RETURN_CODE_MAP.put(40025, "不合法的子菜单按钮名字长度");
        RETURN_CODE_MAP.put(40026, "不合法的子菜单按钮KEY长度");
        RETURN_CODE_MAP.put(40027, "不合法的子菜单按钮URL长度");
        RETURN_CODE_MAP.put(40028, "不合法的自定义菜单使用用户");
        RETURN_CODE_MAP.put(40029, "不合法的oauth_code");
        RETURN_CODE_MAP.put(40030, "不合法的refresh_token");
        RETURN_CODE_MAP.put(40031, "不合法的openid列表");
        RETURN_CODE_MAP.put(40032, "不合法的openid列表长度");
        RETURN_CODE_MAP.put(40033, "不合法的请求字符，不能包含\\uxxxx格式的字符");
        RETURN_CODE_MAP.put(40035, "不合法的参数");
        RETURN_CODE_MAP.put(40038, "不合法的请求格式");
        RETURN_CODE_MAP.put(40039, "不合法的URL长度");
        RETURN_CODE_MAP.put(40050, "不合法的分组id");
        RETURN_CODE_MAP.put(40051, "分组名字不合法");
        RETURN_CODE_MAP.put(40053, "无效的操作信息,请检查文档");
        RETURN_CODE_MAP.put(40054, "无效的url域名");
        RETURN_CODE_MAP.put(40059, "不合法的msgId");
        RETURN_CODE_MAP.put(40117, "分组名字不合法");
        RETURN_CODE_MAP.put(40118, "media_id大小不合法");
        RETURN_CODE_MAP.put(40119, "button类型错误");
        RETURN_CODE_MAP.put(40120, "button类型错误");
        RETURN_CODE_MAP.put(40121, "不合法的media_id类型");
        RETURN_CODE_MAP.put(40132, "微信号不合法");
        RETURN_CODE_MAP.put(41001, "缺少access_token参数");
        RETURN_CODE_MAP.put(41002, "缺少appid参数");
        RETURN_CODE_MAP.put(41003, "缺少refresh_token参数");
        RETURN_CODE_MAP.put(41004, "缺少secret参数");
        RETURN_CODE_MAP.put(41005, "缺少多媒体文件数据");
        RETURN_CODE_MAP.put(41006, "缺少media_id参数");
        RETURN_CODE_MAP.put(41007, "缺少子菜单数据");
        RETURN_CODE_MAP.put(41008, "缺少aouthcode");
        RETURN_CODE_MAP.put(41009, "缺少openid");
        RETURN_CODE_MAP.put(42001, "access_token超时，请检查access_token的有效期，请参考基础支持-获取access_token中，对access_token的详细机制说明");
        RETURN_CODE_MAP.put(42002, "refresh_token超时");
        RETURN_CODE_MAP.put(42003, "oauth_code超时");
        RETURN_CODE_MAP.put(43001, "需要GET请求");
        RETURN_CODE_MAP.put(43002, "需要POST请求");
        RETURN_CODE_MAP.put(43003, "需要HTTPS请求");
        RETURN_CODE_MAP.put(43004, "需要接收者关注");
        RETURN_CODE_MAP.put(43005, "需要好友关系");
        RETURN_CODE_MAP.put(44001, "多媒体文件为空");
        RETURN_CODE_MAP.put(44002, "POST的数据包为空");
        RETURN_CODE_MAP.put(44003, "图文消息内容为空");
        RETURN_CODE_MAP.put(44004, "文本消息内容为空");
        RETURN_CODE_MAP.put(45001, "多媒体文件大小超过限制");
        RETURN_CODE_MAP.put(45002, "消息内容超过限制");
        RETURN_CODE_MAP.put(45003, "标题字段超过限制");
        RETURN_CODE_MAP.put(45004, "描述字段超过限制");
        RETURN_CODE_MAP.put(45005, "链接字段超过限制");
        RETURN_CODE_MAP.put(45006, "图片链接字段超过限制");
        RETURN_CODE_MAP.put(45007, "语音播放时间超过限制");
        RETURN_CODE_MAP.put(45008, "图文消息超过限制");
        RETURN_CODE_MAP.put(45009, "接口调用超过限制");
        RETURN_CODE_MAP.put(45010, "创建菜单个数超过限制");
        RETURN_CODE_MAP.put(45015, "回复时间超过限制");
        RETURN_CODE_MAP.put(45016, "系统分组，不允许修改");
        RETURN_CODE_MAP.put(45017, "分组名字过长");
        RETURN_CODE_MAP.put(45018, "分组数量超过上限");
        RETURN_CODE_MAP.put(46001, "不存在媒体数据");
        RETURN_CODE_MAP.put(46002, "不存在的菜单版本");
        RETURN_CODE_MAP.put(46003, "不存在的菜单数据");
        RETURN_CODE_MAP.put(46004, "不存在的用户");
        RETURN_CODE_MAP.put(47001, "解析JSON/XML内容错误");
        RETURN_CODE_MAP.put(48001, "api功能未授权，请确认公众号已获得该接口，可以在公众平台官网-开发者中心页中查看接口权限");
        RETURN_CODE_MAP.put(48003, "没有群发权限");
        RETURN_CODE_MAP.put(50001, "用户未授权该api");
        RETURN_CODE_MAP.put(50002, "用户受限，可能是违规后接口被封禁");
        RETURN_CODE_MAP.put(61451, "参数错误(invalidparameter)");
        RETURN_CODE_MAP.put(61452, "无效客服账号(invalidkf_account)");
        RETURN_CODE_MAP.put(61453, "客服帐号已存在(kf_accountexsited)");
        RETURN_CODE_MAP.put(61454, "客服帐号名长度超过限制(仅允许10个英文字符，不包括@及@后的公众号的微信号)(invalidkf_acountlength)");
        RETURN_CODE_MAP.put(61455, "客服帐号名包含非法字符(仅允许英文+数字)(illegalcharacterinkf_account)");
        RETURN_CODE_MAP.put(61456, "客服帐号个数超过限制(10个客服账号)(kf_accountcountexceeded)");
        RETURN_CODE_MAP.put(61457, "无效头像文件类型(invalidfiletype)");
        RETURN_CODE_MAP.put(61458, "客户正在被其他客服接待(customer accepted by xxx@xxxx)");
        RETURN_CODE_MAP.put(61459, "客服不在线(kf offline)");
        RETURN_CODE_MAP.put(61450, "系统错误(systemerror)");
        RETURN_CODE_MAP.put(61500, "日期格式错误");
        RETURN_CODE_MAP.put(61501, "日期范围错误");
        RETURN_CODE_MAP.put(9001001, "POST数据参数不合法");
        RETURN_CODE_MAP.put(9001002, "远端服务不可用");
        RETURN_CODE_MAP.put(9001003, "Ticket不合法");
        RETURN_CODE_MAP.put(9001004, "获取摇周边用户信息失败");
        RETURN_CODE_MAP.put(9001005, "获取商户信息失败");
        RETURN_CODE_MAP.put(9001006, "获取OpenID失败");
        RETURN_CODE_MAP.put(9001007, "上传文件缺失");
        RETURN_CODE_MAP.put(9001008, "上传素材的文件类型不合法");
        RETURN_CODE_MAP.put(9001009, "上传素材的文件尺寸不合法");
        RETURN_CODE_MAP.put(9001010, "上传失败");
        RETURN_CODE_MAP.put(9001020, "帐号不合法");
        RETURN_CODE_MAP.put(9001021, "已有设备激活率低于50%，不能新增设备");
        RETURN_CODE_MAP.put(9001022, "设备申请数不合法，必须为大于0的数字");
        RETURN_CODE_MAP.put(9001023, "已存在审核中的设备ID申请");
        RETURN_CODE_MAP.put(9001024, "一次查询设备ID数量不能超过50");
        RETURN_CODE_MAP.put(9001025, "设备ID不合法");
        RETURN_CODE_MAP.put(9001026, "页面ID不合法");
        RETURN_CODE_MAP.put(9001027, "页面参数不合法");
        RETURN_CODE_MAP.put(9001028, "一次删除页面ID数量不能超过10");
        RETURN_CODE_MAP.put(9001029, "页面已应用在设备中，请先解除应用关系再删除");
        RETURN_CODE_MAP.put(9001030, "一次查询页面ID数量不能超过50");
        RETURN_CODE_MAP.put(9001031, "时间区间不合法");
        RETURN_CODE_MAP.put(9001032, "保存设备与页面的绑定关系参数错误");
        RETURN_CODE_MAP.put(9001033, "门店ID不合法");
        RETURN_CODE_MAP.put(9001034, "设备备注信息过长");
        RETURN_CODE_MAP.put(9001035, "设备申请参数不合法");
        RETURN_CODE_MAP.put(9001036, "查询起始值begin不合法");
    }

    /**
     * 返回异常信息
     *
     * @param returnCode
     * @return
     */
    public static String getMsg(int returnCode) {
        if (RETURN_CODE_MAP.containsKey(returnCode)) {
            return RETURN_CODE_MAP.get(returnCode);
        }
        return "";
    }

    /**
     * 返回异常信息
     * @param returnCode
     * @param errMsg
     * @return
     */
    public static String getMsg(int returnCode,String errMsg) {
        if (RETURN_CODE_MAP.containsKey(returnCode)) {
            return RETURN_CODE_MAP.get(returnCode);
        }
        if(StringUtils.isNotEmpty(errMsg)){
            return errMsg;
        }
        //返回空白字符串，防止页面报js错误
        return " ";
    }
}
