package com.cloud.dao;


import com.cloud.model.Salary;
import com.cloud.mybatis.CrudMapper;
import org.springframework.stereotype.Repository;

/**
 * @author zhuz
 * @date 2020/8/3
 */

@Repository
public interface SalaryMapper extends CrudMapper<Salary> {

    /**
     * 工资单id更新工资单pdf路径
     *
     * @param salaryId
     * @param path
     * @return
     * @author zhuz
     * @date 2020/8/3
     */
    void updatePathById(Integer salaryId, String path);

}