package com.cloud.utils.wechat;

/**
 * @author zhuz
 * @date 2020/8/3
 */
public class WxTemplate {
    private String openId;
    private String url;
    private String first;
    /**
     * 学校
     */
    private String schoolName;
    /**
     * 通知人
     */
    private String user;
    /**
     * 时间
     */
    private String time;
    /**
     * 通知内容
     */
    private String content;

    private String remark;
    /**
     * 模板id
     */
    private String templateId;
    private String schId;

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getFirst() {
        return first;
    }

    public void setFirst(String first) {
        this.first = first;
    }

    public String getSchoolName() {
        return schoolName;
    }

    public void setSchoolName(String schoolName) {
        this.schoolName = schoolName;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getTemplateId() {
        return templateId;
    }

    public void setTemplateId(String templateId) {
        this.templateId = templateId;
    }

    public String getSchId() {
        return schId;
    }

    public void setSchId(String schId) {
        this.schId = schId;
    }

}
