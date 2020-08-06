package com.cloud.utils;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.excel.exception.ExcelDataConvertException;
import com.alibaba.fastjson.JSON;
import com.cloud.model.User;
import com.cloud.service.UserService;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.cloud.util.PinyUtil.toPinyin;

/**
 * @author zhuz
 */
public class UserExcelListener extends AnalysisEventListener<User> {

    private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(UserExcelListener.class);

    /**
     * 每隔5条存储数据库，实际使用中可以3000条，然后清理list ，方便内存回收
     */
    private static final int BATCH_COUNT = 3000;

    /**
     * 假设这个是一个DAO，当然有业务逻辑这个也可以是一个service。当然如果不用存储这个对象没用。
     */
    private UserService userService;

    private String schId;

    public UserExcelListener(UserService userService, String schId) {
        super();
        this.userService = userService;
        this.schId = schId;
    }

    List<User> data = new ArrayList<>(BATCH_COUNT);

    /**
     * 每解析一行都会回调invoke()方法
     *
     * @param user    读取后的数据对象
     * @param context 内容
     */
    @Override
    public void invoke(User user, AnalysisContext context) {
        LOGGER.info("解析到一条数据:{}" + JSON.toJSONString(data));
        //读取的数据处理
        user.setCreateTime(new Date());
        user.setLoginName(toPinyin(user.getFullName()));
        user.setUserType("教师".equals(user.getUserType()) ? "T" : ("学生".equals(user.getUserType()) ? "S" : "P"));
        user.setGender("男".equals(user.getGender()) ? "1" : "0");
        user.setIsAdmin(0);
        user.setSchId(schId);
        data.add(user);
        // 达到BATCH_COUNT了，需要去存储一次数据库，防止数据几万条数据在内存，容易BOOM
        if (data.size() > BATCH_COUNT) {
            saveData();
            // 存储完成清理 list
            data.clear();
        }
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {
        //解析结束销毁不用的资源
        saveData();
        //注意不要调用data.clear(),否则getData为null
    }

    /**
     * 在转换异常 获取其他异常下会调用本接口。抛出异常则停止读取。如果这里不抛出异常则 继续读取下一行。
     *
     * @param exception
     * @param context
     * @throws Exception
     */
    @Override
    public void onException(Exception exception, AnalysisContext context) {
        LOGGER.error("解析失败，但是继续解析下一行:{}", exception.getMessage());
        // 如果要获取头的信息 配合invokeHeadMap使用
        if (exception instanceof ExcelDataConvertException) {
            ExcelDataConvertException excelDataConvertException = (ExcelDataConvertException) exception;
            LOGGER.error("第{}行，第{}列解析异常", excelDataConvertException.getRowIndex(), excelDataConvertException.getColumnIndex());
            LOGGER.error("解析异常的数据", excelDataConvertException.getCellData());
            //错误处理
        }
    }

    /**
     * 加上存储数据库
     */
    private void saveData() {
        LOGGER.info("{}条数据，开始存储数据库！" + data.size());
        userService.insertUsers(data);
        LOGGER.info("存储数据库成功！");
    }

}
