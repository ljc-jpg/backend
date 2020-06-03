package com.cloud.controller;

import com.cloud.service.SalaryService;
import com.cloud.utils.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.io.OutputStream;

import static com.cloud.utils.RegExUtil.isEmail;


/**
 * @Description:
 * @Author: zhuzheng
 * @Date: 2019/12/11 17:57
 */

@RestController
@RequestMapping(value = "/salaryUserAttr")
@Api(value = "工资单人员明细接口")
public class SalaryController {

    private static final Logger log = LoggerFactory.getLogger(SalaryController.class);

    @Autowired
    private SalaryService salaryService;

    @ApiOperation(value = "阅览pdf zhuzheng")
    @GetMapping(value = "/loadSalaryPdf/{salaryId}/{schId}")
    public Result loadSalaryPdf(@PathVariable Integer salaryId, @PathVariable Integer schId) {
        Result result = new Result();
        OutputStream o = null;
        try {
            if (isEmpty(salaryId, schId, result)) return result;
            String path = salaryService.loadSalaryPdf(o, salaryId, schId);
            result.setData(path);
            return result;
        } catch (Exception e) {
            result.setMsg("loadSalaryPdf" + e);
            log.error("loadSalaryPdf", e);
            result.setReturnCode(Result.RETURN_CODE_ERR);
        } finally {
            if (null != o) {
                try {
                    o.close();
                } catch (IOException e) {
                    result.setMsg("loadSalaryPdf" + e);
                    log.error("loadSalaryPdf", e);
                    result.setReturnCode(Result.RETURN_CODE_ERR);
                }
            }
        }
        return result;
    }

    @ApiOperation(value = "发指定老师的邮件")
    @GetMapping("/sendSalaryEmail")
    public Result sendSalaryEmail(String addressee, Integer salaryId, Integer schId) {
        Result result = new Result();
        try {
            if (isEmpty(salaryId, schId, result)) return result;
            if (StringUtils.isEmpty(addressee) || (!isEmail(addressee))) {
                result.setMsg("收件人邮箱有问题");
                result.setReturnCode(Result.RETURN_CODE_ERR);
                return result;
            }
            salaryService.sendSalaryEmail(addressee, salaryId, schId);
        } catch (Exception e) {
            log.error("updateConfirm", e);
            result.setMsg("updateConfirm" + e);
            result.setReturnCode(Result.RETURN_CODE_ERR);
        }
        return result;
    }

    private boolean isEmpty(Integer salaryId, Integer schId, Result result) {
        if (null == salaryId) {
            result.setMsg("salaryId为空");
            result.setReturnCode(Result.RETURN_CODE_ERR);
            return true;
        }
        if (null == schId) {
            result.setMsg("schId为空");
            result.setReturnCode(Result.RETURN_CODE_ERR);
            return true;
        }
        return false;
    }

}