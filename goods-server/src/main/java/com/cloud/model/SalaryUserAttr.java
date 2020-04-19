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

@Table(name = "t_salary_user_attr")
public class SalaryUserAttr implements Serializable {

    @Id
    @Column(name = "user_attr_id")
    private Integer userAttrId;

    private String userId;

    private Integer salaryId;

    private String name;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date updateTime;

    private Integer status;

    @Transient
    private String XM;

    @Transient
    private String LOGINID;

    @Transient
    private String salaryName;

    @Transient
    private Integer type;

    private BigDecimal money;

    @Transient
    private String projectName;

    @Transient
    private Integer projectType;

    @Transient
    private List<SalaryUserAttr> salaryUserAttrs;

    @Transient
    private Integer total;

    @Transient
    private Integer[] personMoney;

    @Transient
    private SalaryPerson people;

    public SalaryPerson getPeople() {
        return people;
    }

    public void setPeople(SalaryPerson people) {
        this.people = people;
    }

    public Integer[] getPersonMoney() {
        return personMoney;
    }
    public void setPersonMoney(Integer[] personMoney) {
        this.personMoney = personMoney;
    }

    public Integer getTotal() {
        return total;
    }
    public void setTotal(Integer total) {
        this.total = total;
    }

    public List<SalaryUserAttr> getSalaryUserAttrs() {
        return salaryUserAttrs;
    }
    public void setSalaryUserAttrs(List<SalaryUserAttr> salaryUserAttrs) {
        this.salaryUserAttrs = salaryUserAttrs;
    }

    public String getXM() {
        return XM;
    }
    public void setXM(String XM) {
        this.XM = XM;
    }

    public String getLOGINID() {
        return LOGINID;
    }
    public void setLOGINID(String LOGINID) {
        this.LOGINID = LOGINID;
    }

    public String getSalaryName() {
        return salaryName;
    }
    public void setSalaryName(String salaryName) {
        this.salaryName = salaryName;
    }

    public String getProjectName() {
        return projectName;
    }
    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public Integer getProjectType() {
        return projectType;
    }
    public void setProjectType(Integer projectType) {
        this.projectType = projectType;
    }

    public Integer getUserAttrId() {
        return userAttrId;
    }
    public void setUserAttrId(Integer userAttrId) {
        this.userAttrId = userAttrId;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name == null ? null : name.trim();
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

    public BigDecimal getMoney() {
        return money;
    }
    public void setMoney(BigDecimal money) {
        this.money = money;
    }

    public Integer getType() {
        return type;
    }
    public void setType(Integer type) {
        this.type = type;
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
        this.userId = userId;
    }

}