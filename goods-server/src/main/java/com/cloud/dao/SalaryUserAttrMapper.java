package com.cloud.dao;


import com.cloud.model.SalaryUserAttr;
import com.cloud.mybatis.CrudMapper;

import java.util.List;
import java.util.Map;

/**
 * @author zhuz
 * @date 2020/8/3
 */
public interface SalaryUserAttrMapper extends CrudMapper<SalaryUserAttr> {

    /**
     * 指定工资单对应的时发项 扣发项
     *
     * @param map
     * @return {@link List< SalaryUserAttr>}
     * @author zhuz
     * @date 2020/8/3
     */
    List<SalaryUserAttr> selectSalaryByMap(Map<String, Integer> map);

}