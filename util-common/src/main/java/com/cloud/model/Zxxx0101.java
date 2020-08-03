package com.cloud.model;

import java.util.Date;

/**
 * @author zhuz
 * @date 2020/7/31
 */

public class Zxxx0101 {
    /**
     *
     */
    private Integer schId;

    /**
     *
     */
    private Integer xqhId;

    /**
     * 姓名
     */
    private String fullName;

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
     * 管理员userId
     */
    private String adminUserId;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     *
     */
    private Date updateTime;

    /**
     * 1 启用 0不启用
     */
    private Integer enabled;

    private String weChatAppId;

    private String appSecret;

    public Zxxx0101(Integer schId, Integer xqhId, String fullName, String mobile, String headUrl, String email,
                    String adminUserId, String weChatAppId, String appSecret, Date createTime, Date updateTime, Integer enabled) {
        this.schId = schId;
        this.xqhId = xqhId;
        this.weChatAppId = weChatAppId;
        this.appSecret = appSecret;
        this.fullName = fullName;
        this.mobile = mobile;
        this.headUrl = headUrl;
        this.email = email;
        this.adminUserId = adminUserId;
        this.createTime = createTime;
        this.updateTime = updateTime;
        this.enabled = enabled;
    }

    public Zxxx0101() {
        super();
    }

    public Integer getSchId() {
        return schId;
    }

    public void setSchId(Integer schId) {
        this.schId = schId;
    }

    public Integer getXqhId() {
        return xqhId;
    }

    public void setXqhId(Integer xqhId) {
        this.xqhId = xqhId;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName == null ? null : fullName.trim();
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

    public String getAdminUserId() {
        return adminUserId;
    }

    public void setAdminUserId(String adminUserId) {
        this.adminUserId = adminUserId == null ? null : adminUserId.trim();
    }

    public String getWechatAppId() {
        return weChatAppId;
    }

    public void setWechatAppId(String weChatAppId) {
        this.weChatAppId = weChatAppId;
    }

    public String getAppSecret() {
        return appSecret;
    }

    public void setAppSecret(String appSecret) {
        this.appSecret = appSecret;
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
}