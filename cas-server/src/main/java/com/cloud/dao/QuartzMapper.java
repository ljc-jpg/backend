package com.cloud.dao;

import com.cloud.model.Quartz;
import com.cloud.mybatis.CrudMapper;
import org.springframework.stereotype.Repository;

/**
 * @author zhuz
 * @date 2020/8/3
 */
@Repository
public interface QuartzMapper extends CrudMapper<Quartz> {

}