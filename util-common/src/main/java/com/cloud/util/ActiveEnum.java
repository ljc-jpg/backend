package com.cloud.util;

/**
 * 用于替换魔法值&固定意义的字符串
 *
 * @author zhuz
 * @date 2020/7/29
 */
public enum ActiveEnum {

    MINUS_ONE(-1, "-1", "字符串-1"),

    ZERO_EVENT(0, "0", "字符串0"),

    ONE_EVENT(1, "1", "字符串 1"),

    TWO_EVENT(2, "2", "字符串 2"),

    THREE_EVENT(3, "3", "字符串 2"),

    FOUR_EVENT(4, "4", "字符串 2"),

    EIGHT_EVENT(8, "8", "字符串 2"),

    COMMA_EVENT(110, ",", "代表 英文‘,’ 号"),

    ROLE_PA_EVENT(121, "P", "代表家长"),

    ROLE_STU_EVENT(120, "S", "代表学生"),

    ROLE_TEA_EVENT(122, "T", "代表教师"),

    REGION_SCH_EVENT(131, "999", "代表区域学校"),

    UN_KNOWN_EVENT(151, "unknown", "网关拦截使用"),

    USER_AGENT_EVENT(161, "User-Agent", "cookieUtil使用"),

    PRE_EVENT(171, "pre", "过滤器使用"),

    MB_EVENT(141, "1024", "文件大小换算单位");

    private int key;
    private String value;
    private String desc;

    ActiveEnum(int key, String value, String desc) {
        this.key = key;
        this.value = value;
        this.desc = desc;
    }

    public int getKey() {
        return key;
    }

    public String getValue() {
        return value;
    }

    public String getDesc() {
        return desc;
    }
}