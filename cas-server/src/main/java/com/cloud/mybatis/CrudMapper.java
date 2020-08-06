package com.cloud.mybatis;

/**
 * @desc 基础增删改查功能mapper
 *
 * @author zhc
 */
public interface CrudMapper<T> extends
        InsertMapper<T>,
        DeleteMapper<T>,
        UpdateMapper<T>,
        SelectMapper<T> {
}