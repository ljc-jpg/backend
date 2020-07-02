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

/**
 * @Author zhuz
 * @Description 用户管理接口
 * @Date 14:51 2020/5/25
 * @Param
 **/
@RestController
@RequestMapping("/user")
public class UserController {
    private static final Logger log = LoggerFactory.getLogger(UserController.class);

    @Resource
    private UserService userService;


    /**
     * @api {GET} /user/selectByUser selectByUser
     * @apiVersion 1.0.0
     * @apiGroup UserController
     * @apiName selectByUser
     * @apiParam (请求参数) {String} userId
     * @apiParam (请求参数) {String} loginName 登录名
     * @apiParam (请求参数) {String} fullName 姓名
     * @apiParam (请求参数) {String} gender 性别 1男 0女
     * @apiParam (请求参数) {String} userType 用户角色 T 教职工 S学生 P 家长 D默认用户
     * @apiParam (请求参数) {String} mobile 手机号
     * @apiParam (请求参数) {Number} enabled 1正常 0 删除
     * @apiParam (请求参数) {Number} isAdmin 管理员 1是0否
     * @apiParam (请求参数) {String} psw 密码
     * @apiParam (请求参数) {Number} createTime 创建时间
     * @apiParam (请求参数) {Number} updateTime 更新时间
     * @apiParam (请求参数) {String} email 邮箱
     * @apiParam (请求参数) {String} headUrl 用户头像
     * @apiParam (请求参数) {String} schId 学校id
     * @apiParam (请求参数) {Number} xqhId 校区id
     * @apiParamExample 请求参数示例
     * gender=YCV8vPmSYs&xqhId=3723&mobile=MkWr&headUrl=vf6noV&fullName=UdJtugo&updateTime=3590330610873&isAdmin=2145&userId=tB&enabled=9629&schId=tqOAs&psw=NG1Y0dgLQ&createTime=1494425295578&loginName=YF793q8kl&userType=aUd6BtP12W&email=X
     * @apiSuccess (响应结果) {Number} code 默认成功 1
     * @apiSuccess (响应结果) {String} msg 消息
     * @apiSuccess (响应结果) {Array} data 具体值
     * @apiSuccess (响应结果) {String} data.userId
     * @apiSuccess (响应结果) {String} data.loginName 登录名
     * @apiSuccess (响应结果) {String} data.fullName 姓名
     * @apiSuccess (响应结果) {String} data.gender 性别 1男 0女
     * @apiSuccess (响应结果) {String} data.userType 用户角色 T 教职工 S学生 P 家长 D默认用户
     * @apiSuccess (响应结果) {String} data.mobile 手机号
     * @apiSuccess (响应结果) {Number} data.enabled 1正常 0 删除
     * @apiSuccess (响应结果) {Number} data.isAdmin 管理员 1是0否
     * @apiSuccess (响应结果) {String} data.psw 密码
     * @apiSuccess (响应结果) {Number} data.createTime 创建时间
     * @apiSuccess (响应结果) {Number} data.updateTime 更新时间
     * @apiSuccess (响应结果) {String} data.email 邮箱
     * @apiSuccess (响应结果) {String} data.headUrl 用户头像
     * @apiSuccess (响应结果) {String} data.schId 学校id
     * @apiSuccess (响应结果) {Number} data.xqhId 校区id
     * @apiSuccessExample 响应结果示例
     * {"msg":"lX","code":8,"data":[{"gender":"NIqhD","xqhId":8195,"mobile":"6MaPh","headUrl":"C","fullName":"R","updateTime":566592143294,"isAdmin":583,"userId":"zbfmbXCX","enabled":5516,"schId":"3YghhA","psw":"Vg","createTime":1372707110419,"loginName":"DYXUqIPpyo","userType":"2","email":"d"}]}
     */
    @GetMapping("/selectByUser")
    public ResultVo<List<User>> selectByUser(User user) {
        ResultVo ResultVo = new ResultVo();
        try {
            List<User> users = userService.selectByUser(user);
            ResultVo.setData(users);
        } catch (Exception e) {
            log.error("selectByUser:", e);
            ResultVo.setMsg("selectByUser:" + e);
            ResultVo.setCode(ResultVo.RETURN_CODE_ERR);
            return ResultVo;
        }
        return ResultVo;
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
     * schId=QMHC&multipartFile=null
     * @apiSuccess (响应结果) {Number} code 默认成功 1
     * @apiSuccess (响应结果) {String} msg 消息
     * @apiSuccess (响应结果) {Boolean} data 具体值
     * @apiSuccessExample 响应结果示例
     * {"msg":"Yk2xFy9","code":80,"data":{}}
     */
    @PostMapping(value = "/insertExcel")
    public ResultVo<Boolean> insertUsers(@RequestParam(value = "multipartFile") MultipartFile multipartFile,
                                         @RequestParam(value = "schId") String schId) {
        ResultVo resultVo = new ResultVo();
        try {
            if (null == multipartFile) {
                resultVo.setData(false);
                resultVo.setMsg("multipartFile为空");
                resultVo.setCode(ResultVo.RETURN_CODE_ERR);
                return resultVo;
            }
            if (!multipartFile.getOriginalFilename().contains("xlsx")) {
                resultVo.setData(false);
                resultVo.setMsg("导入的文件格式不正确，应该是*.xlsx的文件");
                return resultVo;
            }
            userService.readExcel(multipartFile, userService, schId);
            resultVo.setData(true);
        } catch (Exception e) {
            resultVo.setData(false);
            log.error("insertUsers:", e);
            resultVo.setMsg("insertUsers:" + e);
            resultVo.setCode(ResultVo.RETURN_CODE_ERR);
        }
        return resultVo;
    }

    /**
     * @api {GET} /user/downloadExcel downloadExcel
     * @apiVersion 1.0.0
     * @apiGroup UserController
     * @apiName downloadExcel
     * @apiDescription user对象查询数据下载对应excel
     * @apiParam (请求参数) {String} userId
     * @apiParam (请求参数) {String} loginName 登录名
     * @apiParam (请求参数) {String} fullName 姓名
     * @apiParam (请求参数) {String} gender 性别 1男 0女
     * @apiParam (请求参数) {String} userType 用户角色 T 教职工 S学生 P 家长 D默认用户
     * @apiParam (请求参数) {String} mobile 手机号
     * @apiParam (请求参数) {Number} enabled 1正常 0 删除
     * @apiParam (请求参数) {Number} isAdmin 管理员 1是0否
     * @apiParam (请求参数) {String} psw 密码
     * @apiParam (请求参数) {Number} createTime 创建时间
     * @apiParam (请求参数) {Number} updateTime 更新时间
     * @apiParam (请求参数) {String} email 邮箱
     * @apiParam (请求参数) {String} headUrl 用户头像
     * @apiParam (请求参数) {String} schId 学校id
     * @apiParam (请求参数) {Number} xqhId 校区id
     * @apiParamExample 请求参数示例
     * gender=CSWRyzkr&xqhId=3057&mobile=Hq9Sbfn&headUrl=JSdUFu2tP&fullName=2y1&updateTime=669536528371&isAdmin=1750&userId=CCDu&enabled=4316&schId=w&psw=USxMkhyzqs&createTime=2175324089636&loginName=J&userType=agYXGBFuO&email=h0Z6aaN
     * @apiSuccess (响应结果) {Number} code 默认成功 1
     * @apiSuccess (响应结果) {String} msg 消息
     * @apiSuccess (响应结果) {Boolean} data 具体值
     * @apiSuccessExample 响应结果示例
     * {"msg":"ZI5Yr","code":73,"data":false}
     */
    @GetMapping("downloadExcel")
    public ResultVo<Boolean> downloadExcel(HttpServletResponse response, User user) {
        ResultVo resultVo = new ResultVo();
        try {
            response.setCharacterEncoding("utf-8");
            String fileName = URLEncoder.encode("测试", "UTF-8");
            response.setHeader("Content-disposition", "attachment;filename=" + fileName + ".xlsx");
            userService.writeExcel(response.getOutputStream(), user);
            resultVo.setData(true);
        } catch (Exception e) {
            resultVo.setData(false);
            log.error("downloadExcel:", e);
            resultVo.setMsg("downloadExcel:" + e);
            resultVo.setCode(ResultVo.RETURN_CODE_ERR);
        }
        return resultVo;
    }


}
