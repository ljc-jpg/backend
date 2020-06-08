package com.cloud.controller;

import com.cloud.model.User;
import com.cloud.service.UserService;
import com.cloud.util.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.net.URLEncoder;
import java.util.List;

/**
 * @Author zhuz
 * @Description  用户管理接口
 * @Date 14:51 2020/5/25
 * @Param
 **/
@Api(tags = "用户管理接口")
@RestController
@RequestMapping("/user")
public class UserController {
    private static final Logger log = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;

    @ApiOperation(value = "根据user对象查询对象")
    @GetMapping("/selectByUser")
    public Result selectByUser(User user) {
        Result result = new Result();
        try {
            List<User> users = userService.selectByUser(user);
            result.setData(users);
        } catch (Exception e) {
            log.error("selectByUser:", e);
            result.setMsg("selectByUser:" + e);
            result.setReturnCode(Result.RETURN_CODE_ERR);
            return result;
        }
        return result;
    }

    @ApiOperation(value = "导入excel")
    @PostMapping(value = "/insertExcel")
    public Result insertUsers(@RequestParam(value = "multipartFile") MultipartFile multipartFile,
                              @RequestParam(value = "schId") String schId) {
        Result result = new Result();
        try {
            if (null == multipartFile) {
                result.setMsg("multipartFile为空");
                result.setReturnCode(Result.RETURN_CODE_ERR);
                return result;
            }
            if (!multipartFile.getOriginalFilename().contains("xlsx")) {
                result.setMsg("导入的文件格式不正确，应该是*.xlsx的文件");
                return result;
            }
            userService.readExcel(multipartFile, userService, schId);
        } catch (Exception e) {
            log.error("insertUsers:", e);
            result.setMsg("insertUsers:" + e);
            result.setReturnCode(Result.RETURN_CODE_ERR);
            return result;
        }
        return result;
    }

    @GetMapping("downloadExcel")
    public Result downloadExcel(HttpServletResponse response, User user) {
        Result result = new Result();
        try {
            response.setCharacterEncoding("utf-8");
            String fileName = URLEncoder.encode("测试", "UTF-8");
            response.setHeader("Content-disposition", "attachment;filename=" + fileName + ".xlsx");
            userService.writeExcel(response.getOutputStream(), user);
        } catch (Exception e) {
            log.error("downloadExcel:", e);
            result.setMsg("downloadExcel:" + e);
            result.setReturnCode(Result.RETURN_CODE_ERR);
            return result;
        }
        return result;
    }


}
