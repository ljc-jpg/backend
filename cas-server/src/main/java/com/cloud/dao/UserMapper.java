package com.cloud.dao;

import com.cloud.model.User;
import com.cloud.mybatis.CrudMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author zhuz
 * @date 2020/8/3
 */
public interface UserMapper extends CrudMapper<User> {

    void insertUsers(List<User> list);

    List<User> selectExcelUser(User user);

    User selectByLoginName(@Param("loginName") String loginName, @Param("psd") String psd);
}