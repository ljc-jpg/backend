package com.cloud.util;


import java.io.Serializable;

/**
 * @author zhuz
 * @date 2019/12/17 0017
 */
public class ResultVo<T> implements Serializable {

    /**
     * 失败 0
     */
    public static final byte RETURN_CODE_ERR = 0;

    /**
     * 默认成功 1
     */
    private byte code = 1;

    /**
     * 其他 3
     */
    public static final byte RETURN_OTHER = 2;

    /**
     * 消息
     */
    private String msg;

    /**
     * 具体值
     */
    private T data;

    public ResultVo(byte code, String message) {
        this.code = code;
        this.msg = message;
    }

    public ResultVo() {

    }

    public byte getCode() {
        return code;
    }

    public void setCode(byte code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
