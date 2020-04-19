package com.cloud.controller;

import com.cloud.service.OSSService;
import com.cloud.utils.Result;
import com.cloud.utils.UploadResult;
import io.swagger.annotations.Api;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Description;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import static com.cloud.utils.RegExUtil.isEmail;

/**
 * Created by zhuzheng on 2020/10/24
 */
@Api(description = "文件 邮件处理接口")
@RestController
@RequestMapping(value = "/OSS")
public class OSSController {

    private static final Logger logger = LoggerFactory.getLogger(OSSController.class);

    @Autowired
    private OSSService ossService;

    @Description("单个文件上传")
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

    @Description("发送邮件")
    @PostMapping(value = "/sendEmail")
    public Result sendEmails(@RequestParam(value = "addressee") String addressee,
                             @RequestParam(value = "content") String content,
                             @RequestParam(value = "subject") String subject) {
        Result result = new Result();
        try {
            if (!isEmail(addressee)) {
                result.setReturnCode(Result.RETURN_CODE_ERR);
                result.setMsg("收件人邮箱地址不正确");
                return result;
            }
            Boolean flag = ossService.sendEmail(addressee, content, subject);
            if (!flag) {
                throw new Exception("发送邮箱异常");
            }
        } catch (Exception e) {
            result.setMsg("sendEmails error:" + e);
            result.setReturnCode(Result.RETURN_CODE_ERR);
            logger.error("sendEmails error:" + e);
        }
        return result;
    }


}
