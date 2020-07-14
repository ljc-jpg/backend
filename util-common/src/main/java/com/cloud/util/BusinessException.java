package com.cloud.util;

public class BusinessException extends RuntimeException {

    /**
     * 模版异常
     */
    private RunExceptionEnum runExceptionEnum;

    /**
     * 自定义异常信息
     */
    private String errMsg;

    /**
     * 带自定义异常信息的构造方法
     * @param runExceptionEnum
     * @param errMsg
     */
    public BusinessException(RunExceptionEnum runExceptionEnum, String errMsg) {
        this.runExceptionEnum = runExceptionEnum;
        this.errMsg = errMsg;
    }

    /**
     * 模版异常的构造方法
     * @param runExceptionEnum
     */
    public BusinessException(RunExceptionEnum runExceptionEnum) {
        this.runExceptionEnum = runExceptionEnum;
    }
    public RunExceptionEnum getExceptionEnums() {
        return runExceptionEnum;
    }

    public String getErrMsg() {
        return errMsg;
    }
    public void setErrMsg(String errMsg) {
        this.errMsg = errMsg;
    }

}
