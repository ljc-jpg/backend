package com.cloud.controller;

import com.cloud.service.WeChatService;
import com.cloud.util.ResultVo;
import com.cloud.utils.wechat.WxTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import static com.cloud.util.ResultVo.RETURN_CODE_ERR;

/**
 * @author zhuz
 * @date 2020/8/3
 */
@RequestMapping(value = "/weChat")
@RestController
public class WeChatController {

    private static final Logger logger = LoggerFactory.getLogger(WeChatController.class);

    @Resource
    private WeChatService weChatService;

    /**
     * @api {POST} /weChat/sendGlobalTemplate 发送微信模板消息
     * @apiVersion 1.0.0
     * @apiGroup WeChatController
     * @apiName sendGlobalTemplate
     * @apiDescription 发送微信模板消息
     * @apiParam (请求参数) {String} openIds
     * @apiParam (请求参数) {String} openId
     * @apiParam (请求参数) {String} url
     * @apiParam (请求参数) {String} first
     * @apiParam (请求参数) {String} schoolName 学校
     * @apiParam (请求参数) {String} user 通知人
     * @apiParam (请求参数) {String} time 时间
     * @apiParam (请求参数) {String} content 通知内容
     * @apiParam (请求参数) {String} remark
     * @apiParam (请求参数) {String} templateId 模板id
     * @apiParam (请求参数) {String} schId
     * @apiParamExample 请求参数示例
     * schId=LcH6tSWbu8&
     * openId=MsrLniCg2&
     * openIds=e8V&
     * remark=lwz&
     * time=OFzgtT8t&
     * schoolName=ykZhfjNsk&
     * templateId=5cwNehUR&
     * user=67&
     * url=Z7BVqpK&
     * first=F77TU&
     * content=zF
     * @apiSuccessExample 响应结果示例
     * {"data":{}}
     */
    @PostMapping("/sendGlobalTemplate")
    public ResultVo sendGlobalTemplate(@RequestBody WxTemplate wxTemplate) {
        ResultVo result = new ResultVo();
        try {
            weChatService.sendGlobalTemplate(wxTemplate);
        } catch (Exception e) {
            logger.error("sendGlobalTemplate", e);
            result.setMsg("sendGlobalTemplate:" + e);
            result.setCode(RETURN_CODE_ERR);
        }
        return result;
    }

    /**
     * @api {GET} /weChat/bindH5WeChat_forAllUser bindH5WeChat
     * @apiVersion 1.0.0
     * @apiGroup WeChatController
     * @apiName bindH5WeChat
     * @apiDescription 绑定H5微信授权
     * @apiParam (请求参数) {String} appId
     * @apiParam (请求参数) {String} paramStr
     * @apiParamExample 请求参数示例
     * paramStr=p4S&
     * appId=In
     * @apiSuccessExample 响应结果示例
     * null
     */
    @GetMapping(value = "/bindH5WeChat_forAllUser/{appId}/{paramStr}")
    public void bindH5WeChat(HttpServletResponse resp, @PathVariable String appId, @PathVariable String paramStr) {
        try {
            StringBuffer url = weChatService.bindH5WeChatServer(appId, paramStr);
            resp.setCharacterEncoding("utf-8");
            resp.sendRedirect(url.toString());
        } catch (Exception e) {
            logger.info("授权失败 e：{}", e);
        }
    }

    /**
     * @api {GET} /weChat/accessToken/{schId} 获取学校对应的token
     * @apiVersion 1.0.0
     * @apiGroup WeChatController
     * @apiName accessToken
     * @apiDescription 获取学校对应的token
     * @apiParam (请求参数) {String} schId
     * @apiParamExample 请求参数示例
     * schId=100075
     * @apiSuccessExample 响应结果示例
     * {
     * "code": 1,
     * "msg": null,
     * "data": "35_si1Rizrt6R56AKqSpB7j14HZDqD8wdSI6LTCNoYx75X90FGMgl2Lk7kgs6QvEzaC1GsFiVb66mhaeVQbGAmkAtJqZRZgSXzYlYNdUevNLlpS3Dc2GYf6eMeuFtydqw1nbvRFluFD8DGA4NbKRNUfABAJFM"
     * }
     */
    @GetMapping(value = "/accessToken/{schId}")
    public ResultVo<String> accessToken(@PathVariable String schId) {
        ResultVo result = new ResultVo();
        try {
            if (StringUtils.isEmpty(schId)) {
                result.setMsg("schId为空");
                result.setCode(RETURN_CODE_ERR);
                return result;
            }
            String token = weChatService.getAccessToken(schId);
            result.setData(token);
        } catch (Exception e) {
            logger.error("accessToken", e);
            result.setMsg("accessToken:" + e);
            result.setCode(RETURN_CODE_ERR);
        }
        return result;
    }


}
