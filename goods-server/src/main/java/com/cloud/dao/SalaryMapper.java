package com.cloud.dao;


import com.cloud.model.Salary;
import com.cloud.mybatis.CrudMapper;
import org.springframework.stereotype.Repository;


@Repository
public interface SalaryMapper extends CrudMapper<Salary> {

    void updatePathById(Integer salaryId, String filePreviewPathFull);

}