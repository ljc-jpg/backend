package com.cloud.controller;

import com.cloud.service.LoginService;
import com.cloud.utils.Result;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Author zhuz
 * @Description  登录管理
 * @Date 14:52 2020/5/25
 * @Param
 **/
@RestController
@RequestMapping("/cas")
public class LoginController {

    private static final Logger logger = LoggerFactory.getLogger(LoginController.class);

    @Autowired
    private LoginService loginService;

    @PostMapping("/login")
    public Result login(HttpServletRequest request, HttpServletResponse response, String loginName, String pwd) {
        Result result = new Result();
        try {
            if (StringUtils.isEmpty(loginName)) {
                result.setMsg("loginName为空");
                result.setReturnCode(Result.RETURN_CODE_ERR);
                return result;
            }
            if (StringUtils.isEmpty(pwd)) {
                result.setMsg("pwd为空");
                result.setReturnCode(Result.RETURN_CODE_ERR);
                return result;
            }
            loginService.login(request, response, loginName, pwd);
        } catch (Exception e) {
            logger.error("login:", e);
            result.setMsg("login:" + e);
            result.setReturnCode(Result.RETURN_CODE_ERR);
        }
        return result;
    }


}
