package com.cloud.controller;

import com.cloud.service.WeChatService;
import com.cloud.util.ResultVo;
import com.cloud.utils.weChat.WXTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

@RequestMapping(value = "/weChat")
@RestController
public class WeChatController {

    private static final Logger logger = LoggerFactory.getLogger(WeChatController.class);

    @Resource
    private WeChatService weChatService;

    /**
     * @api {POST} /weChat/sendGlobalTemplate sendGlobalTemplate
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
     * schId=LcH6tSWbu8&openId=MsrLniCg2&openIds=e8V&remark=lwz&time=OFzgtT8t&schoolName=ykZhfjNsk&templateId=5cwNehUR&user=67&url=Z7BVqpK&first=F77TU&content=zF
     * @apiSuccess (响应结果) {Object} data 具体值
     * @apiSuccessExample 响应结果示例
     * {"data":{}}
     */
    @PostMapping("/sendGlobalTemplate")
    public ResultVo sendGlobalTemplate(String openIds, WXTemplate wxTemplate) {
        ResultVo result = new ResultVo();
        try {
            weChatService.sendGlobalTemplate(openIds, wxTemplate);
        } catch (Exception e) {
            logger.error("sendGlobalTemplate", e);
            result.setMsg("sendGlobalTemplate:" + e);
            result.setCode(ResultVo.RETURN_CODE_ERR);
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
     * paramStr=p4S&appId=In
     * @apiSuccess (响应结果) {Object} response
     * @apiSuccessExample 响应结果示例
     * null
     */
    @RequestMapping(value = "/bindH5WeChat_forAllUser", method = RequestMethod.GET)
    public void bindH5WeChat(HttpServletResponse resp, String appId, String paramStr) {
        try {
            StringBuffer url = weChatService.bindH5WeChatServer(appId, paramStr);
            resp.setCharacterEncoding("utf-8");
            resp.sendRedirect(url.toString());
        } catch (Exception e) {
            logger.info("授权失败 e：{}", e);
        }
    }

}
