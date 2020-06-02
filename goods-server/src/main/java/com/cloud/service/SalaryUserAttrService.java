package com.cloud.service;

import com.cloud.dao.SalaryUserAttrMapper;
import com.cloud.model.SalaryUserAttr;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class SalaryUserAttrService {

    private static final Logger log = LoggerFactory.getLogger(SalaryService.class);

    @Autowired
    private SalaryUserAttrMapper salaryUserAttrMapper;

    public List<SalaryUserAttr> selectSalaryByMap(Map<String, Integer> searchMap) {
        log.info("searchMap:" + searchMap);
        return salaryUserAttrMapper.selectSalaryByMap(searchMap);
    }


}
