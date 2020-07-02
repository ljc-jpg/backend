package com.cloud.service;


import com.cloud.util.ResultVo;
import com.cloud.util.UploadResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

@FeignClient(name = "cas-server")
public interface CaseClientService {

    @RequestMapping(method = RequestMethod.POST, value = "/OSS/uploadSingle", produces = {MediaType.APPLICATION_JSON_VALUE}, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    UploadResult uploadInputStream(@RequestPart("multipartFile") MultipartFile multipartFile, @RequestParam("fileName") String fileName);

    @RequestMapping(method = RequestMethod.POST, value = "/OSS/sendEmail")
    ResultVo sendEmails(@RequestParam(value = "addressee") String addressee, @RequestParam(value = "content") String content, @RequestParam(value = "subject") String subject);

}
