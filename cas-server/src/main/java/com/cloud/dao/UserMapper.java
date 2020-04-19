package com.cloud.dao;

import com.cloud.model.User;
import com.cloud.mybatis.CrudMapper;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface UserMapper extends CrudMapper<User> {

    void insertUsers(List<User> list);

    List<User> selectExcelUser(User user);
}