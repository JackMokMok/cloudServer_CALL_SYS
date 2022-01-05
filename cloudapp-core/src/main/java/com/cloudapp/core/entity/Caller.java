package com.cloudapp.core.entity;

import com.cloudapp.core.entity.base.Entity;

public class Caller implements Entity {

    private static final long serialVersionUID = 1L;

    private Integer id;

    /**
     * mac地址
     */
    private String mac;

    /**
     * 呼叫器分组ID
     */
    private Integer groupId;

    /**
     * 呼叫器类别
     */
    private Integer categoryId;

    /**
     * 呼叫器位置号
     */
    private String name;

    /**
     * 呼叫器编码
     */
    private String code;

    /**
     * 呼叫主机名称
     */
    private String hostName;

    /**
     * 对接电视的mac地址
     */
    private String tvMac;

    /**
     * 呼叫器类型
     */
    private String categoryName;

    /**
     * 对接场所的名称
     */
    private String customerName;

    private Integer customerId;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public Integer getGroupId() {
        return groupId;
    }

    public void setGroupId(Integer groupId) {
        this.groupId = groupId;
    }

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getHostName() {
        return hostName;
    }

    public void setHostName(String hostName) {
        this.hostName = hostName;
    }

    public Integer getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }

    public String getTvMac() {
        return tvMac;
    }

    public void setTvMac(String tvMac) {
        this.tvMac = tvMac;
    }
}
