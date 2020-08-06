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
 * @date 2020/3/30
 */
@ColumnWidth(10)
@Table(name = "t_user")
public class User implements Serializable {

    @Id
    @ExcelIgnore
    /** 用户id */
    private String userId;

    /** 登录名 */
    @ColumnWidth(15)
    @ExcelProperty("登录名")
    private String loginName;

    /** 姓名 */
    @ExcelProperty("姓名")
    private String fullName;

    /** 性别 1男 0女 */
    @ExcelProperty("性别")
    private String gender;

    /** 用户角色 T 教职工 S学生 P 家长 D默认用户 */
    @ColumnWidth(12)
    @ExcelProperty("用户角色")
    private String userType;

    /** 手机号 */
    @ColumnWidth(15)
    @ExcelProperty("手机号")
    private String mobile;

    /** 1正常 0 删除 */
    @ExcelIgnore
    private Integer enabled = 1;

    /** 管理员 1是0否 */
    @ExcelIgnore
    private Integer isAdmin;

    /** 密码 */
    @ExcelIgnore
    private String psw;

    /** 创建时间 */
    @ColumnWidth(20)
    @ExcelProperty("创建时间")
    @DateTimeFormat("yyyy年MM月dd日HH时")
    private Date createTime;

    /** 更新时间 */
    @ColumnWidth(20)
    @ExcelProperty("更新时间")
    @DateTimeFormat("yyyy年MM月dd日HH时")
    private Date updateTime;

    /** 邮箱 */
    @ColumnWidth(20)
    @ExcelProperty("邮箱")
    private String email;

    /** 用户头像 */
    @ExcelIgnore
    private String headUrl;

    /** 学校id */
    @ExcelIgnore
    private String schId;

    /** 校区id*/
    @ExcelIgnore
    private Integer xqhId;

    public User(String userId, String gender, String loginName, String mobile, Integer enabled, String fullName, Integer isAdmin, String psw, String userType, Date createTime, Date updateTime, String email, String headUrl, String schId, Integer xqhId) {
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
        this.xqhId = xqhId;
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

    public Integer getXqhId() {
        return xqhId;
    }

    public void setXqhId(Integer xqhId) {
        this.xqhId = xqhId;
    }
}