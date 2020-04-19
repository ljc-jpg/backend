package com.cloud.dao;


import com.cloud.model.SalaryUserAttr;
import com.cloud.mybatis.CrudMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface SalaryUserAttrMapper extends CrudMapper<SalaryUserAttr> {

    List<SalaryUserAttr> selectSalaryByMap(Map<String, Object> map);

    SalaryUserAttr selectSalaryByUserId(Map<String, Object> searchMap);

    void updatePathById(Integer salaryId, String filePreviewPathFull);
}