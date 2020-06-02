package com.cloud.controller;

import com.alibaba.fastjson.JSONObject;
import com.cloud.model.EmailContent;
import com.cloud.service.OSSService;
import com.cloud.utils.Result;
import com.cloud.utils.UploadResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

import static com.cloud.utils.RegExUtil.isEmail;

/**
 * @Author zhuz
 * @Description  文件,邮件处理接口
 * @Date 14:51 2020/5/25
 **/
@Api(tags = "文件,邮件处理接口")
@RestController
@RequestMapping(value = "/OSS")
public class OSSController {

    private static final Logger logger = LoggerFactory.getLogger(OSSController.class);

    @Autowired
    private OSSService ossService;

    @ApiOperation("单个文件上传")
    @PostMapping(value = "/uploadSingle")
    public UploadResult uploadSingle(@RequestParam(value = "multipartFile") MultipartFile multipartFile,
                                     @RequestParam(value = "fileName") String fileName) {
        UploadResult uploadResult = new UploadResult();
        try {
            if (null == multipartFile) {
                uploadResult.setMsg("multipartFile为空");
                uploadResult.setReturnCode(Result.RETURN_CODE_ERR);
                return uploadResult;
            }
            if (StringUtils.isEmpty(fileName)) {
                fileName = multipartFile.getOriginalFilename();
            }
            uploadResult = ossService.uploadSingle(fileName, "/mobilecompus/file", multipartFile.getInputStream());
        } catch (Exception e) {
            logger.error("uploadSingle error", e);
            uploadResult.setMsg("上传文件异常！");
        }
        return uploadResult;
    }

    @ApiOperation("发送邮件")
    @PostMapping(value = "/sendEmail")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "addressee", value = "收件邮件", paramType = "query"),
            @ApiImplicitParam(name = "content", value = "邮件内容[{\"content\":\"<div>2222<div>\",\"type\":1},{\"content\":\"https://dss2.bdstatic.com/70cFvnSh_Q1YnxGkpoWK1HF6hhy/it/u=3252521864,872614242&fm=26&gp=0.jpg\",\"type\":2},{\"content\":\"<div>333<div>\",\"type\":1}]", paramType = "query"),
            @ApiImplicitParam(name = "subject", value = "邮件主题", paramType = "query"),
            @ApiImplicitParam(name = "files", value = "邮件附件http://12.pdf,http://13.pdf", paramType = "query")

    })
    public Result sendEmail(String addressee, String content, String subject, String[] files) {
        Result result = new Result();
        try {
            if (!isEmail(addressee)) {
                result.setMsg("收件邮件错误");
                return result;
            }
            if (StringUtils.isEmpty(content)) {
                result.setMsg("邮件内容为空");
                return result;
            }
            if (StringUtils.isEmpty(subject)) {
                result.setMsg("邮件主题为空");
                return result;
            }
            List<EmailContent> contents =  JSONObject.parseArray(content,EmailContent.class);
            ossService.sendEmail(addressee, contents, subject, files);
            result.setReturnCode(Result.RETURN_CODE_SUCC);
        } catch (Exception e) {
            logger.error("uploadSingle error", e);
            result.setMsg("发送邮件异常！" + e);
        }
        return result;
    }


}
