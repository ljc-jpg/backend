package com.cloud.util;

public enum RunExceptionEnum {
    SERVER_DO_ERROR("1001", "参数错误"),
    SERVER_FTP_DOWN_ERROR("1002", "下载文件失败"),
    SERVER_UPLOAD_ERROR("1003", "上传失败"),
    SERVER_DB_ERROR("1004", "数据库错误"),
    SERVER_PARAM_ERROR("1005", "服务运行错误"),
    SERVER_MQ_ERROR("1006", "mq错误"),
    SERVER_DELAY_ERROR("1007", "feign延迟"),
    SERVER_OTHER_ERROR("1099", "服务器繁忙");

    private String code;

    private String msg;

    RunExceptionEnum(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public String getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

    public static RunExceptionEnum stateOfCode(String code) {
        for (RunExceptionEnum state : values()) {
            if (state.getCode().equals(code)) {
                return state;
            }
        }
        return null;
    }

}
