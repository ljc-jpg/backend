package com.cloud.service;


import com.cloud.util.ResultVo;
import com.cloud.util.UploadResult;
import org.apache.ibatis.annotations.Param;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

/**
 * zhuz
 *
 * @author zhuz
 * @date 2020/8/3
 */
@FeignClient(name = "cas-server" , fallback = CaseClientFallback.class)
public interface CaseClientService {

    /**
     * feign调用文件上传接口
     *
     * @param multipartFile
     * @param fileName
     * @return {@link UploadResult}
     * @author zhuz
     * @date 2020/8/3
     */
    @RequestMapping(method = RequestMethod.POST, value = "/OSS/uploadSingle", produces = {MediaType.APPLICATION_JSON_VALUE}, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    UploadResult uploadInputStream(@RequestPart("multipartFile") MultipartFile multipartFile, @RequestParam("fileName") String fileName);

    /**
     * feign调用发送邮件接口
     *
     * @param addressee
     * @param content
     * @param subject
     * @return {@link ResultVo}
     * @author zhuz
     * @date 2020/8/3
     */
    @RequestMapping(method = RequestMethod.POST, value = "/OSS/sendEmail")
    ResultVo sendEmails(@RequestParam(value = "addressee") String addressee, @RequestParam(value = "content") String content, @RequestParam(value = "subject") String subject);

    @RequestMapping(method = RequestMethod.GET, value = "/user/selectByUser")
    ResultVo selectByUser(@RequestParam("userId") String userId);

}
