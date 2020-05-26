package com.cloud.dao;

import com.cloud.model.zxjs0101;

public interface zxjs0101Mapper {
    int deleteByPrimaryKey(String userId);

    int insert(zxjs0101 record);

    int insertSelective(zxjs0101 record);

    zxjs0101 selectByPrimaryKey(String userId);

    int updateByPrimaryKeySelective(zxjs0101 record);

    int updateByPrimaryKey(zxjs0101 record);
}