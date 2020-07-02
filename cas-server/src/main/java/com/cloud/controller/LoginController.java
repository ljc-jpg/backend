package com.cloud.controller;

import com.cloud.service.LoginService;
import com.cloud.util.ResultVo;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
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

    @Resource
    private LoginService loginService;

    @PostMapping("/login")
    public ResultVo login(HttpServletRequest request, HttpServletResponse response, String loginName, String pwd) {
        ResultVo ResultVo = new ResultVo();
        try {
            if (StringUtils.isEmpty(loginName)) {
                ResultVo.setMsg("loginName为空");
                ResultVo.setCode(ResultVo.RETURN_CODE_ERR);
                return ResultVo;
            }
            if (StringUtils.isEmpty(pwd)) {
                ResultVo.setMsg("pwd为空");
                ResultVo.setCode(ResultVo.RETURN_CODE_ERR);
                return ResultVo;
            }
            loginService.login(request, response, loginName, pwd);
        } catch (Exception e) {
            logger.error("login:", e);
            ResultVo.setMsg("login:" + e);
            ResultVo.setCode(ResultVo.RETURN_CODE_ERR);
        }
        return ResultVo;
    }


}
