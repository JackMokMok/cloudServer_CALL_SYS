package com.cloudapp.core.entity;

import com.cloudapp.core.constants.OnlineConstant;
import com.cloudapp.core.entity.base.Entity;

public class CallerHost implements Entity {

    private static final long serialVersionUID = 1L;

    private Integer id;

    /**
     * 场所id
     */
    private Integer customerId;

    /**
     * mac地址
     */
    private String mac;

    /**
     * 设备名称
     */
    private String name;

    /**
     * 类型名称
     */
    private String categoryName;

    /**
     * 场所名称
     */
    private String customerName;

    /**
     * 离线时间
     */
    private Long offTime;

    /**
     * 是否在线，0为离线，1为在线
     */
    private Integer online;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }

    public String getMac() {
        if(mac != null) {
            return mac.toUpperCase();
        } else {
            return null;
        }
    }

    public void setMac(String mac) {
        if(mac != null) {
            mac = mac.toUpperCase();
        }
        this.mac = mac == null ? null : mac.trim();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public Long getOffTime() {
        return offTime;
    }

    public void setOffTime(Long offTime) {
        this.offTime = offTime;
    }

    public Integer getOnline() {
        return online;
    }

    public void setOnline(Integer online) {
        this.online = online;
    }

    /**
     * 获取该设备是否在线
     */
    public String getOnlineName() {		//小于等于180秒算在线
        if (this.offTime != null && this.offTime <= 180)
            return  OnlineConstant.ONLINE.getName();
        return OnlineConstant.OFFLINE.getName();
    }
}
