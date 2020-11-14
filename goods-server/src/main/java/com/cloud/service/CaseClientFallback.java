package com.cloud.service;

import com.cloud.util.ResultVo;
import com.cloud.util.UploadResult;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

/**
 * @Author zhuz
 * @Date 2020/11/9
 */
@Component
public class  CaseClientFallback implements CaseClientService{
    @Override
    public UploadResult uploadInputStream(MultipartFile multipartFile, String fileName) {

        return null;
    }

    @Override
    public ResultVo sendEmails(String addressee, String content, String subject) {
        return null;
    }
}
