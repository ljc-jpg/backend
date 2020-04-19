package com.cloud.model;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.format.DateTimeFormat;
import com.alibaba.excel.annotation.write.style.ColumnWidth;

import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

/**
 * @author zhu zheng
 * @Description: 建议 index 和 name 同时用
 * @date 2020/3/30
 */
@ColumnWidth(10)
@Table(name = "t_user")
public class User implements Serializable {

    @Id
    @ExcelIgnore
    private String userId;

    @ColumnWidth(15)
    @ExcelProperty("登录名")
    private String loginName;

    @ExcelProperty("姓名")
    private String fullName;

    @ExcelProperty("性别")
    private String gender;

    @ColumnWidth(12)
    @ExcelProperty("用户角色")
    private String userType;

    @ColumnWidth(15)
    @ExcelProperty("手机号")
    private String mobile;

    @ExcelIgnore
    private Integer enabled = 1;

    @ExcelIgnore
    private Integer isAdmin;

    @ExcelIgnore
    private String psw;

    @ColumnWidth(20)
    @ExcelProperty("创建时间")
    @DateTimeFormat("yyyy年MM月dd日HH时")
    private Date createTime;

    @ColumnWidth(20)
    @ExcelProperty("更新时间")
    @DateTimeFormat("yyyy年MM月dd日HH时")
    private Date updateTime;

    @ColumnWidth(20)
    @ExcelProperty("邮箱")
    private String email;

    @ExcelIgnore
    private String headUrl;

    @ExcelIgnore
    private String schId;

    @ExcelIgnore
    private Integer platformType;

    public User(String userId, String gender, String loginName, String mobile, Integer enabled, String fullName, Integer isAdmin, String psw, String userType, Date createTime, Date updateTime, String email,  String headUrl, String schId, Integer platformType) {
        this.userId = userId;
        this.loginName = loginName;
        this.mobile = mobile;
        this.enabled = enabled;
        this.fullName = fullName;
        this.isAdmin = isAdmin;
        this.psw = psw;
        this.userType = userType;
        this.createTime = createTime;
        this.updateTime = updateTime;
        this.email = email;
        this.headUrl = headUrl;
        this.schId = schId;
        this.platformType = platformType;
        this.gender = gender;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public User() {
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

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName == null ? null : fullName.trim();
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

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType == null ? null : userType.trim();
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

    public String getSchId() {
        return schId;
    }

    public void setSchId(String schId) {
        this.schId = schId;
    }

    public Integer getPlatformType() {
        return platformType;
    }

    public void setPlatformType(Integer platformType) {
        this.platformType = platformType;
    }
}