package com.cloud.dao;

import com.cloud.model.ZZJS0101;

/**
 * @author zhuz
 */
public interface ZXJS0101Mapper {
    int deleteByPrimaryKey(String userId);

    int insert(ZZJS0101 record);

    int insertSelective(ZZJS0101 record);

    ZZJS0101 selectByPrimaryKey(String userId);

    int updateByPrimaryKeySelective(ZZJS0101 record);

    int updateByPrimaryKey(ZZJS0101 record);
}