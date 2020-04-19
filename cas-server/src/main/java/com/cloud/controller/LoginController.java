package com.cloud.controller;

import com.cloud.service.LoginService;
import com.cloud.utils.Result;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/cas")
public class LoginController {

    private static final Logger logger = LoggerFactory.getLogger(LoginController.class);

    @Autowired
    private LoginService loginService;

    @ApiOperation(value = "登录")
    @PostMapping("/login")
    public Result login(HttpServletResponse response, HttpServletRequest request, String userName, String pwd) {
        Result result = new Result();
        try {
            String token = loginService.login(response, request, userName, pwd);
            result.setData(token);
            return result;
        } catch (Exception e) {
            logger.error("login:", e);
            result.setMsg("login:" + e);
            result.setReturnCode(Result.RETURN_CODE_ERR);
        }
        return result;
    }


}
