package com.cloud.model;

import java.util.Date;

public class zxjs0101 {
    private String userId;

    private String loginName;

    private String fullName;

    private String gender;

    private String mobile;

    private Integer enabled;

    private Integer isAdmin;

    private String psw;

    private Date createTime;

    private Date updateTime;

    private String email;

    private String headUrl;

    private Integer schId;

    private Integer xqhId;

    public zxjs0101(String userId, String loginName, String fullName, String gender, String mobile, Integer enabled, Integer isAdmin, String psw, Date createTime, Date updateTime, String email, String headUrl, Integer schId, Integer xqhId) {
        this.userId = userId;
        this.loginName = loginName;
        this.fullName = fullName;
        this.gender = gender;
        this.mobile = mobile;
        this.enabled = enabled;
        this.isAdmin = isAdmin;
        this.psw = psw;
        this.createTime = createTime;
        this.updateTime = updateTime;
        this.email = email;
        this.headUrl = headUrl;
        this.schId = schId;
        this.xqhId = xqhId;
    }

    public zxjs0101() {
        super();
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId == null ? null : userId.trim();
    }

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName == null ? null : loginName.trim();
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName == null ? null : fullName.trim();
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender == null ? null : gender.trim();
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile == null ? null : mobile.trim();
    }

    public Integer getEnabled() {
        return enabled;
    }

    public void setEnabled(Integer enabled) {
        this.enabled = enabled;
    }

    public Integer getIsAdmin() {
        return isAdmin;
    }

    public void setIsAdmin(Integer isAdmin) {
        this.isAdmin = isAdmin;
    }

    public String getPsw() {
        return psw;
    }

    public void setPsw(String psw) {
        this.psw = psw == null ? null : psw.trim();
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email == null ? null : email.trim();
    }

    public String getHeadUrl() {
        return headUrl;
    }

    public void setHeadUrl(String headUrl) {
        this.headUrl = headUrl == null ? null : headUrl.trim();
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
}