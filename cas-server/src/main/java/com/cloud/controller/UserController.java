package com.cloud.controller;

import com.cloud.model.User;
import com.cloud.service.UserService;
import com.cloud.util.ResultVo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.net.URLEncoder;
import java.util.List;

import static com.cloud.util.ResultVo.RETURN_CODE_ERR;


/**
 * @author zhuz
 */
@RestController
@RequestMapping("/user")
public class UserController {
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Resource
    private UserService userService;

    /**
     * @api {GET} /user/selectByUser selectByUser
     * @apiVersion 1.0.0
     * @apiGroup UserController
     * @apiName selectByUser
     * @apiParam (请求参数) {String} loginName 登录名
     * @apiParam (请求参数) {String} fullName 姓名
     * @apiParam (请求参数) {String} gender 性别 1男 0女
     * @apiParam (请求参数) {String} userType 用户角色 T 教职工 S学生 P 家长 D默认用户
     * @apiParam (请求参数) {String} mobile 手机号
     * @apiParam (请求参数) {Number} isAdmin 管理员 1是0否
     * @apiParam (请求参数) {String} email 邮箱
     * @apiParam (请求参数) {String} schId 学校id
     * @apiParam (请求参数) {Number} xqhId 校区id
     * @apiParamExample 请求参数示例
     * gender=YCV8vPmSYs&
     * xqhId=3723&
     * mobile=MkWr&
     * fullName=UdJtugo&
     * isAdmin=2145&
     * schId=tqOAs&
     * loginName=YF793q8kl&
     * userType=aUd6BtP12W&
     * email=X
     */
    @GetMapping("/selectByUser")
    public ResultVo<List<User>> selectByUser(User user) {
        ResultVo resultVo = new ResultVo();
        try {
            List<User> users = userService.selectByUser(user);
            resultVo.setData(users);
        } catch (Exception e) {
            logger.error("selectByUser:", e);
            resultVo.setMsg("selectByUser:" + e);
            resultVo.setCode(RETURN_CODE_ERR);
        }
        return resultVo;
    }

    /**
     * @api {POST} /user/insertExcel insertUsers
     * @apiVersion 1.0.0
     * @apiGroup UserController
     * @apiName insertUsers
     * @apiDescription 导入学生excel数据
     * @apiParam (请求参数) {Object} multipartFile excel文件
     * @apiParam (请求参数) {String} schId 学校id
     * @apiParamExample 请求参数示例
     * schId=QMHC&
     * multipartFile=null
     * @apiSuccessExample 响应结果示例
     * {"msg":"Yk2xFy9","code":80,"data":{}}
     */
    @PostMapping(value = "/insertExcel/{multipartFile}/{schId}")
    public ResultVo<Boolean> insertUsers(@PathVariable MultipartFile multipartFile, @PathVariable String schId) {
        ResultVo resultVo = new ResultVo();
        try {
            if (null == multipartFile) {
                resultVo.setData(false);
                resultVo.setMsg("multipartFile为空");
                resultVo.setCode(RETURN_CODE_ERR);
                return resultVo;
            }
            String excelType = "xlsx";
            if (!multipartFile.getOriginalFilename().contains(excelType)) {
                resultVo.setData(false);
                resultVo.setMsg("导入的文件格式不正确，应该是excel的文件");
                return resultVo;
            }
            userService.readExcel(multipartFile, userService, schId);
            resultVo.setData(true);
        } catch (Exception e) {
            resultVo.setData(false);
            logger.error("insertUsers:", e);
            resultVo.setMsg("insertUsers:" + e);
            resultVo.setCode(RETURN_CODE_ERR);
        }
        return resultVo;
    }

    /**
     * @api {GET} /user/downloadExcel downloadExcel
     * @apiVersion 1.0.0
     * @apiGroup UserController
     * @apiName downloadExcel
     * @apiDescription user对象查询数据下载对应excel
     * @apiParam (请求参数) {String} loginName 登录名
     * @apiParam (请求参数) {String} fullName 姓名
     * @apiParam (请求参数) {String} gender 性别 1男 0女
     * @apiParam (请求参数) {String} userType 用户角色 T 教职工 S学生 P 家长 D默认用户
     * @apiParam (请求参数) {String} mobile 手机号
     * @apiParam (请求参数) {Number} isAdmin 管理员 1是0否
     * @apiParam (请求参数) {String} email 邮箱
     * @apiParam (请求参数) {String} schId 学校id
     * @apiParam (请求参数) {Number} xqhId 校区id
     * @apiParamExample 请求参数示例
     * gender=CSWRyzkr
     * &xqhId=3057&
     * mobile=Hq9Sbfn&
     * headUrl=JSdUFu2tP&
     * fullName=2y1&
     * updateTime=669536528371&
     * isAdmin=1750&
     * userId=CCDu&
     * enabled=4316&
     * schId=w&
     * psw=USxMkhyzqs&
     * createTime=2175324089636&
     * loginName=J&
     * userType=agYXGBFuO&
     * email=h0Z6aaN
     * @apiSuccessExample 响应结果示例
     * {"msg":"ZI5Yr","code":73,"data":false}
     */
    @PostMapping("downloadExcel")
    public ResultVo<Boolean> downloadExcel(HttpServletResponse response, @RequestBody User user) {
        ResultVo resultVo = new ResultVo();
        try {
            response.setCharacterEncoding("utf-8");
            String fileName = URLEncoder.encode("测试", "UTF-8");
            response.setHeader("Content-disposition", "attachment;filename=" + fileName + ".xlsx");
            userService.writeExcel(response.getOutputStream(), user);
            resultVo.setData(true);
        } catch (Exception e) {
            resultVo.setData(false);
            logger.error("downloadExcel:", e);
            resultVo.setMsg("downloadExcel:" + e);
            resultVo.setCode(RETURN_CODE_ERR);
        }
        return resultVo;
    }


}
