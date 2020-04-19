package com.cloud.model;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Table(name = "t_salary_person")
public class SalaryPerson implements Serializable {

    @Id
    @Column(name = "person_id")
    private Integer personId;

    private Integer salaryId;

    private String userId;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date updateTime;

    private Integer status;

    @Transient
    private String name;

    private Integer lookStatus;

    private Integer confimStatus;

    @Transient
    private String fullName;

    @Transient
    private String loginName;

    @Transient
    private BigDecimal yfhj;

    @Transient
    private BigDecimal kfhj;

    @Transient
    private BigDecimal sfgz;


    @Transient
    private List<SalaryUserAttr> salaryUserAttrList;

    public Integer getPersonId() {
        return personId;
    }

    public void setPersonId(Integer personId) {
        this.personId = personId;
    }

    public Integer getSalaryId() {
        return salaryId;
    }

    public void setSalaryId(Integer salaryId) {
        this.salaryId = salaryId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId == null ? null : userId.trim();
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

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getLookStatus() {
        return lookStatus;
    }

    public void setLookStatus(Integer lookStatus) {
        this.lookStatus = lookStatus;
    }

    public Integer getConfimStatus() {
        return confimStatus;
    }

    public void setConfimStatus(Integer confimStatus) {
        this.confimStatus = confimStatus;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public List<SalaryUserAttr> getSalaryUserAttrList() {
        return salaryUserAttrList;
    }

    public void setSalaryUserAttrList(List<SalaryUserAttr> salaryUserAttrList) {
        this.salaryUserAttrList = salaryUserAttrList;
    }

    public BigDecimal getYfhj() {
        return yfhj;
    }

    public void setYfhj(BigDecimal yfhj) {
        this.yfhj = yfhj;
    }

    public BigDecimal getKfhj() {
        return kfhj;
    }

    public void setKfhj(BigDecimal kfhj) {
        this.kfhj = kfhj;
    }

    public BigDecimal getSfgz() {
        return sfgz;
    }

    public void setSfgz(BigDecimal sfgz) {
        this.sfgz = sfgz;
    }
}