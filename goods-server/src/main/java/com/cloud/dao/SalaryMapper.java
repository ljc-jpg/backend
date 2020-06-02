package com.cloud.dao;


import com.cloud.model.Salary;
import com.cloud.mybatis.CrudMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;


@Repository
public interface SalaryMapper extends CrudMapper<Salary> {

    void updatePathById(Integer salaryId, String filePreviewPathFull);

    List<Salary> selectSalaryByMap(Map<String, Object> searchMap);
}