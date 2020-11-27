package com.cloud.controller;

import com.cloud.model.User;
import com.cloud.service.LoginService;
import com.cloud.util.ResultVo;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static com.cloud.util.ResultVo.RETURN_CODE_ERR;

/**
 * 登录管理
 *
 * @author zhuz
 * @date 2020/5/25
 **/
@RestController
@RequestMapping("/cas")
public class LoginController {

    private static final Logger logger = LoggerFactory.getLogger(LoginController.class);

    @Resource
    private LoginService loginService;

    /**
     * @api {POST} /cas/login 登录
     * @apiVersion 1.0.0
     * @apiGroup LoginController
     * @apiName login
     * @apiDescription 登录
     * @apiParam (请求参数) {String} loginName 账号
     * @apiParam (请求参数) {String} pwd 密码
     * @apiParamExample 请求参数示例
     * /cas/login/liujingkun/1
     * @apiSuccessExample 响应结果示例
     * {"data":{}}
     */
    @GetMapping("/login/{loginName}/{pwd}")
    public ResultVo login(HttpServletRequest request, HttpServletResponse response,
                          @PathVariable String loginName, @PathVariable String pwd) {
        ResultVo resultVo = new ResultVo();
        try {
            if (StringUtils.isEmpty(loginName)) {
                resultVo.setMsg("loginName为空");
                resultVo.setCode(RETURN_CODE_ERR);
                return resultVo;
            }
            if (StringUtils.isEmpty(pwd)) {
                resultVo.setMsg("pwd为空");
                resultVo.setCode(RETURN_CODE_ERR);
                return resultVo;
            }
            User user = loginService.login(request, response, loginName, pwd);
            resultVo.setData(user);
        } catch (Exception e) {
            logger.error("login:", e);
            resultVo.setMsg("login:" + e);
            resultVo.setCode(RETURN_CODE_ERR);
        }
        return resultVo;
    }

    @PostMapping("/loginOut")
    public void loginOut(HttpServletRequest request) {
        try {
            loginService.loginOut(request);
        } catch (Exception e) {
            logger.error("login:", e);
        }
    }

}
