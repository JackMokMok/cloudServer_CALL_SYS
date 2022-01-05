package com.cloudapp.core.entity;

import com.cloudapp.core.entity.base.Entity;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

public class ExpandHeartbeat implements Entity {

    private static final long serialVersionUID = 1L;

    private Integer id;

    /**
     * mac地址
     */
    private String mac;

    /**
     * 最近心跳时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date hTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date gethTime() {
        return hTime;
    }

    public void sethTime(Date hTime) {
        this.hTime = hTime;
    }

    public String getMac() {
        return mac;
    }

    public void setMac(String mac) {
        this.mac = mac;
    }

}
