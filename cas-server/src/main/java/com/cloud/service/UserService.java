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
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import java.io.IOException;
import java.util.*;

/**
 * @author zhuz
 * @date 2020/7/31
 */
@Service
public class UserService {

    private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(UserService.class);

    @Resource
    private UserMapper userMapper;

    /**
     * 根据user对象查询用户信息
     *
     * @param user
     * @return {@link List< User>}
     * @author zhuz
     * @date 2020/12/7
     */
    public List<User> selectByUser(User user) {
        LOGGER.debug("User:" + user);
        List<User> users = userMapper.selectExcelUser(user);
        return users;
    }

    /**
     * 批量导入
     *
     * @param users
     * @return
     * @author zhuz
     * @date 2020/12/7
     */
    public void insertUsers(List<User> users) {
        LOGGER.debug("users:" + users.size());
        userMapper.insertUsers(users);
    }

    /**
     * 批量导入excelUser对象数据
     *
     * @param multipartFile
     * @param userService
     * @param schId
     * @return
     * @author zhuz
     * @date 2020/12/7
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
     * user对象查询数据写入excel
     *
     * @param outputStream
     * @param user
     * @return
     * @author zhuz
     * @date 2020/12/7
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
