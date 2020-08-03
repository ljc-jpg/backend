package com.cloud.controller;

import com.cloud.service.SalaryService;
import com.cloud.util.ResultVo;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;

import static com.cloud.util.RegExUtil.isEmail;
import static com.cloud.util.ResultVo.RETURN_CODE_ERR;


/**
 * @Description:
 * @Author: zhuzheng
 * @Date: 2019/12/11 17:57
 */

@RestController
@RequestMapping(value = "/salaryUserAttr")
public class SalaryController {

    private static final Logger logger = LoggerFactory.getLogger(SalaryController.class);

    @Resource
    private SalaryService salaryService;

    /**
     * @api {GET} /salaryUserAttr/salaryPdf/{salaryId}/{schId} salaryPdf
     * @apiVersion 1.0.0
     * @apiGroup SalaryController
     * @apiName salaryPdf
     * @apiDescription 阅览工资单对应pdf
     * @apiParam (请求参数) {Number} salaryId 工资单id
     * @apiParam (请求参数) {Number} schId 学校id
     * @apiParamExample 请求参数示例
     * schId=2658&salaryId=2299
     * @apiSuccess (响应结果) {Number} code 默认成功 1
     * @apiSuccess (响应结果) {String} msg 消息
     * @apiSuccess (响应结果) {String} data 具体值
     * @apiSuccessExample 响应结果示例
     * {"msg":"skmvPns9L","code":100,"data":"x"}
     */
    @GetMapping(value = "/salaryPdf/{salaryId}/{schId}")
    public ResultVo<String> salaryPdf(@PathVariable Integer salaryId, @PathVariable Integer schId) {
        ResultVo result = new ResultVo();
        try {
            if (null == salaryId) {
                result.setMsg("salaryId为空");
                result.setCode(RETURN_CODE_ERR);
                return result;
            }
            if (null == schId) {
                result.setMsg("schId为空");
                result.setCode(RETURN_CODE_ERR);
                return result;
            }
            String path = salaryService.loadSalaryPdf(salaryId, schId);
            result.setData(path);
        } catch (Exception e) {
            logger.error("salaryPdf", e);
            result.setMsg("salaryPdf" + e);
            result.setCode(RETURN_CODE_ERR);
        }
        return result;
    }

    /**
     * @api {GET} /salaryUserAttr/salaryEmail/{addressee}/{salaryId}/{schId} salaryEmail
     * @apiVersion 1.0.0
     * @apiGroup SalaryController
     * @apiName salaryEmail
     * @apiDescription 工资单pdf发送至邮箱
     * @apiParam (请求参数) {String} addressee 邮件地址
     * @apiParam (请求参数) {Number} salaryId 工资单id
     * @apiParam (请求参数) {Number} schId 学校id
     * @apiParamExample 请求参数示例
     * schId=4084&addressee=cW9FW&salaryId=3798
     * @apiSuccess (响应结果) {Number} code 默认成功 1
     * @apiSuccess (响应结果) {String} msg 消息
     * @apiSuccess (响应结果) {Object} data 具体值
     * @apiSuccessExample 响应结果示例
     * {"msg":"KVZAUE3","code":116,"data":{}}
     */
    @GetMapping("/salaryEmail/{addressee}/{salaryId}/{schId}")
    public ResultVo<Boolean> salaryEmail(@PathVariable String addressee, @PathVariable Integer salaryId, @PathVariable Integer schId) {
        ResultVo result = new ResultVo();
        try {
            if (null == schId) {
                result.setData(false);
                result.setMsg("schId为空");
                result.setCode(RETURN_CODE_ERR);
                return result;
            }
            if (null == salaryId) {
                result.setData(false);
                result.setMsg("salaryId为空");
                result.setCode(RETURN_CODE_ERR);
                return result;
            }
            if (StringUtils.isEmpty(addressee) || (!isEmail(addressee))) {
                result.setData(false);
                result.setMsg("收件人邮箱有问题");
                result.setCode(RETURN_CODE_ERR);
                return result;
            }
            salaryService.sendSalaryEmail(addressee, salaryId, schId);
            result.setData(true);
        } catch (Exception e) {
            result.setData(false);
            logger.error("salaryEmail", e);
            result.setMsg("salaryEmail" + e);
            result.setCode(RETURN_CODE_ERR);
        }
        return result;
    }

}