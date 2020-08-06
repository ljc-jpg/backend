package com.cloud.dao;

import com.cloud.model.User;
import com.cloud.mybatis.CrudMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 *
 * @author zhuz
 * @date 2020/8/3
 */
@Repository
public interface UserMapper extends CrudMapper<User> {

    void insertUsers(List<User> list);

    List<User> selectExcelUser(User user);
}