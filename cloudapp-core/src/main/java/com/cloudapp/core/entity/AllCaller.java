package com.cloudapp.core.entity;

import com.cloudapp.core.entity.base.Entity;
import org.springframework.format.annotation.DateTimeFormat;
import java.util.Date;

public class AllCaller implements Entity {

    private static final long serialVersionUID = 1L;

    private Integer id;

    private String code;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date checkInTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Date getCheckInTime() {
        return checkInTime;
    }

    public void setCheckInTime(Date checkInTime) {
        this.checkInTime = checkInTime;
    }
}
