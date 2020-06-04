package com.cloud.controller;

import com.cloud.service.SalaryService;
import com.cloud.utils.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


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

    @ApiOperation(value = "阅览pdf")
    @GetMapping(value = "/salaryPdf/{salaryId}/{schId}")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "salaryId", value = "工资单id", paramType = "path"),
            @ApiImplicitParam(name = "schId", value = "学校id", paramType = "path")
    })
    public Result salaryPdf(@PathVariable Integer salaryId, @PathVariable Integer schId) {
        Result result = new Result();
        try {
            if (isEmpty(salaryId, schId, result)) return result;
            String path = salaryService.loadSalaryPdf(salaryId, schId);
            result.setData(path);
        } catch (Exception e) {
            result.setMsg("salaryPdf" + e);
            log.error("salaryPdf", e);
            result.setReturnCode(Result.RETURN_CODE_ERR);
        }
        return result;
    }

    @ApiOperation(value = "发指定老师的邮件")
    @GetMapping("/salaryEmail/{addressee}/{salaryId}/{schId}")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "addressee", value = "收件人邮箱", paramType = "path"),
            @ApiImplicitParam(name = "salaryId", value = "工资单id", paramType = "path"),
            @ApiImplicitParam(name = "schId", value = "学校id", paramType = "path")
    })
    public Result salaryEmail(@PathVariable String addressee, @PathVariable Integer salaryId, @PathVariable Integer schId) {
        Result result = new Result();
        try {
            if (isEmpty(salaryId, schId, result)) return result;
            if (StringUtils.isEmpty(addressee) || (!isEmail(addressee))) {
                result.setMsg("收件人邮箱有问题");
                result.setReturnCode(Result.RETURN_CODE_ERR);
                return result;
            }
            result = salaryService.sendSalaryEmail(addressee, salaryId, schId);
        } catch (Exception e) {
            log.error("salaryEmail", e);
            result.setMsg("salaryEmail" + e);
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