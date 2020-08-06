package com.cloud.model;

/**
 * @author zhuz
 * @date 2020/7/31
 */
public class EmailContent {
    /**
     * 邮件内容
     */
    private String content;

    /**
     * 1代表邮件文字内容   2代表邮件里的图片
     */
    private String type;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

}
