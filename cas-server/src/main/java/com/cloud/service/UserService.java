package com.cloud.service;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelReader;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.read.metadata.ReadSheet;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.cloud.dao.UserMapper;
import com.cloud.model.User;
import com.cloud.utils.UserExcelListener;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import java.io.IOException;
import java.util.*;

@Service
public class UserService {

    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(UserService.class);

    @Resource
    private UserMapper userMapper;

    /**
     * @param user
     * @Description: 根据user对象重新数据
     * @author zhu zheng
     * @date 2020/4/1
     */
    public List<User> selectByUser(User user) {
        logger.debug("User:" + user);
        List<User> users = userMapper.select(user);
        return users;
    }

    /**
     * @param users
     * @Description:将List<User>批量插入
     * @author zhu zheng
     * @date 2020/4/1
     */
    public void insertUsers(List<User> users) {
        logger.debug("users:" + users.size());
        userMapper.insertUsers(users);
    }

    /**
     * @param schId loginName
     * @Description: 根据schId loginName查询单个User对象
     * @author zhu zheng
     * @date 2020/4/1
     */
    public User selectByLoginName(String loginName, String schId) {
        User user = new User();
        user.setLoginName(loginName);
        user.setSchId(schId);
        List<User> users = userMapper.select(user);
        if (!CollectionUtils.isEmpty(users)) {
            return users.get(0);
        }
        return null;
    }

    /**
     * @param userService multipartFile
     * @Description: 根据UserExcelListener批量导入User对象的excel数据
     * @author zhu zheng
     * @date 2020/4/1
     */
    public void readExcel(MultipartFile multipartFile, UserService userService, String schId) throws IOException {
        ExcelReader excelReader = EasyExcel.read(multipartFile.getInputStream(), User.class,
                new UserExcelListener(userService, schId)).build();
        //读取第一个表格
        ReadSheet readSheet = EasyExcel.readSheet(0).build();
        //设置表头1行  默认会根据User来解析默认1行
        readSheet.setHeadRowNumber(1);
        excelReader.read(readSheet);
        // 这里千万别忘记关闭，读的时候会创建临时文件，到时磁盘会崩的
        excelReader.finish();
    }

    /**
     * @param user outputStream
     * @Description: 根据user对象查询数据并且写入excel
     * @author zhu zheng
     * @date 2020/4/1
     */
    public void writeExcel(ServletOutputStream outputStream, User user) {
        List<User> users = userMapper.selectExcelUser(user);
        //指定用哪个class去写
        ExcelWriter excelWriter = EasyExcel.write(outputStream, User.class).build();
        //指定第几个sheet  sheet名称
        WriteSheet writeSheet = EasyExcel.writerSheet(0, "模板").build();
        excelWriter.write(users, writeSheet);
        // 千万别忘记finish
        excelWriter.finish();
    }

}
