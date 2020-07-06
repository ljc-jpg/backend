package com.cloud.controller;

import com.alibaba.fastjson.JSONObject;
import com.cloud.model.EmailContent;
import com.cloud.service.OSSService;
import com.cloud.util.ResultVo;
import com.cloud.util.UploadResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.util.List;

import static com.cloud.util.RegExUtil.isEmail;

/**
 * @Author zhuz
 * @Description 文件, 邮件处理接口
 * @Date 14:51 2020/5/25
 **/
@RestController
@RequestMapping(value = "/OSS")
public class OSSController {

    private static final Logger logger = LoggerFactory.getLogger(OSSController.class);

    @Resource
    private OSSService ossService;

    /**
     * @api {POST} /OSS/uploadSingle uploadSingle
     * @apiVersion 1.0.0
     * @apiGroup OSSController
     * @apiName uploadSingle
     * @apiDescription 单个文件上传
     * @apiParam (请求参数) {Object} multipartFile 上传的文件
     * @apiParam (请求参数) {String} fileName 文件名
     * @apiParamExample 请求参数示例
     * fileName=61u3oWBn&multipartFile=null
     * @apiSuccess (响应结果) {String} originalFileName 原始文件名
     * @apiSuccess (响应结果) {String} fileExtName 文件扩展名
     * @apiSuccess (响应结果) {String} fileSavedName 文件存储名
     * @apiSuccess (响应结果) {String} fileSavedPath 文件存储相对路径
     * @apiSuccess (响应结果) {String} filePreviewPathFull 文件预览完整路径
     * @apiSuccess (响应结果) {Object} data 具体值
     * @apiSuccessExample 响应结果示例
     * {"originalFileName":"qXFuxfA","fileSavedName":"yyF0mLFf","data":{},"filePreviewPathFull":"n3Bk7O","fileSavedPath":"bBQCAzqg","fileExtName":"bjBBxcv"}
     */
    @PostMapping(value = "/uploadSingle")
    public UploadResult uploadSingle(@RequestParam(value = "multipartFile") MultipartFile multipartFile,
                                     @RequestParam(value = "fileName") String fileName) {
        UploadResult uploadResult = new UploadResult();
        try {
            if (null == multipartFile) {
                uploadResult.setMsg("multipartFile为空");
                uploadResult.setCode(ResultVo.RETURN_CODE_ERR);
                return uploadResult;
            }
            if (StringUtils.isEmpty(fileName)) {
                fileName = multipartFile.getOriginalFilename();
            }
            uploadResult = ossService.uploadSingle(fileName, "/mobilecompus/file", multipartFile.getInputStream());
        } catch (Exception e) {
            logger.error("uploadSingle error", e);
            uploadResult.setCode(ResultVo.RETURN_CODE_ERR);
            uploadResult.setMsg("上传文件异常！");
        }
        return uploadResult;
    }

    /**
     * @api {POST} /OSS/sendEmail sendEmail
     * @apiVersion 1.0.0
     * @apiGroup OSSController
     * @apiName sendEmail
     * @apiDescription 发送邮件
     * @apiParam (请求参数) {String} addressee 收件邮件
     * @apiParam (请求参数) {String} content 邮件内容
     * @apiParam (请求参数) {String} subject 邮件主题
     * @apiParam (请求参数) {Array} files 邮件附件
     * @apiParamExample 请求参数示例
     * addressee=804251123@qq.com&subject=邮件主题=http://12.pdf,http://13.pdf&content=[{\"content\":\"<div>2222<div>\",\"type\":1},{\"content\":\"https://dss2.bdstatic.com/70cFvnSh_Q1YnxGkpoWK1HF6hhy/it/u=3252521864,872614242&fm=26&gp=0.jpg\",\"type\":2},{\"content\":\"<div>333<div>\",\"type\":1}]
     * @apiSuccess (响应结果) {Object} data 具体值
     * @apiSuccessExample 响应结果示例
     * {"data":{true}}
     */
    @PostMapping(value = "/sendEmail")
    public ResultVo<Boolean> sendEmail(String addressee, String content, String subject, String[] files) {
        ResultVo result = new ResultVo();
        try {
            if (!isEmail(addressee)) {
                result.setMsg("收件邮件错误");
                result.setCode(ResultVo.RETURN_CODE_ERR);
                return result;
            }
            if (StringUtils.isEmpty(content)) {
                result.setMsg("邮件内容为空");
                result.setCode(ResultVo.RETURN_CODE_ERR);
                return result;
            }
            if (StringUtils.isEmpty(subject)) {
                result.setMsg("邮件主题为空");
                result.setCode(ResultVo.RETURN_CODE_ERR);
                return result;
            }
            List<EmailContent> contents = JSONObject.parseArray(content, EmailContent.class);
            ossService.sendEmail(addressee, contents, subject, files);
            result.setData(true);
        } catch (Exception e) {
            result.setData(false);
            logger.error("uploadSingle error", e);
            result.setCode(ResultVo.RETURN_CODE_ERR);
            result.setMsg("发送邮件异常！" + e);
        }
        return result;
    }


}
