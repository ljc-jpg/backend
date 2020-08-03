package com.cloud.model;

import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Table(name = "t_quartz")
public class Quartz {
    @Id
    private Integer quartzId;
    private String taskName;
    private String cron;
    private String address;
    private Date createTime;
    private String phone;
    private String groupName;
    private String param;
    private Integer status;

    public Quartz(Integer quartzId, String taskName, String cron, Integer status, String address, Date createTime, String phone, String groupName, String param) {
        this.quartzId = quartzId;
        this.taskName = taskName;
        this.cron = cron;
        this.status = status;
        this.address = address;
        this.createTime = createTime;
        this.phone = phone;
        this.groupName = groupName;
        this.param = param;
    }

    public Quartz() {
        super();
    }

    public Integer getQuartzId() {
        return quartzId;
    }

    public void setQuartzId(Integer quartzId) {
        this.quartzId = quartzId;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName == null ? null : taskName.trim();
    }

    public String getCron() {
        return cron;
    }

    public void setCron(String cron) {
        this.cron = cron == null ? null : cron.trim();
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address == null ? null : address.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone == null ? null : phone.trim();
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName == null ? null : groupName.trim();
    }

    public String getParam() {
        return param;
    }

    public void setParam(String param) {
        this.param = param == null ? null : param.trim();
    }

}