package com.cloud.util;

import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * Result
 *
 * @author: fengwang
 * @date: 2016/3/31 0:17
 * @version: 1.0
 * @since: JDK 1.7
 */
public class Result implements Serializable {
    /**
     * 成功返回码
     */
    public static final byte RETURN_CODE_SUCC = 1;

    /**
     * 失败返回码
     */
    public static final byte RETURN_CODE_ERR = 0;

    /**
     * returnCode
     */
    private byte returnCode;

    /**
     * msg
     */
    private String msg;

    /**
     * data
     */
    private Object data;

    public Result() {
        this.returnCode = RETURN_CODE_SUCC;
        this.msg = StringUtils.EMPTY;
        this.data = new HashMap<String, Object>();
    }

    public Result(byte returnCode, String msg) {
        this.returnCode = returnCode;
        this.msg = msg;
    }

    public Result(byte returnCode, String msg, Map data) {
        this.returnCode = returnCode;
        this.msg = msg;
        this.data = data;
    }

    public byte getReturnCode() {
        return returnCode;
    }

    public void setReturnCode(byte returnCode) {
        this.returnCode = returnCode;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
