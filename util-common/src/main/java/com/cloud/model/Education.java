package com.cloud.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * @author zhuz
 * @date 2020/12/7
 */
@Table(name = "table_education")
public class Education {

    @Id
    private Integer educationId;

    private String educationName;

    /**
     * 手机号
     */
    private String mobile;

    /**
     * 头像地址
     */
    private String headUrl;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 姓名
     */
    private String fullName;

    private String adminUserId;

    /**
     * DateTimeFormat 接收处理   JsonFormat 返回格式化
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;

    /**
     * 1 启用 0不启用
     */
    private Integer enabled;

    /**
     * 学校公众号appid
     */
    private String wechatAppId;

    private String appSecret;

    public Education(Integer educationId, String educationName, String mobile, String headUrl, String email, String fullName, String adminUserId, Date createTime, Date updateTime, Integer enabled, String wechatAppId, String appSecret) {
        this.educationId = educationId;
        this.educationName = educationName;
        this.mobile = mobile;
        this.headUrl = headUrl;
        this.email = email;
        this.fullName = fullName;
        this.adminUserId = adminUserId;
        this.createTime = createTime;
        this.updateTime = updateTime;
        this.enabled = enabled;
        this.wechatAppId = wechatAppId;
        this.appSecret = appSecret;
    }

    public Education() {
        super();
    }

    public Integer getEducationId() {
        return educationId;
    }

    public void setEducationId(Integer educationId) {
        this.educationId = educationId;
    }

    public String getEducationName() {
        return educationName;
    }

    public void setEducationName(String educationName) {
        this.educationName = educationName == null ? null : educationName.trim();
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile == null ? null : mobile.trim();
    }

    public String getHeadUrl() {
        return headUrl;
    }

    public void setHeadUrl(String headUrl) {
        this.headUrl = headUrl == null ? null : headUrl.trim();
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email == null ? null : email.trim();
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName == null ? null : fullName.trim();
    }

    public String getAdminUserId() {
        return adminUserId;
    }

    public void setAdminUserId(String adminUserId) {
        this.adminUserId = adminUserId == null ? null : adminUserId.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Integer getEnabled() {
        return enabled;
    }

    public void setEnabled(Integer enabled) {
        this.enabled = enabled;
    }

    public String getWechatAppId() {
        return wechatAppId;
    }

    public void setWechatAppId(String wechatAppId) {
        this.wechatAppId = wechatAppId == null ? null : wechatAppId.trim();
    }

    public String getAppSecret() {
        return appSecret;
    }

    public void setAppSecret(String appSecret) {
        this.appSecret = appSecret == null ? null : appSecret.trim();
    }
}